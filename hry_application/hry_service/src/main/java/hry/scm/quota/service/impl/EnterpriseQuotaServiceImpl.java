/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:45:12 
 */
package hry.scm.quota.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.scm.quota.dao.EnterpriseQuotaDao;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> EnterpriseQuotaService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:45:12 
 */
@Service("enterpriseQuotaService")
public class EnterpriseQuotaServiceImpl extends BaseServiceImpl<EnterpriseQuota, Long> implements EnterpriseQuotaService {

	@Resource(name = "enterpriseQuotaDao")
	@Override
	public void setDao (BaseDao<EnterpriseQuota, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private FundSupportService fundSupportService;

	@Override
	public void initEnterpriseQuota(Long enterpriseId) {
		List<FundSupport> fundSupportList = fundSupportService.findAll();
		for(FundSupport fundSupport :fundSupportList){
			initEnterpriseQuota(enterpriseId, fundSupport.getId(),fundSupport.getFundSupportName());
		}
	}

	@Override
	public void initEnterpriseQuota(Long enterpriseId, Long fundSupportId,String fundSupportName) {
		EnterpriseQuota enterpriseQuota = new EnterpriseQuota();
		enterpriseQuota.setEnterpriseId(enterpriseId);
		enterpriseQuota.setFundSupportId(fundSupportId);
		enterpriseQuota.setFundSupportName(fundSupportName);
		enterpriseQuota.setCreated(new Date());
		enterpriseQuota.setModified(new Date());
		((EnterpriseQuotaDao)dao).insertSelective(enterpriseQuota);
	}

	@Override
	public EnterpriseQuota getEnterpriseSumQuota(Long enterpriseId) {
		return ((EnterpriseQuotaDao)dao).getEnterpriseSumQuota(enterpriseId);
	}

	@Override
	public PageResult getEnterpriseQuotaList(QueryFilter filter,Map<String, Object> map) {
		Page<EnterpriseQuota> page = PageFactory.getPage(filter);
		((EnterpriseQuotaDao) dao).getEnterpriseQuotaList(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult updateAduitStatus(Long InfoId, Long fundSupportId,Integer aduitStatus) {
		JsonResult jsonResult = new JsonResult();
		QueryFilter queryFilter = new QueryFilter(EnterpriseQuota.class);
		queryFilter.addFilter("enterpriseId=",InfoId);
		queryFilter.addFilter("fundSupportId=",fundSupportId);
		EnterpriseQuota enterpriseQuota = this.get(queryFilter);
		enterpriseQuota.setAduitStatus(aduitStatus);
		this.update(enterpriseQuota);
		return null;
	}
}
