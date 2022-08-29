/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:10:31
 */
package hry.platform.website.dao;

import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.platform.website.model.AdContentManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> AdContentManageDao </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:10:31 
 */
@Mapper
public interface AdContentManageDao extends BaseDao<AdContentManage, Long> {

    /**
     * 新sql分页查询
     * @param map
     * @return
     */
    List<AdContentManage> findPageBySql(Map<String, Object> map);

}
