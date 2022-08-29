package hry.util.email;

import com.alibaba.fastjson.JSONObject;
import hry.core.thread.ThreadPoolUtils;
import hry.redis.RedisService;
import hry.util.SpringUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author: liuchenghui
 * @Date: 2020/3/24 18:27
 * @Description: 发送邮件工具类
 */
public class EmailUtils {

    /**
     * @author: liuchenghui
     * @Date: 2020/3/24 18:28
     * @Description: 多线程发送邮件
     */
    public static void sendEmailByThread (EmailParam emailParam) {
        try {
            // 设置验证码
            String hryCode = RandomStringUtils.random(6, false, true);
            emailParam.setValidCode(hryCode);
            // 线程执行发送邮件
            ThreadPoolUtils.execute(new EmailRunnable(emailParam));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/26 14:33
     *  @Description: 邮箱参数封装
     */
    public static void mailServiceParamHandler(EmailParam emailParam) {
        try {
            // 读取邮箱服务器配置
            RedisService redisService = SpringUtil.getBean("redisService");
            String email_config = redisService.get("email_config");
            if (StringUtils.isNotEmpty(email_config)) {
                JSONObject object = JSONObject.parseObject(email_config);
                if (object != null) {
                    // 端口
                    emailParam.setPort(object.getString("port"));
                    // 同时通过认证
                    String auth = object.getInteger("auth").compareTo(1) == 0 ? "true" : "false";
                    emailParam.setAuth(auth);
                    // 是否开启了ssl验证
                    String sslflag = object.getInteger("sslflag").compareTo(1) == 0 ? "true" : "false";
                    emailParam.setSslflag(sslflag);
                    // 是否开启TLS
                    String starttls = object.getInteger("starttls").compareTo(1) == 0 ? "true" : "false";
                    emailParam.setStarttls(starttls);
                    // 邮件服务器主机
                    emailParam.setHost(object.getString("host"));
                    // 邮件服务器协议
                    emailParam.setProtocol(object.getString("protocol"));
                    // 邮件标题前缀
                    emailParam.setPrefix(object.getString("prefix"));
                    // 认证用户
                    emailParam.setEmailUser(object.getString("emailUser"));
                    // 认证密码
                    emailParam.setAgreedPwd(object.getString("agreedPwd"));
                    // 设置发件人地址，如果发件人为空，则用系统默认的
                    String fromEmail = emailParam.getFromEmail();
                    if (StringUtils.isEmpty(fromEmail)) {
                        //设置自定义发件人昵称
                        String nick = MimeUtility.encodeText(object.getString("customName"));
                        emailParam.setFromNickName(nick);
                        emailParam.setFromEmail(emailParam.getEmailUser());

                    } else {
                        emailParam.setFromNickName(fromEmail);
                        emailParam.setFromEmail(fromEmail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * @author: liuchenghui
     * @Date: 2020/3/25 16:08
     * @Description: 发送邮件具体方法
     */
    public static boolean sendMailHandler (EmailParam emailParam) {
        // 可以加载一个配置文件
        Properties props = new Properties();
        // 端口
        String port = emailParam.getPort();
        // 使用smtp：简单邮件传输协议
        // 邮箱服务器主机
        props.put("mail.smtp.host", emailParam.getHost());
        // 是否开启了ssl验证
        props.put("mail.smtp.ssl.enable", emailParam.getSslflag());
        // 同时通过认证
        props.put("mail.smtp.auth", emailParam.getAuth());
        // 邮箱服务器协议
        props.put("mail.transport.protocol", emailParam.getProtocol());
        // 是否开启TLS
        props.put("mail.smtp.starttls.enable", emailParam.getStarttls());
        try {
            // 根据属性新建一个邮件会话
            Session session = Session.getDefaultInstance(props);
            // 通过Session的setDebug()来设置是否启用调试监控模式
            session.setDebug(false);
            // 由邮件会话新建一个消息对象
            MimeMessage message = new MimeMessage(session);
            // 设置发件人
            message.setFrom(new InternetAddress(emailParam.getFromEmail(), emailParam.getFromNickName(), "utf-8"));
            // 获取收件人
            List<String> toAddress = emailParam.getToEmails();
            if (toAddress != null && toAddress.size() > 0) {
                final int num = toAddress.size();
                InternetAddress[] to_address = new InternetAddress[num];
                for (int i = 0; i < num; i++) {
                    to_address[i] = new InternetAddress(toAddress.get(i));
                }
                // 设置收件人，单个或多个
                message.setRecipients(Message.RecipientType.TO, to_address);
                // 设置标题
                message.setSubject(emailParam.getPrefix() + emailParam.getEmailTitle(), "utf-8");
                // 设置信件内容，内容样式比较丰富
                message.setContent(emailParam.getEmailContent(), "text/html;charset=UTF-8");
                // 设置发信时间
                message.setSentDate(new Date());
                // 存储邮件信息
                message.saveChanges();
                // 发送邮件
                Transport transport = session.getTransport();
                // 第一个参数：认证用户     第二个参数：认证密码
                if (StringUtils.isEmpty(port)) {
                    transport.connect(emailParam.getEmailUser(), emailParam.getAgreedPwd());
                } else {
                    transport.connect(emailParam.getHost(), Integer.valueOf(port), emailParam.getEmailUser(), emailParam.getAgreedPwd());
                }
                // 发送邮件,其中第二个参数是所有已设好的收件人地址
                transport.sendMessage(message, message.getAllRecipients());
                transport.close();
                return true;
            }
        } catch (Exception e) {
            System.out.println("发送邮件报错 ---- 接收人为  ： " + emailParam.getToEmails().toString() + "   邮件标题是 ： " + emailParam.getEmailTitle());
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
