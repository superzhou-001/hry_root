/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:29:01
 */
package hry.platform.communication.sms.service;

import hry.core.mvc.service.BaseService;
import hry.platform.communication.sms.model.AppSmsTemplate;

/**
 * <p> AppSmsTemplateService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:29:01
 */
public interface AppSmsTemplateService extends BaseService<AppSmsTemplate, Long> {

    AppSmsTemplate getSmsTemplate (String smsType, String smsLang);
}
