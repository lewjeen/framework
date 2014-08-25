package com.mocha.unitcode.mina.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mocha.common.utils.CollectionUtils;
import com.mocha.tydb_common.entity.TodoChangeEntity;
import com.mocha.tydb_common.entity.TodoEntity;

/**
 * <strong>Title : ObjectUtil </strong>. <br>
 * <strong>Description : 对象处理工具.</strong> <br>
 * <strong>Create on : 2014年8月21日 下午6:51:47 </strong>. <br>
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
public class ObjectUtil {

	/**
	 * @param <T>
	 * 
	 */
	public static Class getClass(Object object) {

		if (object instanceof TodoEntity) {
			return TodoEntity.class;
		} else if (object instanceof TodoChangeEntity) {
			return TodoChangeEntity.class;
		} else {
			return object.getClass();
		}

	}

	public static <E> Class getListClass(E e) {
		if (e instanceof List<?>) {
			return CollectionUtils.findCommonElementType((Collection<?>) e);
		} else {
			return getClass(e);
		}
	}

	public static <E> boolean checkList(E e) {
		if (e instanceof List<?>) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			Object object = Class.forName(
					"com.mocha.tydb_common.entity.TodoEntity").newInstance();
			List<TodoEntity> list = new ArrayList<TodoEntity>();
			TodoEntity entity = new TodoEntity();
			list.add(entity);
			list.add(entity);
			// list.add("1111");
			// list.add("2222");
			// list.add("3333");
			System.out.println("===========");
			// System.out.println("===========" + TodoEntity.class);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
