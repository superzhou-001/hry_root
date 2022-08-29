/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-30 11:37:30 
 */
package hry.platform.newuser.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppOrganizationChargeDao;
import hry.platform.newuser.model.NewAppOrganizationCharge;
import hry.platform.newuser.service.NewAppOrganizationChargeService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> NewAppOrganizationChargeService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-30 11:37:30 
 */
@Service("newAppOrganizationChargeService")
public class NewAppOrganizationChargeServiceImpl extends BaseServiceImpl<NewAppOrganizationCharge, Long> implements NewAppOrganizationChargeService {

	@Resource(name = "newAppOrganizationChargeDao")
	@Override
	public void setDao (BaseDao<NewAppOrganizationCharge, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult addOrganizationCharge(Long organizationId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppOrganizationCharge.class);
			filter.addFilter("organizationid=", organizationId);
			filter.addFilter("userid=", userId);
			if (super.get(filter) == null) {
				NewAppOrganizationCharge userOrganization = new NewAppOrganizationCharge();
				userOrganization.setOrganizationid(organizationId);
				userOrganization.setUserid(Long.parseLong(userId));
				super.save(userOrganization);
			}
		});
		return new JsonResult(true);
	}

	@Override
	public JsonResult removeOrganizationCharge(Long organizationId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppOrganizationCharge.class);
			filter.addFilter("organizationid=", organizationId);
			filter.addFilter("userid=", userId);
			super.delete(filter);
		});
		return new JsonResult(true);
	}

	@Override
	public PageResult findPageOrganizationCharge(QueryFilter filter) {
		Page<NewAppOrganizationCharge> page = PageFactory.getPage(filter);
		String organizationid = filter.getRequest().getParameter("organizationId");
		String name = filter.getRequest().getParameter("name");
		String startTime = filter.getRequest().getParameter("startTime");
		String endTime = filter.getRequest().getParameter("endTime");
		Map<String, Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(name)){
			map.put("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(endTime)){
			map.put("endTime",endTime);
		}
		if(!StringUtils.isEmpty(startTime)){
			map.put("startTime",startTime);
		}
		map.put("organizationid", organizationid);
		((NewAppOrganizationChargeDao)dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
