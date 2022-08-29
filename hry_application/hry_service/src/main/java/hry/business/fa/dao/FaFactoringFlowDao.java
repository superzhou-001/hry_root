/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 17:01:01 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaFactoringFlow;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaFactoringFlowDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 17:01:01 
 */
@Mapper
public interface FaFactoringFlowDao extends BaseDao<FaFactoringFlow, Long> {

}
