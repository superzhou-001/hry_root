/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-13 17:30:46 
 */
package hry.platform.config.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.config.model.RequestLogRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> RequestLogRecordDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-13 17:30:46 
 */
@Mapper
public interface RequestLogRecordDao extends BaseDao<RequestLogRecord, Long> {

}
