package com.mocha.unitcode.mina.server.factory;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * <strong>Title : MinaProtocolCodecFactory </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:12:50 </strong>. <br>
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
public class MinaProtocolCodecFactory implements ProtocolCodecFactory {
	private ProtocolDecoder decoder = new MinaRequestDecoder(
			Charset.forName(MinaConstant.UTF8));
	private ProtocolEncoder encoder = new MinaResponseEncoder();

	public ProtocolDecoder getDecoder(IoSession sessionIn) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession sessionIn) throws Exception {
		return encoder;
	}
}
