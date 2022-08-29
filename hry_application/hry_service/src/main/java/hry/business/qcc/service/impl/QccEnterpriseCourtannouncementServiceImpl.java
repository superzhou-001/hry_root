/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 15:04:09 
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseCourtannouncement;
import hry.business.qcc.service.QccEnterpriseCourtannouncementService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p> QccEnterpriseCourtannouncementService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 15:04:09 
 */
@Service("qccEnterpriseCourtannouncementService")
public class QccEnterpriseCourtannouncementServiceImpl extends BaseServiceImpl<QccEnterpriseCourtannouncement, Long> implements QccEnterpriseCourtannouncementService {

	@Resource(name = "qccEnterpriseCourtannouncementDao")
	@Override
	public void setDao (BaseDao<QccEnterpriseCourtannouncement, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
		JsonResult jsonResult = new JsonResult();

		try {
			EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
			String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
			if (!StringUtil.isNull(result)) {
				return jsonResult.setSuccess(false).setMsg("法院公告信息未查询到");
			}

			//将返回结果转为实体
			List<QccEnterpriseCourtannouncement> list = JSON.parseArray(result, QccEnterpriseCourtannouncement.class);
			if (list == null) {
				return jsonResult.setSuccess(false).setMsg("法院公告信息未查询到！");
			}

			for (QccEnterpriseCourtannouncement qccEnterpriseCourtannouncement : list) {
				qccEnterpriseCourtannouncement.setUuid((String) paramMap.get("ordernumber"));
				qccEnterpriseCourtannouncement.setEnterpriseId(paramMap.get("enterpriseId").toString());
			}
			//保存
			this.saveAll(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return jsonResult.setSuccess(false).setMsg("法院公告信息查询失败");
		}
		return jsonResult.setSuccess(true).setMsg("法院公告信息查询成功");
	}
}
