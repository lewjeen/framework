package com.mocha.redis.test;

import java.util.ArrayList;
import java.util.List;

import com.mocha.redis.util.ListObject;
import com.mocha.redis.util.ListObjectToString;

/**
 * <strong>Title : TestUser </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月17日 下午3:13:45 </strong>. <br>
 * <p>
 * <strong>Copyright (C) lewjeen</strong> <br>
 * </p>
 * 
 * @author Administrator lewjeen@163.com <br>
 * @version <strong> Java v7.0.0</strong> <br>
 * <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public class TestUser extends ListObjectToString<User> {
	private List<String> toString(List<User> list) {
		return this.ToObjectString(list);
	}

	private List<User> toStringObject(List<String> list) {
		return this.ToStringObject(list, User.class);
	}

	public static void main(String[] args) {
		List<User> list = new ArrayList<User>();
		List<String> list1 = new ArrayList<String>();
		User user = new User();
		User user1 = new User();
		user.setId("1");
		user.setUser("liujun");
		user.setName("刘军");
		user1.setId("2");
		user1.setUser("zhangshan");
		user1.setName("张三");
		list.add(user);
		list.add(user1);
		TestUser test = new TestUser();
		// test.toString(list);
		list = ListObject.ToStringObject(ListObject.ToObjectString(list),
				User.class);
		// ListObject.ToObjectString(list);
		// System.out.println(test.toString(list));
		// System.out.println(test.toStringObject(test.toString(list)));
	}
}
