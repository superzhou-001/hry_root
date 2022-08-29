/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 14:13:21 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseJudgmentdoc;
import hry.business.qcc.service.QccEnterpriseJudgmentdocService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseJudgmentdocService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 14:13:21 
 */
@Service("qccEnterpriseJudgmentdocService")
public class QccEnterpriseJudgmentdocServiceImpl extends BaseServiceImpl<QccEnterpriseJudgmentdoc, Long> implements QccEnterpriseJudgmentdocService {

	@Resource(name = "qccEnterpriseJudgmentdocDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseJudgmentdoc, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("企业裁判文书信息未查询到");
			}

			//将返回结果转为实体
			List<QccEnterpriseJudgmentdoc> list = JSON.parseArray(result, QccEnterpriseJudgmentdoc.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("企业裁判文书信息未查询到！");
			}

			for (QccEnterpriseJudgmentdoc qccEnterpriseJudgmentdoc : list) {
				qccEnterpriseJudgmentdoc.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseJudgmentdoc.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业裁判文书信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业裁判文书信息查询成功");
	}
}
