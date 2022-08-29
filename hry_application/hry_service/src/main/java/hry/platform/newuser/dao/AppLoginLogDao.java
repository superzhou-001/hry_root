/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-14 14:18:11 
 */
package hry.platform.newuser.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.newuser.model.AppLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppLoginLogDao </p>
 *
 * @author: zhouming
 * @Date: 2020-08-14 14:18:11 
 */
@Mapper
public interface AppLoginLogDao extends BaseDao<AppLoginLog, Long> {

}
