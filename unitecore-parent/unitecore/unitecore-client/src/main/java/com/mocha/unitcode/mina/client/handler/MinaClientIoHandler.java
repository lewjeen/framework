package com.mocha.unitcode.mina.client.handler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.unitcode.mina.client.CoreClientFactory;
import com.mocha.unitcode.mina.client.thread.ActiveThread;
import com.mocha.unitcode.mina.client.thread.MsgSendThread;
import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.ActiveTest;
import com.mocha.unitcode.mina.pdu.ActiveTestResp;
import com.mocha.unitcode.mina.pdu.Connect;
import com.mocha.unitcode.mina.pdu.ConnectResp;
import com.mocha.unitcode.mina.pdu.MinaPDU;

/**
 * TODO: Document me !
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public class MinaClientIoHandler extends IoHandlerAdapter {
	private static final Logger logger = LoggerFactory
			.getLogger(MinaClientIoHandler.class);
	public static AtomicInteger received = new AtomicInteger(0);
	public static AtomicInteger closed = new AtomicInteger(0);
	private ExecutorService exec = Executors.newSingleThreadExecutor();
	private final Object LOCK;
	public static boolean Connect = false;
	public static boolean Firstmsg = true;
	public static final int MSG_SIZE = 5000;
	public static final int MSG_COUNT = 10;
	public static final String OPEN = "open";

	public MinaClientIoHandler(Object lock) {
		LOCK = lock;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		if (!(cause instanceof IOException)) {
			logger.error("Exception: ", cause);
		} else {
			logger.info("I/O error: " + cause.getMessage());
		}
		// session.close(true);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.info("Session " + session.getId() + " is opened");
		// Connect(session);
		Thread t = new Thread(new ActiveThread(session));
		t.setDaemon(true);
		// t.start();
		session.resumeRead();
	}

	public void Connect(IoSession session) {
		Connect request = new Connect(MinaConstant.TRANSMITTER);
		request.setClientId("liujun");
		request.setSharedSecret("password");
		request.setTimeStamp(request.genTimeStamp());
		request.setAuthClient(request.genAuthClient());
		request.setVersion((byte) 0x30);
		request.assignSequenceNumber();
		logger.info("Connect: " + request.getData().getHexDump());
		logger.info(request.dump());
		session.write(request);
		exec.execute(new MsgSendThread(session));
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.info("Creation of session " + session.getId());
		session.setAttribute(OPEN);
		session.suspendRead();

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.removeAttribute(OPEN);
		CoreClientFactory.resetClient();
		logger.info("{}> Session closed", session.getId());
		final int clsd = closed.incrementAndGet();

		if (clsd == MSG_COUNT) {
			synchronized (LOCK) {
				LOCK.notifyAll();
			}
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		MinaPDU pdu = (MinaPDU) message;
		logger.info("MESSAGE: " + pdu.header.getCommandId() + ":"
				+ pdu.header.getSequenceNumber() + "on session "
				+ session.getId());
		final int rec = received.incrementAndGet();
		if (Firstmsg == true || Connect == true) {
			Firstmsg = false;
			switch (pdu.header.getCommandId()) {
			case MinaConstant.UNITE_CONNECT_RESP:
				ConnectResp conrsp = (ConnectResp) pdu;
				pdu.dump();
				logger.info("conresp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				if (conrsp.getStatus() == 0) {
					Connect = true;
				} else {
					Connect = false;
					session.close(true);
				}
				break;
			case MinaConstant.UNITE_TODO_SUBMIT_RESP:
				com.mocha.unitcode.mina.pdu.SubmitTodoEntityResp subItemresp = (com.mocha.unitcode.mina.pdu.SubmitTodoEntityResp) pdu;
				subItemresp.dump();
				logger.info("subItemresp:" + subItemresp.dump());
				logger.info("subItemresp:"
						+ subItemresp.header.getSequenceNumber()
						+ " on session " + session.getId());
				break;
			case MinaConstant.UNITE_ACTIVE_TEST_RESP:
				ActiveTestResp activeTestRsp = (ActiveTestResp) pdu;
				pdu.dump();
				logger.info("activeTestRsp:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				ActiveThread.lastActiveTime = System.currentTimeMillis();
				break;
			case MinaConstant.UNITE_ACTIVE_TEST:
				ActiveTest activeTest = (ActiveTest) pdu;
				ActiveTestResp activeTestResp = (ActiveTestResp) activeTest
						.getResponse();
				session.write(activeTestResp);
				logger.info("active_test:" + pdu.header.getSequenceNumber()
						+ " on session " + session.getId());
				break;
			default:
				logger.warn("Unexpected PDU received! PDU Header: "
						+ pdu.header.getData().getHexDump());
				break;
			}
		}
		if (rec == MSG_COUNT) {
			synchronized (LOCK) {
				LOCK.notifyAll();
			}
		}

		// session.close(true);
	}

}
