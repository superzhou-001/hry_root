/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:07 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDefineForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDefineFormDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:07 
 */
@Mapper
public interface ProDefineFormDao extends BaseDao<ProDefineForm, Long> {

}
