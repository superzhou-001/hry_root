/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:53:14
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProDefineDocument;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProDefineDocumentDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:53:14
 */
@Mapper
public interface ProDefineDocumentDao extends BaseDao<ProDefineDocument, Long> {

}
