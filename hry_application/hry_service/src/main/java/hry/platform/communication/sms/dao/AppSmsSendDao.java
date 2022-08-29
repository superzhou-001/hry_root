/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:27:23
 */
package hry.platform.communication.sms.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.communication.sms.model.AppSmsSend;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppSmsSendDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:27:23
 */
@Mapper
public interface AppSmsSendDao extends BaseDao<AppSmsSend, Long> {

}
