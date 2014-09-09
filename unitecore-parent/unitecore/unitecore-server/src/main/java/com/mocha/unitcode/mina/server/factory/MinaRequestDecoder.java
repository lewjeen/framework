package com.mocha.unitcode.mina.server.factory;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
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
 * <strong>Title : MinaRequestDecoder </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 下午3:32:57 </strong>. <br>
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
	private final AttributeKey CONTEXT = new AttributeKey(getClass(), "context");
	private Charset charset;
	private static final Logger logger = LoggerFactory
			.getLogger(MinaRequestDecoder.class);
	private int maxPackLength = 4000;

	public MinaRequestDecoder() {
		this(Charset.defaultCharset());
	}

	public MinaRequestDecoder(Charset charset) {
		this.charset = charset;
	}

	// public int getMaxLineLength() {
	// return maxPackLength;
	// }
	//
	// public void setMaxLineLength(int maxLineLength) {
	// if (maxLineLength <= 0) {
	// throw new IllegalArgumentException("maxLineLength: "
	// + maxLineLength);
	// }
	// this.maxPackLength = maxLineLength;
	// }
	//
	// private Context getContext(IoSession session) {
	// Context ctx;
	// ctx = (Context) session.getAttribute(CONTEXT);
	// if (ctx == null) {
	// ctx = new Context();
	// session.setAttribute(CONTEXT, ctx);
	// }
	// return ctx;
	// }
	//
	// public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput
	// out)
	// throws Exception {
	// final int packHeadLength = 4;
	// // 先获取上次的处理上下文，其中可能有未处理完的数据
	// Context ctx = getContext(session);
	// // 先把当前buffer中的数据追加到Context的buffer当中
	// ctx.append(in);
	// // 把position指向0位置，把limit指向原来的position位置
	// IoBuffer buf = ctx.getBuffer();
	// buf.flip();
	// // 然后按数据包的协议进行读取
	// while (buf.remaining() >= packHeadLength) {
	// buf.mark();
	// // 读取消息头部分
	// int length = buf.getInt();
	//
	// // 检查读取是否正常，不正常的话清空buffer
	// if (length < 0 || length > maxPackLength) {
	// System.out.println("长度[" + length
	// + "] > maxPackLength or <0....");
	// buf.clear();
	// break;
	// }
	// // 读取正常的消息，并写入输出流中，以便IoHandler进行处理
	// else if (length >= packHeadLength
	// && length - packHeadLength <= buf.remaining()) {
	// int oldLimit2 = buf.limit();
	// buf.limit(buf.position() + length - packHeadLength);
	// String content = buf.getString(ctx.getDecoder());
	// buf.limit(oldLimit2);
	// byte[] bytedata = new byte[length - packHeadLength];
	// in.get(bytedata);
	// ByteBuffer buffer = new ByteBuffer();
	// buffer.appendInt(length);
	// buffer.appendBytes(bytedata);
	// MinaPDU pdu = MinaPDUParser.createPDUFromBuffer(buffer);
	// out.write(content);
	// } else {
	// // 如果消息包不完整
	// // 将指针重新移动消息头的起始位置
	// buf.reset();
	// break;
	// }
	// }
	// if (buf.hasRemaining()) {
	// // 将数据移到buffer的最前面
	// IoBuffer temp = IoBuffer.allocate(maxPackLength)
	// .setAutoExpand(true);
	// temp.put(buf);
	// temp.flip();
	// buf.clear();
	// buf.put(temp);
	//
	// } else {// 如果数据已经处理完毕，进行清空
	// buf.clear();
	// }
	// }

	protected boolean doDecode(final IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {

		// 考虑以下几种情况：
		// 1. 一个ip包中只包含一个完整消息
		// 2. 一个ip包中包含一个完整消息和另一个消息的一部分
		// 3. 一个ip包中包含一个消息的一部分
		// 4. 一个ip包中包含两个完整的数据消息或更多（循环处理在父类的decode中）
		// logger.info("in.remaining()======" + in.remaining());
		System.out.println("in.remaining()=   MinaRequestDecoder= doDecode===="
				+ in.remaining());
		if (in.remaining() > 4) {
			logger.info("resv msg " + in.toString());
			in.mark();
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
			System.out
					.println("====MinaRequestDecoder===doDecode======length===="
							+ length);
			System.out
					.println("===MinaRequestDecoder====doDecode======bytedata===="
							+ bytedata.toString());

			MinaPDU pdu = MinaPDUParser.createPDUFromBuffer(buffer);
			System.out
					.println("===MinaRequestDecoder====doDecode======buffer===="
							+ buffer.toString());
			System.out
					.println("===MinaRequestDecoder====doDecode======MinaPDU===="
							+ pdu);
			if (pdu == null)
				return false;
			logger.info(pdu.dump());
			out.write(pdu);
			return true;

		}

		return false;
	}

	// 记录上下文，因为数据触发没有规模，很可能只收到数据包的一半
	// 所以，需要上下文拼起来才能完整的处理
	// private class Context {
	// private final CharsetDecoder decoder;
	// private IoBuffer buf;
	// private int matchCount = 0;
	// private int overflowPosition = 0;
	//
	// private Context() {
	// decoder = charset.newDecoder();
	// buf = IoBuffer.allocate(3000).setAutoExpand(true);
	// }
	//
	// public CharsetDecoder getDecoder() {
	// return decoder;
	// }
	//
	// public IoBuffer getBuffer() {
	// return buf;
	// }
	//
	// public int getOverflowPosition() {
	// return overflowPosition;
	// }
	//
	// public int getMatchCount() {
	// return matchCount;
	// }
	//
	// public void setMatchCount(int matchCount) {
	// this.matchCount = matchCount;
	// }
	//
	// public void reset() {
	// overflowPosition = 0;
	// matchCount = 0;
	// decoder.reset();
	// }
	//
	// public void append(IoBuffer in) {
	// getBuffer().put(in);
	//
	// }
	//
	// }
	//
	// @Override
	// protected boolean doDecode(IoSession session, IoBuffer in,
	// ProtocolDecoderOutput out) throws Exception {
	// // TODO Auto-generated method stub
	// return false;
	// }
}
