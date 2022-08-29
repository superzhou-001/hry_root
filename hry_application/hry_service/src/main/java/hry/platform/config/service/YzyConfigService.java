/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 16:12:18 
 */
package hry.platform.config.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.platform.config.model.YzyConfig;

import java.util.List;

/**
 * <p> YzyConfigService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 16:12:18 
 */
public interface YzyConfigService extends BaseService<YzyConfig, Long> {

    /**
     * 根据编码查询信息
     * @param productCode
     * @return
     */
    YzyConfig getYzyConfigByProductCode(String productCode);


    /**
     * 根据产品分类查询信息
     * @param productClassify
     * @return
     */
    List<YzyConfig> findYzyConfigByProductClassify(Integer productClassify);

    /**
     * 获取易租云购买产品信息
     * @return
     */
    JsonResult updateYzyProduct();

}
