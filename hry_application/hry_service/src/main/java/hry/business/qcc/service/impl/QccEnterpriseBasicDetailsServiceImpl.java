/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 15:54:35 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseBasicDetails;
import hry.business.qcc.service.QccEnterpriseBasicDetailsService;
import hry.core.util.QueryFilter;
import hry.platform.config.service.AppConfigService;
import hry.util.StringUtil;
import hry.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> QccEnterpriseBasicDetailsService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 15:54:35 
 */
@Service("qccEnterpriseBasicDetailsService")
public class QccEnterpriseBasicDetailsServiceImpl extends BaseServiceImpl<QccEnterpriseBasicDetails, Long> implements QccEnterpriseBasicDetailsService {

	@Resource(name = "qccEnterpriseBasicDetailsDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseBasicDetails, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();
		try {
			//根据信用码查询企业信息
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap,qccMap);
			if(!StringUtil.isNull(result)){
				return jsonResult.setSuccess(false).setMsg("工商基础信息为查询到！");
			}
			//将返回结果转为实体
			QccEnterpriseBasicDetails qccEnterpriseBasicDetails = JSON.parseObject(result,QccEnterpriseBasicDetails.class);
			qccEnterpriseBasicDetails.setUuid((String)paramMap.get("ordernumber"));
			qccEnterpriseBasicDetails.setEnterpriseId(paramMap.get("enterpriseId").toString());
			//保存
			this.save(qccEnterpriseBasicDetails);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("工商基础信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("工商基础信息查询成功");
	}

}
