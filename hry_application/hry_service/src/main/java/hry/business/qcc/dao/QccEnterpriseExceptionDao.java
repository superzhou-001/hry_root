/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 18:55:10 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseException;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseExceptionDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 18:55:10 
 */
@Mapper
public interface QccEnterpriseExceptionDao extends BaseDao<QccEnterpriseException, Long> {

}
