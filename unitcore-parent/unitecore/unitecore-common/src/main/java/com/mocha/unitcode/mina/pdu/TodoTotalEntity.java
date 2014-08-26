/**
 * 
 */
package com.mocha.unitcode.mina.pdu;

import java.util.Date;

/**
 * 
 * <strong>Title :TodoTotalEntity.java. </strong>. <br>
 * <strong>Description :统一待办系统待办统计实体.</strong> <br>
 * <strong>Create on :2014年8月11日下午8:08:14 </strong>. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.</strong> <br>
 * </p>
 * 
 * @author zhaofg zhaofg@mochasoft.com.cn <br>
 * @version <strong>全国统一待办系统</strong> <br>
 * <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public class TodoTotalEntity {
	/**
	 * 待办生成应用系统ID
	 */
	private String appId;
	/**
	 * 数据总数(等于：待办+待阅+已办+已阅+注销)
	 */
	private long allCount;
	/**
	 * 待办数量
	 */
	private long dbCount;
	/**
	 * 待阅数量
	 */
	private long dyCount;
	/**
	 * 已办数量
	 */
	private long ybCount;
	/**
	 * 已阅数量
	 */
	private long yyCount;
	/**
	 * 注销待办数量
	 */
	private long zxCount;
	/**
	 * 待办创建开始时间yyyy-MM-dd 24HH:mm:ss
	 */
	private Date beginTime;
	/**
	 * 待办创建结束时间yyyy-MM-dd 24HH:mm:ss
	 */
	private Date endTime;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public long getAllCount() {
		return allCount;
	}

	public void setAllCount(long allCount) {
		this.allCount = allCount;
	}

	public long getDbCount() {
		return dbCount;
	}

	public void setDbCount(long dbCount) {
		this.dbCount = dbCount;
	}

	public long getDyCount() {
		return dyCount;
	}

	public void setDyCount(long dyCount) {
		this.dyCount = dyCount;
	}

	public long getYbCount() {
		return ybCount;
	}

	public void setYbCount(long ybCount) {
		this.ybCount = ybCount;
	}

	public long getYyCount() {
		return yyCount;
	}

	public void setYyCount(long yyCount) {
		this.yyCount = yyCount;
	}

	public long getZxCount() {
		return zxCount;
	}

	public void setZxCount(long zxCount) {
		this.zxCount = zxCount;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
