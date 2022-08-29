/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:34:53 
 */
package hry.business.fa.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaProductContract;
import hry.core.util.QueryFilter;

/**
 * <p> FaProductContractService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:34:53 
 */
public interface FaProductContractService extends BaseService<FaProductContract, Long> {

    public PageResult findPageProductContractList(QueryFilter filter);

    public JsonResult addProductContract(Long productid, String contractids);

    public JsonResult getProductContract(Long productid);

}
