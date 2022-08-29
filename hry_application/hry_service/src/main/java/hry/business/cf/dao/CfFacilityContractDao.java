/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-05 17:44:32 
 */
package hry.business.cf.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cf.model.CfFacilityContract;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CfFacilityContractDao </p>
 *
 * @author: yaoz
 * @Date: 2020-08-05 17:44:32 
 */
@Mapper
public interface CfFacilityContractDao extends BaseDao<CfFacilityContract, Long> {

}
