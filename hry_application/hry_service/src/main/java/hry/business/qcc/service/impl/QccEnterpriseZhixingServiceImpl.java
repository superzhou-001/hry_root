/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 17:13:23 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseZhixing;
import hry.business.qcc.service.QccEnterpriseZhixingService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseZhixingService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 17:13:23 
 */
@Service("qccEnterpriseZhixingService")
public class QccEnterpriseZhixingServiceImpl extends BaseServiceImpl<QccEnterpriseZhixing, Long> implements QccEnterpriseZhixingService {

	@Resource(name = "qccEnterpriseZhixingDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseZhixing, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("企业被执行人信息未查询到");
			}

			//将返回结果转为实体
			List<QccEnterpriseZhixing> list = JSON.parseArray(result, QccEnterpriseZhixing.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("企业被执行人信息未查询到！");
			}

			for (QccEnterpriseZhixing qccEnterpriseZhixing : list) {
				qccEnterpriseZhixing.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseZhixing.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业被执行人信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业被执行人信息查询成功");
	}
}
