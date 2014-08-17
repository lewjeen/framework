package com.mocha.redis.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mocha.redis.client.RedisClientTemplate;

/**
 * <strong>Title : Test </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月17日 下午4:37:08 </strong>. <br>
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
public class Test {

	/**
	 * 方法描述.
	 * @param args
	 */
	public static void main(String[] args) {
        ApplicationContext ac =  new ClassPathXmlApplicationContext("classpath:redis-data-source.xml");
        RedisClientTemplate redisClient = (RedisClientTemplate)ac.getBean("redisClientTemplate");
//        redisClient.set("a", "abc");
        
//        redisClient.blpop("liujun");
        System.out.println(redisClient.blpop(0,"1111"));
//        System.out.println(redisClient.get("a"));
    }

}
