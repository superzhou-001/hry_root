package hry.util.sms;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * @author: liuchenghui
 * @Date: 2020/3/23 14:06
 * @Description: 短信参数实体
 */
@Data
@Entity
public class SmsSendParam implements Serializable {

    /**
     * @Description: 内部系统调用认证Key
     */
    public String smsKey;

    /**
     * @Description: 国际区号，以+号开头
     */
    private String hryCountry;

    /**
     * @Description: 手机号
     */
    private String hrySmsPhoneNum;

    /**
     * @Description: 短信类型，注册，找回密码等，数据字典配置的参数
     */
    private String hrySmstype;

    /**
     * @Description: 短信验证码
     */
    private String hryCode;

    /**
     * @Description: 短信语种
     */
    private String hrySmsLang;

    /**
     * @Description: 模板中参数，按模板顺序，用逗号分隔
     */
    private String hryParams;

    /**
     * @Description: 服务热线
     */
    private String hrySmsCall;

    /**
     * @Description: 短信处理类名
     */
    private String hrySmsClassName;

    /**
     * @Description: 实体转json串
     * @Author: liuchenghui
     * @Date: 2020/3/23 14:09
     * @Param:
     * @Return:
     */
    public String toJson () {
        return JSON.toJSONString(this);
    }

}
