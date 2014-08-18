package com.mocha.redis.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.SortingParams;
import redis.clients.jedis.Tuple;

/**
 * <strong>Title : RedisClientTemplate </strong>. <br>
 * <strong>Description : redis操作封装接口.</strong> <br>
 * <strong>Create on : 2014年8月16日 下午10:21:16 </strong>. <br>
 * <p>
 * <strong>Copyright (C) lewjeen</strong> <br>
 * </p>
 * 
 * @author Administrator lewjeen@163.com <br>
 * @version <strong> Java v7.0.0</strong> <br>
 * <br>
 *          <strong>修改历史: .</strong> <br>
 *          修改人 修改日期 修改描述<br>
 *          -------------------------------------------<br>
 * <br>
 * <br>
 */
public abstract interface RedisClientTemplate {
	/**
	 * 说明：<br>
	 * SET key value [EX seconds] [PX milliseconds] [NX|XX]<br>
	 * 将字符串值 value 关联到 key 。<br>
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。<br>
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。<br>
	 * 
	 * @param key
	 * <br>
	 * @param value
	 * <br>
	 * @return
	 */
	public abstract String set(String key, String value);

	/**
	 * 
	 * SET key value [EX seconds] [PX milliseconds] [NX|XX] <br>
	 * 将字符串值 value 关联到 key 。 <br>
	 * 如果 key 已经持有其他值， SET 就覆写旧值，无视类型。 <br>
	 * 对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除。 <br>
	 * EX second：设置键的过期时间为 second 秒。<br>
	 * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 <br>
	 * NX ：只在键不存在时，才对键进行设置操作。 <br>
	 * XX ：只在键已经存在时，才对键进行设置操作。 <br>
	 * 
	 * @param nxxx
	 * @param expx
	 * @param time
	 *            <
	 * @return
	 */
	public abstract String set(String key, String value, String nxxx,
			String expx, long time);

	/**
	 * 
	 * 返回 key 所关联的字符串值。 <br>
	 * 如果 key 不存在那么返回特殊值 nil 。 <br>
	 * 假如 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。 <br>
	 * 
	 * @param key
	 * @return
	 */
	public abstract String get(String key);

	/**
	 * 
	 * EXISTS key <br>
	 * 检查给定 key 是否存在。 <br>
	 * 
	 * @param key
	 * @return
	 */
	public abstract Boolean exists(String key);

	/**
	 * 
	 * PERSIST key <br>
	 * 移除给定 key 的生存时间，将这个 key 从『易失的』(带生存时间 key )转换成『持久的』(一个不带生存时间、永不过期的 key )。 <br>
	 * 
	 * @param key
	 * @return 当生存时间移除成功时，返回 1 . 如果 key 不存在或 key 没有设置生存时间，返回 0 。
	 */
	public abstract Long persist(String key);

	/**
	 * 
	 * TYPE key <br>
	 * 返回 key 所储存的值的类型 <br>
	 * 
	 * @param key
	 * @return none (key不存在) string (字符串) list (列表) set (集合) zset (有序集) hash
	 *         (哈希表)
	 */
	public abstract String type(String key);

	/**
	 * 
	 * EXPIRE key seconds <br>
	 * 
	 * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。 <br>
	 * 
	 * @param key
	 * @param seconds
	 * @return 设置成功返回 1 。 当 key 不存在或者不能为 key 设置生存时间时(比如在低于 2.1.3 版本的 Redis
	 *         中你尝试更新 key 的生存时间)，返回 0 。
	 */
	public abstract Long expire(String key, int seconds);

	/**
	 * 
	 * EXPIREAT 的作用和 EXPIRE 类似，都用于为 key 设置生存时间。 <br>
	 * 不同在于 EXPIREAT 命令接受的时间参数是 UNIX 时间戳(unix timestamp) <br>
	 * 
	 * @param key
	 * @param timestamp
	 * @return 如果生存时间设置成功，返回 1 。 当 key 不存在或没办法设置生存时间，返回 0 。
	 */
	public abstract Long expireAt(String key, long timestamp);

	/**
	 * TTL key 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。 <br>
	 * 
	 * @param key
	 * @return 当 key 不存在时，返回 -2 。 当 key 存在但没有设置剩余生存时间时，返回 -1 。 否则，以秒为单位，返回 key
	 *         的剩余生存时间。
	 */
	public abstract Long ttl(String key);

	/**
	 * SETBIT key offset value 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。 <br>
	 * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。 <br>
	 * 当 key 不存在时，自动生成一个新的字符串值。 <br>
	 * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。 <br>
	 * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。 <br>
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return 指定偏移量原来储存的位。
	 */
	public abstract Boolean setbit(String key, long offset, boolean value);

	/**
	 * SETBIT key offset value 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。 <br>
	 * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。 <br>
	 * 当 key 不存在时，自动生成一个新的字符串值。 <br>
	 * 字符串会进行伸展(grown)以确保它可以将 value 保存在指定的偏移量上。当字符串值进行伸展时，空白位置以 0 填充。 <br>
	 * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。 <br>
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return 指定偏移量原来储存的位。
	 */
	public abstract Boolean setbit(String key, long offset, String value);

	/**
	 * 
	 * GETBIT key offset <br>
	 * 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。 <br>
	 * 当 offset 比字符串值的长度大，或者 key 不存在时，返回 0 。 <br>
	 * 
	 * @param key
	 * @param offset
	 * @return 字符串值指定偏移量上的位(bit)。
	 */
	public abstract Boolean getbit(String key, long offset);

	/**
	 * 
	 * SETRANGE key offset value <br>
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始 <br>
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return 被 SETRANGE 修改之后，字符串的长度。
	 */
	public abstract Long setrange(String key, long offset, String value);

	/**
	 * 
	 * SETRANGE key offset value <br>
	 * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始 <br>
	 * 
	 * @param key
	 * @param offset
	 * @param value
	 * @return 被 SETRANGE 修改之后，字符串的长度。
	 */
	public abstract String getrange(String key, long offset, long value);

	/**
	 * 
	 * GETSET key value <br>
	 * 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。 <br>
	 * 当 key 存在但不是字符串类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return 返回给定 key 的旧值。 当 key 没有旧值时，也即是， key 不存在时，返回 nil 。
	 */
	public abstract String getSet(String key, String value);

	/**
	 * 
	 * 将 key 的值设为 value ，当且仅当 key 不存在。 <br>
	 * 若给定的 key 已经存在，则 SETNX 不做任何动作。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return 设置成功，返回 1 。 设置失败，返回 0 。
	 */
	public abstract Long setnx(String key, String value);

	/**
	 * 
	 * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。 <br>
	 * 如果 key 已经存在， SETEX 命令将覆写旧值。 <br>
	 * 
	 * @param key
	 * @param seconds
	 * @param value
	 * @return 设置成功时返回 OK 。 当 seconds 参数不合法时，返回一个错误。
	 */
	public abstract String setex(String key, int seconds, String value);

	/**
	 * 
	 * 将 key 所储存的值减去减量 decrement 。 <br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECRBY 操作。 <br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 <br>
	 * 
	 * @param key
	 * @param decrement
	 * @return 减去 decrement 之后， key 的值。
	 */
	public abstract Long decrBy(String key, long decrement);

	/**
	 * 
	 * DECR key <br>
	 * 将 key 中储存的数字值减一。 <br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作。 <br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误 <br>
	 * 
	 * @param key
	 * @return 执行 DECR 命令之后 key 的值。
	 */
	public abstract Long decr(String key);

	/**
	 * 
	 * INCRBY key increment <br>
	 * 将 key 所储存的值加上增量 increment 。 <br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCRBY 命令。 <br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 <br>
	 * 
	 * @param key
	 * @param increment
	 * @return 加上 increment 之后， key 的值
	 */
	public abstract Long incrBy(String key, long increment);

	/**
	 * 
	 * INCR key <br>
	 * 将 key 中储存的数字值增一。 <br>
	 * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。 <br>
	 * 如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。 <br>
	 * 
	 * @param key
	 * @return 执行 INCR 命令之后 key 的值。
	 */
	public abstract Long incr(String key);

	/**
	 * 
	 * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。 <br>
	 * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return 追加 value 之后， key 中字符串的长度。
	 */
	public abstract Long append(String key, String value);

	public abstract String substr(String key, int start, int end);

	/**
	 * 
	 * HSET key field value <br>
	 * 将哈希表 key 中的域 field 的值设为 value 。 <br>
	 * 如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。 <br>
	 * 如果域 field 已经存在于哈希表中，旧值将被覆盖。 <br>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。<br>
	 *         如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
	 */
	public abstract Long hset(String key, String field, String value);

	/**
	 * 
	 * HGET key field <br>
	 * 返回哈希表 key 中给定域 field 的值。 <br>
	 * 
	 * @param key
	 * @param field
	 * @return 给定域的值。 <br>
	 *         当给定域不存在或是给定 key 不存在时，返回 nil 。
	 */
	public abstract String hget(String key, String field);

	/**
	 * 
	 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。 <br>
	 * 若域 field 已经存在，该操作无效。 <br>
	 * 如果 key 不存在，一个新哈希表被创建并执行 HSETNX 命令。<br>
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return 设置成功，返回 1 。<br>
	 *         如果给定域已经存在且没有操作被执行，返回 0 。
	 */
	public abstract Long hsetnx(String key, String field, String value);

	/**
	 * HMSET key field value [field value ...]<br>
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中。<br>
	 * 
	 * 此命令会覆盖哈希表中已存在的域。<br>
	 * 
	 * 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作。<br>
	 * 
	 * @param key
	 * @param field
	 * @return 如果命令执行成功，返回 OK 。<br>
	 *         当 key 不是哈希表(hash)类型时，返回一个错误。
	 */
	public abstract String hmset(String key, Map<String, String> field);

	/**
	 * HMGET key field [field ...] <br>
	 * 返回哈希表 key 中，一个或多个给定域的值。 <br>
	 * 如果给定的域不存在于哈希表，那么返回一个 nil 值。 <br>
	 * 因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行 HMGET 操作将返回一个只带有 nil 值的表。 <br>
	 * 
	 * @param key
	 * @param field
	 * @return 一个包含多个给定域的关联值的表，表值的排列顺序和给定域参数的请求顺序一样。
	 */
	public abstract List<String> hmget(String key, String[] field);

	/**
	 * HINCRBY key field increment <br>
	 * 为哈希表 key 中的域 field 的值加上增量 increment 。 <br>
	 * 增量也可以为负数，相当于对给定域进行减法操作。 <br>
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令。 <br>
	 * 如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。 <br>
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。 <br>
	 * 
	 * @param key
	 * @param field
	 * @param increment
	 * @return 执行 HINCRBY 命令之后，哈希表 key 中域 field 的值。
	 */
	public abstract Long hincrBy(String key, String field, long increment);

	/**
	 * 
	 * HEXISTS key field <br>
	 * 查看哈希表 key 中，给定域 field 是否存在。 <br>
	 * 
	 * @param key
	 * @param field
	 * @return 如果哈希表含有给定域，返回 1 。 <br>
	 *         如果哈希表不含有给定域，或 key 不存在，返回 0
	 */
	public abstract Boolean hexists(String key, String field);

	/**
	 * 
	 * HDEL key field [field ...] <br>
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。 <br>
	 * 
	 * @param key
	 * @param field
	 * @return 被成功移除的域的数量，不包括被忽略的域。
	 */
	public abstract Long hdel(String key, String[] field);

	/**
	 * 
	 * HLEN key <br>
	 * 返回哈希表 key 中域的数量。 <br>
	 * 
	 * @param key
	 * @return 哈希表中域的数量。<br>
	 *         当 key 不存在时，返回 0 。
	 */
	public abstract Long hlen(String key);

	/**
	 * 
	 * HKEYS key <br>
	 * 返回哈希表 key 中的所有域。 <br>
	 * 
	 * @param key
	 * @return 一个包含哈希表中所有域的表。 <br>
	 *         当 key 不存在时，返回一个空表。
	 */
	public abstract Set<String> hkeys(String key);

	/**
	 * 
	 * HVALS key <br>
	 * 返回哈希表 key 中所有域的值。 <br>
	 * 
	 * @param key
	 * @return 一个包含哈希表中所有值的表。 <br>
	 *         当 key 不存在时，返回一个空表。
	 */
	public abstract List<String> hvals(String key);

	/**
	 * 
	 * HGETALL key <br>
	 * 返回哈希表 key 中，所有的域和值。 <br>
	 * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。 <br>
	 * 
	 * @param key
	 * @return 以列表形式返回哈希表的域和域的值。 <br>
	 *         若 key 不存在，返回空列表。
	 */
	public abstract Map<String, String> hgetAll(String key);

	/**
	 * 
	 * RPUSH key value [value ...] <br>
	 * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。 <br>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾：比如对一个空列表 mylist 执行 RPUSH mylist
	 * a b c ，得出的结果列表为 a b c ，等同于执行命令 RPUSH mylist a 、 RPUSH mylist b 、 RPUSH
	 * mylist c 。 <br>
	 * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。 <br>
	 * 当 key 存在但不是列表类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return 执行 RPUSH 操作后，表的长度。
	 */
	public abstract Long rpush(String key, String[] value);

	/**
	 * 
	 * LPUSH key value [value ...] <br>
	 * 将一个或多个值 value 插入到列表 key 的表头 <br>
	 * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头： 比如说，对空列表 mylist 执行命令 LPUSH
	 * mylist a b c ，列表的值将是 c b a ，这等同于原子性地执行 LPUSH mylist a 、 LPUSH mylist b 和
	 * LPUSH mylist c 三个命令。 <br>
	 * 如果 key 不存在，一个空列表会被创建并执行 LPUSH 操作。 <br>
	 * 当 key 存在但不是列表类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return 执行 LPUSH 命令后，列表的长度。
	 */
	public abstract Long lpush(String key, String[] value);

	public abstract Long lpush(String key, Object object);

	/**
	 * 
	 * 返回列表 key 的长度。 <br>
	 * 如果 key 不存在，则 key 被解释为一个空列表，返回 0 . <br>
	 * 如果 key 不是列表类型，返回一个错误。 <br>
	 * 
	 * @param key
	 * @return 列表 key 的长度。
	 */
	public abstract Long llen(String key);

	/**
	 * 
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 <br>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 <br>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return 一个列表，包含指定区间内的元素。
	 */
	public abstract List<String> lrange(String key, long start, long stop);

	/**
	 * 
	 * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 <br>
	 * 举个例子，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。 <br>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 <br>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。 <br>
	 * 当 key 不是列表类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return 命令执行成功时，返回 ok 。
	 */
	public abstract String ltrim(String key, long start, long stop);

	/**
	 * 
	 * 返回列表 key 中，下标为 index 的元素。 <br>
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。 <br>
	 * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。 <br>
	 * 如果 key 不是列表类型，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param index
	 * @return 列表中下标为 index 的元素。 如果 index 参数的值不在列表的区间范围内(out of range)，返回 nil 。
	 */
	public abstract String lindex(String key, long index);

	/**
	 * 
	 * 将列表 key 下标为 index 的元素的值设置为 value 。 <br>
	 * 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param index
	 * @param value
	 * @return 操作成功返回 ok ，否则返回错误信息。
	 */
	public abstract String lset(String key, long index, String value);

	/**
	 * 
	 * 根据参数 count 的值，移除列表中与参数 value 相等的元素。 <br>
	 * count 的值可以是以下几种： <br>
	 * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。 count < 0 :
	 * 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。 count = 0 : 移除表中所有与 value
	 * 相等的值。
	 * 
	 * @param key
	 * @param count
	 * @param value
	 * @return 被移除元素的数量。 因为不存在的 key 被视作空表(empty list)，所以当 key 不存在时， LREM 命令总是返回
	 *         0
	 */
	public abstract Long lrem(String key, long count, String value);

	/**
	 * 
	 * LPOP key <br>
	 * 移除并返回列表 key 的头元素。 <br>
	 * 
	 * @param key
	 * @return 列表的头元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract String lpop(String key);

	/**
	 * 
	 * LPOP key <br>
	 * 移除并返回列表 key 的头元素。 <br>
	 * 
	 * @param key
	 * @return 列表的头元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract Object lpopObject(String key);

	/**
	 * 
	 * RPOP key <br>
	 * 移除并返回列表 key 的尾元素。 <br>
	 * 
	 * @param key
	 * @return 列表的尾元素。 当 key 不存在时，返回 nil 。
	 */
	public abstract String rpop(String key);

	/**
	 * 
	 * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 <br>
	 * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。 <br>
	 * 当 key 不是集合类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 被添加到集合中的新元素的数量，不包括被忽略的元素。
	 */
	public abstract Long sadd(String key, String[] member);

	/**
	 * 
	 * SMEMBERS key <br>
	 * 返回集合 key 中的所有成员。 <br>
	 * 不存在的 key 被视为空集合 <br>
	 * 
	 * @param key
	 * @return 集合中的所有成员。
	 */
	public abstract Set<String> smembers(String key);

	/**
	 * 
	 * SREM key member [member ...] <br>
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。 <br>
	 * 当 key 不是集合类型，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 被成功移除的元素的数量，不包括被忽略的元素。
	 */
	public abstract Long srem(String key, String[] member);

	/**
	 * SPOP key <br>
	 * 移除并返回集合中的一个随机元素。 <br>
	 * 
	 * @param key
	 * @return 被移除的随机元素。 当 key 不存在或 key 是空集时，返回 nil 。
	 */
	public abstract String spop(String key);

	/**
	 * SCARD key <br>
	 * 返回集合 key 的基数(集合中元素的数量)。 <br>
	 * 
	 * @param key
	 * @return 集合的基数。 当 key 不存在时，返回 0 。
	 */
	public abstract Long scard(String key);

	/**
	 * 
	 * SISMEMBER key member <br>
	 * 判断 member 元素是否集合 key 的成员。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 如果 member 元素是集合的成员，返回 1 。 <br>
	 *         如果 member 元素不是集合的成员，或 key 不存在，返回 0 。
	 */
	public abstract Boolean sismember(String key, String member);

	/**
	 * SRANDMEMBER key <br>
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。 方法描述. <br>
	 * 
	 * @param key
	 * @return 只提供 key 参数时，返回一个元素； <br>
	 *         如果集合为空，返回 nil 。 如果集合为空，返回空数组。
	 */
	public abstract String srandmember(String key);

	/**
	 * 
	 * STRLEN key <br>
	 * 返回 key 所储存的字符串值的长度。 <br>
	 * 当 key 储存的不是字符串值时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @return 字符串值的长度。 当 key 不存在时，返回 0 。
	 */
	public abstract Long strlen(String key);

	/**
	 * 
	 * ZADD key score member <br>
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。 <br>
	 * 
	 * @param key
	 * @param score
	 * @param member
	 * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
	 */
	public abstract Long zadd(String key, double score, String member);

	/**
	 * 
	 * ZADD key score member [[score member] [score member] ...] <br>
	 * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。 <br>
	 * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该
	 * member 在正确的位置上。 <br>
	 * score 值可以是整数值或双精度浮点数。 <br>
	 * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。 <br>
	 * 当 key 存在但不是有序集类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
	 */
	public abstract Long zadd(String key, Map<String, Double> member);

	/**
	 * 
	 * ZRANGE key start stop <br>
	 * 返回有序集 key 中，指定区间内的成员。 <br>
	 * 其中成员的位置按 score 值递增(从小到大)来排序。 <br>
	 * 具有相同 score 值的成员按字典序(lexicographical order )来排列。
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
	 */
	public abstract Set<String> zrange(String key, long start, long stop);

	/**
	 * 
	 * ZREM key member [member ...] <br>
	 * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。 <br>
	 * 当 key 存在但不是有序集类型时，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 被成功移除的成员的数量，不包括被忽略的成员。
	 */
	public abstract Long zrem(String key, String[] member);

	/**
	 * 
	 * ZINCRBY key increment member <br>
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment 。 <br>
	 * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让
	 * member 的 score 值减去 5 。 <br>
	 * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key
	 * increment member 。 <br>
	 * 当 key 不是有序集类型时，返回一个错误。 <br>
	 * score 值可以是整数值或双精度浮点数。 <br>
	 * 
	 * @param key
	 * @param increment
	 * @param member
	 * @return member 成员的新 score 值，以字符串形式表示。
	 */
	public abstract Double zincrby(String key, double increment, String member);

	/**
	 * 
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。 <br>
	 * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 如果 member 是有序集 key 的成员，返回 member 的排名。 如果 member 不是有序集 key 的成员，返回
	 *         nil 。
	 */
	public abstract Long zrank(String key, String member);

	/**
	 * 
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。 <br>
	 * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return 如果 member 是有序集 key 的成员，返回 member 的排名。 <br>
	 *         如果 member 不是有序集 key 的成员，返回 nil 。
	 */
	public abstract Long zrevrank(String key, String member);

	/**
	 * 
	 * ZREVRANGE key start stop [WITHSCORES] <br>
	 * 返回有序集 key 中，指定区间内的成员。 <br>
	 * 其中成员的位置按 score 值递减(从大到小)来排列。 具有相同 score 值的成员按字典序的逆序(reverse
	 * lexicographical order)排列。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
	 */
	public abstract Set<String> zrevrange(String key, long start, long stop);

	/**
	 * 
	 * 返回有序集 key 中，指定区间内的成员。 <br>
	 * 其中成员的位置按 score 值递减(从大到小)来排列。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param stop
	 * @return 指定区间内，带有 score 值(可选)的有序集成员的列表。
	 */
	public abstract Set<Tuple> zrangeWithScores(String key, long start,
			long stop);

	public abstract Set<Tuple> zrevrangeWithScores(String paramString,
			long paramLong1, long paramLong2);

	/**
	 * 
	 * ZCARD key <br>
	 * 返回有序集 key 的基数。 <br>
	 * 
	 * @param key
	 * @return 当 key 存在且是有序集类型时，返回有序集的基数。 <br>
	 *         当 key 不存在时，返回 0 。
	 */
	public abstract Long zcard(String key);

	/**
	 * 
	 * ZSCORE key member <br>
	 * 返回有序集 key 中，成员 member 的 score 值。 <br>
	 * 如果 member 元素不是有序集 key 的成员，或 key 不存在，返回 nil 。 <br>
	 * 
	 * @param key
	 * @param member
	 * @return member 成员的 score 值，以字符串形式表示。
	 */
	public abstract Double zscore(String key, String member);

	/**
	 * 
	 * SORT key <br>
	 * 返回或保存给定列表、集合、有序集合 key 中经过排序的元素。 <br>
	 * 
	 * @param key
	 * @return
	 */
	public abstract List<String> sort(String key);

	public abstract List<String> sort(String key,
			SortingParams paramSortingParams);

	/**
	 * 
	 * ZCOUNT key min max <br>
	 * 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。 <br>
	 * 
	 * @param key
	 * @param min
	 * @param max
	 * @return score 值在 min 和 max 之间的成员的数量。
	 */
	public abstract Long zcount(String key, double min, double max);

	public abstract Long zcount(String paramString1, String paramString2,
			String paramString3);

	public abstract Set<String> zrangeByScore(String paramString,
			double paramDouble1, double paramDouble2);

	public abstract Set<String> zrangeByScore(String paramString1,
			String paramString2, String paramString3);

	public abstract Set<String> zrevrangeByScore(String paramString,
			double paramDouble1, double paramDouble2);

	public abstract Set<String> zrangeByScore(String paramString,
			double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2);

	public abstract Set<String> zrevrangeByScore(String paramString1,
			String paramString2, String paramString3);

	public abstract Set<String> zrangeByScore(String paramString1,
			String paramString2, String paramString3, int paramInt1,
			int paramInt2);

	public abstract Set<String> zrevrangeByScore(String paramString,
			double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramString,
			double paramDouble1, double paramDouble2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString,
			double paramDouble1, double paramDouble2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramString,
			double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2);

	public abstract Set<String> zrevrangeByScore(String paramString1,
			String paramString2, String paramString3, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramString1,
			String paramString2, String paramString3);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString1,
			String paramString2, String paramString3);

	public abstract Set<Tuple> zrangeByScoreWithScores(String paramString1,
			String paramString2, String paramString3, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString,
			double paramDouble1, double paramDouble2, int paramInt1,
			int paramInt2);

	public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString1,
			String paramString2, String paramString3, int paramInt1,
			int paramInt2);

	public abstract Long zremrangeByRank(String paramString, long paramLong1,
			long paramLong2);

	public abstract Long zremrangeByScore(String paramString,
			double paramDouble1, double paramDouble2);

	public abstract Long zremrangeByScore(String paramString1,
			String paramString2, String paramString3);

	/**
	 * 
	 * LINSERT key BEFORE|AFTER pivot value <br>
	 * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。 <br>
	 * 当 pivot 不存在于列表 key 时，不执行任何操作。 <br>
	 * 当 key 不存在时， key 被视为空列表，不执行任何操作。 <br>
	 * 如果 key 不是列表类型，返回一个错误。 <br>
	 * 
	 * @param key
	 * @param paramLIST_POSITION
	 * @param pivot
	 * @param value
	 * @return 如果命令执行成功，返回插入操作完成之后，列表的长度。 <br>
	 *         如果没有找到 pivot ，返回 -1 。 如果 key 不存在或为空列表，返回 0 。
	 */
	public abstract Long linsert(String key,
			BinaryClient.LIST_POSITION paramLIST_POSITION, String pivot,
			String value);

	/**
	 * 
	 * LPUSHX key value <br>
	 * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。 <br>
	 * 
	 * @param key
	 * @param value
	 * @return LPUSHX 命令执行之后，表的长度。
	 */
	public abstract Long lpushx(String key, String[] value);

	/**
	 * 
	 * RPUSHX key value <br>
	 * 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表 <br>
	 * 
	 * @param key
	 * @param value
	 * @return RPUSHX 命令执行之后，表的长度。
	 */
	public abstract Long rpushx(String key, String[] value);

	/**
	 * 
	 * BLPOP key <br>
	 * BLPOP 是列表的阻塞式(blocking)弹出原语。 <br>
	 * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 <br>
	 * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。 <br>
	 * 
	 * @param key
	 * @return 如果列表为空，返回一个 nil 。 否则，返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key
	 *         ，第二个元素是被弹出元素的值。
	 */
	public abstract List<String> blpop(String key);

	/**
	 * 
	 * BRPOP key <br>
	 * BRPOP 是列表的阻塞式(blocking)弹出原语。 <br>
	 * 它是 RPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BRPOP 命令阻塞，直到等待超时或发现可弹出元素为止。 <br>
	 * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的尾部元素。 <br>
	 * 
	 * @param key
	 * @return
	 */
	public abstract List<String> brpop(String key);

	/**
	 * 
	 * 删除给定的一个 key 。 <br>
	 * 
	 * @param key
	 * @return 被删除 key 的数量。
	 */
	public abstract Long del(String key);

	/**
	 * 
	 * 打印一个特定的信息 message ，测试时使用 <br>
	 * 
	 * @param message
	 * @return message 自身。
	 */
	public abstract String echo(String message);

	/**
	 * 
	 * 将当前数据库的 key 移动到给定的数据库 db 当中。 <br>
	 * 如果当前数据库(源数据库)和给定数据库(目标数据库)有相同名字的给定 key ，或者 key 不存在于当前数据库，那么 MOVE 没有任何效果。 <br>
	 * 
	 * @param key
	 * @param db
	 * @return 移动成功返回 1 ，失败则返回 0 。
	 */
	public abstract Long move(String key, int db);

	/**
	 * 
	 * BITCOUNT key <br>
	 * 计算给定字符串中，被设置为 1 的比特位的数量。 <br>
	 * 
	 * 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行 BITCOUNT 操作，结果为 0 。 <br>
	 * 
	 * @param key
	 * @return 被设置为 1 的位的数量。
	 */
	public abstract Long bitcount(String key);

	/**
	 * 
	 * BITCOUNT key [start] [end] <br>
	 * 计算给定字符串中，被设置为 1 的比特位的数量。 <br>
	 * 一般情况下，给定的整个字符串都会被进行计数，通过指定额外的 start 或 end 参数，可以让计数只在特定的位上进行。 <br>
	 * start 和 end 参数的设置和 GETRANGE 命令类似，都可以使用负数值： 比如 -1 表示最后一个字节， -2
	 * 表示倒数第二个字节，以此类推。 <br>
	 * 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行 BITCOUNT 操作，结果为 0 。 <br>
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return 被设置为 1 的位的数量。
	 */
	public abstract Long bitcount(String key, long start, long end);

	@Deprecated
	public abstract ScanResult<Map.Entry<String, String>> hscan(
			String paramString, int paramInt);

	@Deprecated
	public abstract ScanResult<String> sscan(String paramString, int paramInt);

	@Deprecated
	public abstract ScanResult<Tuple> zscan(String paramString, int paramInt);

	public abstract ScanResult<Map.Entry<String, String>> hscan(
			String paramString1, String paramString2);

	public abstract ScanResult<String> sscan(String paramString1,
			String paramString2);

	public abstract ScanResult<Tuple> zscan(String paramString1,
			String paramString2);

	public abstract Long pfadd(String paramString, String[] paramArrayOfString);

	public abstract long pfcount(String paramString);

	/**
	 * 
	 * 取出队列中的数据
	 * 
	 * @param timeout
	 *            超时时间
	 * @param key
	 *            队列key值
	 * @return 返回List对象
	 */
	public List<String> blpop(int timeout, String key);

	/**
	 * 
	 * 设置对象
	 * 
	 * @param key
	 * @param object
	 * @return
	 */
	public String set(String key, Object object);

	/**
	 * 
	 * 获取对象
	 * 
	 * @param key
	 * @return
	 */
	public Object getObject(String key);

}
