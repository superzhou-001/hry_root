/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:12:26 
 */
package hry.business.cu.dao;

import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuCustomer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> CuCustomerDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:12:26 
 */
@Mapper
public interface CuCustomerDao extends BaseDao<CuCustomer, Long> {

    /**
     * 查询没有绑定企业的用户
     * @param map
     * @return
     */
    List<CuCustomer> findCustomerNotEnterprise(Map<String,Object> map);

}
