/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:42:17 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDefinePerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDefinePersonDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:42:17 
 */
@Mapper
public interface ProDefinePersonDao extends BaseDao<ProDefinePerson, Long> {

}
