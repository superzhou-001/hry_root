package hry.util.sms;

import hry.bean.JsonResult;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/23 17:48
 *  @Description: Strategy
 */
public interface SmsStrategy {
    /**
     * @Description: 发送短信
     * @Date: 2020/7/13 16:57
     */
    JsonResult smsSend (SmsSendVo smsSendVo);
}
