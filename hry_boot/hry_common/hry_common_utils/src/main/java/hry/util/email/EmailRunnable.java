package hry.util.email;

import hry.util.SpringUtil;

/**
 * @author: liuchenghui
 * @Date: 2020/3/24 18:24
 * @Description: 电子邮件线程类
 */
public class EmailRunnable implements Runnable {

    private EmailParam emailParam;

    public EmailRunnable (EmailParam emailParam) {
        this.emailParam = emailParam;
    }

    @Override
    public void run () {
        EmailContext emailContext = SpringUtil.getBean("emailContext");
        emailContext.getResource(emailParam.getEmailClassName(), emailParam);
    }
}
