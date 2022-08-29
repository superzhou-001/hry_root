/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:25:30 
 */
package hry.scm.fundsupport.service;

import hry.core.mvc.service.BaseService;
import hry.scm.fundsupport.model.FundSupport;

/**
 * <p> FundSupportService </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:25:30 
 */
public interface FundSupportService extends BaseService<FundSupport, Long> {

       public void initEnterpriseQuota(FundSupport fundSupport);

       public void updateEnterpriseQuota(FundSupport fundSupport);

}
