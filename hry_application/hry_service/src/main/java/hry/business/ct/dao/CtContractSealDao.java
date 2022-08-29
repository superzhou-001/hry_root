/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:40 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtContractSeal;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CtContractSealDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:40 
 */
@Mapper
public interface CtContractSealDao extends BaseDao<CtContractSeal, Long> {

}
