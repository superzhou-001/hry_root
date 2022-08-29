/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:33:28 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuBank;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> CuBankDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:33:28 
 */
@Mapper
public interface CuBankDao extends BaseDao<CuBank, Long> {

}
