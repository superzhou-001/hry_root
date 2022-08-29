package hry.redis.cluster;

import hry.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Component("redisClusterServiceImpl")
//@Component
public class RedisClusterServiceImpl implements RedisService {

    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public String save(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public void save(String key, String value, int second) {
        jedisCluster.set(key, value);
        jedisCluster.expire(key, second);
    }

    @Override
    public Long delete(String key) {
        return jedisCluster.del(key);
    }

    @Override
    public void setTime(String key, int second) {
        jedisCluster.expire(key, second);
    }

    @Override
    public Set<String> keys(String patt) {
        return jedisCluster.keys(patt);
    }

    @Override
    public Set<String> hkeys(String key) {
        new RuntimeException("集群模式不支持");
        return jedisCluster.hkeys(key);
    }

    @Override
    public String getLendConfig(String s) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public void saveMap(String key, Map<String, String> map) {
        new RuntimeException("集群模式不支持");
    }

    @Override
    public Map<String, String> getMap(String key) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public String getMap(String key, String code) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public void hset(String hkey, String key, String value) {
        jedisCluster.hset(key, key, value);
    }

    @Override
    public Long expire(String key, int seconds) {
        return jedisCluster.expire(key, seconds);
    }

    @Override
    public void publish(String channel, String message) {
        jedisCluster.publish(channel,message);
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        jedisPubSub.subscribe(String.valueOf(jedisPubSub),channel);
    }

    @Override
    public Long sadd(String key, String... val) {
        return jedisCluster.sadd(key,val);
    }

    @Override
    public Set<String> smembers(String key) {
        return jedisCluster.smembers(key);
    }

    @Override
    public Long zremrangeByScore(String key, double scoreMin, double scoreMax) {
        return jedisCluster.zremrangeByScore(key,scoreMin,scoreMax);
    }

    @Override
    public Long zrank(String key, String member) {
        return jedisCluster.zrank(key,member);
    }

    @Override
    public Double zscore(String key, String member) {
        return jedisCluster.zscore(key,member);
    }

    @Override
    public Long zrem(String key, String... members) {
        return jedisCluster.zrem(key,members);
    }

    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        return jedisCluster.zrangeWithScores(key,start,end);
    }

    @Override
    public String lpop(String key) {
        return jedisCluster.lpop(key);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return jedisCluster.lrange(key,start,end);
    }

    @Override
    public Long lrem(String key, long count, String value) {
        return jedisCluster.lrem(key,count,value);
    }

    @Override
    public String hget(String key, String field) {
        return jedisCluster.hget(key, field);
    }

    @Override
    public Map<String, String> hgetall(String hkey) {
        return jedisCluster.hgetAll(hkey);
    }

    @Override
    public Long getKeyTime(String key) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public Set<String> noPerkeys(String patt) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public boolean lock(String key) {
        new RuntimeException("集群模式不支持");
        return false;
    }

    @Override
    public void unLock(String key) {
        new RuntimeException("集群模式不支持");
    }

    @Override
    public void saveMap(String key, String mKey, String value) {
        new RuntimeException("集群模式不支持");
    }

    @Override
    public String delMapKey(String key, String mKey) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public Long getExpireTime(String key) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public void rpush(String key, String value) {
        jedisCluster.rpush(key,value);
    }

    @Override
    public void zadd(String key, double score, String value) {
        jedisCluster.zadd(key,score,value);
    }

    @Override
    public void zream(String key, double score) {
        new RuntimeException("集群模式不支持");
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        return jedisCluster.zrange(key,start,end);
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        return jedisCluster.zrangeByScore(key,min,max);
    }

    @Override
    public Long setnx(String key, String value) {
        return jedisCluster.setnx(key,value);
    }

    @Override
    public Long hdel(String key, String... field) {
        return jedisCluster.hdel(key, field);
    }

    @Override
    public Long lpush(String key, String... val) {
        return jedisCluster.lpush(key,val);
    }

    @Override
    public String set(byte[] key, byte[] val) {
        return jedisCluster.set(key,val);
    }

    @Override
    public byte[] get(byte[] key) {
        return jedisCluster.get(key);
    }

    @Override
    public Set<String> delkeys(String patt) {
        new RuntimeException("集群模式不支持");
        return null;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        return jedisCluster.zrevrange(key,start,end);
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String start, String end) {
        return jedisCluster.zrevrangeByScore(key,start,end);
    }

    @Override
    public long setIncr(String key, int cacheSeconds) {
        new RuntimeException("集群模式不支持");
        return 0L;
    }

    @Override
    public void srem(String key, String value) {
        jedisCluster.srem(key,value);
    }

    @Override
    public Map<String,String> hgetAll(String key) {
        return jedisCluster.hgetAll(key);
    }

}
