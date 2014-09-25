package com.mocha.unitcode.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.mocha.unitcode.mina.client.factory.MinaProtocolCodecFactory;
import com.mocha.unitcode.mina.client.handler.MinaClientIoHandler;
import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.Connect;
import com.mocha.unitcode.mina.util.PropertyUtil;

public class CoreClientCenterStore {
	private final static Object LOCK = new Object();
	public static PropertyUtil pu = new PropertyUtil("ServerIPAddress");
	private static IoSession session;
	private static SocketConnector connector;
	private static CoreClientCenterStore _instance;
	private static String IP;
	private static int PORT;
	private static long TIMEOUTMILLIS;

	private CoreClientCenterStore() {

	}

	public static CoreClientCenterStore getInstance(String ip, int port,
			long timeoutMillis) {
		if (_instance == null) {
			_instance = new CoreClientCenterStore();
		}
		IP = ip;
		PORT = port;
		TIMEOUTMILLIS = timeoutMillis;
		return _instance;
	}

	private boolean init() {
		boolean is = true;
		if (session == null || !session.isConnected()) {
			try {
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"  初始化通道 init()");
				ConnectFuture future = getConnectFuture();
				// 等待连接创建完成
				future.awaitUninterruptibly();
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"进入MTClient。init  awaitUninterruptibly 结束");
				// 获取当前session
				session = future.getSession();
				Logger.getLogger(MinaConstant.LOG_CONNECT)
						.info("进入MTClient。init  结束" + " sessionid="
								+ session.getId());
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"CONNECT()" + " sessionid=" + session.getId());
				CONNECT();

			} catch (Exception e) {
				e.printStackTrace();
				is = false;
			}
		}
		return is;
	}

	private ConnectFuture getConnectFuture() {
		if (connector == null) {
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(
					"MinaClient。getConnectFuture  connector == null未连接，重新创建");
			connectConnector();
			return connector.connect();
		} else if (!connector.isActive()) {
			connector.dispose();
			connector = null;
			Logger.getLogger(MinaConstant.LOG_CONNECT)
					.info("MinaClient。getConnectFuture  !connector.isActive()未连接，重新创建");
			connectConnector();
			return connector.connect();
		} else {
			Logger.getLogger(MinaConstant.LOG_CONNECT).info("重新连接   重连机制");
			return connector.connect();
		}
	}

	private void connectConnector() {
		connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeoutMillis(TIMEOUTMILLIS);

		// 获取过滤器链
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();

		connector.getSessionConfig().setReadBufferSize(MinaConstant.BufferSize);
		// connector.setConnectTimeoutMillis(30000);
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 添加编码过滤器 处理乱码、编码问题
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new MinaProtocolCodecFactory(Charset
						.forName(MinaConstant.UTF8))));
		// 消息核心处理器
		connector.setHandler(new MinaClientIoHandler(LOCK));
		// 设置默认连接的地址和端口
		// String ip = StringUtils.defaultIfEmpty(pu.getValue("mina.server.ip"),
		// "127.0.0.1");
		// int port = Integer.parseInt(StringUtils.defaultIfEmpty(
		// pu.getValue("mina.server.port"), "7890"));
		connector.setDefaultRemoteAddress(new InetSocketAddress(IP, PORT));
	}

	public synchronized boolean send(Object message) {
		try {
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(
					"------------开始发送数据--------");
			if (init()) {
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"--------------------连接初始话成功----------------"
								+ message.hashCode() + " session ="
								+ session.getId());
				session.write(message);
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"--------------------发送消息成功----------------"
								+ message.hashCode() + " session ="
								+ session.getId());
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).info(
					"--------------------发送消息异常----------------"
							+ message.hashCode());
			Logger.getLogger(MinaConstant.LOG_ERROR).info(
					"--------------------发送消息异常----------------"
							+ e.getLocalizedMessage());
			e.printStackTrace();
			return false;
		}
	}

	public void close() {
		if (connector != null) {
			connector.dispose();
		}
	}

	public boolean isOk() {
		return init();
	}

	public void CONNECT() {
		if (init()) {
			Logger.getLogger("CONNECT 开始发送认证请求");
			Connect request = new Connect(MinaConstant.TRANSMITTER);
			String clientId = pu.getValue("mina.server.clientId") == null ? "10934871"
					: pu.getValue("mina.server.clientId");
			request.setClientId(clientId);
			String sharedSecret = pu.getValue("mina.server.password") == null ? "123456"
					: pu.getValue("mina.server.clientId");
			request.setSharedSecret(sharedSecret);
			request.setTimeStamp(request.genTimeStamp());
			request.setAuthClient(request.genAuthClient());
			request.setVersion((byte) 0x30);
			request.assignSequenceNumber();
			Logger.getLogger("CONNECT 发送认证请求ClientId=" + clientId
					+ "   sharedSecret= " + sharedSecret);
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(request.dump());
			session.write(request);
		}
	}
}
