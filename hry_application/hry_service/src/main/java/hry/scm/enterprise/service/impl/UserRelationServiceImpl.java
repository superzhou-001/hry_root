/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:41:09 
 */
package hry.scm.enterprise.service.impl;

import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.dao.UserRelationDao;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.UserRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> UserRelationService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:41:09 
 */
@Service("userRelationService")
public class UserRelationServiceImpl extends BaseServiceImpl<UserRelation, Long> implements UserRelationService {

	@Resource(name = "userRelationDao")
	@Override
	public void setDao (BaseDao<UserRelation, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private CuCustomerService cuCustomerService;

	@Override
	public List<UserRelation> getRelationList(CuCustomer cuCustomer) {
		//查询用户关系，确定用户身份
		QueryFilter filter = new QueryFilter(UserRelation.class);
		filter.addFilter("customerId=",cuCustomer.getId());
		filter.addFilter("userType=",cuCustomer.getUserType());
		return this.find(filter);
	}

	@Override
	public void saveUserRelation(Long customerId, Long infoId, Integer userType,Integer role) {
		UserRelation userRelation = new UserRelation();
		userRelation.setCustomerId(customerId);
		userRelation.setInfoId(infoId);
		userRelation.setUserType(userType);
		userRelation.setRole(role);
		((UserRelationDao)dao).insertSelective(userRelation);
	}

	@Override
	public CuCustomer getCustomer(Long infoId, Integer userType) {
		//查询企业管理员
		QueryFilter filter = new QueryFilter(UserRelation.class);
		filter.addFilter("infoId=",infoId);
		filter.addFilter("userType=",userType);
		filter.addFilter("role=",1);
		UserRelation userRelation = this.get(filter);
		return cuCustomerService.get(userRelation.getCustomerId());
	}

	@Override
	public UserRelation getRelation(Long customerId, Long infoId) {
		QueryFilter filter = new QueryFilter(UserRelation.class);
		filter.addFilter("customerId=",customerId);
		filter.addFilter("infoId=",infoId);
		return this.get(filter);
	}
}
