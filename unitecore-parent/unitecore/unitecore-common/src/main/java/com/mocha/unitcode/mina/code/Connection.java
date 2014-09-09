package com.mocha.unitcode.mina.code;

import java.io.IOException;
import java.net.Socket;

import com.mocha.unitcode.mina.common.MinaConstant;

public abstract class Connection extends MinaObject {

	private long commsTimeout = MinaConstant.COMMS_TIMEOUT;

	private long receiveTimeout = MinaConstant.CONNECTION_RECEIVE_TIMEOUT;

	protected Connection() {
	}

	public abstract void open() throws IOException;

	public abstract void close() throws IOException;

	public abstract void send(ByteBuffer data) throws IOException;

	public abstract ByteBuffer receive() throws IOException;

	public abstract ByteBuffer receiveEx(int bytesToRead) throws IOException,
			java.net.SocketTimeoutException;

	public abstract Connection accept() throws IOException;

	public abstract Socket acceptEx() throws IOException;

	public abstract int available() throws IOException;

	public synchronized void setCommsTimeout(long commsTimeout) {
		this.commsTimeout = commsTimeout;
	}

	public synchronized void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	public synchronized long getCommsTimeout() {
		return commsTimeout;
	}

	public synchronized long getReceiveTimeout() {
		return receiveTimeout;
	}

}
