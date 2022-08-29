/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 18:55:10
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseBasicDetails;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseException;
import hry.business.qcc.service.QccEnterpriseExceptionService;
import hry.core.util.QueryFilter;
import hry.platform.config.service.AppConfigService;
import hry.util.StringUtil;
import hry.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p> QccEnterpriseExceptionService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 18:55:10
 */
@Service("qccEnterpriseExceptionService")
public class QccEnterpriseExceptionServiceImpl extends BaseServiceImpl<QccEnterpriseException, Long> implements QccEnterpriseExceptionService {

    @Resource(name = "qccEnterpriseExceptionDao")
    @Override
    public void setDao(BaseDao<QccEnterpriseException, Long> dao) {
        super.dao = dao;
    }

    @Override
    public JsonResult getQccInfo(Map<String, Object> paramMap, Map<String, Object> qccMap) {
        JsonResult jsonResult = new JsonResult();
        try {

            EnterpriseSearchApi enterpriseSearchApi = new EnterpriseSearchApi();
            String result = enterpriseSearchApi.getBeanClassByQcc(paramMap, qccMap);
            if (!StringUtil.isNull(result)) {
                return jsonResult.setSuccess(true).setMsg("企业异常信息为查询到!");
            }
            //将返回结果转为实体
            List<QccEnterpriseException> list = JSON.parseArray(result, QccEnterpriseException.class);
            if (list == null) {
                return jsonResult.setSuccess(false).setMsg("企业异常信息为查询到！");
            }
            for (QccEnterpriseException qccEnterpriseException : list) {
                qccEnterpriseException.setUuid((String)paramMap.get("ordernumber"));
                qccEnterpriseException.setEnterpriseId(paramMap.get("enterpriseId").toString());
            }
            //保存
            this.saveAll(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return jsonResult.setSuccess(false).setMsg("企业异常信息查询失败");
        }
        return jsonResult.setSuccess(true).setMsg("企业异常信息查询成功");

    }

}
