/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-28 16:37:12 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.IncreaseMoney;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> IncreaseMoneyDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-28 16:37:12 
 */
@Mapper
public interface IncreaseMoneyDao extends BaseDao<IncreaseMoney, Long> {

}
