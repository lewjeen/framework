package com.mocha.unitcode.mina.client.thread;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.tydb_common.entity.TodoEntity;
import com.mocha.unitcode.mina.pdu.ActiveTest;
import com.mocha.unitcode.mina.pdu.SubmitTodoEntity;
import com.mocha.unitcode.mina.pdu.TodoEntityMessage;
import com.mocha.unitcode.mina.pdu.Tools;

public class ActiveThread extends Thread {
	private IoSession session = null;
	private static final Logger logger = LoggerFactory
			.getLogger(ActiveThread.class);
	private long heartbeatInterval = 60000;
	private long heartbeatRetry = 3;
	private long reconnectInterval = 10000;
	public static long lastActiveTime = 0;
	private long lastCheckTime = 0;

	public ActiveThread(IoSession s) {
		setDaemon(true);
		this.session = s;
		lastCheckTime = System.currentTimeMillis();
		lastActiveTime = System.currentTimeMillis();
	}

	public void run() {
		try {
			while (session.isConnected()) {
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastCheckTime) > heartbeatInterval) {
					logger.info("CmppSession.checkConnection");
					if ((currentTime - lastActiveTime) < (heartbeatInterval * heartbeatRetry)) {
						logger.info("send ActiveTest");
						lastCheckTime = currentTime;
						ActiveTest activeTest = new ActiveTest();
						activeTest.assignSequenceNumber();
						activeTest.timeStamp = currentTime;
						session.write(activeTest);
					} else {
						logger.info("connection lost!");
						session.close(true);
						break;
					}
				}
				try {
					Thread.sleep(reconnectInterval);
				} catch (InterruptedException e) {
					//
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void test1() {
		// TODO Auto-generated method stub
		byte[] msgid = Tools.GetMsgid();
		SubmitTodoEntity submitItem = new SubmitTodoEntity();
		TodoEntity todo = new TodoEntity();
		submitItem.setMsgId(msgid);
		todo.setAppItemId("ascvv");
		String title = "多应用系统应用的都不是标准的web service或XML等";
		todo.setItemTitle(title);
		TodoEntityMessage tm = new TodoEntityMessage();
		// tm.setEntityMessage(todo, (byte) 15);
		submitItem.setEntityMessage(tm);
		session.write(submitItem);
	}
}
