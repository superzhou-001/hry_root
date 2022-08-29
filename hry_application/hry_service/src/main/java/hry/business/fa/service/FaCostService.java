/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:23 
 */
package hry.business.fa.service;

import hry.core.mvc.service.BaseService;
import hry.business.fa.model.FaCost;

import java.util.List;

/**
 * <p> FaCostService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:23 
 */
public interface FaCostService extends BaseService<FaCost, Long> {
    public List<FaCost> findFaCostList(Long productId);
}
