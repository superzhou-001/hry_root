/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 17:39:48 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuEnterpriseUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuEnterpriseUserDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 17:39:48 
 */
@Mapper
public interface CuEnterpriseUserDao extends BaseDao<CuEnterpriseUser, Long> {

}
