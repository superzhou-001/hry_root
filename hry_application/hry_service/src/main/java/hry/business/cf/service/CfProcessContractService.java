/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.service;

import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplate;
import hry.core.mvc.service.BaseService;
import hry.business.cf.model.CfProcessContract;
import hry.core.util.QueryFilter;

import java.util.List;

/**
 * <p> CfProcessContractService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-24 17:45:18 
 */
public interface CfProcessContractService extends BaseService<CfProcessContract, Long> {

    /**
     * 根据条件查询信息
     * @param filter
     * @return
     */
    List<CtContractTemplate> findListBySql(Long id);

}
