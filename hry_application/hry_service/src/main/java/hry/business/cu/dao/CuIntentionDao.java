/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:36:21 
 */
package hry.business.cu.dao;

import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuIntention;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> CuIntentionDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:36:21 
 */
@Mapper
public interface CuIntentionDao extends BaseDao<CuIntention, Long> {

    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<CuIntention> findPageBySql(Map<String,Object> map);

}
