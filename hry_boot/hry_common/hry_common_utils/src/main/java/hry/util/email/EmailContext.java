package hry.util.email;

import hry.bean.JsonResult;
import hry.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: liuchenghui
 * @Date: 2020/3/25 15:45
 * @Description: 根据类名调用相应的方法
 */
@Service
@Slf4j
public class EmailContext {

    private final Map<String, EmailStrategy> eStrategyMap = new ConcurrentHashMap<>();

    @Autowired
    private RedisService redisService;

    @Autowired
    public EmailContext (Map<String, EmailStrategy> eStrategyMap) {
        this.eStrategyMap.clear();
        eStrategyMap.forEach((k, v) -> this.eStrategyMap.put(k, v));
    }


    public void getResource (String thirdType, EmailParam emailParam) {
        log.info("----------调用各自实现类的发送短信方法----------");
        JsonResult result = eStrategyMap.get(thirdType).emailSend(emailParam);
        if (result.getSuccess()) {
            List<String> tos =  emailParam.getToEmails();
            if (tos != null && tos.size() == 1) {
                // 设置短信验证码30分钟内有效
                redisService.save("email_valid_code:" + tos.get(0), emailParam.getValidCode(), 30 * 60);
            }
        }
    }
}
