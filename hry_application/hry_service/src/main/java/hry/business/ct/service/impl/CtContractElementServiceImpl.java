/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:49:25 
 */
package hry.business.ct.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractElement;
import hry.business.ct.service.CtContractElementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CtContractElementService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:49:25 
 */
@Service("ctContractElementService")
public class CtContractElementServiceImpl extends BaseServiceImpl<CtContractElement, Long> implements CtContractElementService {

	@Resource(name = "ctContractElementDao")
	@Override
	public void setDao (BaseDao<CtContractElement, Long> dao) {
		super.dao = dao;
	}

}
