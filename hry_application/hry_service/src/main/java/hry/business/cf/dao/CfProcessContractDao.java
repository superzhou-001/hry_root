/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.dao;

import hry.business.ct.model.CtContractTemplate;
import hry.core.mvc.dao.BaseDao;
import hry.business.cf.model.CfProcessContract;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> CfProcessContractDao </p>
 *
 * @author: yaoz
 * @Date: 2020-07-24 17:45:18 
 */
@Mapper
public interface CfProcessContractDao extends BaseDao<CfProcessContract, Long> {

    @Select("SELECT * FROM cf_process_contract cpc LEFT JOIN ct_contract_template cct ON cpc.contractId = cct.id WHERE cpc.processId = #{id}")
    List<CtContractTemplate> findListBySql(@Param("id") Long id);

}
