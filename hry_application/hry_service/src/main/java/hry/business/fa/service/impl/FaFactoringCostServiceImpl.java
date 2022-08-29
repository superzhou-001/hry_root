/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 09:52:22 
 */
package hry.business.fa.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.model.CuEnterprise;
import hry.business.fa.dao.FaFactoringCostDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaFactoringCost;
import hry.business.fa.service.FaFactoringCostService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.security.jwt.JWTContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> FaFactoringCostService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 09:52:22 
 */
@Service("faFactoringCostService")
public class FaFactoringCostServiceImpl extends BaseServiceImpl<FaFactoringCost, Long> implements FaFactoringCostService {

	@Resource(name = "faFactoringCostDao")
	@Override
	public void setDao (BaseDao<FaFactoringCost, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<FaFactoringCost> page = PageFactory.getPage(filter);

		String modified_s = filter.getRequest().getParameter("created_GT");
		String modified_e = filter.getRequest().getParameter("created_LT");
		String status = filter.getRequest().getParameter("status");
		String username = filter.getRequest().getParameter("username");
		String enterpriseName = filter.getRequest().getParameter("enterpriseName");
		String projectCode = filter.getRequest().getParameter("projectCode");
		String id = filter.getRequest().getParameter("id");

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(id)) {
			map.put("id", id);
		}
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		if (!StringUtils.isEmpty(projectCode)) {
			map.put("projectCode", "%" + projectCode + "%");
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			map.put("enterpriseName", "%" + enterpriseName + "%");
		}
		if (!StringUtils.isEmpty(username)) {
			map.put("username", "%" + username + "%");
		}
		if (!StringUtils.isEmpty(modified_s)) {
			map.put("modified_s", modified_s);
		}
		if (!StringUtils.isEmpty(modified_e)) {
			map.put("modified_e", modified_e);
		}
		((FaFactoringCostDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
