/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:29:01
 */
package hry.platform.communication.sms.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.communication.sms.model.AppSmsTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppSmsTemplateDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:29:01
 */
@Mapper
public interface AppSmsTemplateDao extends BaseDao<AppSmsTemplate, Long> {

}
