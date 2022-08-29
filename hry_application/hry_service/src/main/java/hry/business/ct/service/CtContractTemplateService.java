/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:03 
 */
package hry.business.ct.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.ct.model.CtContractTemplate;
import hry.core.util.QueryFilter;
import org.apache.ibatis.annotations.Param;

/**
 * <p> CtContractTemplateService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:03 
 */
public interface CtContractTemplateService extends BaseService<CtContractTemplate, Long> {

    /**
     * 查询合同模板信息
     * @param id
     * @return
     */
    CtContractTemplate getContractTemplateById(Long id);

    /**
     * 根据条件查询企业信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 修改模板PDF 与模板中的要素
     * @param ctContractTemplate
     */
    JsonResult updateTemplateAndElement(CtContractTemplate ctContractTemplate);

}
