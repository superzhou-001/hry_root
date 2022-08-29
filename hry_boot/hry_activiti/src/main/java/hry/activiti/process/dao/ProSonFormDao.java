/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:34:06 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProSonForm;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProSonFormDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:34:06 
 */
@Mapper
public interface ProSonFormDao extends BaseDao<ProSonForm, Long> {

}
