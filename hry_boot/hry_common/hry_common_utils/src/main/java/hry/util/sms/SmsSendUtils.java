package hry.util.sms;


import hry.core.thread.ThreadPoolUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: yaozh
 * @Description: 发送短信工具
 * @Date: 2020/7/13 16:06
 */
public class SmsSendUtils {
    private static Logger logger= LoggerFactory.getLogger(SmsSendUtils.class);
    /**
     * @Description: 发送短信功能
     */
    public static String sendSms (SmsSendVo smsSendVo) {
        //6位短信验证码
        int length = 6;
        //生成随机码
        String smsCode = RandomStringUtils.random(length, false, true);
        smsSendVo.setSmsCode(smsCode);
        Map<String, String> param = smsSendVo.getParam();
        if (param == null) {
            param = new HashMap<>();
        }
        param.put("code",smsCode);
        smsSendVo.setParam(param);
        logger.info("----------调用线程发送短信---------");
        SmsSendRunable smsSendRunable = new SmsSendRunable(smsSendVo);
        ThreadPoolUtils.execute(smsSendRunable);
        return smsCode;
    }
}
