/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-02 14:52:53 
 */
package hry.business.cf.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.dao.CfFacilityGuaranteeDao;
import hry.business.cf.dao.CfFacilityMortgageDao;
import hry.business.cf.model.CfFacilityMortgage;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cf.model.CfFacilityGuarantee;
import hry.business.cf.service.CfFacilityGuaranteeService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> CfFacilityGuaranteeService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-02 14:52:53 
 */
@Service("cfFacilityGuaranteeService")
public class CfFacilityGuaranteeServiceImpl extends BaseServiceImpl<CfFacilityGuarantee, Long> implements CfFacilityGuaranteeService {

	@Resource(name = "cfFacilityGuaranteeDao")
	@Override
	public void setDao (BaseDao<CfFacilityGuarantee, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<CfFacilityGuarantee> page = PageFactory.getPage(filter);
		Map<String, Object> map = new HashMap<String, Object>();

		((CfFacilityGuaranteeDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult getGuarantee(Long id) {
		JsonResult jsonResult = new JsonResult();
		CfFacilityGuarantee cfFacilityGuarantee = ((CfFacilityGuaranteeDao) dao).getCfFacilityGuarantee(id);
		return jsonResult.setSuccess(true).setObj(cfFacilityGuarantee);
	}
}
