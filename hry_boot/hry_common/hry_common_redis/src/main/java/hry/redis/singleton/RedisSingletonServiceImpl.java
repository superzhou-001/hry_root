package hry.redis.singleton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Tuple;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis工具类实现
 */
@Component("redisSingletonServiceImpl")
//@Component
public class RedisSingletonServiceImpl implements RedisService {

    @Autowired
    private JedisPool jedisPool;

    // 锁前缀
    private static final String LOCK_PREFIX = "LOCK:LOCK_";

    private static final int timeout = 20;

    private static final int time = 10 * 1000;

    @Override
    public String save(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            jedis.close();
        }
    }

    @Override
    public Map<String, String> getMap(String key) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            Map<String, String> map = jedis.hgetAll(key);
            Map var4 = map;
            return var4;
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            jedis.close();
        }

        return null;
    }

    @Override
    public String getMap(String key, String code) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String ss = jedis.hget(key, code);
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public void hset(String hkey, String key, String value) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.hset(hkey, key, value);
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            jedis.close();
        }

    }


    @Override
    public void saveMap(String key, Map<String, String> map) {
        Jedis jedis = null;

        try {
            jedis = this.jedisPool.getResource();
            jedis.hmset(key, map);
        } catch (Exception var8) {
            ;
        } finally {
            jedis.close();
        }

    }

    @Override
    public void save(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key, second);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            jedis.close();
        }
    }

    @Override
    public Long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            return (long) 0;
        } finally {
            jedis.close();
        }
    }

    @Override
    public void setTime(String key, int second) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key, second);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }


    /***
     *
     * @param patt
     * @return
     */
    @Override
    public Set<String> keys(String patt) {

        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys("*" + patt + "*");
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return null;
    }

    @Override
    public Set<String> hkeys(final String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.hkeys(key);
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public String getLendConfig(String key) {

        String val = "";
        Jedis jedis = jedisPool.getResource();
        try {
            String string = jedis.get("appconfig:financeLendConfig");
            JSONObject obj = JSON.parseObject(string);
            if (null != key && obj.get(key) != null) {
                val = obj.get(key).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return val;

    }

    @Override
    public String hget(String hkey, String key) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hget(hkey, key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            jedis.close();
        }

    }

    @Override
    public Map<String, String> hgetall(String hkey) {

        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(hkey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }

    }

    /**
     * 获取key生存时间
     */
    @Override
    public Long getKeyTime(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            Long l = jedis.ttl(key);
            return l;
        } catch (Exception e) {
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public Set<String> noPerkeys(String patt) {


        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys(patt + "*");
            return keys;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return null;

    }

    /**
     * 加锁
     *
     * @param key
     * @return
     */
    @Override
    public boolean lock(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String nkey = LOCK_PREFIX + key;
            String value = System.currentTimeMillis() + time + "";
            Long result = jedis.setnx(nkey, value);
            if (result == 1) {
                jedis.expire(nkey, timeout);
                return true;
            } else {
                String dkey = jedis.get(LOCK_PREFIX + key);
                Long skey = dkey != null ? Long.valueOf(dkey) : null;
                long currentTime = System.currentTimeMillis();
                if ((null != skey && skey > currentTime) || skey == null) {
                    String svalue = System.currentTimeMillis() + time + "";
                    Long setnx = jedis.setnx(nkey, svalue);
                    if (setnx == 1) {
                        jedis.expire(nkey, timeout);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param key
     */
    @Override
    public void unLock(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String nkey = LOCK_PREFIX + key;

            jedis.del(nkey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     * 保存一个单个键值对进到map里.如果mKey已经存在于map里将会覆盖之前的值。
     *
     * @param key
     * @param mKey
     * @param value
     */
    @Override
    public void saveMap(String key, String mKey, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.hset(key, mKey, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除某个key 所对应的value。
     *
     * @param key
     * @param mKey
     * @return
     */
    @Override
    public String delMapKey(String key, String mKey) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String ss = jedis.hget(key, mKey);
            return ss;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    @Override
    public Long getExpireTime(String key) {
        Long result = -2L;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.ttl(key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public void rpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void zadd(String key, double score, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void zream(String key, double score) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.zremrangeByScore(key, score, score);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Set<String> zrangeByScore(String key, double min, double max) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrangeByScore(key, min, max);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.setnx(key, value);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                //jedisPool.returnResource(jedis);
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.expire(key, seconds);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                //jedisPool.returnResource(jedis);
                jedis.close();
            }
        }
        return null;
    }


    @Override
    public void publish(String channel, String message) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.publish(channel, message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String channel) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.subscribe(jedisPubSub, channel);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     * set sadd
     *
     * @param key
     * @param val
     * @return
     * @author denghf
     */
    public Long sadd(String key, String... val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, val);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        } finally {
            jedis.close();
        }
    }

    /**
     * set smembers
     *
     * @param key
     * @return
     * @author denghf
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<String> smembers = jedis.smembers(key);
            return smembers;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * zremrangeByScore
     *
     * @param key
     * @param scoreMin
     * @param scoreMax
     * @return
     * @author denghf
     */
    @Override
    public Long zremrangeByScore(String key, double scoreMin, double scoreMax) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long aLong = jedis.zremrangeByScore(key, scoreMin, scoreMax);
            return aLong;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * zscore
     *
     * @param key
     * @param member
     * @return
     * @author denghf
     */
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long aLong = jedis.zrank(key, member);
            return aLong;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * zscore
     *
     * @param key
     * @param member
     * @return
     * @author denghf
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Double aLong = jedis.zscore(key, member);
            return aLong;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * zrem
     *
     * @param key
     * @param members
     * @return
     * @author denghf
     */
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long aLong = jedis.zrem(key, members);
            return aLong;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * zrangeWithScores
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @Override
    public Set<Tuple> zrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<Tuple> tuples = jedis.zrangeWithScores(key, start, end);
            return tuples;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * lpop
     *
     * @param key
     * @return
     * @author denghf
     */
    public String lpop(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String lpop = jedis.lpop(key);
            return lpop;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * lrange
     *
     * @param key
     * @return
     * @author denghf
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> lrange = jedis.lrange(key, start, end);
            return lrange;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * lrem
     *
     * @param key
     * @return
     * @author denghf
     */
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lrem = jedis.lrem(key, count, value);
            return lrem;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * hdel
     *
     * @param key
     * @return
     * @author denghf
     */
    public Long hdel(String key, String... field) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long hdel = jedis.hdel(key, field);
            return hdel;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * lpush
     *
     * @param key
     * @return
     * @author denghf
     */
    public Long lpush(String key, String... val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Long lpush = jedis.lpush(key, val);
            return lpush;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * set
     *
     * @param key
     * @param val
     * @return
     * @author denghf
     */
    public String set(byte[] key, byte[] val) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String set = jedis.set(key, val);
            return set;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * get
     *
     * @param key
     * @return
     * @author denghf
     */
    public byte[] get(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            byte[] bytes = jedis.get(key);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Set<String> delkeys(String patt) {

        Jedis jedis = jedisPool.getResource();
        try {
            Set<String> keys = jedis.keys("*" + patt + "*");
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                jedis.del(iterator.next());
            }

            return keys;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }

        return null;
    }

    @Override
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    @Override
    public Set<String> zrevrangeByScore(String key, String start, String end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrangeByScore(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    /**
     * 对某个键的值自增
     * @param key
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    @Override
    public long setIncr(String key, int cacheSeconds) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            result = jedis.incr(key);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            }
            //logger.debug("set " + key + " = " + result);
        } catch (Exception e) {
            //logger.warn("set " + key + " = " + result);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public void srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.srem(key,value);
        } catch (Exception e) {
            //logger.warn("set " + key + " = " + result);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public Map<String,String> hgetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            //logger.warn("set " + key + " = " + result);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return null;
    }
}
