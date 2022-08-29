/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午7:27:20
 */
package hry.platform.newuser.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppUserOrganizationDao;
import hry.platform.newuser.dao.NewAppUserPostDao;
import hry.platform.newuser.model.NewAppOrganization;
import hry.platform.newuser.model.NewAppUserOrganization;
import hry.platform.newuser.model.NewAppUserPost;
import hry.platform.newuser.service.NewAppOrganizationService;
import hry.platform.newuser.service.NewAppUserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午7:27:20
 */
@Service("newAppUserOrganizationService")
public class NewAppUserOrganizationServiceImpl extends BaseServiceImpl<NewAppUserOrganization, Long> implements NewAppUserOrganizationService {

	@Autowired
	public NewAppOrganizationService newAppOrganizationService;

	@Resource(name = "newAppUserOrganizationDao")
	@Override
	public void setDao(BaseDao<NewAppUserOrganization, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<NewAppUserOrganization> findUserOrganization(String userid) {
		return ((NewAppUserOrganizationDao)dao).findUserOrganization(userid);
	}

	@Override
	public PageResult findPageUserOrganization(QueryFilter filter) {
		Page<NewAppUserOrganization> page = PageFactory.getPage(filter);
		String organizationid = filter.getRequest().getParameter("organizationId");
		String name = filter.getRequest().getParameter("name");
		String userid = filter.getRequest().getParameter("userid");
		String startTime = filter.getRequest().getParameter("startTime");
		String endTime = filter.getRequest().getParameter("endTime");
		Map<String, Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(userid)){
			map.put("userid", userid);
		}
		if(!StringUtils.isEmpty(name)){
			map.put("name", name+"%");
		}
		if(!StringUtils.isEmpty(endTime)){
			map.put("endTime",endTime);
		}
		if(!StringUtils.isEmpty(startTime)){
			map.put("startTime",startTime);
		}
		map.put("organizationid", organizationid);
		((NewAppUserOrganizationDao)dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult addOrganizationUser(Long organizationId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppUserOrganization.class);
			filter.addFilter("organizationid=", organizationId);
			filter.addFilter("userid=", userId);
			if (super.get(filter) == null) {
				NewAppOrganization organization = newAppOrganizationService.get(organizationId);
				NewAppUserOrganization userOrganization = new NewAppUserOrganization();
				userOrganization.setOrganizationid(organizationId);
				userOrganization.setUserid(Long.parseLong(userId));
				userOrganization.setType(organization.getType());
				super.save(userOrganization);
			}
		});
		return new JsonResult(true);
	}

	@Override
	public JsonResult removeOrganizationUser(Long organizationId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppUserOrganization.class);
			filter.addFilter("organizationid=", organizationId);
			filter.addFilter("userid=", userId);
			super.delete(filter);
		});
		return new JsonResult(true);
	}
}

