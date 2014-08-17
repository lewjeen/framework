package com.mocha.redis.util;

import java.util.Iterator;

/**
 * <strong>Title : Collection </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月17日 下午2:41:49 </strong>. <br>
 * <p>
 * <strong>Copyright (C) lewjeen</strong> <br>
 * </p>
 * @author Administrator lewjeen@163.com <br>
 * @version <strong> Java v7.0.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public abstract interface Collection<E> {
	
	public abstract int size();

	public abstract boolean isEmpty();

	public abstract boolean contains(Object paramObject);

	public abstract Object[] toArray();

	public abstract <T> T[] toArray(T[] paramArrayOfT);

	public abstract boolean add(E paramE);

	public abstract boolean remove(Object paramObject);

	public abstract boolean containsAll(Collection<?> paramCollection);

	public abstract boolean addAll(Collection<? extends E> paramCollection);

	public abstract boolean removeAll(Collection<?> paramCollection);

	public abstract boolean retainAll(Collection<?> paramCollection);

	public abstract void clear();

	public abstract boolean equals(Object paramObject);

	public abstract int hashCode();

}
