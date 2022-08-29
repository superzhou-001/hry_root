/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:13:41 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseJudgmentdoc;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseJudicialAssistance;
import hry.business.qcc.service.QccEnterpriseJudicialAssistanceService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseJudicialAssistanceService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:13:41 
 */
@Service("qccEnterpriseJudicialAssistanceService")
public class QccEnterpriseJudicialAssistanceServiceImpl extends BaseServiceImpl<QccEnterpriseJudicialAssistance, Long> implements QccEnterpriseJudicialAssistanceService {

	@Resource(name = "qccEnterpriseJudicialAssistanceDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseJudicialAssistance, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("企业股权冻结信息未查询到");
			}

			//将返回结果转为实体
			List<QccEnterpriseJudicialAssistance> list = JSON.parseArray(result, QccEnterpriseJudicialAssistance.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("企业股权冻结信息未查询到！");
			}

			for (QccEnterpriseJudicialAssistance qccEnterpriseJudicialAssistance : list) {
				qccEnterpriseJudicialAssistance.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseJudicialAssistance.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业股权冻结信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业股权冻结信息查询成功");
	}
}
