/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:18:39 
 */
package hry.business.cu.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> CuEnterpriseService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:18:39 
 */
public interface CuEnterpriseService extends BaseService<CuEnterprise, Long> {

    /**
     * 根据条件查询企业信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 导出execl-企业信息
     * @param request
     * @param response
     */
    void exportExcelEnterprise(HttpServletRequest request, HttpServletResponse response);

    /**
     * 企业建档
     * @return
     */
    JsonResult createEnterprise(CuEnterprise cuEnterprise);

    /**
     * 企业信息更新
     * @return
     */
    JsonResult updateEnterprise(CuEnterprise cuEnterprise);

}