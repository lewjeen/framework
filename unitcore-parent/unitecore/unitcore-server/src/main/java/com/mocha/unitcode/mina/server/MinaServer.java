package com.mocha.unitcode.mina.server;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.integration.jmx.IoServiceMBean;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.server.factory.MinaProtocolCodecFactory;
import com.mocha.unitcode.mina.server.handler.MinaIoHandler;

/**
 * 
 * <strong>Title : MinaServer </strong>. <br>
 * <strong>Description : mina服务.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:07:30 </strong>. <br>
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
public class MinaServer extends IoHandlerAdapter {
	public static final int MSG_SIZE = 5000;
	public static final int MSG_COUNT = 10;
	private static final int PORT = 7890;
	private static final int BUFFER_SIZE = 2048 * 5000;

	public static final String OPEN = "open";

	public SocketAcceptor acceptor;
	public SocketConnector connector;

	private final Object LOCK = new Object();

	private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
		public Thread newThread(final Runnable r) {
			return new Thread(null, r, "MinaThread", 64 * 1024);
		}
	};

	private OrderedThreadPoolExecutor executor;

	public static AtomicInteger sent = new AtomicInteger(0);

	public MinaServer() throws IOException {
		executor = new OrderedThreadPoolExecutor(0, 1000, 60, TimeUnit.SECONDS,
				THREAD_FACTORY);
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"設置线程池corePoolSize=0  maximumPoolSize=1000 keepAliveTime=60  unit="
						+ TimeUnit.SECONDS + "  threadFactory");
		/**
		 * 配置SocketConnector监听器的I/O Processor的线程的数量, 此处的I/O
		 * Processor的线程数量由CPU的核数决定，但SocketConnector
		 * 的线程数量只有一个，也就是接收客户端连接的线程数只有一个， SocketConnector的线程数量不能配置。
		 * */
		acceptor = new NioSocketAcceptor(Runtime.getRuntime()
				.availableProcessors() + 1);
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"线程的数量: " + Runtime.getRuntime().availableProcessors() + 1);
		// 设置地址是否可以复用
		acceptor.setReuseAddress(true);
		// 设置Session返回消息缓存大小
		acceptor.getSessionConfig().setReceiveBufferSize(BUFFER_SIZE);

		acceptor.getSessionConfig().setSendBufferSize(BUFFER_SIZE);
		// 向Filter添加线程池
		acceptor.getFilterChain().addLast("threadPool",
				new ExecutorFilter(executor));
		// 向filter添加编码器
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new MinaProtocolCodecFactory()));
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		IoServiceMBean acceptorMBean = new IoServiceMBean(acceptor);
		ObjectName acceptorName;
		try {
			acceptorName = new ObjectName(acceptor.getClass().getPackage()
					.getName()
					+ ":type=acceptor,name="
					+ acceptor.getClass().getSimpleName());
			mBeanServer.registerMBean(acceptorMBean, acceptorName);
		} catch (MalformedObjectNameException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"MalformedObjectNameException  " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"NullPointerException  " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceAlreadyExistsException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"InstanceAlreadyExistsException  " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MBeanRegistrationException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"MBeanRegistrationException  " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotCompliantMBeanException e) {
			Logger.getLogger(MinaConstant.LOG_ERROR).error(
					"NotCompliantMBeanException  " + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start() throws Exception {
		final InetSocketAddress socketAddress = new InetSocketAddress(
				"127.0.0.1", PORT);
		acceptor.setHandler(new MinaIoHandler(LOCK));
		acceptor.bind(socketAddress);
		Logger.getLogger(MinaConstant.LOG_CONNECT).info(
				"MinaServer服务已启动，端口是" + PORT);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		if (!(cause instanceof IOException)) {
			Logger.getLogger(MinaConstant.LOG_ERROR)
					.error("Exception: ", cause);
		} else {
			Logger.getLogger(MinaConstant.LOG_ERROR).info(
					"I/O error: " + cause.getMessage());
		}
		session.close(true);
	}

	public static void main(String[] args) throws Exception {
		new MinaServer().start();
	}
}
