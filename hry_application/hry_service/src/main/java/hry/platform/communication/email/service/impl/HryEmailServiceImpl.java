package hry.platform.communication.email.service.impl;

import hry.bean.JsonResult;
import hry.core.util.QueryFilter;
import hry.platform.communication.email.model.Mail;
import hry.platform.communication.email.model.MailTemplate;
import hry.platform.communication.email.service.MailService;
import hry.platform.communication.email.service.MailTemplateService;
import hry.platform.utils.BaseConfUtil;
import hry.util.email.EmailParam;
import hry.util.email.EmailStrategy;
import hry.util.email.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *  @author: liuchenghui
 *  @Date: 2020/3/25 17:40
 *  @Description: 邮件业务处理实现
 */
@Component
@Slf4j
public class HryEmailServiceImpl implements EmailStrategy {

    @Autowired
    private MailTemplateService mailTemplateService;
    @Autowired
    private MailService mailService;

    @Override
    public JsonResult emailSend (EmailParam emailParam) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setSuccess(false);
        jsonResult.setMsg("邮件发送失败");

        Mail mail = new Mail();
        mail.setErrorCode("send faile");
        mail.setErrorContent("");

        // 获取邮件内容语种。默认为中文
        String langCode = emailParam.getLangCode();
        if (StringUtils.isEmpty(langCode)) {
            langCode = "zh_CN";
        }
        // 设置邮件服务器配置
        EmailUtils.mailServiceParamHandler(emailParam);

        // 获取网站参数配置-暂时按这个key取，以后换
        String siteName = BaseConfUtil.getConfigSingle("appconfig:baseConfig" + langCode, "siteName");

        // 查询模版内容
        QueryFilter temp_qf = new QueryFilter(MailTemplate.class);
        temp_qf.addFilter("tempStatus=", "1");
        temp_qf.addFilter("languageDic=", langCode);
        temp_qf.addFilter("tempKey=", emailParam.getType());
        MailTemplate template = mailTemplateService.get(temp_qf);
        if (template != null) {
            // 设置邮件标题
            emailParam.setEmailTitle(template.getTempName());

            // 设置邮件内容
            StringBuffer sb = new StringBuffer();
            sb.append("<div style='width:650px;height:500px;border:1px solid #285586;margin:30px auto 60px;font-size:14px;color:#333333;word-wrap: break-word; '>");
            sb.append("<h3 style='position:relative;margin:0;background:#285586;height:100px;line-height:100px;padding:0 30px;font-size:20px;font-family:\"微软雅黑\";color:#FFFFFF;'>");
            sb.append(siteName);

            // 附带钓鱼码
            if (!StringUtils.isEmpty(emailParam.getFishingCode())) {
                sb.append("<p style='position: absolute;right: 57px;top: 1px;font-size: 14px;'>钓鱼码：");
                sb.append(emailParam.getFishingCode());
                sb.append("</p>");
            }
            sb.append("</h3><div style='padding:0px 60px 0'>");
            // 按模版类型设置邮件显示内容
            emailContent(template, emailParam, sb);

            sb.append("<br><br>");
            sb.append("</div>");
            sb.append("</div>");
            log.info("发送邮件内容:" + sb.toString());

            // 设置邮件内容
            emailParam.setEmailContent(sb.toString());
            // 发送邮件
            boolean b = EmailUtils.sendMailHandler(emailParam);
            // 设置邮件发送记录
            mail.setAddress(StringUtils.join(emailParam.getToEmails(), ","));
            mail.setContent(HtmlUtils.htmlEscape(emailParam.getEmailContent()));
            mail.setTitle(emailParam.getEmailTitle());
            mail.setSaasId("hurong_system");
            if (b) {
                mail.setErrorCode("send success");
                mail.setErrorContent("");
                System.out.println("邮件发送成功");
                jsonResult.setSuccess(true);
            }
        }
        // 保存邮件记录
        mailService.save(mail);
        return jsonResult;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/3/25 18:47
     *  @Description: 按模版内容封装参数
     */
    private void emailContent (MailTemplate template, EmailParam emailParam, StringBuffer sb) {
        // 获取邮件模版内容
        String tempContent = HtmlUtils.htmlUnescape(template.getTempContent());
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
        String format = sim.format(new Date());
        //tempContent = tempContent.replace("EMAIL_LINK", "<a href='" + url + "'>" + url + "</a>");
        // 处理收件人
        List<String> tos = emailParam.getToEmails();
        String toEmail = StringUtils.join(tos, ",");
        // 替换模版中的验证码
        tempContent = tempContent.replace("EMAIL_CODE", emailParam.getValidCode());
        // 替换模版中的收件人
        tempContent = tempContent.replace("EMAIL_ADDRESSEE", toEmail);
        // 替换模版中的发件人
        tempContent = tempContent.replace("EMAIL_SENDER", emailParam.getFromNickName());
        // 替换模版中的发件时间
        tempContent = tempContent.replace("EMAIL_SENDTIME", format);
        sb.append(tempContent + "<br><br>");
    }

}
