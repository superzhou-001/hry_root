/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:23:51 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtTableColumn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CtTableColumnDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:23:51 
 */
@Mapper
public interface CtTableColumnDao extends BaseDao<CtTableColumn, Long> {

    /**
     * 获取表的所有字段
     * @param tableName
     * @return
     */
    List<CtTableColumn> findFieldByTableName(@Param("tableName") String tableName);

}
