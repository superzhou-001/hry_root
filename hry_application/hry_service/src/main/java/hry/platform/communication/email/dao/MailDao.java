/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:54:27 
 */
package hry.platform.communication.email.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.communication.email.model.Mail;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> MailDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:54:27 
 */
@Mapper
public interface MailDao extends BaseDao<Mail, Long> {

}
