/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:03 
 */
package hry.business.ct.dao;

import hry.business.cu.model.CuPerson;
import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtContractTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p> CtContractTemplateDao </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:03 
 */
@Mapper
public interface CtContractTemplateDao extends BaseDao<CtContractTemplate, Long> {

    /**
     * 查询合同模板信息
     * @param id
     * @return
     */
    CtContractTemplate getContractTemplateById(@Param("id") Long id);

    /**
     * 查询合同模板信息列表
     * @param map
     * @return
     */
    List<CtContractTemplate> findContractTemplate(Map<String,Object> map);


}
