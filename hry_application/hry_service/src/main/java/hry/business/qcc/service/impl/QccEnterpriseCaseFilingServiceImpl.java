/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:34:54 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseBranch;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseCaseFiling;
import hry.business.qcc.service.QccEnterpriseCaseFilingService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseCaseFilingService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:34:54 
 */
@Service("qccEnterpriseCaseFilingService")
public class QccEnterpriseCaseFilingServiceImpl extends BaseServiceImpl<QccEnterpriseCaseFiling, Long> implements QccEnterpriseCaseFilingService {

	@Resource(name = "qccEnterpriseCaseFilingDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseCaseFiling, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();
		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap,qccMap);
			if(!StringUtil.isNull(result)){
				return jsonResult.setSuccess(false).setMsg("立案信息未查询到!");
			}
			//将返回结果转为实体
			List<QccEnterpriseCaseFiling> list = JSON.parseArray(result, QccEnterpriseCaseFiling.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("立案信息未查询到！");
			}
			for (QccEnterpriseCaseFiling qccEnterpriseCaseFiling : list) {
				qccEnterpriseCaseFiling.setUuid((String)paramMap.get("ordernumber"));
				qccEnterpriseCaseFiling.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("立案信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("立案信息查询成功");
	}
}
