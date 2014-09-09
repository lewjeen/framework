package com.mocha.util.queue;

import java.util.Collection;
import java.util.Iterator;

/**
 * <strong>Title : QueueHandler </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月14日 下午2:30:30 </strong>. <br>
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
public interface QueueHandler<E> {

	// =========================对队列的操作
	public ConcurrentLinkedQueueExtends<E> createQueueByName(String name)
			throws Exception;

	public ConcurrentLinkedQueueExtends<E> createQueueByName(String name,
			int maxSize) throws Exception;

	public ConcurrentLinkedQueueExtends<E> getQueueByName(String name)
			throws Exception;

	public void removeQueueByName(String name);

	// 既然是队列，那么就应该屏蔽掉这些与队列特效不相符的操作方法。

	// =========================对队列中的元素的操作
	// 1.添加
	public boolean add(String queueName, E e) throws Exception;

	public boolean addAll(String queueName, Collection<? extends E> c)
			throws Exception;

	public boolean offer(String queueName, E e) throws Exception;

	// 2.得到但不删除
	public E element(String queueName) throws Exception;

	public E peek(String queueName) throws Exception;

	// 3.得到并删除
	public E remove(String queueName) throws Exception;

	public E poll(String queueName) throws Exception;

	// 4.删除特定元素
	public boolean remove(String queueName, Object o) throws Exception;

	public boolean removeAll(String queueName, Collection<?> c)
			throws Exception;

	// 5.其他操作
	public void clear(String queueName) throws Exception;

	public boolean isEmpty(String queueName) throws Exception;

	public int size(String queueName) throws Exception;

	public boolean contains(String queueName, Object o) throws Exception;

	public boolean containsAll(String queueName, Collection<?> c)
			throws Exception;

	public Object[] toArray(String queueName) throws Exception;

	public <T> T[] toArray(String queueName, T[] a) throws Exception;

	public Iterator<E> iterator(String queueName) throws Exception;
}
