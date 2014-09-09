package com.mocha.unitcode.mina.pdu;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.ByteData;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : MinaPDUHeader </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:34:55 </strong>. <br>
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
public class MinaPDUHeader extends ByteData {
	private int commandLength = MinaConstant.PDU_HEADER_SIZE;
	private int commandId = 0;
	private int sequenceNumber = 0;

	public ByteBuffer getData() {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendInt(getCommandLength());
		buffer.appendInt(getCommandId());
		buffer.appendInt(getSequenceNumber());
		return buffer;
	}

	public void setData(ByteBuffer buffer) throws PDUException {
		try {
			commandLength = buffer.removeInt();
			commandId = buffer.removeInt();
			sequenceNumber = buffer.removeInt();
		} catch (NotEnoughDataInByteBufferException e) {
			throw new PDUException(e);
		}
	}

	public int getCommandLength() {
		return commandLength;
	}

	public int getCommandId() {
		return commandId;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setCommandLength(int cmdLen) {
		commandLength = cmdLen;
	}

	public void setCommandId(int cmdId) {
		commandId = cmdId;
	}

	public void setSequenceNumber(int seqNr) {
		sequenceNumber = seqNr;
	}

}
