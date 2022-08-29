/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:36:21 
 */
package hry.business.cu.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.dao.CuIntentionDao;
import hry.business.cu.model.*;
import hry.business.cu.service.*;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.security.jwt.JWTContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> CuIntentionService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:36:21 
 */
@Service("cuIntentionService")
public class CuIntentionServiceImpl extends BaseServiceImpl<CuIntention, Long> implements CuIntentionService {

	@Resource(name = "cuIntentionDao")
	@Override
	public void setDao (BaseDao<CuIntention, Long> dao) {
		super.dao = dao;
	}
	@Autowired
	private CuEnterpriseUserService cuEnterpriseUserService;
	@Autowired
	private CuEnterpriseService cuEnterpriseService;
	@Autowired
	private CuIntentionUserService cuIntentionUserService;
	@Autowired
	private CuIntentionInfoService cuIntentionInfoService;
	@Autowired
	private CuIntentionPersonService cuIntentionPersonService;

	@Autowired
	private CuPersonService cuPersonService;

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<CuIntention> page = PageFactory.getPage(filter);

		String modified_s = filter.getRequest().getParameter("created_GT");
		String modified_e = filter.getRequest().getParameter("created_LT");
		String status = filter.getRequest().getParameter("status");
		String intentionCode = filter.getRequest().getParameter("intentionCode");
		String type = filter.getRequest().getParameter("type");
		String enterpriseContacts = filter.getRequest().getParameter("enterpriseContacts");
		String movePhone = filter.getRequest().getParameter("movePhone");

		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		if(user==null){
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		if (!StringUtils.isEmpty(movePhone)) {
			map.put("movePhone", "%" + movePhone + "%");
		}
		if (!StringUtils.isEmpty(intentionCode)) {
			map.put("intentionCode", "%" + intentionCode + "%");
		}
		if (!StringUtils.isEmpty(enterpriseContacts)) {
			map.put("enterpriseContacts", "%" + enterpriseContacts + "%");
		}
		if (!StringUtils.isEmpty(type)) {
			map.put("type", type);
		}
		if (!StringUtils.isEmpty(modified_s)) {
			map.put("modified_s", modified_s);
		}
		if (!StringUtils.isEmpty(modified_e)) {
			map.put("modified_e", modified_e);
		}
		((CuIntentionDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult updateStatusFormal(Long id) {
		JsonResult jsonResult = new JsonResult();
		/*NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		if(user==null){
			jsonResult.setSuccess(false).setMsg("请重新登陆");
		}
		//查询意向客户信息
		CuIntention cuIntention = super.get(id);
		if (cuIntention==null){
			jsonResult.setSuccess(false).setMsg("信息不存在");
		}
		//根据信用代码查询企业信息是否存在
		QueryFilter filter = new QueryFilter(CuEnterprise.class);
		filter.addFilter("creditCode=",cuIntention.getCreditCode());
		CuEnterprise cuE = cuEnterpriseService.get(filter);
		if (cuE!=null){
			jsonResult.setSuccess(false).setMsg("企业信息已存在");
		}

		CuEnterprise cuEnterprise = new CuEnterprise();
		cuEnterprise.setSource(3);// 3.意向转入
		cuEnterprise.setEnterpriseType(1);//企业类型 1.卖方 2.买方
		cuEnterprise.setStatus(2);
		cuEnterprise.setName(cuIntention.getName());
		cuEnterprise.setUserName(user.getUserName());
		cuEnterprise.setUserId(user.getId());
		cuEnterprise.setCreditCode(cuIntention.getCreditCode());
		cuEnterpriseService.save(cuEnterprise);

		//添加权限
		CuEnterpriseUser cuEnterpriseUser = new CuEnterpriseUser();
		cuEnterpriseUser.setEnterpriseId(cuEnterprise.getId());
		cuEnterpriseUser.setUserId(user.getId());
		cuEnterpriseUserService.save(cuEnterpriseUser);*/

		return jsonResult.setSuccess(true).setMsg("成功");
	}

	@Override
	public JsonResult addIntention(CuIntention cuIntention) {
		JsonResult jsonResult = new JsonResult();
		NewAppUser user = null;
		if(!StringUtils.isEmpty(cuIntention.getSource())&&cuIntention.getSource()!=2){
			user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
			if (user != null) {
				cuIntention.setUserId(user.getId());
			} else {
				jsonResult.setSuccess(false).setMsg("登陆用户异常");
				return  jsonResult;
			}
		}else{
			user = new NewAppUser();
			user.setId(0L);
		}

		super.save(cuIntention);
		cuIntentionUserService.add(cuIntention.getId(), user.getId().toString());
		//保存意向信息
		if(null != cuIntention.getCuIntentionInfo() && !"".equals(cuIntention.getCuIntentionInfo())){
			CuIntentionInfo cuIntentionInfo = JSON.parseObject((String) cuIntention.getCuIntentionInfo(), CuIntentionInfo.class);
			cuIntentionInfo.setUserId(user.getId());
			cuIntentionInfo.setUserName(user.getUserName());
			cuIntentionInfo.setStatus(1);
			cuIntentionInfo.setIntentionId(cuIntention.getId());
			cuIntentionInfoService.save(cuIntentionInfo);
		}
		/**添加意向客户联系人*/
		//查询联系人是否存在
		QueryFilter filter = new QueryFilter(CuPerson.class);
		filter.addFilter("mobile=",cuIntention.getMovePhone());
		filter.or("email=",cuIntention.getEmail());
		CuPerson cuPerson = cuPersonService.get(filter);
		Long cuPersonId = null;
		if(cuPerson==null){
			CuPerson cuP = new CuPerson();
			cuP.setMobile(cuIntention.getMovePhone());
			cuP.setEmail(cuIntention.getEmail());
			cuP.setName(cuIntention.getEnterpriseContacts());
			cuPersonService.save(cuP);
			cuPersonId = cuP.getId();
		}else {
			cuPersonId = cuPerson.getId();
		}

		CuIntentionPerson cuIntentionPerson = new CuIntentionPerson();
		cuIntentionPerson.setIntentionId(cuIntention.getId());
		cuIntentionPerson.setIsMainPerson(1);
		cuIntentionPerson.setPersonId(cuPersonId);
		cuIntentionPerson.setType(3);
		cuIntentionPersonService.save(cuIntentionPerson);
		return jsonResult.setSuccess(true).setMsg("成功");
	}
}
