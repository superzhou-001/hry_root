/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 14:19:13 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseBasicDetails;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseOverviewInfo;
import hry.business.qcc.service.QccEnterpriseOverviewInfoService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p> QccEnterpriseOverviewInfoService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 14:19:13 
 */
@Service("qccEnterpriseOverviewInfoService")
public class QccEnterpriseOverviewInfoServiceImpl extends BaseServiceImpl<QccEnterpriseOverviewInfo, Long> implements QccEnterpriseOverviewInfoService {

	@Resource(name = "qccEnterpriseOverviewInfoDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseOverviewInfo, Long> dao) {
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
				return jsonResult.setSuccess(false).setMsg("企业工商风险扫描为查询到！");
			}
			//将返回结果转为实体
			QccEnterpriseOverviewInfo qccEnterpriseOverviewInfo = JSON.parseObject(result,QccEnterpriseOverviewInfo.class);
			qccEnterpriseOverviewInfo.setUuid((String)paramMap.get("ordernumber"));
			qccEnterpriseOverviewInfo.setEnterpriseId(paramMap.get("enterpriseId").toString());
			//保存
			this.save(qccEnterpriseOverviewInfo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("企业工商风险扫描信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("企业工商风险扫描信息查询成功");
	}
}
