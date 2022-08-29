/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 14:11:12 
 */
package hry.business.fa.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaFactoringContract;
import hry.business.fa.service.FaFactoringContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FaFactoringContractService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 14:11:12 
 */
@Service("faFactoringContractService")
public class FaFactoringContractServiceImpl extends BaseServiceImpl<FaFactoringContract, Long> implements FaFactoringContractService {

	@Resource(name = "faFactoringContractDao")
	@Override
	public void setDao (BaseDao<FaFactoringContract, Long> dao) {
		super.dao = dao;
	}

}
