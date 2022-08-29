/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:29 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDocumentDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:29 
 */
@Mapper
public interface ProDocumentDao extends BaseDao<ProDocument, Long> {

}
