package com.mocha.unitcode.mina.pdu;

import org.apache.log4j.Logger;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.MinaObject;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : MinaPDUParser </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
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

	public static MinaPDU createPDUFromBuffer(ByteBuffer buffer) {
		MinaPDU pdu = null;
		MinaPDUHeader pduHeader = new MinaPDUHeader();
		try {
			pduHeader.setData(buffer);
			switch (pduHeader.getCommandId()) {
			case MinaConstant.UNITE_CONNECT:
				System.out.println("=====UNITE_CONNECT========="
						+ MinaConstant.UNITE_CONNECT);
				Connect login = new Connect();
				login.header = pduHeader;
				login.setBody(buffer);
				pdu = login;
				break;
			case MinaConstant.UNITE_CONNECT_RESP:
				ConnectResp loginResp = new ConnectResp();
				loginResp.header = pduHeader;
				loginResp.setBody(buffer);
				pdu = loginResp;
				break;
			case MinaConstant.UNITE_TODO_SUBMIT:
				System.out.println("=====UNITE_TODO_SUBMIT========="
						+ MinaConstant.UNITE_TODO_SUBMIT);
				Logger.getLogger(MinaConstant.LOG_TODO).warn(
						"MinaConstant.UNITE_TODO_SUBMIT Header: "
								+ MinaConstant.UNITE_TODO_SUBMIT);
				SubmitItem submitItem = new SubmitItem();
				submitItem.header = pduHeader;
				submitItem.setBody(buffer);
				System.out.println("=====UNITE_TODO_SUBMIT=======buffer=="
						+ buffer.toString());
				Logger.getLogger(MinaConstant.LOG_TODO).warn(
						"MinaConstant.UNITE_TODO_SUBMIT buffer: " + buffer);
				pdu = submitItem;
				break;
			case MinaConstant.UNITE_TODO_SUBMIT_RESP:
				SubmitItemResp submitItemResp = new SubmitItemResp();
				submitItemResp.header = pduHeader;
				submitItemResp.setBody(buffer);
				pdu = submitItemResp;
				break;

			case MinaConstant.UNITE_ACTIVE_TEST:
				ActiveTest activeTest = new ActiveTest();
				activeTest.header = pduHeader;
				pdu = activeTest;
				break;
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
