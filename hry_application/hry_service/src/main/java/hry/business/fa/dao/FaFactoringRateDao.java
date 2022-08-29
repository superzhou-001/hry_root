/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 10:36:12 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaFactoringRate;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaFactoringRateDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 10:36:12 
 */
@Mapper
public interface FaFactoringRateDao extends BaseDao<FaFactoringRate, Long> {

}
