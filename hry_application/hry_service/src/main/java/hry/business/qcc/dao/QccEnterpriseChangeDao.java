/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 10:17:37 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseChange;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseChangeDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 10:17:37 
 */
@Mapper
public interface QccEnterpriseChangeDao extends BaseDao<QccEnterpriseChange, Long> {

}
