package com.mocha.unitcode.mina.server.thread;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.ActiveTest;

/**
 * 
 * <strong>Title : ActiveThread </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 下午3:33:33 </strong>. <br>
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
public class ActiveThread extends Thread {
	private IoSession session = null;
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
					Logger.getLogger(MinaConstant.LOG_ACTIVE).info(
							"CmppSession.checkConnection");
					if ((currentTime - lastActiveTime) < (heartbeatInterval * heartbeatRetry)) {
						Logger.getLogger(MinaConstant.LOG_ACTIVE).info(
								"send ActiveTest");
						lastCheckTime = currentTime;
						ActiveTest activeTest = new ActiveTest();
						activeTest.assignSequenceNumber();
						activeTest.timeStamp = currentTime;
						session.write(activeTest);
					} else {
						Logger.getLogger(MinaConstant.LOG_ACTIVE).info(
								"connection lost!");
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
}
