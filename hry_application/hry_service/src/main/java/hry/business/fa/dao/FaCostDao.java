/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:23 
 */
package hry.business.fa.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaCost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p> FaCostDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:23 
 */
@Mapper
public interface FaCostDao extends BaseDao<FaCost, Long> {
    public List<FaCost> findFaCostList(Long productId);
}
