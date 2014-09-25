package com.mocha.unitcode.mina.server.handler;

import com.mocha.util.queue.ConcurrentLinkedQueueExtendsHandler;

/**
 * <strong>Title : QueueHandler </strong>. <br>
 * <strong>Description : 队列核心节点处理.</strong> <br>
 * <strong>Create on : 2014年9月25日 下午5:23:40 </strong>. <br>
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
public class QueueHandler {
	// 私有化对象
	private QueueHandler() {
	}

	private static QueueHandler queue = new QueueHandler();

	private static ConcurrentLinkedQueueExtendsHandler instance = ConcurrentLinkedQueueExtendsHandler
			.getInstance();

	public static QueueHandler getInstance() {
		return queue;
	}

}
