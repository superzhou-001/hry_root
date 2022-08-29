/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 16:09:20 
 */
package hry.business.cu.dao;

import hry.business.cu.model.CuPerson;
import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuIntentionPerson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CuIntentionPersonDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 16:09:20 
 */
@Mapper
public interface CuIntentionPersonDao extends BaseDao<CuIntentionPerson, Long> {

    /**
     * 查询意向客户主要联系人
     * @param cuIntentionId
     * @return
     */
    CuPerson getIntentionPersonByIntentionId(@Param("cuIntentionId") Long cuIntentionId);

    /**
     * 查询意向客户联系人列表
     * @param cuIntentionId
     * @return
     */
    List<CuPerson> findIntentionPersonByIntentionId(@Param("cuIntentionId") Long cuIntentionId);

}
