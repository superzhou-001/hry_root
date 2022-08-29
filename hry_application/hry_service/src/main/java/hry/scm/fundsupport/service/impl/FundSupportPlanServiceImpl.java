/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:37:09 
 */
package hry.scm.fundsupport.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.scm.fundsupport.model.FundSupportPlan;
import hry.scm.fundsupport.service.FundSupportPlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FundSupportPlanService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:37:09 
 */
@Service("fundSupportPlanService")
public class FundSupportPlanServiceImpl extends BaseServiceImpl<FundSupportPlan, Long> implements FundSupportPlanService {

	@Resource(name = "fundSupportPlanDao")
	@Override
	public void setDao (BaseDao<FundSupportPlan, Long> dao) {
		super.dao = dao;
	}

}
