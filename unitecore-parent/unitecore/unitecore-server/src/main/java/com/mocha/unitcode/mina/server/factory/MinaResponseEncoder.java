package com.mocha.unitcode.mina.server.factory;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.mocha.unitcode.mina.pdu.MinaPDU;
/**
 * 
 * <strong>Title : MinaResponseEncoder </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午11:03:05 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * @author 刘军  liujun1@mochasoft.com.cn <br>
 * @version <strong>Mocha JavaOA v7.0.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class MinaResponseEncoder implements ProtocolEncoder {
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		MinaPDU pdu = (MinaPDU) message;
		byte[] bytes = pdu.getData().getBuffer();
		IoBuffer buf = IoBuffer.allocate(bytes.length, false);

		buf.setAutoExpand(true);
//		buf.putInt(bytes.length);
		buf.put(bytes);

		buf.flip();
		out.write(buf);

	}

	public void dispose(IoSession session) throws Exception {

	}
}