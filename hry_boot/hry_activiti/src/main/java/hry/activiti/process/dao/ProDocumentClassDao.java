/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:44 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDocumentClass;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDocumentClassDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:44 
 */
@Mapper
public interface ProDocumentClassDao extends BaseDao<ProDocumentClass, Long> {

}
