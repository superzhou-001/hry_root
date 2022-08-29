/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:35:04 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDefine;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDefineDao </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:35:04 
 */
@Mapper
public interface ProDefineDao extends BaseDao<ProDefine, Long> {

}
