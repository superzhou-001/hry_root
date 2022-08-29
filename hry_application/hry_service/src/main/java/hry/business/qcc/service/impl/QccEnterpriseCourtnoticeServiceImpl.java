/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 14:20:31 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseCourtnotice;
import hry.business.qcc.service.QccEnterpriseCourtnoticeService;
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
 * <p> QccEnterpriseCourtnoticeService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 14:20:31 
 */
@Service("qccEnterpriseCourtnoticeService")
public class QccEnterpriseCourtnoticeServiceImpl extends BaseServiceImpl<QccEnterpriseCourtnotice, Long> implements QccEnterpriseCourtnoticeService {

	@Resource(name = "qccEnterpriseCourtnoticeDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseCourtnotice, Long> dao) {
		super.dao = dao;
	}


	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();
		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("开庭公告信息未查询到");
			}
			//将返回结果转为实体
			List<QccEnterpriseCourtnotice> list = JSON.parseArray(result, QccEnterpriseCourtnotice.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("开庭公告信息未查询到！");
			}

			for (QccEnterpriseCourtnotice qccEnterpriseCourtnotice : list) {
				qccEnterpriseCourtnotice.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseCourtnotice.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("开庭公告信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("开庭公告信息查询成功");
	}

}
