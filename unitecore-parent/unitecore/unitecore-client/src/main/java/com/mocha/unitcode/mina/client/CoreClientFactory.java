package com.mocha.unitcode.mina.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mocha.common.utils.StringUtils;

public class CoreClientFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(CoreClientFactory.class);
	/**
	 * 默认连接IP
	 */
	private static String _defaultIP = "127.0.0.1";
	/**
	 * 默认连接端口
	 */
	private static int _defaultPort = 7890;
	/**
	 * 默认连接超时时间单位毫秒
	 */
	private static long _defaultTimeoutMillis = 10000L;
	/**
	 * 客户端连接缓存信息，根据连接前置信息
	 */
	private static Map<String, CoreClient> clientMap = new HashMap<String, CoreClient>();

	/**
	 * 
	 * 清除缓存内容
	 */
	public static void resetClient() {
		clientMap.clear();
	}

	/**
	 * 
	 * 验证前置节点ID是否正确，并把前置节点转化为小写字母
	 * 
	 * @param nodeId
	 *            前置节点ID
	 * @return
	 * @throws Exception
	 */
	private static String checknodeId(String nodeId) throws Exception {
		if (!(checkId(nodeId))) {
			throw new Exception(nodeId);
		}
		return nodeId.toUpperCase();
	}

	private static boolean checkId(String nodeId) {
		if (!StringUtils.hasLength(nodeId)) {
			return false;
		}
		return (nodeId.indexOf("@") >= 0);
	}

	public static synchronized CoreClient getInstance(String ip, int port,
			long defaultTimeoutMillis, String nodeId) throws Exception {
		// 验证nodeid并转化为大写字母
		String nodeIds = checknodeId(nodeId);
		CoreClient instance = null;
		// 判断缓存clientMap中是否存在连接客户端对象
		logger.info("开始验证缓存clientMap中是否存在连接客户端对象");
		if (clientMap.containsKey(nodeIds)) {
			logger.info("开始验证缓存clientMap中是否存在连接客户端对象------已存在");
			instance = (CoreClient) clientMap.get(nodeIds);
		} else {
			logger.info("开始验证缓存clientMap中是否存在连接客户端对象------从新创建连接");
			instance = createNewInstance(nodeId, ip, defaultTimeoutMillis, port);
			clientMap.put(nodeIds, instance);
		}
		return instance;

	}

	public static synchronized CoreClient getInstance(String ip, int port,
			String nodeId) throws Throwable {

		return getInstance(ip, port, _defaultTimeoutMillis, nodeId);

	}

	@SuppressWarnings("unused")
	private static CoreClient createNewInstance(String nodeId, String ip,
			long timeoutMillis, int port) throws Exception {
		CoreClientImpl instance = null;
		// 同步缓存对象
		synchronized (clientMap) {
			// 获取客户端连接对象
			com.mocha.unitcode.mina.client.CoreClientCenterStore client = CoreClientCenterStore
					.getInstance(ip, port, timeoutMillis);
			// 验证连接是否正常
			if (client.isOk()) {
				instance = new CoreClientImpl(nodeId, client);
				logger.info("createNewInstance 连接服务成功！");
			} else {
				logger.error("createNewInstance 连接服务异常");
				throw new Exception("连接服务异常");
			}

		}
		return instance;
	}
}
