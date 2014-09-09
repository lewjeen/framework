package com.mocha.unitcode.mina.pdu;

import java.security.MessageDigest;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mocha.unitcode.mina.code.ByteBuffer;
import com.mocha.unitcode.mina.code.NotEnoughDataInByteBufferException;
import com.mocha.unitcode.mina.code.PDUException;
import com.mocha.unitcode.mina.common.MinaConstant;

/**
 * 
 * <strong>Title : Connect </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 下午3:42:12 </strong>. <br>
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
public class Connect extends Request {

	private String clientId = "";
	private byte[] authClient = new byte[16];
	private byte version = (byte) 0x00;
	private int timeStamp = 0;
	private String sharedSecret = "";

	public Connect() {
		super(MinaConstant.UNITE_CONNECT);
	}

	public Connect(byte version) {
		super(MinaConstant.UNITE_CONNECT);
		setVersion(version);
	}

	public void setBody(ByteBuffer buffer) throws PDUException {
		try {
			setClientId(buffer.removeStringEx(6));
			setAuthClient(buffer.removeBytes(16).getBuffer());
			setVersion(buffer.removeByte());
			setTimeStamp(buffer.removeInt());
		} catch (NotEnoughDataInByteBufferException e) {
			e.printStackTrace();
			throw new PDUException(e);
		}
	}

	public ByteBuffer getBody() {
		ByteBuffer buffer = new ByteBuffer();
		buffer.appendString(getClientId(), 6);
		buffer.appendBytes(getAuthClient(), 16);
		buffer.appendByte(getVersion());
		buffer.appendInt(getTimeStamp());
		return buffer;
	}

	public byte[] genAuthClient() {
		byte[] result = new byte[16];
		try {
			ByteBuffer buffer = new ByteBuffer();
			buffer.appendString(clientId, clientId.length());
			byte[] ba = new byte[9];
			buffer.appendBytes(ba);
			buffer.appendString(sharedSecret, sharedSecret.length());
			String timeStamp = "" + getTimeStamp();
			for (int i = 10 - timeStamp.length(); i > 0; i--)
				timeStamp = "0" + timeStamp;
			buffer.appendString(timeStamp, timeStamp.length());
			MessageDigest md = MessageDigest.getInstance("MD5");
			result = md.digest(buffer.getBuffer());
		} catch (Exception ex) {
			logger.error("Failed genAuthClient!");
		}
		return result;
	}

	public static String getHexDump(byte[] data) {
		String dump = "";
		try {
			int dataLen = data.length;
			for (int i = 0; i < dataLen; i++) {
				dump += Character.forDigit((data[i] >> 4) & 0x0f, 16);
				dump += Character.forDigit(data[i] & 0x0f, 16);
			}
		} catch (Throwable t) {
			// catch everything as this is for debug
			dump = "Throwable caught when dumping = " + t;
		}
		return dump;
	}

	public int genTimeStamp() {
		Date date = new Date();
		Format formatter = new SimpleDateFormat("MMddHHmmss");
		int timeStamp = Integer.parseInt(formatter.format(date), 10);
		return timeStamp;
	}

	public byte[] getAuthClient() {
		return authClient;
	}

	public void setAuthClient(byte[] authClient) {
		this.authClient = authClient;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	public String getSharedSecret() {
		return sharedSecret;
	}

	public void setSharedSecret(String sharedSecret) {
		this.sharedSecret = sharedSecret;
	}

	protected Response createResponse() {
		return new ConnectResp();
	}

	public boolean isTransmitter() {
		return (version == (byte) 0x18);
	}

	public boolean isReceiver() {
		return (version == (byte) 0x01);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cmpp.sms.ByteData#setData(cmpp.sms.util.ByteBuffer)
	 */
	public void setData(ByteBuffer buffer) throws PDUException {
		header.setData(buffer);
		setBody(buffer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cmpp.sms.ByteData#getData()
	 */
	public ByteBuffer getData() {
		ByteBuffer bodyBuf = getBody();
		header.setCommandLength(MinaConstant.PDU_HEADER_SIZE + bodyBuf.length());
		ByteBuffer buffer = header.getData();
		buffer.appendBuffer(bodyBuf);
		return buffer;
	}

	public String name() {
		return "CMPP Connect";
	}

	// private void writeObject(java.io.ObjectOutputStream out) throws
	// IOException {
	// out.write(this.getData().getBuffer());
	// }
	//
	// private void readObject(java.io.ObjectInputStream in) throws IOException,
	// ClassNotFoundException {
	// int length = in.readInt();
	// byte[] bytemsg = new byte[length - 4];
	// byte[] bytes = new byte[length];
	// in.read(bytemsg);
	// System.arraycopy(bytes, 0, length, 0, 4);
	// System.arraycopy(bytes, 4, bytemsg, 0, bytemsg.length);
	// try {
	// this.setData(new ByteBuffer(bytes));
	// } catch (PDUException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public String dump() {
		String rt = "\r\nLogin************************************"
				+ "\r\nclientId:		" + clientId + "\r\nauthClient:	"
				+ new String(authClient) + "\r\nversion:		" + version
				+ "\r\ntimeStamp:	" + timeStamp + "\r\nsharedSecret:	"
				+ sharedSecret
				+ "\r\n************************************Login";
		return rt;
	}
}
