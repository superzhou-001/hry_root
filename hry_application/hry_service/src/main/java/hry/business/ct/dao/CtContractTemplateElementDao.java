/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-09 11:30:50 
 */
package hry.business.ct.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.ct.model.CtContractTemplateElement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CtContractTemplateElementDao </p>
 *
 * @author: yaoz
 * @Date: 2020-06-09 11:30:50 
 */
@Mapper
public interface CtContractTemplateElementDao extends BaseDao<CtContractTemplateElement, Long> {
    /**
     * 查询模板元素内容列表
     * @param templateId
     * @return
     */
    List<CtContractTemplateElement> findElementByTemplateId(@Param("templateId") Long templateId);
}
