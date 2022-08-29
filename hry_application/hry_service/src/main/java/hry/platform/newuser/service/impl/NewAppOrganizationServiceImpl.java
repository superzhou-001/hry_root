/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月2日 下午4:33:16
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.*;
import hry.platform.newuser.model.NewAppOrganization;
import hry.platform.newuser.model.NewAppUserOrganization;
import hry.platform.newuser.service.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月2日 下午4:33:16
 */
@Service("newAppOrganizationService")
public class NewAppOrganizationServiceImpl extends BaseServiceImpl<NewAppOrganization, Long>  implements NewAppOrganizationService {

	@Resource(name = "newAppOrganizationDao")
	@Override
	public void setDao(BaseDao<NewAppOrganization, Long> dao) {
		super.dao = dao;
	}


	@Resource
	private NewAppUserRoleDao appUserRoleDao;

	@Resource
	private NewAppUserRoleService appUserRoleService;


	@Resource
	private NewAppRoleDao appRoleDao;

	@Resource
	private NewAppRoleService appRoleService;

	@Resource
	private NewAppUserDao appUserDao;

	@Resource
	private NewAppUserService appUserService;

	@Resource
	private NewAppUserOrganizationDao appUserOrganizationDao;

	@Resource
	private NewAppUserOrganizationService appUserOrganizationService;



	@Override
	public JsonResult remove(Long id) {
		JsonResult jsonResult = new JsonResult();
		//判空
		if(StringUtils.isEmpty(id)){
			jsonResult.setSuccess(false);
			jsonResult.setMsg("id不能为空");
			return jsonResult;
		}else{
			// 校验部门下是否有子部门
			QueryFilter childFilter = new QueryFilter(NewAppOrganization.class);
			childFilter.addFilter("pid=", id);
			List<NewAppOrganization> childOrg = super.find(childFilter);
			if (childOrg != null && childOrg.size() > 0) {
				jsonResult.setSuccess(false);
				jsonResult.setMsg("该组织存在子节点,请先删除子节点");
				return jsonResult;
			}
			// 校验组织中是否有用户
			QueryFilter filter = new QueryFilter(NewAppUserOrganization.class);
			filter.addFilter("organizationid=", id);
			List<NewAppUserOrganization> findByAppOrganization =  appUserOrganizationService.find(filter);
			if(findByAppOrganization!=null&&findByAppOrganization.size()>0){
				jsonResult.setSuccess(false);
				jsonResult.setMsg("该组织下存在用户,请先删除用户");
				return jsonResult;
			}
			super.delete(Long.valueOf(id));
			jsonResult.setSuccess(true);
			return jsonResult;
		}
	}

	@Override
	public JsonResult removeSubCompanty(String[] ids) {
		return null;
	}


	@Override
	public List<NewAppOrganization> findByCompanyId(String companyId, String type) {
		return findRecursive(companyId,type,new ArrayList<NewAppOrganization>());
	}

	public  List<NewAppOrganization> findRecursive(String companyId, String type, List<NewAppOrganization> allList){

		QueryFilter filter = new QueryFilter(NewAppOrganization.class);

		if(!StringUtils.isEmpty(companyId)){
			filter.addFilter("pid=", companyId);
		}
		if(!StringUtils.isEmpty(type)){
			filter.addFilter("type=", type);
		}
		List<NewAppOrganization> dataList = dao.selectByExample(filter.getExample());
		if(dataList!=null&&dataList.size()>0){
			for(NewAppOrganization appOrganization : dataList){
				allList.add(appOrganization);
				findRecursive(appOrganization.getId().toString(), type, allList);
			}
		}
		return allList;
	}

	@Override
	public List<NewAppOrganization> findSons(NewAppOrganization appOrganization) {
		return findRecursive(appOrganization.getId().toString(),null,new ArrayList<NewAppOrganization>());
	}

	@Override
	public String findSonsToIds(NewAppOrganization appOrganization) {
		List<NewAppOrganization> findSons = findSons(appOrganization);
		String ids ="";
		if(findSons!=null){
			for(int i = 0 ; i < findSons.size() ; i++ ){
				ids += findSons.get(i).getId().toString();
				if(i!=findSons.size()-1){
					ids += ",";
				}
			}
			ids = ids + "," + appOrganization.getId();
		}
		if(ids.length()==0){
			ids = appOrganization.getId()+"";
		}
		return ids;
	}

	@Override
	public boolean hasSonOrganization(NewAppOrganization appOrganization) {
		List<NewAppOrganization> list = ((NewAppOrganizationDao)dao).findSonOrganization(appOrganization.getId());
		if(list!=null&&list.size()>0){
			return true;
		}
		return false;
	}

	@Override
	public NewAppUserOrganization getUserOrganization(String userid) {
		List<NewAppUserOrganization> userOrganizations = appUserOrganizationService.findUserOrganization(userid);
		return userOrganizations != null && userOrganizations.size() > 0 ? userOrganizations.get(0) : null;
	}
}
