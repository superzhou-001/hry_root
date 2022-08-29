/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:34:54 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseCaseFiling;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseCaseFilingDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:34:54 
 */
@Mapper
public interface QccEnterpriseCaseFilingDao extends BaseDao<QccEnterpriseCaseFiling, Long> {

}
