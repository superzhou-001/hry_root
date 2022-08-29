/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:25:30 
 */
package hry.scm.fundsupport.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> FundSupportService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:25:30 
 */
@Service("fundSupportService")
public class FundSupportServiceImpl extends BaseServiceImpl<FundSupport, Long> implements FundSupportService {

	@Resource(name = "fundSupportDao")
	@Override
	public void setDao (BaseDao<FundSupport, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	ScmEnterpriseService scmEnterpriseService;

	@Autowired
	EnterpriseQuotaService enterpriseQuotaService;

	@Override
	public void initEnterpriseQuota(FundSupport fundSupport) {
		if(null!=fundSupport&&null!=fundSupport.getId()){
			QueryFilter filter = new QueryFilter(ScmEnterprise.class);
			filter.addFilter("status=",2);
			List<ScmEnterprise> enterpriseList = scmEnterpriseService.find(filter);
			QueryFilter qfilter = new QueryFilter(EnterpriseQuota.class);
			qfilter.addFilter("fundSupportId=",fundSupport.getId());
			Long count = enterpriseQuotaService.count(qfilter);
			if(null!=enterpriseList&&count==0){
				for (ScmEnterprise enterprise : enterpriseList){
					enterpriseQuotaService.initEnterpriseQuota(enterprise.getId(),fundSupport.getId(),fundSupport.getFundSupportName());
				}
			}

		}
	}

	@Override
	public void updateEnterpriseQuota(FundSupport fundSupport) {
		if(null!=fundSupport&&null!=fundSupport.getId()){
			QueryFilter filter = new QueryFilter(ScmEnterprise.class);
			filter.addFilter("fundSupportId=",fundSupport.getId());
			List<EnterpriseQuota> enterpriseQuotaList = enterpriseQuotaService.find(filter);
			if(null!=enterpriseQuotaList){
				for (EnterpriseQuota enterpriseQuota : enterpriseQuotaList){
					enterpriseQuota.setIsValid(fundSupport.getIsEnable());
					enterpriseQuotaService.update(enterpriseQuota);
				}
			}
		}
	}
}
