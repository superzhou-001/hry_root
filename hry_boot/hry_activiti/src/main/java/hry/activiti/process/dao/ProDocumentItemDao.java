/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-21 11:19:36 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDocumentItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDocumentItemDao </p>
 *
 * @author: liushilei
 * @Date: 2020-07-21 11:19:36 
 */
@Mapper
public interface ProDocumentItemDao extends BaseDao<ProDocumentItem, Long> {

}
