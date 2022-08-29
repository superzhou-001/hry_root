/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 15:54:35 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseBasicDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseBasicDetailsDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 15:54:35 
 */
@Mapper
public interface QccEnterpriseBasicDetailsDao extends BaseDao<QccEnterpriseBasicDetails, Long> {

}
