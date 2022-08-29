/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-02 14:52:53
 */
package hry.business.cf.dao;

import hry.business.cf.model.CfFacilityMortgage;
import hry.core.mvc.dao.BaseDao;
import hry.business.cf.model.CfFacilityGuarantee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p> CfFacilityGuaranteeDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-02 14:52:53
 */
@Mapper
public interface CfFacilityGuaranteeDao extends BaseDao<CfFacilityGuarantee, Long> {

    /**
     * 新sql分页查询
     *
     * @param map
     * @return
     */
    List<CfFacilityGuarantee> findPageBySql(Map<String, Object> map);

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @Select("SELECT " +
            "cfg.*," +
            "IF (cfg.customerType = 1," +
            "(SELECT NAME FROM cu_person WHERE id = cfg.customerSubjectId LIMIT 1 )," +
            "(SELECT NAME FROM cu_enterprise WHERE id = cfg.customerSubjectId LIMIT 1 )" +
            ") as guarantorName" +
            " FROM " +
            "cf_facility_guarantee cfg where cfg.id=#{id}")
    CfFacilityGuarantee getCfFacilityGuarantee(@Param("id") Long id);


}
