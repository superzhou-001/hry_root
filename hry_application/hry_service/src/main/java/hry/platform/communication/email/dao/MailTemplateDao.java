/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:59:19 
 */
package hry.platform.communication.email.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.communication.email.model.MailTemplate;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> MailTemplateDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:59:19 
 */
@Mapper
public interface MailTemplateDao extends BaseDao<MailTemplate, Long> {

}
