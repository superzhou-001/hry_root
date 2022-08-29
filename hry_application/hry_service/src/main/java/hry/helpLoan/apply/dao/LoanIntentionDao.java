/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-22 13:50:08 
 */
package hry.helpLoan.apply.dao;

import hry.core.mvc.dao.BaseDao;
import hry.helpLoan.apply.model.LoanIntention;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> LoanIntentionDao </p>
 *
 * @author: huanggh
 * @Date: 2020-07-22 13:50:08 
 */
@Mapper
public interface LoanIntentionDao extends BaseDao<LoanIntention, Long> {

}
