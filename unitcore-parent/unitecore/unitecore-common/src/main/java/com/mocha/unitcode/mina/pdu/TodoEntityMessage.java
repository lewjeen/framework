package com.mocha.unitcode.mina.pdu;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSON;
import com.mocha.tydb_common.entity.TodoEntity;
import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.ByteData;
import com.mocha.unitcode.mina.code.PDUException;

/**
 * 
 * <strong>Title :TodoEntityMessage.java. </strong>. <br>
 * <strong>Description :统一待办平台待办项实体 .</strong> <br>
 * <strong>Create on :2014年8月11日下午5:45:57 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * 
 * @author liujun liujun1@mochasoft.com.cn <br>
 * @version <strong>全国统一待办系统</strong> <br>
 * <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public class TodoEntityMessage extends ByteData {
	byte msgFormat = 0;
	byte[] messageData = null;
	/**
	 * 待办实体
	 */
	TodoEntity entity;
	String encoding = "US-ASCII";

	public byte[] getMessageData() {
		return messageData;
	}

	public void setMessageData(byte[] messageData) {
		this.messageData = messageData;
	}

	public TodoEntity getEntity() {
		return entity;
	}

	public void setEntity(TodoEntity entity) {
		this.entity = entity;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public ByteBuffer getData() {
		ByteBuffer buffer = null;
		buffer = new ByteBuffer(messageData);
		return buffer;
	}

	/**
	 * 
	 * 设置待办对象
	 * 
	 * @param entity
	 *            待办数据对象
	 * @param msgFormat
	 *            字符集 <br>
	 *            0-US-ASCII<br>
	 *            8-UnicodeBigUnmarked <br>
	 *            15-gbk <br>
	 *            10-UTF
	 */
	public void setEntityMessage(TodoEntity entity, byte msgFormat) {
		this.messageData = JSON.toJSONString(entity).getBytes();
		this.msgFormat = msgFormat;
		setMsgFormat(msgFormat);
	}

	public TodoEntity getTodoEntity() {
		String str = "";
		TodoEntity todoEntity = null;
		System.out.println("=====getEntityMessage======messageData============"
				+ messageData);
		System.out
				.println("=====getEntityMessage=========encoding======================"
						+ encoding);

		try {
			str = new String(messageData, encoding);
			System.out
					.println("======getEntityMessage========str======================"
							+ str);
			todoEntity = JSON.parseObject(str, TodoEntity.class);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todoEntity;
	}

	public String getEntityMessage() {
		String str = "";
		try {
			System.out
					.println("=====getEntityMessage======messageData============"
							+ messageData);
			System.out
					.println("=====getEntityMessage=========encoding======================"
							+ encoding);
			str = new String(messageData, encoding);
			System.out
					.println("======getEntityMessage========str======================"
							+ str);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			//
		}
		return str;
	}

	public int getLength() {
		return messageData == null ? 0 : messageData.length;
	}

	public String dump() {
		String rt = "\r\nShortMessage: " + "\r\nmsgFormat: 	" + msgFormat
				+ "\r\nmsg: 			" + getEntityMessage();
		return rt;
	}

	public byte getMsgFormat() {
		return msgFormat;
	}

	public void setData(ByteBuffer buffer) throws PDUException {
		this.messageData = buffer.getBuffer();
	}

	public void setData(byte[] data) {
		this.messageData = data;
	}

	public void setMsgFormat(byte msgFormat) {
		this.msgFormat = msgFormat;
		if (msgFormat == 0) {
			encoding = "US-ASCII";
		} else if (msgFormat == 8) {
			encoding = "UnicodeBigUnmarked";
		} else if (msgFormat == 15) {
			encoding = "GBK";
		} else if (msgFormat == 10) {
			encoding = "UTF-8";
		}
	}

	public void setEntity(String msg, byte msgFormat) {
		setMsgFormat(msgFormat);
		try {
			setData(msg.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			logger.warn("msgFormat unsupportted!", e);
		}
	}

}
