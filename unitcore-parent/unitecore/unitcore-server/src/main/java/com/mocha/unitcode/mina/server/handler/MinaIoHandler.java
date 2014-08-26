package com.mocha.unitcode.mina.server.handler;

import static com.mocha.unitcode.mina.server.MinaServer.MSG_COUNT;
import static com.mocha.unitcode.mina.server.MinaServer.OPEN;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.MinaPDU;
import com.mocha.unitcode.mina.pdu.TodoEntityMessage;
import com.mocha.unitcode.mina.server.thread.ActiveThread;
import com.mocha.unitcode.mina.server.thread.SendMoThread;

/**
 * 
 * <strong>Title : MinaIoHandler </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:57:13 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * 
 * @author 刘军 liujun1@mochasoft.com.cn <br>
 * @version <strong>Mocha JavaOA v7.0.0</strong> <br>
 * <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public class MinaIoHandler extends IoHandlerAdapter {
	public static AtomicInteger received = new AtomicInteger(0);
	public static AtomicInteger closed = new AtomicInteger(0);
	public static Map<String, IoSession> map = new HashMap<String, IoSession>();
	private final Object LOCK;
	public static boolean Connect = false;
	public static boolean Firstmsg = true;
	private ExecutorService exec = Executors.newSingleThreadExecutor();

	public MinaIoHandler(Object lock) {
		LOCK = lock;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		if (!(cause instanceof IOException)) {
			Logger.getLogger(MinaConstant.LOG_ERROR)
					.error("Exception: ", cause);
		} else {
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(
					"I/O error: " + cause.getMessage());
		}
		session.close(true);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"Session " + session.getId() + " is opened");
		Thread t = new Thread(new ActiveThread(session));
		t.setDaemon(true);
		t.start();
		session.resumeRead();

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"Creation of session " + session.getId());
		session.setAttribute(OPEN);
		session.suspendRead();

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		session.removeAttribute(OPEN);
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"{}> Session closed" + session.getId());
		final int clsd = closed.incrementAndGet();

		if (clsd == MSG_COUNT) {
			synchronized (LOCK) {
				LOCK.notifyAll();
			}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("==========messageReceived================="
				+ message.toString().getBytes().length);
		MinaPDU pdu = (MinaPDU) message;
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"MESSAGE: " + pdu.header.getCommandId() + ":"
						+ pdu.header.getSequenceNumber() + "on session "
						+ session.getId());
		map.put(String.valueOf(pdu.header.getCommandId()), session);
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"map=" + map.get(String.valueOf(pdu.header.getCommandId())));
		workByEntry(map);
		final int rec = received.incrementAndGet();
		if (Firstmsg == true || Connect == true) {
			Firstmsg = false;
			switch (pdu.header.getCommandId()) {
			case MinaConstant.UNITE_CONNECT:
				com.mocha.unitcode.mina.pdu.Connect con = (com.mocha.unitcode.mina.pdu.Connect) pdu;
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"com.mocha.unitcode.mina.pdu.Connect   " + con.dump());
				com.mocha.unitcode.mina.pdu.ConnectResp conresp = (com.mocha.unitcode.mina.pdu.ConnectResp) con
						.getResponse();
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"UNITE_CONNECT   " + conresp.dump());
				session.write(conresp);
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"conresp:" + pdu.header.getSequenceNumber()
								+ " on session " + session.getId());
				Connect = true;
				exec.execute(new SendMoThread(session));
				break;
			case MinaConstant.UNITE_ACTIVE_TEST:
				com.mocha.unitcode.mina.pdu.ActiveTest activeTest = (com.mocha.unitcode.mina.pdu.ActiveTest) pdu;
				com.mocha.unitcode.mina.pdu.ActiveTestResp activeTestResp = (com.mocha.unitcode.mina.pdu.ActiveTestResp) activeTest
						.getResponse();
				session.write(activeTestResp);
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"active_test:" + pdu.header.getSequenceNumber()
								+ " on session " + session.getId());
				break;
			case MinaConstant.UNITE_ACTIVE_TEST_RESP:
				com.mocha.unitcode.mina.pdu.ActiveTestResp activeTestRsp = (com.mocha.unitcode.mina.pdu.ActiveTestResp) pdu;
				pdu.dump();
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"activeTestRsp:" + pdu.header.getSequenceNumber()
								+ " on session " + session.getId());
				ActiveThread.lastActiveTime = System.currentTimeMillis();
				break;
			case MinaConstant.UNITE_TODO_SUBMIT:
				Logger.getLogger(MinaConstant.LOG_TODO).warn(
						"MinaConstant.UNITE_TODO_SUBMIT Header: "
								+ MinaConstant.UNITE_TODO_SUBMIT);
				@SuppressWarnings("rawtypes")
				com.mocha.unitcode.mina.pdu.SubmitTodoEntity submitItem = (com.mocha.unitcode.mina.pdu.SubmitTodoEntity) pdu;
				@SuppressWarnings("rawtypes")
				TodoEntityMessage tem = submitItem.getEntityMessage();
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"====UNITE_TODO_SUBMIT=======getEntityMessage============="
								+ tem.getEntityMessage());
				 Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				 "====UNITE_TODO_SUBMIT=======getClazz============="
				 + new String(tem.getClazz()));
//				Object todoEntity = tem.getTodoEntity();
				 //判断单条还是批量传输
				if (tem.getType()==tem.TODO_TYPE_SINGLE) {
					
					
				}else if (tem.getType()==tem.TODO_TYPE_BATCH) {
					
				}
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"====UNITE_TODO_SUBMIT=======getMsgFormat============="
								+ tem.getMsgFormat());
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"=====UNITE_TODO_SUBMIT======getEncoding============="
								+ tem.getEncoding());
				// Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				// "====UNITE_TODO_SUBMIT=======TodoEntity============="
				// + todoEntity.getItemTitle());
				submitItem.dump();
				com.mocha.unitcode.mina.pdu.SubmitTodoEntityResp subItemresp = (com.mocha.unitcode.mina.pdu.SubmitTodoEntityResp) submitItem
						.getResponse();
				subItemresp.setMsgId(com.mocha.unitcode.mina.pdu.Tools
						.GetRspid());
				session.write(subItemresp);
				break;
			default:
				Logger.getLogger(MinaConstant.LOG_ERROR).warn(
						"Unexpected PDU received! PDU Header: "
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

	public static void workByEntry(Map<String, IoSession> map) {
		Set<Map.Entry<String, IoSession>> set = map.entrySet();
		for (Iterator<Map.Entry<String, IoSession>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, IoSession> entry = (Map.Entry<String, IoSession>) it
					.next();
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(
					"=======workByEntry===================" + entry.getKey()
							+ "--->" + entry.getValue());
		}
	}
}
