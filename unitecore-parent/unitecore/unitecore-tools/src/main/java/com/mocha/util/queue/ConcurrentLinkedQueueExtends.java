package com.mocha.util.queue;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * <strong>Title : ConcurrentLinkedQueueExtends </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月14日 下午2:28:34 </strong>. <br>
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
public class ConcurrentLinkedQueueExtends<E> extends ConcurrentLinkedQueue<E> {

	public ConcurrentLinkedQueueExtends() {

	}

	public ConcurrentLinkedQueueExtends(Collection<E> c) {
		super(c);
	}

	public ConcurrentLinkedQueueExtends(String name) {
		this.name = name;
	}

	public ConcurrentLinkedQueueExtends(String name, int maxSize)
			throws Exception {
		this(name);
		this.maxSize = maxSize;
	}

	// 队列名称
	private String name;

	// 队列大小，默认为100000
	private int maxSize = 100000;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

}
