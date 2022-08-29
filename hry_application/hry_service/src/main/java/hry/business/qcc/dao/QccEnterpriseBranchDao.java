/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 09:59:10 
 */
package hry.business.qcc.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.qcc.model.QccEnterpriseBranch;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> QccEnterpriseBranchDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 09:59:10 
 */
@Mapper
public interface QccEnterpriseBranchDao extends BaseDao<QccEnterpriseBranch, Long> {

}
