/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:56:45
 */
package hry.platform.communication.email.service;

import hry.core.mvc.service.BaseService;
import hry.platform.communication.email.model.MailConfig;

/**
 * <p> MailConfigService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:56:45
 */
public interface MailConfigService extends BaseService<MailConfig, Long> {

    void initCache ();
}
