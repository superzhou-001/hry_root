/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:45:12 
 */
package hry.scm.quota.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.scm.quota.model.EnterpriseQuota;

import java.util.List;
import java.util.Map;

/**
 * <p> EnterpriseQuotaService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:45:12 
 */
public interface EnterpriseQuotaService extends BaseService<EnterpriseQuota, Long> {

       /**
        * 增加资金方初始化企业额度
        * @param enterpriseId
        * @param fundSupportId
        * @param fundSupportName
        */
       public void initEnterpriseQuota(Long enterpriseId,Long fundSupportId,String fundSupportName);

       /**
        * 企业开户成功初始化企业额度
        * @param enterpriseId
        */
       public void initEnterpriseQuota(Long enterpriseId);

       public EnterpriseQuota getEnterpriseSumQuota(Long enterpriseId);

       PageResult getEnterpriseQuotaList(QueryFilter filter, Map<String, Object> map);

       /**
        * 修改审批状态
        * @param InfoId  企业Id
        * @param fundSupportId  资金方Id
        * @param aduitStatus 0待审，1申请中，2已审，3已拒绝
        * @return
        */
       JsonResult updateAduitStatus(Long InfoId,Long fundSupportId,Integer aduitStatus);

}
