/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:48:43 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtContractType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CtContractTypeDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:48:43 
 */
@Mapper
public interface CtContractTypeDao extends BaseDao<CtContractType, Long> {

}
