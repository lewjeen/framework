package com.mocha.unitcode.mina.pdu;

import java.util.Date;

/**
 * 
 * 
 * <strong>Title :TodoChangeEntity< >. <br>
 * <strong>Description : 统一待办平台待办项实体---待办变更.< > <br>
 * <strong>Create on : 2014年8月12日< >. <br>
 * <p>
 * <strong>Copyright (C) Mocha Software Co.,Ltd.< > <br>
 * </p>
 * 
 * @author hexuhui hexh@mochasoft.com.cn <br>
 * @version <strong>全国统一待办< > <br>
 * <br>
 *          <strong>修改历史: .< > <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public class TodoChangeEntity {

	/**
	 * 待办平台待办项标识（0：应用侧，1：待办平台侧）
	 */
	public static final String TODO_CAHNGE_ITEM_TYPE_YC = "0";
	public static final String TODO_CAHNGE_ITEM_TYPE_DC = "1";

	/**
	 * 主键ID
	 */
	private String itemChangeId;
	/**
	 * 统一待办平台待办项唯一标识
	 */
	private String itemId;
	/**
	 * 待办生成应用唯一标识
	 */
	private String appId;
	/**
	 * 待办平台待办项标识
	 */
	private String itemIdType;
	/**
	 * 待办生成应用待办项标识
	 */
	private String appItemId;
	/**
	 * 待办项变更创建时间
	 */
	private Date createTime;
	/**
	 * 变更时间
	 */
	private Date updateTime;
	/**
	 * 待办项变更序号
	 */
	private String itemUpdateId;
	/**
	 * 变更字段名称
	 */
	private String propertyName;
	/**
	 * 字段历史值
	 */
	private String previousValue;
	/**
	 * 字段当前值
	 */
	private String currentValue;
	/**
	 * 预留字段1
	 */
	private String reserved1;
	/**
	 * 预留字段2
	 */
	private String reserved2;
	/**
	 * 预留字段3
	 */
	private String reserved3;
	/**
	 * 预留字段4
	 */
	private String reserved4;
	/**
	 * 预留字段5
	 */
	private String reserved5;

	public String getItemChangeId() {
		return itemChangeId;
	}

	public void setItemChangeId(String itemChangeId) {
		this.itemChangeId = itemChangeId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getItemIdType() {
		return itemIdType;
	}

	public void setItemIdType(String itemIdType) {
		this.itemIdType = itemIdType;
	}

	public String getAppItemId() {
		return appItemId;
	}

	public void setAppItemId(String appItemId) {
		this.appItemId = appItemId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getItemUpdateId() {
		return itemUpdateId;
	}

	public void setItemUpdateId(String itemUpdateId) {
		this.itemUpdateId = itemUpdateId;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPreviousValue() {
		return previousValue;
	}

	public void setPreviousValue(String previousValue) {
		this.previousValue = previousValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getReserved2() {
		return reserved2;
	}

	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	public String getReserved3() {
		return reserved3;
	}

	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	public String getReserved4() {
		return reserved4;
	}

	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	public String getReserved5() {
		return reserved5;
	}

	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}

	@Override
	public String toString() {
		return "TodoChangeEntity [itemChangeId=" + itemChangeId + ", itemId="
				+ itemId + ", appId=" + appId + ", itemIdType=" + itemIdType
				+ ", appItemId=" + appItemId + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", itemUpdateId="
				+ itemUpdateId + ", propertyName=" + propertyName
				+ ", previousValue=" + previousValue + ", currentValue="
				+ currentValue + ", reserved1=" + reserved1 + ", reserved2="
				+ reserved2 + ", reserved3=" + reserved3 + ", reserved4="
				+ reserved4 + ", reserved5=" + reserved5 + "]";
	}

	public TodoChangeEntity(String itemChangeId, String itemId, String appId,
			String itemIdType, String appItemId, Date createTime,
			Date updateTime, String itemUpdateId, String propertyName,
			String previousValue, String currentValue, String reserved1,
			String reserved2, String reserved3, String reserved4,
			String reserved5) {
		super();
		this.itemChangeId = itemChangeId;
		this.itemId = itemId;
		this.appId = appId;
		this.itemIdType = itemIdType;
		this.appItemId = appItemId;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.itemUpdateId = itemUpdateId;
		this.propertyName = propertyName;
		this.previousValue = previousValue;
		this.currentValue = currentValue;
		this.reserved1 = reserved1;
		this.reserved2 = reserved2;
		this.reserved3 = reserved3;
		this.reserved4 = reserved4;
		this.reserved5 = reserved5;
	}

	public TodoChangeEntity() {
		super();
	}
}
