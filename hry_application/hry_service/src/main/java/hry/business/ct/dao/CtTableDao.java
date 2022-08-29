/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:22:58 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> CtTableDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:22:58 
 */
@Mapper
public interface CtTableDao extends BaseDao<CtTable, Long> {

    /**
     * 查询表是否存在
     * @return
     */
    @Select("select TABLE_NAME from information_schema.TABLES where TABLE_SCHEMA = (select database()) and TABLE_NAME=#{tableName}")
    String listTableColumn(@Param("tableName") String tableName);

}
