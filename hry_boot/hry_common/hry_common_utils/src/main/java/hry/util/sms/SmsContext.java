package hry.util.sms;

import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.directwebremoting.json.types.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/23 17:54
 *  @Description: 采用策略模式
 *  将实现SmsStrategy接口的类封装到strategyMap中，只需传入实现类名即可获取实现类对象
 *  实现类名未类名首字母小写，也可通过@Component或@Service设置实现类名
 *  例如：HrySmsServiceImpl.java  ------> map的key就是"hrySmsServiceImpl"
 *
 *  短信参数配置：
 *  smsSendParam.setHrySmsClassName("hrySmsServiceImpl");
 */
@Service
@Slf4j
public class SmsContext {

    private final static String VALID_CODE = "1";

    private final Map<String, SmsStrategy> strategyMap = new ConcurrentHashMap<>();
    @Autowired
    private RedisService redisService;

    @Autowired
    public SmsContext(Map<String, SmsStrategy> strategyMap) {
        this.strategyMap.clear();
        strategyMap.forEach((k, v)-> this.strategyMap.put(k, v));
    }

    public void getResource(SmsSendVo smsSendVo){
        log.info("----------调用各自实现类的发送短信方法----------");
        String serviceJson = redisService.get("appconfig:smsConfig");
        List<JSONObject> jsonList = JSONObject.parseObject(serviceJson, List.class);
        for (JSONObject s : jsonList) {
            if (s.get("configkey").equals("smsServiceClass")) {
                smsSendVo.setServiceType(s.get("value").toString());
                break;
            }
        }
        JsonResult jsonResult = strategyMap.get(smsSendVo.getServiceType()).smsSend(smsSendVo);
        // 发送成功后对验证码处理
        if (jsonResult.getSuccess()) {
            log.info("----------返回成功，如果是短信验证码，则存入redis中----------");
            if (VALID_CODE.equals(smsSendVo.getSmsType())) {
                // 设置短信验证码5分钟内有效
                redisService.save("sms_valid_code:" + smsSendVo.getPhone(), smsSendVo.getSmsCode(), 300);
            }
        }
    }

}
