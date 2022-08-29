package hry.redis.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Configuration
public class RedisClusterConfig {
    @Autowired
    private RedisClusterProperties redisProperties;

    @Bean
    public JedisCluster getJedisCluster(){
        //获取redis集群的ip及端口号等相关信息；
        String[] serverArray = redisProperties.getNodes().split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisProperties.getMaxTotal());//最大连接数
        config.setMaxIdle(redisProperties.getMaxIdle());// 最大空闲实例的个数
        config.setMinIdle(redisProperties.getMinIdle());//最小空闲实例个数

        //遍历add到HostAndPort中；
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        //构建对象并返回；
        JedisCluster jedisCluster = new JedisCluster(nodes, redisProperties.getCommandTimeout(), 5000, 10, redisProperties.getPassword(), config);
        return jedisCluster;
    }

}
