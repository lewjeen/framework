/**
 * 
 */
package com.mocha.unitcode.mina.pdu;

import java.util.Date;

/**
 * 
 * <strong>Title :TodoChangePrimitiveEntity< >. <br>
 * <strong>Description : 原始待变项变更数据实体.< > <br>
 * <strong>Create on : 2014年8月13日< >. <br>
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
public class TodoChangePrimitiveEntity {

	/**
	 * 指明是应用侧待办项标识或统一待办平台待办项标识（0：应用侧，1：待办平台侧）
	 */
	public static final String TODO_CHANGE_PRIMI_TYPE_YC = "0";
	public static final String TODO_CHANGE_PRIMI_TYPE_DC = "1";

	/**
	 * 待办生成应用AppId
	 */
	private String appId;
	/**
	 * 指明是应用侧待办项标识或统一待办平台待办项标识
	 */
	private String itemIdType;
	/**
	 * 待办项标识/应用系统待办项标识
	 */
	private String appItemId;
	/**
	 * 变更属性名称
	 */
	private String propertyName;
	/**
	 * 变更属性值
	 */
	private String propertyValue;
	/**
	 * 待办变更项创建时间
	 */
	private Date createTime;

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

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "TodoChangePrimitiveEntity [appId=" + appId + ", itemIdType="
				+ itemIdType + ", appItemId=" + appItemId + ", propertyName="
				+ propertyName + ", propertyValue=" + propertyValue
				+ ", createTime=" + createTime + "]";
	}

	public TodoChangePrimitiveEntity(String appId, String itemIdType,
			String appItemId, String propertyName, String propertyValue,
			Date createTime) {
		super();
		this.appId = appId;
		this.itemIdType = itemIdType;
		this.appItemId = appItemId;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.createTime = createTime;
	}

	public TodoChangePrimitiveEntity() {
		super();
	}
}
