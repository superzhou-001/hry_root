package hry.redis.singleton;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

@Configuration
@Slf4j
public class RedisConfig {

    public RedisConfig() {
        log.info("****************【系统启动中】加载redis配置,****************");
    }

    @Value("${redis.url}")
    private String redisUrl;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.maxTotal}")
    private Integer redisMaxTotal;

    /**
     * 单节点redis模式
     *
     * @return
     * @throws IOException
     */
    @Bean("jedisPool")
    public JedisPool redisson() throws IOException {
        JedisPoolConfig config = new JedisPoolConfig();
        // 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(redisMaxTotal);
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(100);
        // 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(1000 * 100);
        config.setTestOnBorrow(true);
        return new JedisPool(config, redisUrl, redisPort, 100000, redisPassword);
    }
}

