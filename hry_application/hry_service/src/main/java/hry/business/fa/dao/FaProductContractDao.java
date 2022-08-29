/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:34:53 
 */
package hry.business.fa.dao;

import hry.business.ct.model.CtContractTemplate;
import hry.core.mvc.dao.BaseDao;
import hry.business.fa.model.FaProductContract;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> FaProductContractDao </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:34:53 
 */
@Mapper
public interface FaProductContractDao extends BaseDao<FaProductContract, Long> {

    public List<FaProductContract> findProductContractList(Map<String, Object> map);

    public List<CtContractTemplate> findContractTemplate(Long productid);
}
