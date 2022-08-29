/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.scm.enterprise.service;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.model.ScmEnterprise;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ScmEnterpriseService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:35:56 
 */
public interface ScmEnterpriseService extends BaseService<ScmEnterprise, Long> {
    /**
     * 添加企业信息与用户基础信息
     * @param jsonStr
     * @return
     */
    JsonResult addEnterprise(HttpServletRequest request, String jsonStr, CuCustomer user);
    /**
     * 修改企业信息与用户基础信息
     * @param jsonStr
     * @return
     */
    JsonResult updateEnterprise(HttpServletRequest request, String jsonStr, CuCustomer user);

    /**
     * 分页查询企业列表
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);

    /**
     * 验证社会信用代码已经存在
     * @param creditCode
     * @return
     */
    ScmEnterprise checkCreditCode(String creditCode);

}
