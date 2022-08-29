/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:21 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDefineNode;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDefineNodeDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:21 
 */
@Mapper
public interface ProDefineNodeDao extends BaseDao<ProDefineNode, Long> {

}
