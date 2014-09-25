package com.mocha.unitcode.mina.common;

public class MinaConstant {
	public static final byte COMMAND_NAME_LENGTH = 12;
	public static final byte TRANSMITTER = (byte) 0x00;
	public static final byte RECEIVER = (byte) 0x01;
	public static final byte TRANSCEIVER = (byte) 0x02;

	public static final int CONNECTION_CLOSED = 0;
	public static final int CONNECTION_OPENED = 1;

	public static final long ACCEPT_TIMEOUT = 60000;
	public static final long RECEIVER_TIMEOUT = 60000;
	public static final long COMMS_TIMEOUT = 60000;
	public static final long QUEUE_TIMEOUT = 60000;
	public static final long RECEIVE_BLOCKING = 0;
	public static final long CONNECTION_RECEIVE_TIMEOUT = 0;
	public static int PDU_HEADER_SIZE = 12;

	// Command Set
	/**
	 * 请求连接
	 */
	public static final int UNITE_CONNECT = 0x00000001;
	/**
	 * 请求连接返回
	 */
	public static final int UNITE_CONNECT_RESP = 0x80000001;
	/**
	 * 请求拆除连接
	 */
	public static final int UNITE_TERMINATE = 0x00000002;
	/**
	 * 请求拆除连接返回值
	 */
	public static final int UNITE_TERMINATE_RESP = 0x80000002;
	/**
	 * 请求提交待办数据
	 */
	public static final int UNITE_TODO_SUBMIT = 0x00000003;
	/**
	 * 请求提交待办数据回值
	 */
	public static final int UNITE_TODO_SUBMIT_RESP = 0x80000003;
	/**
	 * 发送待办变更数据
	 */
	public static final int UNITE_TODOCHANGE_SUBMIT = 0x00000004;
	/**
	 * 发送待办变更数据回值
	 * 
	 */
	public static final int UNITE_TODOCHANGE_SUBMIT_RESP = 0x80000004;
	/**
	 * 发送待办对账数据
	 */
	public static final int UNITE_RTODO_SUBMIT = 0x00000005;
	/**
	 * 发送待办对账数据回值
	 */
	public static final int UNITE_RTODO_SUBMIT_RESP = 0x80000005;
	/**
	 * 发送待办对账变更数据
	 */
	public static final int UNITE_RTODOCHANGE_SUBMIT = 0x00000006;
	/**
	 * 发送待办对账变更数据回值
	 */
	public static final int UNITE_RTODOCHANGE_SUBMIT_RESP = 0x80000006;
	/**
	 * 请求连接
	 */
	public static final int UNITE_ACTIVE_TEST = 0x00000007;
	/**
	 * 请求连接
	 */
	public static final int UNITE_ACTIVE_TEST_RESP = 0x80000007;
	/**
	 * 提交待办处理返回数据
	 */
	public static final int UNITE_TODO_BACK_SUBMIT = 0x00000008;
	/**
	 * 提交待办处理返回数据回值
	 */
	public static final int UNITE_TODO_BACK_SUBMIT_RESP = 0x80000008;

	/**
	 * 请求前置采集采集数据
	 */
	public static final int UNITE_TODO_REQUEST_SUBMIT = 0x00000009;
	/**
	 * 请求前置采集采集数据回值
	 */
	public static final int UNITE_TODO_REQUEST_SUBMIT_RESP = 0x80000009;
	/**
	 * Vip待办数据发送
	 */
	public static final int UNITE_TODO_VIP_SUBMIT = 0x00000010;
	/**
	 * Vip待办数据返回
	 */
	public static final int UNITE_TODO_VIP_SUBMIT_RESP = 0x80000010;

	/**
	 * Vip待办变更数据发送
	 */
	public static final int UNITE_TODOCHANGE_VIP_SUBMIT = 0x00000011;
	/**
	 * Vip待办变更数据返回
	 */
	public static final int UNITE_TODOCHANGE_VIP_SUBMIT_RESP = 0x80000011;
	// Command_Status Error Codes
	public static final int ESME_ROK = 0x00000000;

	// Interface_Version
	public static final byte VERSION = 0x20;

	// Address_TON
	public static final byte GSM_TON_UNKNOWN = 0x00;
	public static final byte GSM_TON_INTERNATIONAL = 0x01;
	public static final byte GSM_TON_NATIONAL = 0x02;
	public static final byte GSM_TON_NETWORK = 0x03;
	public static final byte GSM_TON_SUBSCRIBER = 0x04;
	public static final byte GSM_TON_ALPHANUMERIC = 0x05;
	public static final byte GSM_TON_ABBREVIATED = 0x06;
	public static final byte GSM_TON_RESERVED_EXTN = 0x07;

	// Address_NPI
	public static final byte GSM_NPI_UNKNOWN = 0x00;
	public static final byte GSM_NPI_E164 = 0x01;
	public static final byte GSM_NPI_ISDN = GSM_NPI_E164;
	public static final byte GSM_NPI_X121 = 0x03;
	public static final byte GSM_NPI_TELEX = 0x04;
	public static final byte GSM_NPI_LAND_MOBILE = 0x06;
	public static final byte GSM_NPI_NATIONAL = 0x08;
	public static final byte GSM_NPI_PRIVATE = 0x09;
	public static final byte GSM_NPI_ERMES = 0x0A;
	public static final byte GSM_NPI_INTERNET = 0x0E;
	public static final byte GSM_NPI_WAP_CLIENT_ID = 0x12;
	public static final byte GSM_NPI_RESERVED_EXTN = 0x0F;

	// Port Value
	public static final int MIN_VALUE_PORT = 1024;
	public static final int MAX_VALUE_PORT = 65535;

	// Address Length
	public static final int MIN_LENGTH_ADDRESS = 7;

	// list of character encodings
	// see http://java.sun.com/j2se/1.3/docs/guide/intl/encoding.doc.html
	// from rt.jar

	// American Standard Code for Information Interchange
	public static final String ENC_ASCII = "ASCII";
	// Windows Latin-1
	public static final String ENC_CP1252 = "Cp1252";
	// ISO 8859-1, Latin alphabet No. 1
	public static final String ENC_ISO8859_1 = "ISO8859_1";
	// Sixteen-bit Unicode Transformation Format, big-endian byte order
	// with byte-order mark
	public static final String ENC_UTF16_BEM = "UnicodeBig";
	// Sixteen-bit Unicode Transformation Format, big-endian byte order
	public static final String ENC_UTF16_BE = "UnicodeBigUnmarked";
	// Sixteen-bit Unicode Transformation Format, little-endian byte order
	// with byte-order mark
	public static final String ENC_UTF16_LEM = "UnicodeLittle";
	// Sixteen-bit Unicode Transformation Format, little-endian byte order
	public static final String ENC_UTF16_LE = "UnicodeLittleUnmarked";
	// Eight-bit Unicode Transformation Format
	public static final String ENC_UTF8 = "UTF8";

	public static final String UTF8 = "UTF-8";
	// Sixteen-bit Unicode Transformation Format, byte order specified by
	// a mandatory initial byte-order mark
	public static final String ENC_UTF16 = "UTF-16";

	// 缓冲区大小
	public static final int BufferSize = 2048 * 5000;

	// log4j
	public static final String LOG_CONNECT = "sys_connect";
	public static final String LOG_TODO = "sys_todo";
	public static final String LOG_TODOCHANGE = "sys_todochage";
	public static final String LOG_ERROR = "sys_error";
	public static final String LOG_RTODO = "sys_rtodo";
	public static final String LOG_FAIL = "sys_fail";
	public static final String LOG_RTODOCHANGE = "sys_rtodochage";
	public static final String LOG_ACTIVE = "sys_active";
	public static final String LOG_TODO_BACK = "sys_todoback";
	public static final String LOG_TODO_REQUEST = "sys_todorequest";

	// 队列信息常量定义
	/**
	 * 优先级高队列
	 */
	public static final String QUEUE_PRIORITY_HIGH = "queue:high:";

	/**
	 * 普通队列
	 */
	public static final String QUEUE_COMMON = "queue:common:";
	/**
	 * VIP队列
	 */
	public static final String QUEUE_VIP = "queue:vip:";

	/**
	 * 队列最大值
	 */
	public static final Integer QUEUE_MAXSIZE = 100000;

}
