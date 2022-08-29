/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 16:04:23 
 */
package hry.business.cu.service;

import hry.bean.JsonResult;
import hry.business.cu.model.CuIntentionUser;
import hry.core.mvc.service.BaseService;

/**
 * <p> CuIntentionUserService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 16:04:23 
 */
public interface CuIntentionUserService extends BaseService<CuIntentionUser, Long> {

    /**
     * 添加关联
     * @param cuIntentionUser
     * @param serIds
     * @return
     */
    JsonResult add(Long intentionId, String userIds);

}
