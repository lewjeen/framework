package com.mocha.unitcode.mina.client;

import java.util.List;

import com.mocha.tydb_common.entity.TodoEntity;
import com.mocha.unitcode.mina.common.MinaConstant;
import com.mocha.unitcode.mina.pdu.Message;
import com.mocha.unitcode.mina.pdu.SubmitTodoEntity;
import com.mocha.unitcode.mina.pdu.Tools;
import com.mocha.util.verify.CRC16Verifier;

/**
 * <strong>Title : CoreClientImpl </strong>. <br>
 * <strong>Description : 核心节点与前置节点接口实现类.</strong> <br>
 * <strong>Create on : 2014年9月22日 下午4:16:34 </strong>. <br>
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
public class CoreClientImpl implements CoreClient {
	private static String nodeId;
	private static CoreClientCenterStore client;

	public CoreClientImpl(String nodeId, CoreClientCenterStore client) {
		this.nodeId = nodeId;
		this.client = client;
	}

	private <E> boolean send(E e, int commandId) {
		boolean flag = false;
		SubmitTodoEntity<E> todo = new SubmitTodoEntity<E>(commandId);
		Message<E> todoMessage = new Message<E>();
		if (e instanceof List<?>) {
			todoMessage.setEntityMessage(e, todoMessage.FORMAT_UTF8,
					todoMessage.TODO_TYPE_BATCH);
		} else {
			todoMessage.setEntityMessage(e, todoMessage.FORMAT_UTF8,
					todoMessage.TODO_TYPE_SINGLE);
		}

		todo.setMsgId(Tools.GetMsgid());
		todo.setNodeId(nodeId.getBytes());
		todo.setEntityMessage(todoMessage);
		todo.setCrc16Code(CRC16Verifier.getInstance()
				.getVerifyCode(todoMessage));
		flag = client.send(todo);
		return flag;

	}

	public boolean sendTodoEntity(TodoEntity entity) {
		return send(entity, MinaConstant.UNITE_TODO_SUBMIT);
	}

	public boolean sendTodoEntity(List<TodoEntity> entity) {
		return send(entity, MinaConstant.UNITE_TODO_SUBMIT);
	}

	@Override
	public boolean sendTodoChangeEntity(TodoEntity entity) {
		return send(entity, MinaConstant.UNITE_TODOCHANGE_SUBMIT);
	}

	@Override
	public boolean sendTodoChangeEntity(List<TodoEntity> entity) {
		return send(entity, MinaConstant.UNITE_TODOCHANGE_SUBMIT);
	}

	@Override
	public Object getTodoReceiptEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean sendRecTodoEntity(TodoEntity entity) {
		return send(entity, MinaConstant.UNITE_RTODO_SUBMIT);
	}

	@Override
	public boolean sendRecTodoEntity(List<TodoEntity> entity) {
		return send(entity, MinaConstant.UNITE_RTODO_SUBMIT);
	}

	@Override
	public boolean sendRecTodoChangeEntity(TodoEntity entity) {
		return send(entity, MinaConstant.UNITE_RTODOCHANGE_SUBMIT);
	}

	@Override
	public boolean sendRecTodoChangeEntity(List<TodoEntity> entity) {
		boolean flag = false;
		return send(entity, MinaConstant.UNITE_RTODOCHANGE_SUBMIT);
	}

	@Override
	public boolean sendVipTodoByUserid(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendVipTodoByApply(String ApplyId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sendVIPTodoEntity(TodoEntity entity) {
		// TODO Auto-generated method stub
		return send(entity, MinaConstant.UNITE_TODO_VIP_SUBMIT);
	}

	@Override
	public boolean sendVipTodoEntity(List<TodoEntity> entity) {
		// TODO Auto-generated method stub
		return send(entity, MinaConstant.UNITE_TODO_VIP_SUBMIT);
	}

	@Override
	public boolean sendVipTodoChangeEntity(TodoEntity entity) {
		// TODO Auto-generated method stub
		return send(entity, MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT);
	}

	@Override
	public boolean sendVipTodoChangeEntity(List<TodoEntity> entity) {
		// TODO Auto-generated method stub
		return send(entity, MinaConstant.UNITE_TODOCHANGE_VIP_SUBMIT);
	}

}
