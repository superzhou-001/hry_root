/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 16:17:44 
 */
package hry.business.cf.dao;

import hry.business.ct.model.CtContractTemplate;
import hry.core.mvc.dao.BaseDao;
import hry.business.cf.model.CfFacilityFlow;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> CfFacilityFlowDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 16:17:44 
 */
@Mapper
public interface CfFacilityFlowDao extends BaseDao<CfFacilityFlow, Long> {

    /**
     * 查询授信信息列表
     * @param map
     * @return
     */
    List<CfFacilityFlow> findPageBySql(Map<String,Object> map);
}
