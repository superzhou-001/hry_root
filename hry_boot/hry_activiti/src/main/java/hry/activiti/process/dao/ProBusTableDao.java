/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 10:59:51 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProBusTable;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProBusTableDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 10:59:51 
 */
@Mapper
public interface ProBusTableDao extends BaseDao<ProBusTable, Long> {

}
