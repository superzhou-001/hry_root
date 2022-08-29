/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 14:31:34 
 */
package hry.business.cu.dao;

import hry.business.cu.model.CuEnterprisePerson;
import hry.business.cu.model.CuPerson;
import hry.core.mvc.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p> CuEnterprisePersonDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 14:31:34 
 */
@Mapper
public interface CuEnterprisePersonDao extends BaseDao<CuEnterprisePerson, Long> {

    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<CuPerson> findPageBySql(Map<String,Object> map);

    /**
     * 查询企业相关类型人员信息
     * @return
     */
    CuPerson getCuEnterprisePersonByEnterpriseIdAndType(@Param("enterpriseId") Long enterpriseId, @Param("type") Integer type);


    /**
     * 查询企业相关类型人员信息列表
     * @return
     */
    List<CuPerson> findCuEnterprisePersonByEnterpriseIdAndType(Map<String, Object> map);

    /**
     * 更新主要联系人为否
     * @param enterpriseId
     */
    void updatwIsMainPerson(@Param("enterpriseId") Long enterpriseId);

}
