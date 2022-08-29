/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:40 
 */
package hry.business.ct.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractSeal;
import hry.business.ct.service.CtContractSealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CtContractSealService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:40 
 */
@Service("ctContractSealService")
public class CtContractSealServiceImpl extends BaseServiceImpl<CtContractSeal, Long> implements CtContractSealService {

	@Resource(name = "ctContractSealDao")
	@Override
	public void setDao (BaseDao<CtContractSeal, Long> dao) {
		super.dao = dao;
	}

}
