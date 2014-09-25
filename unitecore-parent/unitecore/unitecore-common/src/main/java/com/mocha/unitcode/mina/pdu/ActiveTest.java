/*
 * Created on 2005-5-7
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mocha.unitcode.mina.pdu;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

public class ActiveTest extends Request {

	public ActiveTest() {
		super(MinaConstant.UNITE_ACTIVE_TEST);
	}

	protected Response createResponse() {
		return new ActiveTestResp();
	}

	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
	}

	public ByteBuffer getData() {
		return header.getData();
	}

	public String name() {
		return "Mina ActiveTest";
	}

	@Override
	protected Response createResponse(int commandId) {
		// TODO Auto-generated method stub
		return null;
	}
}
