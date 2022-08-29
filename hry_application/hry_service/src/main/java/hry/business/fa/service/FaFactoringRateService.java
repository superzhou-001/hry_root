/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 10:36:12
 */
package hry.business.fa.service;

import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaFactoringRate;

/**
 * <p> FaFactoringRateService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 10:36:12
 */
public interface FaFactoringRateService extends BaseService<FaFactoringRate, Long> {

    /**
     * 保存费率信息
     * @param projectId
     * @param jsonString
     */
    void saveJson(Long projectId, String jsonString);
}
