package com.mocha.unitcode.mina.pdu;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.ByteData;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.util.ListUtil;
import com.mocha.unitcode.mina.util.ObjectUtil;

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
 * @param <E>
 */
public class TodoEntityMessage<E> extends ByteData {
	/**
	 * 单条类型
	 */
	 public static byte TODO_TYPE_SINGLE =0 ;
	/**
	 * 批量类型
	 */
	public static byte TODO_TYPE_BATCH =1;
	/**
	 * 字符编码
	 */
	byte msgFormat = 0;
	/**
	 * 消息数据
	 */
	byte[] messageData = null;
	/**
	 * 传输实体
	 */
	byte[] clazz = null;
	/**
	 * 发送方式 0 单条发送 1-批量发送
	 */
	byte type = TODO_TYPE_SINGLE;
	String encoding = "US-ASCII";

	public byte[] getMessageData() {
		return messageData;
	}

	public void setMessageData(byte[] messageData) {
		this.messageData = messageData;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public ByteBuffer getData() {
		ByteBuffer buffer = null;
		buffer = new ByteBuffer(messageData);
		return buffer;
	}

	public ByteBuffer getClassData() {
		ByteBuffer buffer = null;
		buffer = new ByteBuffer(clazz);
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
	// public void setEntityMessage(TodoEntity entity, byte msgFormat) {
	// this.messageData = JSON.toJSONString(entity).getBytes();
	// this.msgFormat = msgFormat;
	// setMsgFormat(msgFormat);
	// }

	/**
	 * 
	 * 设置待办对象
	 * 
	 * @param E
	 *            待办数据<br>
	 * @param msgFormat
	 *            字符集 <br>
	 *            0-US-ASCII<br>
	 *            8-UnicodeBigUnmarked <br>
	 *            15-gbk <br>
	 *            10-UTF
	 */
	public void setEntityMessage(E e, byte msgFormat, byte type) {
		if (type == TODO_TYPE_SINGLE) {
			this.messageData = JSON.toJSONString(e).getBytes();
		} else if (type == TODO_TYPE_BATCH) {
			List<String> list=ListUtil.ToObjectString((List<E>) e);
			this.messageData = JSON.toJSONString(list).getBytes();
		}
		this.type=type;
		this.msgFormat = msgFormat;
		this.clazz = e.getClass().getCanonicalName().getBytes();
		setMsgFormat(msgFormat);
	}

	@SuppressWarnings("unchecked")
	public E getTodoEntity() {
		String str = "";
		E todo = null;
		Logger.getLogger("=====getEntityMessage======messageData============"
				+ new String(messageData));
		Logger.getLogger("=====getEntityMessage=========encoding======================"
				+ encoding);

		try {
			str = new String(messageData, encoding);
			Logger.getLogger(MinaConstant.LOG_TODO)
					.info("======getTodoEntity============str================="
							+ str);
			if (type==TODO_TYPE_SINGLE) {
				todo = (E) JSON.parseObject(str, ObjectUtil.getClass(Class.forName(
						new String(clazz)).newInstance()));
			}else if (type==TODO_TYPE_BATCH) {
				List<String> list=JSON.parseObject(str, List.class);
				todo=(E) ListUtil.ToStringObject(list, ObjectUtil.getClass(Class.forName(
						new String(clazz)).newInstance()));
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"JSONException   JSON格式异常  " + e.getLocalizedMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"ClassNotFoundException   Object异常  " + e);
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return todo;
	}

	public byte[] getClazz() {
		return clazz;
	}

	public void setClazz(byte[] clazz) {
		this.clazz = clazz;
	}

	public String getEntityMessage() {
		String str = "";
		try {
			str = new String(messageData, encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			//
		}
		return str;
	}

	public int getLength() {
		return messageData == null ? 0 : messageData.length + clazz.length + 1
				+ type;
	}

	public int getClassLength() {
		return clazz == null ? 0 : clazz.length;
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

	public void setClassData(ByteBuffer buffer) throws PDUException {
		this.clazz = buffer.getBuffer();
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

	// public String getEntityJson(E e) {
	// if (e instanceof List<?>) {
	// if (CollectionUtils.isEmpty((Collection) e)) {
	//
	// }
	//
	// return JSON.toJSONString(e);
	// } else if (e instanceof TodoEntity) {
	// return JSON.toJSONString(e);
	//
	// } else if (e instanceof TodoChangeEntity) {
	// return JSON.toJSONString(e);
	// } else {
	// return "";
	// }
	//
	// }
}
