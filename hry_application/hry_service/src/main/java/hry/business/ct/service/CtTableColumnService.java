/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:23:51 
 */
package hry.business.ct.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.business.ct.model.CtTableColumn;

/**
 * <p> CtTableColumnService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:23:51 
 */
public interface CtTableColumnService extends BaseService<CtTableColumn, Long> {

    /**
     * 数据库中获取表字段 自动添加
     * @param tableId
     * @return
     */
    JsonResult addFieldAll(Long tableId);

}
