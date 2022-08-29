/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:22:58 
 */
package hry.business.ct.service;

import hry.core.mvc.service.BaseService;
import hry.business.ct.model.CtTable;
import org.apache.ibatis.annotations.Param;

/**
 * <p> CtTableService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:22:58 
 */
public interface CtTableService extends BaseService<CtTable, Long> {

    /**
     * 查询表是否存在
     * @param tableName
     * @return
     */
    boolean listTableColumn(String tableName);

}
