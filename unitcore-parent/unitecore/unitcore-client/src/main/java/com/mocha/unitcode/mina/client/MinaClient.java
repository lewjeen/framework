package com.mocha.unitcode.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
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
import com.mocha.unitcode.mina.pdu.SubmitTodoEntity;
import com.mocha.unitcode.mina.pdu.TodoChangeEntity;
import com.mocha.unitcode.mina.pdu.TodoEntityMessage;
import com.mocha.unitcode.mina.pdu.Tools;
import com.mocha.unitcode.mina.util.PropertyUtil;

public class MinaClient {
	private final static Object LOCK = new Object();
	public static PropertyUtil pu = new PropertyUtil("ServerIPAddress");
	private static IoSession session;
	private static SocketConnector connector;
	private static MinaClient _instance;

	private MinaClient() {
	}

	public static MinaClient getInstance() {
		if (_instance == null) {
			_instance = new MinaClient();
		}
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
		connector.setConnectTimeoutMillis(3000);

		// 获取过滤器链
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();

		connector.getSessionConfig().setReadBufferSize(MinaConstant.BufferSize);
		connector.setConnectTimeoutMillis(30000);
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

		// 添加编码过滤器 处理乱码、编码问题
		connector.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new MinaProtocolCodecFactory(Charset
						.forName(MinaConstant.UTF8))));
		// 消息核心处理器
		connector.setHandler(new MinaClientIoHandler(LOCK));
		// 设置默认连接的地址和端口
		String ip = StringUtils.defaultIfEmpty(pu.getValue("mina.server.ip"),
				"127.0.0.1");
		int port = Integer.parseInt(StringUtils.defaultIfEmpty(
				pu.getValue("mina.server.port"), "7890"));
		connector.setDefaultRemoteAddress(new InetSocketAddress(ip, port));
	}

	public synchronized boolean send(Object message) {
		try {
			Logger.getLogger(MinaConstant.LOG_CONNECT).info(
					"------------开始发送数据--------");
			if (init()) {
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"--------------------连接初始话成功----------------"
								+ message.hashCode());
				session.write(message);
				Logger.getLogger(MinaConstant.LOG_CONNECT).info(
						"--------------------发送消息成功----------------"
								+ message.hashCode());
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
			String clientId = pu.getValue("mina.server.clientId") == null ? "admin"
					: pu.getValue("mina.server.clientId");
			request.setClientId(clientId);
			String sharedSecret = pu.getValue("mina.server.password") == null ? "password"
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

	public static void main(String[] args) {
		byte[] msgid = Tools.GetMsgid();
		SubmitTodoEntity submitItem = new SubmitTodoEntity();
		SubmitTodoEntity submitItem1 = new SubmitTodoEntity();
		TodoEntity todo = new TodoEntity();
		TodoEntityMessage<TodoChangeEntity> tmchangeEntity = new TodoEntityMessage<TodoChangeEntity>();
		TodoChangeEntity changeEntity = new TodoChangeEntity();
		changeEntity.setAppId("qqqq@hq.cmcc");
		changeEntity.setAppItemId("2222222222222222");
		changeEntity.setPropertyName("111111");
		changeEntity
				.setPreviousValue("革开放胆子要大一些，敢于试验，不能像小脚女人一样。看准了的，就大胆地试，大胆地闯。”“走不出一条新路，就干不出新的事业。");
		tmchangeEntity.setEntityMessage(changeEntity, (byte) 10,
				tmchangeEntity.TODO_TYPE_SINGLE);
		submitItem.setMsgId(msgid);
		submitItem1.setMsgId(Tools.GetMsgid());
		// todo.setAppActivityId(appActivityId);
		todo.setAppItemId("ascvv");
		todo.setItemUrl("http://www.baidu.com/s?wd=http%3&tn=a");
		String title = "LLLLLLLLLLLLLLLLLL“改革开放胆子要大一些，敢于试验，不能像小脚女人一样。看准了的，就大胆地试，大胆地闯。”“走不出一条新路，就干不出新的事业。”“现在问题相当多，要解决，没有一股劲不行。要敢字当头，横下一条心。”“改革开放中许许多多的东西，都是群众在实践中提出来的”，“绝不是一个人脑筋就可以钻出什么新东西来”，“这是群众的智慧，集体的智慧”。昔日邓小平关于改革的形象描述再次被提及，今天听来依然振聋发聩。虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。虽然没有直接提及具体改革主张，但习近平在讲话中共有18次提到“改革开放”，分别伴随邓小平历史功绩和六大遗产字里行间。中共中央党校报刊社社长谢春涛告诉中新社记者，邓小平领导中国人民改革开放的成绩举世瞩目，他所说的“改革是中国的第二次革命”，领导中共有步骤地展开各方面体制改革，勇敢打开对外开放的大门，奠定了今天中国深化改革的基础。今天中共如此高规格缅怀邓小平的伟大功绩，本身就意味着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。着以时不我待的精神将改革开放、实现社会主义现代化、实现中华民族伟大复兴的伟大事业推向前进。LLLLLLLLlllllllasdlllllllllljjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjllllllllllllllllllllllll";
		todo.setItemTitle(title);
		TodoEntityMessage<TodoEntity> tm = new TodoEntityMessage<TodoEntity>();
		// tm.setEntityMessage(todo, (byte) 10);
		tm.setEntityMessage(todo, (byte) 10, tm.TODO_TYPE_SINGLE);
		System.out.println("==============="
				+ todo.getClass().getCanonicalName());
		// tm.getData();
		System.out.println("tm.getData()" + tm.getData());
		System.out.println("tm.getLength()" + tm.getLength());
		System.out.println("tm.getMsgFormat()" + tm.getMsgFormat());
		submitItem.setEntityMessage(tm);
		submitItem1.setEntityMessage(tmchangeEntity);
		// System.out.println("====================" + submitItem.getBody());
		// System.out.println("====================" + submitItem.getData());
		MinaClient.getInstance().CONNECT();
		// System.out.println("=========================" + submitItem.dump());
		MinaClient.getInstance().send(submitItem);
		MinaClient.getInstance().send(submitItem1);
	}
}
