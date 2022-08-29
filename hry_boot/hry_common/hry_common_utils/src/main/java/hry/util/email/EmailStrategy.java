package hry.util.email;

import hry.bean.JsonResult;

/**
 * @author: liuchenghui
 * @Date: 2020/3/25 15:42
 * @Description: Strategy
 */
public interface EmailStrategy {

    /**
     * @author: liuchenghui
     * @Date: 2020/3/25 15:41
     * @Description: 发送邮件方法
     */
    JsonResult emailSend (EmailParam emailParam);
}
