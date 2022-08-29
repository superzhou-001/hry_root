/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.scm.enterprise.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.dao.CfFacilityFlowDao;
import hry.business.cf.model.CfFacilityFlow;
import hry.business.cf.model.CfFacilityMortgage;
import hry.business.cu.model.CuCar;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.model.CuHouse;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.dao.ScmEnterpriseDao;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.file.model.ScmFile;
import hry.scm.file.service.ScmFileService;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.JWTContext;
import hry.util.UUIDUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ScmEnterpriseService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:35:56 
 */
@Service("scmEnterpriseService")
public class ScmEnterpriseServiceImpl extends BaseServiceImpl<ScmEnterprise, Long> implements ScmEnterpriseService {

	@Resource(name = "scmEnterpriseDao")
	@Override
	public void setDao (BaseDao<ScmEnterprise, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private CuCustomerService cuCustomerService;
	@Autowired
	private UserRelationService userRelationService;
	@Autowired
	private ScmFileService scmFileService;
	@Autowired
	private EnterpriseQuotaService enterpriseQuotaService;

	@Override
	public JsonResult addEnterprise(HttpServletRequest request, String jsonStr, CuCustomer user) {
		JsonResult jsonResult = new JsonResult();
		jsonStr = StringEscapeUtils.unescapeHtml(jsonStr);
		try {
			//1.创建企业
			//将参数转为json类型
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			//企业基本信息
			String enterprise = jsonObject.getString("enterprise");
			if (StringUtils.isEmpty(enterprise)) {
				return jsonResult.setSuccess(false).setMsg("企业基本信息不能为空");
			}
			ScmEnterprise scmEnterprise = JSONObject.parseObject(enterprise, ScmEnterprise.class);
			scmEnterprise.setStatus(1);//待审核

			//社会信用代码为业务唯一标识，不能重复
			if(!StringUtils.isEmpty(checkCreditCode(scmEnterprise.getCreditCode()))){
				return jsonResult.setSuccess(false).setMsg("社会统一信用代码已经存在");
			}


			//2.用户信息维护
			String customer = jsonObject.getString("customer");
			if (StringUtils.isEmpty(customer)) {
				return jsonResult.setSuccess(false).setMsg("企业管理员信息不能为空");
			}
			CuCustomer newCuCustomer = JSONObject.parseObject(customer, CuCustomer.class);

			CuCustomer cuCustomer = cuCustomerService.get(user.getId());
			if(!StringUtils.isEmpty(newCuCustomer.getUsername())){
				//姓名
				cuCustomer.setUsername(newCuCustomer.getUsername());
				user.setUsername(newCuCustomer.getUsername());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getGender())){
				//性别
				cuCustomer.setGender(newCuCustomer.getGender());
				user.setGender(newCuCustomer.getGender());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getEmail())){
				//联系邮箱
				cuCustomer.setEmail(newCuCustomer.getEmail());
				user.setEmail(newCuCustomer.getEmail());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getProvince())){
				//省
				cuCustomer.setProvince(newCuCustomer.getProvince());
				user.setProvince(newCuCustomer.getProvince());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getCity())){
				//市
				cuCustomer.setCity(newCuCustomer.getCity());
				user.setCity(newCuCustomer.getCity());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getCounty())){
				//县区
				cuCustomer.setCounty(newCuCustomer.getCounty());
				user.setCounty(newCuCustomer.getCounty());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getAddress())){
				//详细地址
				cuCustomer.setAddress(newCuCustomer.getAddress());
				user.setAddress(newCuCustomer.getAddress());
			}


			this.save(scmEnterprise);//创建企业信息
			cuCustomerService.update(cuCustomer);//更新用户信息
			//保存用户关系
			userRelationService.saveUserRelation(cuCustomer.getId(),scmEnterprise.getId(),1,1);

			//刷新用户信息
			JWTContext.updateUser(request, JSON.toJSONString(user));

			return jsonResult.setSuccess(true).setMsg("成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	@Override
	public JsonResult updateEnterprise(HttpServletRequest request, String jsonStr, CuCustomer user) {
		JsonResult jsonResult = new JsonResult();
		jsonStr = StringEscapeUtils.unescapeHtml(jsonStr);
		//申请授信
		//类型（1:修改企业信息，2:申请授信）
		String bType = request.getParameter("bType");
		String fundSupportId = request.getParameter("fundSupportId");
		try {
			//1.创建企业
			//将参数转为json类型
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			//企业基本信息
			String enterprise = jsonObject.getString("enterprise");
			if (StringUtils.isEmpty(enterprise)) {
				return jsonResult.setSuccess(false).setMsg("企业基本信息不能为空");
			}

			ScmEnterprise scmEnterprise = JSONObject.parseObject(enterprise, ScmEnterprise.class);
			if(StringUtils.isEmpty(scmEnterprise.getId())){
				return jsonResult.setSuccess(false).setMsg("企业Id不能为空");
			}

			//社会信用代码为业务唯一标识，不能重复
			ScmEnterprise oldScmEnterprise = checkCreditCode(scmEnterprise.getCreditCode());
			if(!StringUtils.isEmpty(oldScmEnterprise) && scmEnterprise.getId() != oldScmEnterprise.getId()){
				return jsonResult.setSuccess(false).setMsg("社会统一信用代码已经存在");
			}

			//判断是否为修改企业信息
			if(!StringUtils.isEmpty(bType) && Integer.valueOf(bType) == 1){
				scmEnterprise.setStatus(1);//待审核
			}

			//维护file信息--资质或荣誉证书
			if(!StringUtils.isEmpty(jsonObject.getString("honorFile"))){
				scmFileService.saveFiles("honorFile",scmEnterprise.getId(),jsonObject.getString("honorFile"));
			}
			if(!StringUtils.isEmpty(jsonObject.getString("financeFile"))){
				//维护file信息--财务报表
				scmFileService.saveFiles("financeFile",scmEnterprise.getId(),jsonObject.getString("financeFile"));
			}

			//2.用户信息维护
			String customer = jsonObject.getString("customer");
			if (StringUtils.isEmpty(customer)) {
				return jsonResult.setSuccess(false).setMsg("企业管理员信息不能为空");
			}
			CuCustomer newCuCustomer = JSONObject.parseObject(customer, CuCustomer.class);

			CuCustomer cuCustomer = cuCustomerService.get(user.getId());
			if(!StringUtils.isEmpty(newCuCustomer.getUsername())){
				//姓名
				cuCustomer.setUsername(newCuCustomer.getUsername());
				user.setUsername(newCuCustomer.getUsername());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getGender())){
				//性别
				cuCustomer.setGender(newCuCustomer.getGender());
				user.setGender(newCuCustomer.getGender());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getEmail())){
				//联系邮箱
				cuCustomer.setEmail(newCuCustomer.getEmail());
				user.setEmail(newCuCustomer.getEmail());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getProvince())){
				//省
				cuCustomer.setProvince(newCuCustomer.getProvince());
				user.setProvince(newCuCustomer.getProvince());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getCity())){
				//市
				cuCustomer.setCity(newCuCustomer.getCity());
				user.setCity(newCuCustomer.getCity());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getCounty())){
				//县区
				cuCustomer.setCounty(newCuCustomer.getCounty());
				user.setCounty(newCuCustomer.getCounty());
			}
			if(!StringUtils.isEmpty(newCuCustomer.getAddress())){
				//详细地址
				cuCustomer.setAddress(newCuCustomer.getAddress());
				user.setAddress(newCuCustomer.getAddress());
			}


			this.update(scmEnterprise);//修改企业信息
			cuCustomerService.update(cuCustomer);//更新用户信息

			//判断是否为申请授信
			if(!StringUtils.isEmpty(bType) && Integer.valueOf(bType) == 2){
				enterpriseQuotaService.updateAduitStatus(user.getInfoId(),Long.valueOf(fundSupportId),1);
			}

			//刷新用户信息
			JWTContext.updateUser(request, JSON.toJSONString(user));

			return jsonResult.setSuccess(true).setMsg("成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonResult;
	}

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<ScmEnterprise> page = PageFactory.getPage(filter);

		String status = filter.getRequest().getParameter("status");
		String enterpriseName = filter.getRequest().getParameter("enterpriseName");
		String username = filter.getRequest().getParameter("username");
		String mobile = filter.getRequest().getParameter("mobile");

		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(status)) {
			map.put("status", status);
		}
		if (!StringUtils.isEmpty(enterpriseName)) {
			map.put("enterpriseName", "%" + enterpriseName + "%");
		}
		if (!StringUtils.isEmpty(username)) {
			map.put("username", "%" + username + "%");
		}
		if (!StringUtils.isEmpty(mobile)) {
			map.put("mobile", "%" + mobile + "%");
		}
		((ScmEnterpriseDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public ScmEnterprise checkCreditCode(String creditCode) {
		QueryFilter filter = new QueryFilter(ScmEnterprise.class);
		filter.addFilter("creditCode=",creditCode);
		return this.get(filter);
	}


}
