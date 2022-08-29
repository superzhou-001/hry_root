/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 11:00:17 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProBusState;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProBusStateDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 11:00:17 
 */
@Mapper
public interface ProBusStateDao extends BaseDao<ProBusState, Long> {

}
