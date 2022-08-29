/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:33:52 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProMainForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProMainFormDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:33:52 
 */
@Mapper
public interface ProMainFormDao extends BaseDao<ProMainForm, Long> {

}
