/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:56:45 
 */
package hry.platform.communication.email.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.communication.email.model.MailConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> MailConfigDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:56:45 
 */
@Mapper
public interface MailConfigDao extends BaseDao<MailConfig, Long> {

}
