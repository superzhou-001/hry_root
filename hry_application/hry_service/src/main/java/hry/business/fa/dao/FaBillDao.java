/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 13:41:22 
 */
package hry.business.fa.dao;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaBill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> FaBillDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 13:41:22 
 */
@Mapper
public interface FaBillDao extends BaseDao<FaBill, Long> {

    /**
     * 根据projectId查询关联的票据信息
     * @param projectId
     * @return
     */
    @Select("SELECT ffb.id as id ,fb.* FROM fa_factoring_bill ffb LEFT JOIN fa_bill fb ON ffb.billId = fb.id WHERE ffb.projectId=${projectId}")
    List<FaBill> findBillByProjectId(@Param("projectId") Long projectId);

    /**
     * 批量更新status=0的更新为1
     * @param ids
     * @return
     */
    @Select("UPDATE fa_bill SET `status`=1 WHERE id in(${ids}) AND `status`=0")
    void updateStatusByIds(@Param("ids") String ids);

}
