package com.mocha.unitcode.mina.pdu;

import com.mocha.unitcode.mina.code.PDU;

/**
 * 
 * <strong>Title : MinaPDU </strong>. <br>
 * <strong>Description : 消息头信息.</strong> <br>
 * <strong>Create on : 2014年8月26日 下午4:47:34 </strong>. <br>
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
public abstract class MinaPDU extends PDU {

	private static int sequenceNumber = 0;

	private boolean sequenceNumberChanged = false;

	public MinaPDUHeader header = null;

	public MinaPDU() {
		header = new MinaPDUHeader();
	}

	public MinaPDU(int commandId) {
		header = new MinaPDUHeader();
		header.setCommandId(commandId);
	}

	/** Checks if the header field is null and if not, creates it. */
	private void checkHeader() {
		if (header == null) {
			header = new MinaPDUHeader();
		}
	}

	public int getCommandLength() {
		checkHeader();
		return header.getCommandLength();
	}

	public int getCommandId() {
		checkHeader();
		return header.getCommandId();
	}

	public int getSequenceNumber() {
		checkHeader();
		return header.getSequenceNumber();
	}

	public void setCommandLength(int cmdLen) {
		checkHeader();
		header.setCommandLength(cmdLen);
	}

	public void setCommandId(int cmdId) {
		checkHeader();
		header.setCommandId(cmdId);
	}

	public void setSequenceNumber(int seqNr) {
		checkHeader();
		header.setSequenceNumber(seqNr);
	}

	public void assignSequenceNumber() {
		assignSequenceNumber(false);
	}

	public void assignSequenceNumber(boolean always) {
		if ((!sequenceNumberChanged) || always) {
			synchronized (this) {
				setSequenceNumber(++sequenceNumber);
			}
			sequenceNumberChanged = true;
		}
	}

	public void resetSequenceNumber() {
		setSequenceNumber(0);
		sequenceNumberChanged = false;
	}

	public boolean equals(Object object) {
		if ((object != null) && (object instanceof MinaPDU)) {
			MinaPDU pdu = (MinaPDU) object;
			return pdu.getSequenceNumber() == getSequenceNumber();
		} else {
			return false;
		}
	}

	public String getSequenceNumberAsString() {
		int data = header.getSequenceNumber();
		byte[] intBuf = new byte[4];
		intBuf[3] = (byte) (data & 0xff);
		intBuf[2] = (byte) ((data >>> 8) & 0xff);
		intBuf[1] = (byte) ((data >>> 16) & 0xff);
		intBuf[0] = (byte) ((data >>> 24) & 0xff);
		return new String(intBuf);
	}

	public abstract boolean isRequest();

	public abstract boolean isResponse();

	public String dump() {
		return name() + " dump() unimplemented";
	}

}
