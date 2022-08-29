/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-15 11:58:19 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.ProcessRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProcessRecordDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-15 11:58:19 
 */
@Mapper
public interface ProcessRecordDao extends BaseDao<ProcessRecord, Long> {

}
