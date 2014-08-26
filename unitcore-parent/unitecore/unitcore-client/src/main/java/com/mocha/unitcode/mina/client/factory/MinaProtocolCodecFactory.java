package com.mocha.unitcode.mina.client.factory;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * TODO: Document me
 * 
 * @author <a href="http://mina.apache.org">Apache MINA Project</a>
 * 
 */
public class MinaProtocolCodecFactory implements ProtocolCodecFactory {
	private Charset charset;

	public MinaProtocolCodecFactory(Charset charset) {
		this.charset = charset;
	}

	private ProtocolDecoder decoder = new MinaRequestDecoder(charset);
	private ProtocolEncoder encoder = new MinaResponseEncoder();

	public ProtocolDecoder getDecoder(IoSession sessionIn) throws Exception {
		return decoder;
	}

	public ProtocolEncoder getEncoder(IoSession sessionIn) throws Exception {
		return encoder;
	}

}
