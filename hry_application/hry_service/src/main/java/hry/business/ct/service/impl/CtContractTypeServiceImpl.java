/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:48:43 
 */
package hry.business.ct.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractType;
import hry.business.ct.service.CtContractTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CtContractTypeService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:48:43 
 */
@Service("ctContractTypeService")
public class CtContractTypeServiceImpl extends BaseServiceImpl<CtContractType, Long> implements CtContractTypeService {

	@Resource(name = "ctContractTypeDao")
	@Override
	public void setDao (BaseDao<CtContractType, Long> dao) {
		super.dao = dao;
	}

}
