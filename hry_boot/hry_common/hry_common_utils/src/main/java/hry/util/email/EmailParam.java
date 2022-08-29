package hry.util.email;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

/**
 * @author: liuchenghui
 * @Date: 2020/3/24 18:16
 * @Description: 邮件参数
 */
@Data
public class EmailParam implements Serializable {
    /**
     * @Description: 邮件处理类名，类全名首字母小写
     */
    private String emailClassName;

// ----------邮箱设置----------
    /**
     * @Description: 收件人帐号（多人或单人）
     */
    private List<String> toEmails;
    /**
     * @Description: 发件人帐号
     */
    private String fromEmail;
    /**
     * @Description: 发件人昵称
     */
    private String fromNickName;
    /**
     * @Description: 邮件标题
     */
    private String emailTitle;
    /**
     * @Description: 邮件内容
     */
    private String emailContent;
    /**
     * @Description: 邮箱服务器端口
     */
    private String port;
    /**
     * @Description: 邮箱服务器主机
     */
    private String host;
    /**
     * @Description: 邮箱服务器协议
     */
    private String protocol;
    /**
     * @Description: 邮箱服务器认证
     */
    private String auth;
    /**
     * @Description: 邮箱服务器ssl验证
     */
    private String sslflag;
    /**
     * @Description: 邮箱服务器TLS验证
     */
    private String starttls;
    /**
     * @Description: 邮箱服务器认证用户
     */
    private String emailUser;
    /**
     * @Description: 邮箱服务器认证密码
     */
    private String agreedPwd;
    /**
     * @Description: 语种
     */
    private String langCode;

// ----------邮件内容参数----------
    /**
     * @Description: 标题前缀
     */
    private String prefix;
    /**
     * @Description: 邮件类型-与模版对应
     */
    private String type;
    /**
     * @Description: 跳转链接
     */
    private String url;
    /**
     * @Description: 验证码
     */
    private String validCode;
    /**
     * @Description: 钓鱼码
     */
    private String fishingCode;
    /**
     * @Description: 设备类型
     */
    private String deviceType;
    /**
     * @Description: 设备浏览器类型
     */
    private String browserType;
    /**
     * @Description: 设备系统类型
     */
    private String osType;
    /**
     * @Description: 认证ip
     */
    private String ip;
    /**
     * @Description: token
     */
    private String token;
    /**
     * @Description: 是否是忘记密码邮件
     */
    private boolean newForgetEmail;

    /**
     * @Author: yaozh
     * @Description:
     * @param emailClassName 邮件处理类名，类全名首字母小写
     * @param toEmails 收件人帐号（多人或单人）
     * @param langCode 语种
     * @param type 邮件类型-与模版对应
     * @Date: 2020/7/14 16:29
     */
    public EmailParam(String emailClassName, List<String> toEmails, String langCode, String type) {

        this.emailClassName = emailClassName;
        this.toEmails = toEmails;
        this.langCode = langCode;
        this.type = type;
        this.fishingCode = RandomStringUtils.random(6, false, true);
    }

    public EmailParam() {
    }
}
