/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-28 12:05:59 
 */
package hry.platform.flow.dao;

import hry.core.mvc.dao.BaseDao;
import hry.platform.flow.model.AppFlow;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> AppFlowDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-28 12:05:59 
 */
@Mapper
public interface AppFlowDao extends BaseDao<AppFlow, Long> {

}
