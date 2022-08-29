package hry.redis;


import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {


	/**
	 * 根据key 获得一个value值
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Liu Shilei
	 * @param: @param
	 *             key
	 * @param: @return
	 * @return: String
	 * @Date : 2016年4月11日 下午6:28:54
	 * @throws:
	 */
	public String get(String key);

	/**
	 * 根据key保存和更新value
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Liu Shilei
	 * @param: @param
	 *             key
	 * @param: @param
	 *             value
	 * @param: @return
	 * @return: String
	 * @Date : 2016年4月11日 下午6:29:16
	 * @throws:
	 */
	public String save(String key, String value);

	/**
	 * 根据key保存和更新value，并设置一段时间后自动删除
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Liu Shilei
	 * @param: @param
	 *             key
	 * @param: @param
	 *             value
	 * @param: @param
	 *             second 秒
	 * @param: @return
	 * @return: String
	 * @Date : 2016年4月11日 下午6:36:23
	 * @throws:
	 */
	public void save(String key, String value, int second);

	/**
	 * 根据key删除
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Liu Shilei
	 * @param: @param
	 *             key
	 * @param: @return
	 * @return: Long
	 * @Date : 2016年4月11日 下午6:29:42
	 * @throws:
	 */
	public Long delete(String key);

	/**
	 * 根据key设置一条记录的过期时间 定时多少秒之后自动删除
	 * <p>
	 * TODO
	 * </p>
	 *
	 * @author: Liu Shilei
	 * @param: @param
	 *             second 秒
	 * @return: void
	 * @Date : 2016年4月11日 下午6:31:14
	 * @throws:
	 */
	public void setTime(String key, int second);

	public Set<String> keys(String patt);

	Set<String> hkeys(String key);

	String getLendConfig(String s);

	public void saveMap(String key, Map<String, String> map);

	public Map<String, String> getMap(String key) ;

	public String getMap(String key, String code) ;

	void hset(String hkey, String key, String value);


	String hget(String hkey, String key);

	Map<String,String> hgetall(String hkey);

	Long getKeyTime(String key);

	public Set<String> noPerkeys(String patt);

	/**
	 * 分布式加锁
	 * @param key
	 * @return
	 */
	public boolean lock(String key);

	/**
	 * 释放锁
	 * @param key
	 */
	public void unLock(String key);

	/**
	 * 保存一个单个键值对进到map里.如果mKey已经存在于map里将会覆盖之前的值。
	 *
	 * @param key
	 * @param mKey
	 * @param value
	 */
	public void saveMap(String key, String mKey, String value);
	/**
	 * 删除某个key 所对应的value。
	 *
	 * @param key
	 * @param mKey
	 * @return
	 */
	public String delMapKey(String key, String mKey) ;

	/**
	 * 获得Key的剩余生存时间
	 * @param key
	 * @return
	 */
	public Long getExpireTime(String key);

    void rpush(String key, String value);

	void zadd(String key, double score, String value);

	void zream(String key, double score);

	Set<String> zrange(String key, long start, long end);

	Set<String> zrangeByScore(String key, double min, double max);

	/**
	 * 没有则创建并返回1，有则不创建并返回0
	 * @param key
	 * @param value
	 * @return
	 */
    public Long setnx(String key, String value);

    public Long expire(String key, int seconds);


	public void publish(String channel, String message);

	public void subscribe(JedisPubSub jedisPubSub, String channel);

	/**
	 * set sadd
	 * @param key
	 * @param val
	 * @return
	 * @author denghf
	 */
	public Long sadd(String key, String... val);

	/**
	 * set smembers
	 * @param key
	 * @return
	 * @author denghf
	 */
	public Set<String> smembers(String key);

	/**
	 * zremrangeByScore
	 * @param key
	 * @param scoreMin
	 * @param scoreMax
	 * @return
	 * @author denghf
	 */
	public Long zremrangeByScore(String key, double scoreMin, double scoreMax);

	/**
	 * zrank
	 * @param key
	 * @param member
	 * @return
	 * @author denghf
	 */
	public Long zrank(String key, String member);

	/**
	 * zscore
	 * @param key
	 * @param member
	 * @return
	 * @author denghf
	 */
	public Double zscore(String key, String member);

	/**
	 * zrem
	 * @param key
	 * @param members
	 * @return
	 * @author denghf
	 */
	public Long zrem(String key, String... members);

	/**
	 * zrangeWithScores
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<Tuple> zrangeWithScores(String key, long start, long end);

	/**
	 * lpop
	 * @param key
	 * @return
	 * @author denghf
	 */
	public String lpop(String key);

	/**
	 * lrange
	 * @param key
	 * @return
	 * @author denghf
	 */
	public List<String> lrange(String key, long start, long end);

	/**
	 * lrem
	 * @param key
	 * @return
	 * @author denghf
	 */
	public Long lrem(String key, long count, String value);

	/**
	 * hdel
	 * @param key
	 * @return
	 * @author denghf
	 */
	public Long hdel(String key, String... field);

	/**
	 * lpush
	 * @param key
	 * @return
	 * @author denghf
	 */
	public Long lpush(String key, String... val);

	/**
	 * set
	 * @param key
	 * @param val
	 * @author denghf
	 * @return
	 */
	public String set(byte[] key, byte[] val);

	/**
	 * get
	 * @param key
	 * @author denghf
	 * @return
	 */
	public byte[] get(byte[] key);
	/*

	 */
	public Set<String> delkeys(String patt);

	/**
	 * zrevrange
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end);

	/**
	 * zrevrangeByScore
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrangeByScore(String key, String start, String end);

	/**
	 * 对某个键的值自增
	 * @param key
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setIncr(String key, int cacheSeconds);

	/**
	 * @Author: yaozh
	 * @Description: 删除set指定集合中的指定元素
	 * @param null
	 * @Date: 2020/5/19 16:44
	 */
	public void srem(String key, String value);


	Map<String,String> hgetAll(String key);

}
