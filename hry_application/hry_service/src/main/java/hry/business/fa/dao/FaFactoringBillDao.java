/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-11 14:14:15 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaFactoringBill;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> FaFactoringBillDao </p>
 *
 * @author: yaoz
 * @Date: 2020-08-11 14:14:15 
 */
@Mapper
public interface FaFactoringBillDao extends BaseDao<FaFactoringBill, Long> {

}
