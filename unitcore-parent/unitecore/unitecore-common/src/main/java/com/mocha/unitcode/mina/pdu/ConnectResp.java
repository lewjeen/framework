package com.mocha.unitcode.mina.pdu;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : ConnectResp </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:37:55 </strong>. <br>
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
public class ConnectResp extends Response {

	private int status = 0;
	private String authServer = "";
	private byte version = 0;

	public ConnectResp() {
		super(MinaConstant.UNITE_CONNECT_RESP);
	}

	/**
	 * @return Returns the authServer.
	 */
	public String getAuthServer() {
		return authServer;
	}

	/**
	 * @param authServer
	 *            The authServer to set.
	 */
	public void setAuthServer(String authServer) {
		this.authServer = authServer;
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return Returns the version.
	 */
	public byte getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            The version to set.
	 */
	public void setVersion(byte version) {
		this.version = version;
	}

	public void setBody(ByteBuffer buffer) throws PDUException {
		try {
			setStatus(buffer.removeInt());
			setAuthServer(buffer.removeStringEx(16));
			setVersion(buffer.removeByte());
		} catch (NotEnoughDataInByteBufferException e) {
			throw new PDUException(e);
		}
	}

	public ByteBuffer getBody() {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendInt(getStatus());
		buffer.appendString(getAuthServer(), 16);
		buffer.appendByte(getVersion());
		return buffer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cmpp.sms.ByteData#setData(cmpp.sms.util.ByteBuffer)
	 */
	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
		setBody(buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cmpp.sms.ByteData#getData()
	 */
	public ByteBuffer getData() {
		ByteBuffer bodyBuf = getBody();
		header.setCommandLength(MinaConstant.PDU_HEADER_SIZE + bodyBuf.length());
		ByteBuffer buffer = header.getData();
		buffer.appendBuffer(bodyBuf);
		return buffer;
	}

	public String name() {
		return "CMPP ConnectResp";
	}

	public String dump() {
		String rt = "\r\nnConnectResp******************************************"
				+ "\r\nstatus:		"
				+ status
				+ "\r\nauthServer:	"
				+ authServer
				+ "\r\nversion:		"
				+ version
				+ "\r\nConnectResp******************************************";
		return rt;
	}
}
