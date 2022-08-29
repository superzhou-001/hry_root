/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:37:09 
 */
package hry.scm.fundsupport.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.fundsupport.model.FundSupportPlan;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FundSupportPlanDao </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:37:09 
 */
@Mapper
public interface FundSupportPlanDao extends BaseDao<FundSupportPlan, Long> {

}
