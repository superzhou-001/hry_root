/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:43:10 
 */
package hry.scm.file.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.file.model.ScmFile;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ScmFileDao </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:43:10 
 */
@Mapper
public interface ScmFileDao extends BaseDao<ScmFile, Long> {

}
