/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 13:57:47 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseEmployee;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseEmployeeDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 13:57:47 
 */
@Mapper
public interface QccEnterpriseEmployeeDao extends BaseDao<QccEnterpriseEmployee, Long> {

}
