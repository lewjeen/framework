package com.mocha.unitcode.mina.pdu;

import org.apache.log4j.Logger;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : SubmitResp </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:40:34 </strong>. <br>
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
 * @param <E>
 */
public class SubmitTodoEntityResp<E> extends Response {

	private byte[] msgId = new byte[8];
	private int result = 0;
	/**
	 * 返回待办数据消息
	 */
	private Message<E> entityMessage = new Message<E>();

	public SubmitTodoEntityResp(int commandId) {
		super(commandId);
	}

	public SubmitTodoEntityResp() {
		// super();
	}

	public ByteBuffer getData() {
		ByteBuffer bodyBuf = getBody();
		header.setCommandLength(MinaConstant.PDU_HEADER_SIZE + bodyBuf.length());
		ByteBuffer buffer = header.getData();
		buffer.appendBuffer(bodyBuf);
		return buffer;
	}

	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
		setBody(buffer);
	}

	public void setBody(ByteBuffer buffer) throws PDUException {
		try {
			msgId = buffer.removeBytes(8).getBuffer();
			result = buffer.removeInt();
			byte msgFormat = buffer.removeByte();
			byte type = buffer.removeByte();
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"===setBody====msgFormat===========" + msgFormat);
			byte classLength = buffer.removeByte();
			int classLh = classLength;
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"===setBody====classLength===========" + classLength);
			entityMessage.setClassData(buffer.removeBuffer(classLh));
			byte signbyte = buffer.removeByte();
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"===setBody====signbyte===========" + signbyte);
			int msgLength = buffer.length();
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"==setBody=====msgLength===========" + msgLength);
			entityMessage.setData(buffer.removeBuffer(msgLength));
			entityMessage.setMsgFormat(msgFormat);
			entityMessage.setType(type);
		} catch (NotEnoughDataInByteBufferException e) {
			throw new PDUException(e);
		}
	}

	public ByteBuffer getBody() {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendBytes(msgId);
		buffer.appendInt(result);
		buffer.appendByte(entityMessage.getMsgFormat());
		buffer.appendByte(entityMessage.getType());
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====msgId===========" + msgId);
		buffer.appendByte((byte) entityMessage.getClassLength());
		buffer.appendBuffer(entityMessage.getClassData());
		buffer.appendByte((byte) entityMessage.getLength());
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====entityMessage.getLength()==========="
						+ entityMessage.getLength());
		buffer.appendBuffer(entityMessage.getData());
		return buffer;
	}

	public byte[] getMsgId() {
		return msgId;
	}

	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public Message<E> getEntityMessage() {
		return entityMessage;
	}

	public void setEntityMessage(Message<E> entityMessage) {
		this.entityMessage = entityMessage;
	}

	public String name() {
		return "SubmitResp";
	}

	public String dump() {
		String rt = "\r\nSubmitResp********************************"
				+ "\r\nmsgId:	" + msgId + "\r\nresult:	" + result
				+ "\r\n********************************SubmitResp";
		return rt;
	}

}
