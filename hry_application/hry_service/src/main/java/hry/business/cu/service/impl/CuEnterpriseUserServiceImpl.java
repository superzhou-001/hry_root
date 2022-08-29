/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 17:39:48 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuEnterpriseUser;
import hry.business.cu.service.CuEnterpriseUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuEnterpriseUserService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 17:39:48 
 */
@Service("cuEnterpriseUserService")
public class CuEnterpriseUserServiceImpl extends BaseServiceImpl<CuEnterpriseUser, Long> implements CuEnterpriseUserService {

	@Resource(name = "cuEnterpriseUserDao")
	@Override
	public void setDao (BaseDao<CuEnterpriseUser, Long> dao) {
		super.dao = dao;
	}

}
