package com.mocha.unitcode.mina.code;

import org.apache.log4j.Logger;

/**
 * <strong>Title : MinaObject </strong>. <br>
 * <strong>Description : 类的描述信息.</strong> <br>
 * <strong>Create on : 2014年8月8日 上午10:17:57 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * @author 刘军  liujun1@mochasoft.com.cn <br>
 * @version <strong>Mocha JavaOA v7.0.0</strong> <br>
 * <br>
 * <strong>修改历史: .</strong> <br>
 * 修改人 修改日期 修改描述<br>
 * -------------------------------------------<br>
 * <br>
 * <br>
 */
public class MinaObject {

	static final public String RT = "\n";
	static protected Logger logger = Logger.getLogger(MinaObject.class.getName());
	
	static public Logger getLogger() {
		return logger;
	}

	static public void setLogger(Logger myLogger) {
		logger = myLogger;
	}
}
