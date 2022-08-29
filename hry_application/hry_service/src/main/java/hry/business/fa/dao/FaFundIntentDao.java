/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-22 13:47:08 
 */
package hry.business.fa.dao;

import hry.business.fa.model.FaFundIntent;
import hry.core.mvc.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> FaFundIntentDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-22 13:47:08 
 */
@Mapper
public interface FaFundIntentDao extends BaseDao<FaFundIntent, Long> {

    public List<FaFundIntent> findFundIntentList(Map<String, Object> map);
}
