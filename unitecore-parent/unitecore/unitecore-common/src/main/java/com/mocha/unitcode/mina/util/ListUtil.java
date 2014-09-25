package com.mocha.unitcode.mina.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.mocha.common.utils.CollectionUtils;

public class ListUtil<E> {

	/**
	 * 
	 * 把实体转化为字符
	 * 
	 * @param <T>
	 * 
	 * @param list
	 * @return
	 */
	public static <E> List<String> ToObjectString(List<E> list) {
		List<String> listString = new ArrayList<String>();
		String str = "";
		if (!CollectionUtils.isEmpty(list)) {
			for (E e : list) {
				str = JSON.toJSONString(e);
				listString.add(str);
			}
		}
		return listString;
	}

	/**
	 * 
	 * 把实体转化为字符
	 * 
	 * @param list
	 * @return
	 */
	public static <E> List<E> ToStringObject(List<String> list, Class<E> clazz) {
		List<E> listString = new ArrayList<E>();
		E e = null;
		if (!CollectionUtils.isEmpty(list)) {
			for (String str : list) {
				e = JSON.parseObject(str, clazz);
				listString.add(e);
			}
		}
		return listString;
	}

	public static void main(String[] args) {

	}
}