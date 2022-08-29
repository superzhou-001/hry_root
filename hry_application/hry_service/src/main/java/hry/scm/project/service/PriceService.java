/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-17 15:08:55 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.scm.project.model.Price;

import java.util.Map;

/**
 * <p> PriceService </p>
 *
 * @author: luyue
 * @Date: 2020-07-17 15:08:55 
 */
public interface PriceService extends BaseService<Price, Long> {
    /**
     * 查询最近60天价格记录
     * @param map
     * @return
     */
    public JsonResult findList(Map<String,String> map);

}
