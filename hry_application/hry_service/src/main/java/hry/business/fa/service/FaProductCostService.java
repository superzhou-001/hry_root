/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:52
 */
package hry.business.fa.service;

import hry.business.fa.model.FaCost;
import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaProductCost;
import java.util.List;

/**
 * <p> FaProductCostService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:52
 */
public interface FaProductCostService extends BaseService<FaProductCost, Long> {

    List<FaCost> findByProductId(Long id);
}
