/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 15:55:35 
 */
package hry.business.cf.dao;

import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.business.cf.model.CfFacilityMortgage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p> CfFacilityMortgageDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 15:55:35 
 */
@Mapper
public interface CfFacilityMortgageDao extends BaseDao<CfFacilityMortgage, Long> {

    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<CfFacilityMortgage> findPageBySql(Map<String,Object> map);
    /**
     * 根据Id查询
     * @param id
     * @return
     */
    @Select("SELECT\n" +
            "            cfm.*,\n" +
            "            IF ( cfm.customerType = 1,\n" +
            "            (SELECT NAME FROM cu_person WHERE id = cfm.mortgageSubjectId LIMIT 1 ),\n" +
            "            (SELECT NAME FROM cu_enterprise WHERE id = cfm.mortgageSubjectId LIMIT 1 )\n" +
            "            ) as ownershipName\n" +
            "            FROM\n" +
            "                cf_facility_mortgage cfm where id=#{id}")
    CfFacilityMortgage getCfFacilityMortgage(@Param("id") Long id);


}
