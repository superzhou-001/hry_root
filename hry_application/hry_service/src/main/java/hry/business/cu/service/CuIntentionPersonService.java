/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 16:09:20 
 */
package hry.business.cu.service;

import hry.business.cu.model.CuPerson;
import hry.core.mvc.service.BaseService;
import hry.business.cu.model.CuIntentionPerson;

import java.util.List;

/**
 * <p> CuIntentionPersonService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 16:09:20 
 */
public interface CuIntentionPersonService extends BaseService<CuIntentionPerson, Long> {

    /**
     * 查询意向客户主要联系人
     * @param cuIntentionId
     * @return
     */
    CuPerson getIntentionPersonByIntentionId(Long cuIntentionId);

    /**
     * 查询意向客户联系人列表
     * @param cuIntentionId
     * @return
     */
    List<CuPerson> findIntentionPersonByIntentionId(Long cuIntentionId);

}
