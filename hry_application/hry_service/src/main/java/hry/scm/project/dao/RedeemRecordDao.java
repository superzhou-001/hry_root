/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.RedeemRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> RedeemRecordDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-21 16:41:25 
 */
@Mapper
public interface RedeemRecordDao extends BaseDao<RedeemRecord, Long> {

}
