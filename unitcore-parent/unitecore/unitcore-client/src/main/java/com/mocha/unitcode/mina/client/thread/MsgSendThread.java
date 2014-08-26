package com.mocha.unitcode.mina.client.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.unitcode.mina.client.handler.MinaClientIoHandler;

public class MsgSendThread extends Thread {
	private static BufferedReader keyboard = new BufferedReader(
			new InputStreamReader(System.in));
	private IoSession session = null;
	private static final Logger logger = LoggerFactory
			.getLogger(MsgSendThread.class);

	public MsgSendThread(IoSession s) {
		setDaemon(true);
		this.session = s;
	}

	public void run() {
		try {
			String option = "1";
			int optionInt;
			while (session.isConnected() & MinaClientIoHandler.Connect == true) {
				logger.info(">Please input your option: ");
				logger.info(">1 shutdown");
				logger.info(">2 test");
				System.out.print(">");
				// test1();
				// test();
				optionInt = -1;
				try {
					option = keyboard.readLine();
					optionInt = Integer.parseInt(option);
				} catch (Exception e) {
					logger.info("" + e + "\nPlease input an option number");
					optionInt = -1;
				}
				switch (optionInt) {
				case 1:
					logger.info("optionInt=" + optionInt);
					shutdown();
					break;
				case 2:
					logger.info("optionInt=" + optionInt);
					// test();
					// test1();
					break;
				case -1:
					// default option if entering an option went wrong
					break;
				default:
					logger.info("Invalid option. Choose between 0 and 10.");
					break;
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
}
