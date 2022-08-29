/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 10:19:39 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuUpdateLog;
import hry.business.cu.service.CuUpdateLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuUpdateLogService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 10:19:39 
 */
@Service("cuUpdateLogService")
public class CuUpdateLogServiceImpl extends BaseServiceImpl<CuUpdateLog, Long> implements CuUpdateLogService {

	@Resource(name = "cuUpdateLogDao")
	@Override
	public void setDao (BaseDao<CuUpdateLog, Long> dao) {
		super.dao = dao;
	}

}
