/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:26:57 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseBasicDetails;
import hry.business.qcc.model.QccEnterpriseJudicialAssistance;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseLegalProcCount;
import hry.business.qcc.service.QccEnterpriseLegalProcCountService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseLegalProcCountService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:26:57 
 */
@Service("qccEnterpriseLegalProcCountService")
public class QccEnterpriseLegalProcCountServiceImpl extends BaseServiceImpl<QccEnterpriseLegalProcCount, Long> implements QccEnterpriseLegalProcCountService {

	@Resource(name = "qccEnterpriseLegalProcCountDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseLegalProcCount, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("企业法律诉讼维度条目信息为查询到");
			}

			//将返回结果转为实体
			QccEnterpriseLegalProcCount qccEnterpriseLegalProcCount = JSON.parseObject(result,QccEnterpriseLegalProcCount.class);
			qccEnterpriseLegalProcCount.setUuid((String)paramMap.get("ordernumber"));
			qccEnterpriseLegalProcCount.setEnterpriseId(paramMap.get("enterpriseId").toString());
			//保存
			this.save(qccEnterpriseLegalProcCount);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业法律诉讼维度条目信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业法律诉讼维度条目信息查询成功");
	}
}
