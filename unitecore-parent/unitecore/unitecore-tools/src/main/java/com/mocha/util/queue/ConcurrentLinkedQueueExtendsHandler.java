package com.mocha.util.queue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <strong>Title : ConcurrentLinkedQueueExtendsHandler </strong>. <br>
 * <strong>Description : 队列管理.</strong> <br>
 * <strong>Create on : 2014年8月14日 下午2:31:02 </strong>. <br>
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
public class ConcurrentLinkedQueueExtendsHandler<E> implements QueueHandler<E> {

	/**
	 * 单例对象实例
	 */
	private static ConcurrentLinkedQueueExtendsHandler instance = null;

	// public static ConcurrentLinkedQueueExtendsHandler getInstance() {
	// if (instance == null) {
	// instance = new ConcurrentLinkedQueueExtendsHandler(); //
	// }
	// return instance;
	// }

	private static class ConcurrentLinkedQueueExtendsSingletonHolder {
		/**
		 * 单例对象实例
		 */
		static final ConcurrentLinkedQueueExtendsHandler INSTANCE = new ConcurrentLinkedQueueExtendsHandler();
	}

	public static ConcurrentLinkedQueueExtendsHandler getInstance() {
		return ConcurrentLinkedQueueExtendsSingletonHolder.INSTANCE;
	}

	/**
	 * private的构造函数用于避免外界直接使用new来实例化对象
	 */
	private ConcurrentLinkedQueueExtendsHandler() {
	}

	// 队列容器
	List<ConcurrentLinkedQueueExtends<E>> queueList = new LinkedList<ConcurrentLinkedQueueExtends<E>>();

	// 队列名字容器
	List<String> queueNames = new ArrayList<String>();

	// =========================对队列的操作
	/**
	 * 根据名称参数动态创建不包含任何元素的空队列
	 * 
	 * @param name
	 *            队列的名字
	 * @return
	 * @throws Exception
	 */
	@Override
	public ConcurrentLinkedQueueExtends<E> createQueueByName(String name)
			throws Exception {
		if (null == name || "".equals(name)) {
			throw new Exception("队列名称不能为空。");
		}

		if (queueNames.contains(name)) {
			throw new Exception("此名称已被使用，请另起其他名字。");
		}

		ConcurrentLinkedQueueExtends<E> queue = new ConcurrentLinkedQueueExtends<E>(
				name);

		// 将队列加到容器中
		queueList.add(queue);
		// 将本队列名加入容器中
		queueNames.add(name);

		return queue;
	}

	/**
	 * 根据名称参数动态创建不包含任何元素的空队列
	 * 
	 * @param name
	 *            队列的名字
	 * @return
	 * @throws Exception
	 */
	@Override
	public void createQueueByNames(String[] names) throws Exception {
		for (String name : names) {
			if (null == name || "".equals(name)) {
				throw new Exception("队列名称不能为空。");
			}

			if (queueNames.contains(name)) {
				throw new Exception("此名称已被使用，请另起其他名字。");
			}

			ConcurrentLinkedQueueExtends<E> queue = new ConcurrentLinkedQueueExtends<E>(
					name);

			// 将队列加到容器中
			queueList.add(queue);
			// 将本队列名加入容器中
			queueNames.add(name);
		}

	}

	/**
	 * 根据名称参数动态创建不包含任何元素的空队列, 并设置队列的大小。
	 * 
	 * @param name
	 *            队列的名字
	 * @param length
	 *            队列元素最大个数
	 * @return 新的队列对象
	 * @throws Exception
	 */
	@Override
	public ConcurrentLinkedQueueExtends<E> createQueueByName(String name,
			int maxSize) throws Exception {
		if (null == name || "".equals(name)) {
			throw new Exception("队列名称不能为空。");
		}

		if (queueNames.contains(name)) {
			throw new Exception("此名称已被使用，请另起其他名字。");
		}

		if (maxSize <= 0) {
			throw new Exception("队列大小必须大于零");
		}

		ConcurrentLinkedQueueExtends<E> queue = new ConcurrentLinkedQueueExtends<E>(
				name, maxSize);

		// 将队列加到容器中
		queueList.add(queue);
		// 将本队列名加入容器中
		queueNames.add(name);

		return queue;
	}

	public boolean checkqueueName(String name) {
		boolean flag = false;
		if (queueNames.contains(name)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据名称参数动态获得队列。
	 * 
	 * @param name
	 *            队列的名字
	 * @return
	 * @throws Exception
	 */
	@Override
	public ConcurrentLinkedQueueExtends<E> getQueueByName(String name)
			throws Exception {
		if (queueNames.contains(name)) {
			return queueList.get(queueNames.indexOf(name));
		} else
			throw new Exception("不存在名称为 " + name + "的队列");
	}

	/**
	 * 根据名称参数动态删除队列
	 * 
	 * @param name
	 *            队列的名字
	 */
	@Override
	public void removeQueueByName(String name) {
		if (queueNames.contains(name)) {
			queueList.remove(queueNames.indexOf(name));
			queueNames.remove(name);
		}
	}

	// =========================对队列中的元素的操作
	// 1.添加
	/**
	 * 根据队列名向队列中添加元素，若添加元素失败，则抛出异常
	 * 
	 * @param queueName
	 *            队列名字
	 * @param e
	 *            向队列中添加的元素
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean add(String queueName, E e) throws Exception {
		ConcurrentLinkedQueueExtends<E> queue = this.getQueueByName(queueName);
		if (queue.size() >= queue.getMaxSize()) {
			throw new Exception("队列已满，不允许继续添加元素。");
		}
		return queue.add(e);
	}

	/**
	 * 根据队列名向队列中添加元素集合，若添加元素集合失败，则抛出异常
	 * 
	 * @param queueName
	 *            队列名称
	 * @param c
	 *            collection containing elements to be added to this queue
	 * @return <tt>true</tt> if this queue changed as a result of the call
	 * @throws Exception
	 */
	@Override
	public boolean addAll(String queueName, Collection<? extends E> c)
			throws Exception {
		ConcurrentLinkedQueueExtends<E> queue = this.getQueueByName(queueName);
		if (queue.size() >= queue.getMaxSize()) {
			throw new Exception("队列已满，不允许继续添加元素。");
		} else if (queue.size() + c.size() > queue.getMaxSize()) {
			throw new Exception("新增的集合中的元素太多，以致超出队列容量上限");
		}
		return queue.addAll(c);
	}

	/**
	 * 根据队列名向队列中添加元素，若添加元素失败，则返回false
	 * 
	 * @param queueName
	 *            队列名
	 * @param e
	 *            向队列中添加的元素
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean offer(String queueName, E e) throws Exception {
		ConcurrentLinkedQueueExtends<E> queue = this.getQueueByName(queueName);
		if (queue.size() >= queue.getMaxSize()) {
			throw new Exception("队列已满，不允许继续添加元素。");
		}
		return queue.offer(e);
	}

	// 2.得到但不删除
	/**
	 * 获取但不移除此队列的头；如果此队列为空，则返回 null。
	 * 
	 * @param queueName
	 *            队列名字
	 * @return 队列的头，如果此队列为空，则返回 null
	 * @throws Exception
	 */
	@Override
	public E peek(String queueName) throws Exception {
		return this.getQueueByName(queueName).peek();
	}

	/**
	 * 获取但不移除此队列的头；如果此队列为空，则抛出异常。
	 * 
	 * @param queueName
	 *            队列名字
	 * @return 队列的头，如果此队列为空，则抛出异常
	 * @throws Exception
	 */
	@Override
	public E element(String queueName) throws Exception {
		return this.getQueueByName(queueName).element();
	}

	// 3.得到并删除
	/**
	 * 获取并移除此队列的头，如果此队列为空，则返回 null。
	 * 
	 * @param queueName
	 *            队列名字
	 * @return 此队列的头；如果此队列为空，则返回 null
	 * @throws Exception
	 */
	@Override
	public E poll(String queueName) throws Exception {
		return this.getQueueByName(queueName).poll();
	}

	/**
	 * 获取并移除此队列的头，如果此队列为空，则抛出异常。
	 * 
	 * @param queueName
	 *            队列名字
	 * @return
	 * @throws Exception
	 */
	@Override
	public E remove(String queueName) throws Exception {
		return this.getQueueByName(queueName).remove();
	}

	/**
	 * 根据队列名删除某一元素
	 * 
	 * @param queueName
	 *            队列名字
	 * @param o
	 *            删除的元素
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean remove(String queueName, Object o) throws Exception {
		return this.getQueueByName(queueName).remove(o);
	}

	/**
	 * 
	 * @param queueName
	 *            队列名字
	 * @param c
	 *            删除的元素
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean removeAll(String queueName, Collection<?> c)
			throws Exception {
		return this.getQueueByName(queueName).removeAll(c);
	}

	/**
	 * 清空队列
	 * 
	 * @param queueName
	 *            队列名字
	 * @throws Exception
	 */
	@Override
	public void clear(String queueName) throws Exception {
		this.getQueueByName(queueName).clear();
	}

	/**
	 * 判断 名称与参数一致的 队列 中是否有元素
	 * 
	 * @param queueName
	 *            队列名
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean isEmpty(String queueName) throws Exception {
		return this.getQueueByName(queueName).isEmpty();
	}

	/**
	 * 根据 队列名 判断队列中已有元素的歌声
	 * 
	 * @param queueName
	 *            队列名
	 * @return
	 * @throws Exception
	 */
	@Override
	public int size(String queueName) throws Exception {
		return this.getQueueByName(queueName).size();
	}

	/**
	 * 根据队列名判断队列中是否包含某一元素
	 * 
	 * @param queueName
	 *            队列名
	 * @param o
	 *            元素
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean contains(String queueName, Object o) throws Exception {
		return this.getQueueByName(queueName).contains(o);
	}

	/**
	 * 根据队列名判断队列中是否包含某些元素
	 * 
	 * @param queueName
	 *            队列名
	 * @param c
	 *            元素集合
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean containsAll(String queueName, Collection<?> c)
			throws Exception {
		return this.getQueueByName(queueName).containsAll(c);
	}

	/**
	 * 根据队列名将某一队列中的元素转换为Object数组形式
	 * 
	 * @param queueName
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object[] toArray(String queueName) throws Exception {
		return this.getQueueByName(queueName).toArray();
	}

	/**
	 * 根据队列名将某一队列中的元素转换为某一特定类型的数组形式
	 * 
	 * @param queueName
	 *            队列名
	 * @param a
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> T[] toArray(String queueName, T[] a) throws Exception {
		return this.getQueueByName(queueName).toArray(a);
	}

	/**
	 * 根据队列名遍历队列中所有元素
	 * 
	 * @param queueName
	 *            队列名
	 * @return
	 * @throws Exception
	 */
	@Override
	public Iterator<E> iterator(String queueName) throws Exception {
		return this.getQueueByName(queueName).iterator();
	}

	@Override
	public Iterator<E> iterator(String[] queueName) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
