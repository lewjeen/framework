package com.mocha.unitcode.mina.pdu;

import org.apache.log4j.Logger;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.util.verify.CRC16Verifier;

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
 * @param <E>
 */
public class SubmitTodoEntity<E> extends Request {
	/**
	 * 消息ID
	 */
	private byte[] msgId = new byte[8];
	/**
	 * Crc16校验码
	 */
	private byte[] crc16Code;

	private byte[] nodeId;
	/**
	 * 待办数据消息
	 */
	private Message<E> entityMessage = new Message<E>();

	public Message<E> getEntityMessage() {
		return entityMessage;
	}

	public void setEntityMessage(Message<E> entityMessage) {
		this.entityMessage = entityMessage;
		CRC16Verifier crc16Verifier = CRC16Verifier.getInstance();
		// this.crc16Code = crc16Verifier.getVerifyCode(code, true);
	}

	public byte[] getMsgId() {
		return msgId;
	}

	public void setMsgId(byte[] msgId) {
		this.msgId = msgId;
	}

	public byte[] getCrc16Code() {
		return crc16Code;
	}

	public void setCrc16Code(byte[] crc16Code) {
		this.crc16Code = crc16Code;
	}

	public byte[] getNodeId() {
		return nodeId;
	}

	public void setNodeId(byte[] nodeId) {
		this.nodeId = nodeId;
	}

	public int getDataLength(byte[] data) {
		return data == null ? 0 : data.length;
	}

	public SubmitTodoEntity(int commandId) {
		super(commandId);
	}

	protected Response createResponse(int commandId) {
		return new SubmitTodoEntityResp(commandId);
	}

	protected Response createResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBuffer getByteBuffer(byte[] data) {
		ByteBuffer buffer = null;
		buffer = new ByteBuffer(data);
		return buffer;
	}

	public void setBody(ByteBuffer buffer) throws PDUException {
		try {
			// 解析消息ID
			msgId = buffer.removeBytes(8).getBuffer();
			Logger.getLogger(MinaConstant.LOG_TODO).info(
					"==setBody=====msgId===========" + msgId.toString());
			// 解析消息验证码
			byte crc16CodeLgs = buffer.removeByte();
			int crc16CodeLh = crc16CodeLgs;
			crc16Code = buffer.removeBuffer(crc16CodeLh).getBuffer();
			// 解析消息节点信息
			byte nodeIdLgs = buffer.removeByte();
			int nodeIdLh = nodeIdLgs;
			nodeId = buffer.removeBuffer(nodeIdLh).getBuffer();
			// 解析待办数据
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
		// 设置消息id
		buffer.appendBytes(msgId, 8);
		// 设置验证码
		buffer.appendByte((byte) getDataLength(crc16Code));
		buffer.appendBuffer(getByteBuffer(crc16Code));
		// 设置前置节点信息
		buffer.appendByte((byte) getDataLength(nodeId));
		buffer.appendBuffer(getByteBuffer(nodeId));
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"===getBody====msgId===========" + msgId);
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
