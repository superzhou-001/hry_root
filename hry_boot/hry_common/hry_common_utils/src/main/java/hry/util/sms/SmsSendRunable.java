package hry.util.sms;

import hry.util.SpringUtil;

/**
 * @Author: yaozh
 * @Description:
 * @Date: 2020/7/13 16:26
 */
public class SmsSendRunable implements Runnable {

    /**
     * @Description: 请求短信参数
     */
    private SmsSendVo smsSendVo;

    public SmsSendRunable(SmsSendVo smsSendVo) {
        this.smsSendVo = smsSendVo;
    }

    @Override
    public void run () {
        try {
            SmsContext smsContext = SpringUtil.getBean("smsContext");
            smsContext.getResource(smsSendVo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
