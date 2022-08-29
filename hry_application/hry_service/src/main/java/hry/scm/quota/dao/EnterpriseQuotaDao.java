/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:45:12 
 */
package hry.scm.quota.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.quota.model.EnterpriseQuota;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p> EnterpriseQuotaDao </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:45:12 
 */
@Mapper
public interface EnterpriseQuotaDao extends BaseDao<EnterpriseQuota, Long> {

    EnterpriseQuota getEnterpriseSumQuota(@Param("enterpriseId")Long enterpriseId);

    List<EnterpriseQuota> getEnterpriseQuotaList(Map<String, Object> map);

}
