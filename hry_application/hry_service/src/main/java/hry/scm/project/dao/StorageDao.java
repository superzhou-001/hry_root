/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-10 11:34:53 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.Storage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p> StorageDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-10 11:34:53 
 */
@Mapper
public interface StorageDao extends BaseDao<Storage, Long> {

    boolean removeStorage(String isMortgage);

    /**
     * 质押物清单查询质押物详细信息
     * @param map
     * @return
     */
    public List<Storage> findStorage(Map<String,String> map);

}
