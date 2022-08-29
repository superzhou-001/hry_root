/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 13:41:22 
 */
package hry.business.fa.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaBill;
import hry.core.util.QueryFilter;

import java.util.List;

/**
 * <p> FaBillService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 13:41:22 
 */
public interface FaBillService extends BaseService<FaBill, Long> {

    /**
     * 根据projectId查询关联的票据信息
     * @param projectId
     * @return
     */
    List<FaBill> findBillByProjectId(Long projectId);

    /**
     * 批量更新status=0的更新为1
     * @param ids
     * @return
     */
    void updateStatusByIds(String ids);
}
