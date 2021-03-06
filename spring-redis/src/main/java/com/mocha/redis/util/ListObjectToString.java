package com.mocha.redis.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

public abstract class ListObjectToString<E>{
	
	/**
	 * 
	 * 把实体转化为字符
	 * @param list
	 * @return
	 */
	public List<String> ToObjectString(List<E> list){
		List<String> listString=new ArrayList<String>();
		String str="";
		if (!CollectionUtils.isEmpty(list)) {
			for (E e : list) {
				str=JSON.toJSONString(e);
				listString.add(str);
			}
		}
		return listString;
	}
	/**
	 * 
	 * 把实体转化为字符
	 * @param list
	 * @return
	 */
	public List<E> ToStringObject(List<String> list,Class<E> clazz){
		List<E> listString=new ArrayList<E>();
		E e=null;
		if (!CollectionUtils.isEmpty(list)) {
			for (String str : list) {
				e=JSON.parseObject(str, clazz);
				listString.add(e);
			}
		}
		return listString;
	}
}
