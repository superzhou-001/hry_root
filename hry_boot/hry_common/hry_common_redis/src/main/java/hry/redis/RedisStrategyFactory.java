package hry.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: yaozh
 * @Description: redis 策略模式工厂类
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Service
public class RedisStrategyFactory {
    @Autowired
    Map<String, RedisService> redisServices = new ConcurrentHashMap<>();
    @Value("${redis.modeService}")
    private String modeService;

    @Bean(name="redisService")
    @Primary
    public RedisService getRedisService(){
        RedisService redisService = redisServices.get(modeService);
        if(redisService== null) {
            throw new RuntimeException("策略模式没找到对应实现类");
        }
        return redisService;
    }

}
