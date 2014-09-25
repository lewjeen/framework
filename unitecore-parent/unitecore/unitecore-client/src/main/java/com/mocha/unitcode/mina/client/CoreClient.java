package com.mocha.unitcode.mina.client;

import java.util.List;

import com.mocha.tydb_common.entity.TodoEntity;

/**
 * <strong>Title : CodeClientServer </strong>. <br>
 * <strong>Description : 核心节点与前置节点接口定义.</strong> <br>
 * <strong>Create on : 2014年9月22日 下午3:18:38 </strong>. <br>
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
public interface CoreClient {
	/**
	 * 
	 * 单条发送待办数据.
	 * 
	 * @param entity
	 * @return
	 */
	public boolean sendTodoEntity(TodoEntity entity);

	/**
	 * 
	 * 多条待办数据发送
	 * 
	 * @param entity
	 *            待办数据集合
	 * @return
	 */
	public boolean sendTodoEntity(List<TodoEntity> entity);

	/**
	 * 
	 * VIP单条发送待办数据.
	 * 
	 * @param entity
	 * @return
	 */
	public boolean sendVIPTodoEntity(TodoEntity entity);

	/**
	 * 
	 * VIP多条待办数据发送
	 * 
	 * @param entity
	 *            待办数据集合
	 * @return
	 */
	public boolean sendVipTodoEntity(List<TodoEntity> entity);

	/**
	 * 
	 * 单条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体
	 * @return
	 */
	public boolean sendTodoChangeEntity(TodoEntity entity);

	/**
	 * 
	 * 多条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体集合
	 * @return
	 */
	public boolean sendTodoChangeEntity(List<TodoEntity> entity);

	/**
	 * 
	 * VIP单条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体
	 * @return
	 */
	public boolean sendVipTodoChangeEntity(TodoEntity entity);

	/**
	 * 
	 * vip多条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体集合
	 * @return
	 */
	public boolean sendVipTodoChangeEntity(List<TodoEntity> entity);

	/**
	 * 
	 * 获取返回状态数据
	 * 
	 * @return 返回待办数据发送状态数据（单个或多条）
	 */
	public Object getTodoReceiptEntity();

	/**
	 * 
	 * 对账单条待办数据发送
	 * 
	 * @param entity
	 *            待办实体
	 * @return
	 */
	public boolean sendRecTodoEntity(TodoEntity entity);

	/**
	 * 
	 * 对账多条待办数据发送
	 * 
	 * @param entity
	 *            待办实体集合
	 * @return
	 */
	public boolean sendRecTodoEntity(List<TodoEntity> entity);

	/**
	 * 
	 * 对账单条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体集合
	 * @return
	 */
	public boolean sendRecTodoChangeEntity(TodoEntity entity);

	/**
	 * 
	 * vip多条待办变更数据发送
	 * 
	 * @param entity
	 *            待办实体集合
	 * @return
	 */
	public boolean sendRecTodoChangeEntity(List<TodoEntity> entity);

	/**
	 * 
	 * VIP根据人员待办信息发送采集请求
	 * 
	 * @param userid
	 * @return
	 */
	public boolean sendVipTodoByUserid(String userid);

	/**
	 * 
	 * VIP根据应用待办信息发送采集请求
	 * 
	 * @param ApplyId
	 * @return
	 */
	public boolean sendVipTodoByApply(String ApplyId);

}
