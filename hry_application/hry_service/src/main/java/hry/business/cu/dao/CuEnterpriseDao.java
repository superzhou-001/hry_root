/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:18:39 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuEnterprise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> CuEnterpriseDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:18:39 
 */
@Mapper
public interface CuEnterpriseDao extends BaseDao<CuEnterprise, Long> {

    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<CuEnterprise> findPageBySql(Map<String,Object> map);

}
