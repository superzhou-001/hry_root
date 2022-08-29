/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 14:31:34
 */
package hry.business.cu.service;

import hry.bean.PageResult;
import hry.business.cu.model.CuEnterprisePerson;
import hry.business.cu.model.CuPerson;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;

import java.util.List;

/**
 * <p> CuEnterprisePersonService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 14:31:34
 */
public interface CuEnterprisePersonService extends BaseService<CuEnterprisePerson, Long> {

    /**
     * 根据条件查询企业关联人信息
     * @param filter
     * @return
     */
    PageResult findPageBySql(QueryFilter filter);
    /**
     * 根据条件查询企业关联人信息
     * @param filter
     * @return
     */
    List<CuPerson> personList(QueryFilter filter);

    /**
     * 查询企业相关类型人员信息
     * @return
     */
    CuPerson getCuEnterprisePersonByEnterpriseIdAndType(Long enterpriseId, Integer type);

    /**
     * 查询企业相关类型人员信息列表
     * @return
     */
    PageResult findCuEnterprisePersonByEnterpriseIdAndType(QueryFilter filter);

    /**
     * 更新主要联系人为否
     * @param enterpriseId
     */
    void updatwIsMainPerson(Long enterpriseId);

    /**
     * 根据条件查询企业关联人信息
     * @param enterpriseId
     * @param type
     * @return
     */
    List<CuPerson> findPersonList(Long enterpriseId, Integer type);
}
