package hry.util.sms;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @slogan: 天下风云出我辈，一入代码岁月催！
 * @Date:
 */
@Data
public class SmsSendVo {
    //手机号
    private String phone;
    //国家
    private String country;
    //使用发送短信的service类型,默认使用易租云
    private String serviceType="hrySmsServiceImpl";
    //短信类型,使用的短信模板(tempType)
    private String smsType;
    //短信验证码
    private String smsCode;
    //语种
    private String smsLang = "zh_CN";
    //参数
    private Map<String, String> param;

    /**
     * @param phone   手机号
     * @param country 国家
     * @param smsType 短信类型,使用的短信模板(tempType)
     * @param param   参数
     */
    public SmsSendVo(String phone, String country, String smsType, Map<String, String> param) {
        this.phone = phone;
        this.country = country;
        this.smsType = smsType;
        this.param = param;
    }
}
