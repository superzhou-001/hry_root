/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 17:18:52
 */
package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.model.QccEnterpriseEmployee;
import hry.business.qcc.model.QccEnterpriseException;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.qcc.model.QccEnterpriseInvestment;
import hry.business.qcc.service.QccEnterpriseInvestmentService;
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
 * <p> QccEnterpriseInvestmentService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 17:18:52 
 */
@Service("qccEnterpriseInvestmentService")
public class QccEnterpriseInvestmentServiceImpl extends BaseServiceImpl<QccEnterpriseInvestment, Long> implements QccEnterpriseInvestmentService {

    @Resource(name = "qccEnterpriseInvestmentDao")
    @Override
    public void setDao(BaseDao<QccEnterpriseInvestment, Long> dao) {
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
                return jsonResult.setSuccess(false).setMsg("企业对外投资信息未查询到!");
            }
            //将返回结果转为实体
            List<QccEnterpriseInvestment> list = JSON.parseArray(result, QccEnterpriseInvestment.class);
            if (list == null) {
                return jsonResult.setSuccess(false).setMsg("企业对外投资信息未查询到！");
            }

            for (QccEnterpriseInvestment qccEnterpriseInvestment : list) {
                qccEnterpriseInvestment.setUuid((String)paramMap.get("ordernumber"));
                qccEnterpriseInvestment.setEnterpriseId(paramMap.get("enterpriseId").toString());
            }
            //保存
            this.saveAll(list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return jsonResult.setSuccess(false).setMsg("企业对外投资信息查询失败");
        }
        return jsonResult.setSuccess(true).setMsg("企业对外投资信息查询成功");
    }

}
