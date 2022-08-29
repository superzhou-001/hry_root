/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-22 17:18:30 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.RedeemTotal;
import hry.scm.project.model.vo.RedeemTotalVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> RedeemTotalDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-22 17:18:30 
 */
@Mapper
public interface RedeemTotalDao extends BaseDao<RedeemTotal, Long> {
    /**
     *赎货汇总清单查询
     * @param map
     * @return
     */
   public List<RedeemTotalVo> findRedeemTotalBySql(Map<String,String> map);

}
