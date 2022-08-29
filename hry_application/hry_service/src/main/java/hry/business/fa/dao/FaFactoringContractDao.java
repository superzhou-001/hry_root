/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 14:11:12 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaFactoringContract;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaFactoringContractDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 14:11:12 
 */
@Mapper
public interface FaFactoringContractDao extends BaseDao<FaFactoringContract, Long> {

}
