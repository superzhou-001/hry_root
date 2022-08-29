/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 17:18:52 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseInvestment;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseInvestmentDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 17:18:52 
 */
@Mapper
public interface QccEnterpriseInvestmentDao extends BaseDao<QccEnterpriseInvestment, Long> {

}
