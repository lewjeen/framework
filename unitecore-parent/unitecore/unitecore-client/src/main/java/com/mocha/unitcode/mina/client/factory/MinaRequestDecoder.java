package com.mocha.unitcode.mina.client.factory;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.pdu.MinaPDU;
import com.mocha.unitcode.mina.pdu.MinaPDUParser;

/**
 * 
 * <strong>Title : CmppRequestDecoder </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 下午2:18:37 </strong>. <br>
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
public class MinaRequestDecoder extends CumulativeProtocolDecoder {
	private static final Logger logger = LoggerFactory
			.getLogger(MinaRequestDecoder.class);
	private Charset charset;

	public MinaRequestDecoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	protected boolean doDecode(final IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		if (in.remaining() > 4) {
			logger.info("resv msg " + in.toString());

			int length = in.getInt();
			logger.info("length=" + length + ",in.limit=" + in.limit()
					+ ",in.remaining=" + in.remaining());
			if (length > (in.remaining() + 4)) {
				in.rewind();
				return false;
			}

			byte[] bytedata = new byte[length - 4];
			in.get(bytedata);
			ByteBuffer buffer = new ByteBuffer();
			buffer.appendInt(length);
			buffer.appendBytes(bytedata);
			MinaPDU pdu = MinaPDUParser.createPDUFromBuffer(buffer);
			if (pdu == null)
				return false;
			logger.info(pdu.dump());
			out.write(pdu);
			return true;

		}

		return false;
	}
}
