/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:29:53 
 */
package hry.business.fa.service;

import hry.bean.JsonResult;
import hry.business.fa.model.FaFundInitParam;
import hry.business.fa.model.FaProduct;
import hry.core.mvc.service.BaseService;

import java.util.Map;

/**
 * <p> FaProductService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:29:53 
 */
public interface FaProductService extends BaseService<FaProduct, Long> {

    /**
     * 创建产品
     * */
    public JsonResult createProduct(FaProduct product);

    /**
     * 创建还款周期---生成分录
     * */
    public JsonResult createFundList(FaFundInitParam param);

    /**
     * 创建还款周期---生成合计
     * */
    public JsonResult createFundListSynthesize(FaFundInitParam param);

    /**
     * 获取到期时间
     * */
    public JsonResult getIntentDate(Map<String, String> map);
}
