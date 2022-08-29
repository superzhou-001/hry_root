/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:55:47 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProProcess;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProProcessDao </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:55:47 
 */
@Mapper
public interface ProProcessDao extends BaseDao<ProProcess, Long> {

}
