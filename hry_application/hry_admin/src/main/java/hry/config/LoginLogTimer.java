package hry.config;

import hry.core.thread.ThreadPoolUtils;
import hry.platform.utils.ConfigEnum;
import hry.redis.RedisService;
import hry.security.jwt.JWTUtil;
import hry.util.SpringUtil;
import hry.util.rmq.RabbitMQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class LoginLogTimer implements ApplicationRunner {

    private final static Object lock = new Object();



    @Override
    public void run(ApplicationArguments args) throws Exception {
        ThreadPoolUtils.execute(() -> {
            RedisService redisService = SpringUtil.getBean("redisService");
            while (true){

                synchronized (lock) {
                    try {
                        TimeUnit.SECONDS.sleep(5L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    redisService.lock(JWTUtil.getManageAllKey()+"lock");
                    try {
                        Map<String, String> map = redisService.hgetAll(JWTUtil.getManageAllKey());
                        if (map != null && map.size() > 0) {
                            Set<String> keySet = map.keySet();
                            for(String key:keySet){

                                String refreshTime = redisService.get("JWT:token:pc:manage:refreshTime:" + key);
                                if(StringUtils.isEmpty(refreshTime)){

                                    //发消息更新日志
                                    String loginId = map.get(key);
                                    RabbitMQProducer rabbitMQProducer = SpringUtil.getBean("rabbitMQProducer");
                                    //rabbitMQProducer.sendMsgByQueue(ConfigEnum.UPDATE_LOGIN_LOG_MQ.getQueueName(), loginId);
                                    //删除
                                    redisService.hdel(JWTUtil.getManageAllKey(),key);
                                }
                            }
                        }
                    }catch (Exception e){

                    }finally {
                        redisService.unLock(JWTUtil.getManageAllKey()+"lock");
                    }

                }
            }
        });
    }
}
