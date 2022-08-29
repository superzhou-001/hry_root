/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-09 11:30:50 
 */
package hry.business.ct.service;

import hry.core.mvc.service.BaseService;
import hry.business.ct.model.CtContractTemplateElement;

import java.util.List;

/**
 * <p> CtContractTemplateElementService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-09 11:30:50 
 */
public interface CtContractTemplateElementService extends BaseService<CtContractTemplateElement, Long> {

    /**
     * 查询模板元素内容列表
     * @param TemplateId
     * @return
     */
    List<CtContractTemplateElement> findElementByTemplateId(Long templateId);

}
