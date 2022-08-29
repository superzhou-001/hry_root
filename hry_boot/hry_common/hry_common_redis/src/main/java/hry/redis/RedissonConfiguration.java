package hry.redis;

import hry.redis.cluster.RedisClusterProperties;
import hry.redis.impl.RedissonDistributedLocker;
import hry.redis.utils.LockUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.misc.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yaozh
 * @Description: redisson 配置类
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Configuration
public class RedissonConfiguration {

    @Value("${redis.url}")
    private String redisUrl;

    @Value("${redis.port}")
    private Integer redisPort;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.modeService}")
    private String modeService;

    @Autowired
    private RedisClusterProperties redisProperties;

    @Bean
    public RedissonClient getRedisson() throws Exception{
        RedissonClient redisson = null;
        Config config = new Config();
        if (StringUtils.isEmpty(modeService)||modeService.equals("redisSingletonServiceImpl")) {
            //单机模式
            config.useSingleServer()
                    .setAddress("redis://"+redisUrl+":"+redisPort)
                    .setPassword(redisPassword);
        } else {
            //获取redis集群的ip及端口号等相关信息；
            String[] serverArray = redisProperties.getNodes().split(",");

            //集群模式
            config.useClusterServers()
                    .setScanInterval(2000) // 集群状态扫描间隔时间，单位是毫秒
                    .setPassword(redisPassword);
            //遍历add到HostAndPort中；
            List<URI> nodeAddresses = config.useClusterServers().getNodeAddresses();
            for (String ipPort : serverArray) {
                nodeAddresses.add(URIBuilder.create("redis://"+ipPort.trim()));
            }
        }
        redisson = Redisson.create(config);

        return  redisson;
    }

    @Bean
    public RedissonDistributedLocker redissonLocker(RedissonClient redissonClient){
        RedissonDistributedLocker locker = new RedissonDistributedLocker(redissonClient);
        //设置LockUtil的锁处理对象
        LockUtil.setLocker(locker);
        return locker;
    }
}
