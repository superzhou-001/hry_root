/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.scm.enterprise.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.enterprise.model.ScmEnterprise;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> ScmEnterpriseDao </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:35:56 
 */
@Mapper
public interface ScmEnterpriseDao extends BaseDao<ScmEnterprise, Long> {
    /**
     * 分页查询企业列表
     * @param map
     * @return
     */
    List<ScmEnterprise> findPageBySql(Map<String,Object> map);

}
