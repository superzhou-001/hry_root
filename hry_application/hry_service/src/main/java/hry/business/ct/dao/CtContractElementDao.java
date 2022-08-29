/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:49:25 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtContractElement;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CtContractElementDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:49:25 
 */
@Mapper
public interface CtContractElementDao extends BaseDao<CtContractElement, Long> {

}
