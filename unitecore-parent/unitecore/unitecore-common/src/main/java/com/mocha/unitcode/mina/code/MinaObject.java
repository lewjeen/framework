package com.mocha.unitcode.mina.code;

import org.apache.log4j.Logger;

import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.MinaPDU;
import com.mocha.unitcode.mina.pdu.MinaPDUHeader;
import com.mocha.unitcode.mina.pdu.SubmitTodoEntity;
import com.mocha.unitcode.mina.pdu.SubmitTodoEntityResp;

/**
 * <strong>Title : MinaObject </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:17:57 </strong>. <br>
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
public class MinaObject {

	static final public String RT = "\n";
	static protected Logger logger = Logger.getLogger(MinaObject.class
			.getName());

	static public Logger getLogger() {
		return logger;
	}

	static public void setLogger(Logger myLogger) {
		logger = myLogger;
	}

	/**
	 * 
	 * 处理所有提交的待办数据处理
	 * 
	 * @param commandId
	 * @param pduHeader
	 * @param buffer
	 *            处理字符
	 * @param message
	 *            提示消息内容
	 * @return
	 * @throws PDUException
	 */
	protected static MinaPDU submit(int commandId, MinaPDUHeader pduHeader,
			ByteBuffer buffer, String message) throws PDUException {
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"---------submit--------------" + message
						+ " ------------------------");
		SubmitTodoEntity submitItem = new SubmitTodoEntity(commandId);
		submitItem.header = pduHeader;
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				message + " getCommandLength: " + pduHeader.getCommandLength());
		submitItem.setBody(buffer);
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				message + " buffer getHexDump: " + buffer.getHexDump());
		return submitItem;
	}

	/**
	 * 
	 * 处理所有提交返回数据处理
	 * 
	 * @param commandId
	 * @param pduHeader
	 * @param buffer
	 * @param message
	 * @return
	 * @throws PDUException
	 */
	protected static MinaPDU submitResp(int commandId, MinaPDUHeader pduHeader,
			ByteBuffer buffer, String message) throws PDUException {
		SubmitTodoEntityResp submitItemResp = new SubmitTodoEntityResp(
				commandId);
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				"---------submitResp--------------" + message
						+ " ------------------------");
		Logger.getLogger(MinaConstant.LOG_TODO).info(
				message + " buffer getHexDump: " + buffer.getHexDump());
		submitItemResp.header = pduHeader;
		submitItemResp.setBody(buffer);
		return submitItemResp;

	}
}
