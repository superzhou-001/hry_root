/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:02:46 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.MortgageDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> MortgageDetailDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:02:46 
 */
@Mapper
public interface MortgageDetailDao extends BaseDao<MortgageDetail, Long> {

}
