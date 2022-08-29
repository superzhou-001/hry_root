/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-05 17:44:32 
 */
package hry.business.cf.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cf.model.CfFacilityContract;
import hry.business.cf.service.CfFacilityContractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CfFacilityContractService </p>
 *
 * @author: yaoz
 * @Date: 2020-08-05 17:44:32 
 */
@Service("cfFacilityContractService")
public class CfFacilityContractServiceImpl extends BaseServiceImpl<CfFacilityContract, Long> implements CfFacilityContractService {

	@Resource(name = "cfFacilityContractDao")
	@Override
	public void setDao (BaseDao<CfFacilityContract, Long> dao) {
		super.dao = dao;
	}

}
