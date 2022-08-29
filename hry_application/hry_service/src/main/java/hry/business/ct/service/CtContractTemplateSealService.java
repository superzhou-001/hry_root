/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-12 17:38:48 
 */
package hry.business.ct.service;

import hry.core.mvc.service.BaseService;
import hry.business.ct.model.CtContractTemplateSeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CtContractTemplateSealService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-12 17:38:48 
 */
public interface CtContractTemplateSealService extends BaseService<CtContractTemplateSeal, Long> {
    /**
     * 查询模板元素内容列表
     * @param templateId
     * @return
     */
    List<CtContractTemplateSeal> findSealByTemplateId(@Param("templateId") Long templateId);
}
