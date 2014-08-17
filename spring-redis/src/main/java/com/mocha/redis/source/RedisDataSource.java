package com.mocha.redis.source;

import redis.clients.jedis.ShardedJedis;

/**
 * 
 * <strong>Title : RedisDataSource </strong>. <br>
 * <strong>Description : redis数据源接口.</strong> <br>
 * <strong>Create on : 2014年8月16日 下午10:15:39 </strong>. <br>
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
public interface RedisDataSource {
	/**
	 * 取得redis的客户端
	 * 方法描述.
	 * @return
	 */
    public abstract ShardedJedis getRedisClient();
    /**
     * 将资源返还给pool
     * 方法描述.
     * @param shardedJedis
     */
    public void returnResource(ShardedJedis shardedJedis);
    /**
     * 出现异常后，将资源返还给pool
     * 方法描述.
     * @param shardedJedis
     * @param broken
     */
    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}
