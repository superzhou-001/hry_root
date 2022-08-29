/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:25:30 
 */
package hry.scm.fundsupport.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.fundsupport.model.FundSupport;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FundSupportDao </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:25:30 
 */
@Mapper
public interface FundSupportDao extends BaseDao<FundSupport, Long> {

}
