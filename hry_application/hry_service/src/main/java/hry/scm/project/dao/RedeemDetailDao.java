/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-24 11:17:34 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.RedeemDetail;
import hry.scm.project.model.vo.RedeemDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> RedeemDetailDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-24 11:17:34 
 */
@Mapper
public interface RedeemDetailDao extends BaseDao<RedeemDetail, Long> {
    /**
     * 解除质押物事查询清单方法
     * @return
     */
    public List<RedeemDetailVo> findAlldetailBySql(Map<String,String> map);

}
