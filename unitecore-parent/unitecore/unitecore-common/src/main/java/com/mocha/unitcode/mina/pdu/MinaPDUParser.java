package com.mocha.unitcode.mina.pdu;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.MinaObject;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : MinaPDUParser </strong>. <br>
 * <strong>Description : 消息剖析器.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:41:00 </strong>. <br>
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
public class MinaPDUParser extends MinaObject {
	/**
	 * 
	 * 对传过来的消息进行解析处理
	 * 
	 * @param buffer
	 *            消息字节
	 * @return
	 */
	public static MinaPDU createPDUFromBuffer(ByteBuffer buffer) {
		MinaPDU pdu = null;
		MinaPDUHeader pduHeader = new MinaPDUHeader();
		try {
			// 获取消息头信息
			pduHeader.setData(buffer);
			// 解析消息头，根据消息头CommandId字段区分不同消息请求
			switch (pduHeader.getCommandId()) {
			/**
			 * 请求连接消息
			 */
			case MinaConstant.UNITE_CONNECT:
				System.out.println("=====UNITE_CONNECT========="
						+ MinaConstant.UNITE_CONNECT);
				Connect login = new Connect();
				login.header = pduHeader;
				login.setBody(buffer);
				pdu = login;
				break;
			/**
			 * 请求连接返回值消息
			 */
			case MinaConstant.UNITE_CONNECT_RESP:
				ConnectResp loginResp = new ConnectResp();
				loginResp.header = pduHeader;
				loginResp.setBody(buffer);
				pdu = loginResp;
				break;
			/**
			 * 提交待办数据消息
			 */
			case MinaConstant.UNITE_TODO_SUBMIT:
				pdu = submit(MinaConstant.UNITE_TODO_SUBMIT, pduHeader, buffer,
						"MinaConstant.UNITE_TODO_SUBMIT");
				break;
			/**
			 * 提交待办数据返回消息
			 */
			case MinaConstant.UNITE_TODO_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_TODO_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_TODO_SUBMIT_RESP");
				break;
			/**
			 * 提交待办变更数据消息
			 */
			case MinaConstant.UNITE_TODOCHANGE_SUBMIT:
				pdu = submit(MinaConstant.UNITE_TODOCHANGE_SUBMIT, pduHeader,
						buffer, "MinaConstant.UNITE_TODOCHANGE_SUBMIT");
				break;
			/**
			 * 提交待办变更数据返回消息
			 */
			case MinaConstant.UNITE_TODOCHANGE_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_TODOCHANGE_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_TODOCHANGE_SUBMIT_RESP");
				break;
			/**
			 * 提交对账待办数据消息
			 */
			case MinaConstant.UNITE_RTODO_SUBMIT:
				pdu = submit(MinaConstant.UNITE_RTODO_SUBMIT, pduHeader,
						buffer, "MinaConstant.UNITE_RTODO_SUBMIT");
				break;
			/**
			 * 提交对账待办数据返回消息
			 */
			case MinaConstant.UNITE_RTODO_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_RTODO_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_RTODO_SUBMIT_RESP");
				;
				break;
			/**
			 * 提交对账待办变更数据消息
			 */
			case MinaConstant.UNITE_RTODOCHANGE_SUBMIT:
				pdu = submit(MinaConstant.UNITE_RTODOCHANGE_SUBMIT, pduHeader,
						buffer, "MinaConstant.UNITE_RTODOCHANGE_SUBMIT");
				break;
			/**
			 * 提交对账待办变更数据返回消息
			 */
			case MinaConstant.UNITE_RTODOCHANGE_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_RTODOCHANGE_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_RTODOCHANGE_SUBMIT_RESP");
				break;
			/**
			 * 提交VIP待办数据消息
			 */
			case MinaConstant.UNITE_TODO_VIP_SUBMIT:
				pdu = submit(MinaConstant.UNITE_TODO_VIP_SUBMIT, pduHeader,
						buffer, "MinaConstant.UNITE_TODO_VIP_SUBMIT");
				break;
			/**
			 * 提交VIP待办数据返回消息
			 */
			case MinaConstant.UNITE_TODO_VIP_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_TODO_VIP_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_TODO_VIP_SUBMIT_RESP");
				break;
			/**
			 * 提交VIP待办变更数据消息
			 */
			case MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT:
				pdu = submit(MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT,
						pduHeader, buffer,
						"MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT");
				break;
			/**
			 * 提交VIP待办变更数据返回消息
			 */
			case MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT_RESP:
				pdu = submitResp(MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT_RESP,
						pduHeader, buffer,
						"MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT_RESP");
				break;
			/**
			 * 链路检查消息
			 */
			case MinaConstant.UNITE_ACTIVE_TEST:
				ActiveTest activeTest = new ActiveTest();
				activeTest.header = pduHeader;
				pdu = activeTest;
				break;
			/**
			 * 链路检查返回消息
			 */
			case MinaConstant.UNITE_ACTIVE_TEST_RESP:
				ActiveTestResp activeTestResp = new ActiveTestResp();
				activeTestResp.header = pduHeader;
				pdu = activeTestResp;
				break;
			default:
				logger.error("Unknown Command! PDU Header: "
						+ pduHeader.getData().getHexDump());
				break;
			}
		} catch (PDUException e) {
			logger.error("Error parsing PDU: ", e);
		}
		return pdu;
	}
}
