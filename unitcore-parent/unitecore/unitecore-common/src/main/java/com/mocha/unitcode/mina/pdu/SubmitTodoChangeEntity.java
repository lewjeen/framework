package com.mocha.unitcode.mina.pdu;

import org.apache.log4j.Logger;

import com.mocha.tydb_common.entity.TodoChangeEntity;
import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : Submit </strong>. <br>
 * <strong>Description : 提交待办数据.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:41:21 </strong>. <br>
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
public class SubmitTodoChangeEntity extends Request {
	/**
	 * 消息ID
	 */
	private byte[] msgId = new byte[8];
	/**
	 * 待办数据消息
	 */

	private TodoEntityMessage<TodoChangeEntity> entityMessage = new TodoEntityMessage<TodoChangeEntity>();

	public TodoEntityMessage<TodoChangeEntity> getEntityMessage() {
		return entityMessage;
	}

	public void setEntityMessage(
			TodoEntityMessage<TodoChangeEntity> entityMessage) {
		this.entityMessage = entityMessage;
	}

	public byte[] getMsgId() {
		return msgId;
	}

	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	public SubmitTodoChangeEntity() {
		super(MinaConstant.UNITE_TODO_SUBMIT);
	}

	protected Response createResponse() {
		return new SubmitTodoEntityResp();
	}

	public void setBody(ByteBuffer buffer) throws PDUException {
		try {
			msgId = buffer.removeBytes(8).getBuffer();
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"==setBody=====msgId===========" + msgId.toString());
			byte msgFormat = buffer.removeByte();
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

		} catch (NotEnoughDataInByteBufferException e) {
			throw new PDUException(e);
		}
	}

	public ByteBuffer getBody() {
		ByteBuffer buffer = new ByteBuffer();
		// 设置消息id
		buffer.appendBytes(msgId, 8);
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====msgId===========" + msgId);
		buffer.appendByte(entityMessage.getMsgFormat());
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====msgId===========" + msgId);
		buffer.appendByte((byte) entityMessage.getClassLength());
		buffer.appendBuffer(entityMessage.getClassData());
		buffer.appendByte((byte) entityMessage.getLength());
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====entityMessage.getLength()==========="
						+ entityMessage.getLength());
		buffer.appendBuffer(entityMessage.getData());
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"entityMessage.getData()" + entityMessage.getData());
		return buffer;

	}

	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
		setBody(buffer);
	}

	public ByteBuffer getData() {
		ByteBuffer bodyBuf = getBody();
		header.setCommandLength(MinaConstant.PDU_HEADER_SIZE + bodyBuf.length());
		ByteBuffer buffer = header.getData();
		buffer.appendBuffer(bodyBuf);
		return buffer;
	}

	public String dump() {
		String rt = "\r\nSubmit.dump**************************************"
				+ "\r\nmsgId:			"
				+ com.mocha.unitcode.mina.pdu.Tools
						.byteArray2HexString(getMsgId())
				+ "\r\n entityMessage Length" + entityMessage.getLength()
				+ "\r\n entityMessage" + entityMessage.getEntityMessage();
		return rt;
	}

	public String name() {
		return " SubmitItem  OK";
	}

}
