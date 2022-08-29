/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 11:21:29 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseException;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterprisePartner;
import hry.business.qcc.service.QccEnterprisePartnerService;
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
 * <p> QccEnterprisePartnerService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 11:21:29 
 */
@Service("qccEnterprisePartnerService")
public class QccEnterprisePartnerServiceImpl extends BaseServiceImpl<QccEnterprisePartner, Long> implements QccEnterprisePartnerService {

	@Resource(name = "qccEnterprisePartnerDao")
	@Override
	public void setDao (BaseDao<QccEnterprisePartner, Long> dao) {
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
				return jsonResult.setSuccess(false).setMsg("企业股东信息未查询到");
			}
			//将返回结果转为实体
			List<QccEnterprisePartner> list = JSON.parseArray(result, QccEnterprisePartner.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("企业股东信息未查询到！");
			}

			for (QccEnterprisePartner qccEnterprisePartner : list) {
				qccEnterprisePartner.setUuid((String)paramMap.get("ordernumber"));
				qccEnterprisePartner.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业股东信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业股东信息查询成功");
	}

}
