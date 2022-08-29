/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 13:57:47 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterprisePartner;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.business.qcc.service.QccEnterpriseEmployeeService;
import hry.core.util.QueryFilter;
import hry.platform.config.service.AppConfigService;
import hry.util.StringUtil;
import hry.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseEmployeeService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 13:57:47 
 */
@Service("qccEnterpriseEmployeeService")
public class QccEnterpriseEmployeeServiceImpl extends BaseServiceImpl<QccEnterpriseEmployee, Long> implements QccEnterpriseEmployeeService {

	@Resource(name = "qccEnterpriseEmployeeDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseEmployee, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("主要人员信息未查询到!");
			}

			//将返回结果转为实体
			List<QccEnterpriseEmployee> list = JSON.parseArray(result, QccEnterpriseEmployee.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("主要人员信息未查询到！");
			}

			for (QccEnterpriseEmployee qccEnterpriseEmployee : list) {
				qccEnterpriseEmployee.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseEmployee.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("主要人员信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("主要人员信息查询成功");
	}


}
