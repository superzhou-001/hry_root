/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:42:39 
 */
package hry.activiti.process.dao;

import hry.core.mvc.dao.BaseDao;
import hry.activiti.process.model.ProPersonGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> ProPersonGroupDao </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:42:39 
 */
@Mapper
public interface ProPersonGroupDao extends BaseDao<ProPersonGroup, Long> {

}
