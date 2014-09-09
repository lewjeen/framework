package com.mocha.unitcode.mina.server.thread;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.unitcode.mina.server.handler.MinaIoHandler;

/**
 * 
 * <strong>Title : SendMoThread </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午11:03:29 </strong>. <br>
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

public class SendMoThread implements Runnable {
	private static int i = 0;
	private IoSession session = null;
	private static final Logger logger = LoggerFactory
			.getLogger(SendMoThread.class);

	public SendMoThread(IoSession s) {
		this.session = s;
	}

	public void run() {
		try {
			while (session.isConnected() & MinaIoHandler.Connect == true) {
				logger.info("session " + session.getId() + " :send mo");
				sendmo();
				try {
					Thread.sleep(10 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void shutdown() {
		if (session != null)
			session.close(true);
	}

	private void sendmo() {

	}
}
