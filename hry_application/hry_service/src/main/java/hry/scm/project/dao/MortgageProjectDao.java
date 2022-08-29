/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.scm.project.dao;

import hry.core.mvc.dao.BaseDao;
import hry.scm.project.model.MortgageProject;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p> MortgageProjectDao </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:03:52 
 */
@Mapper
public interface MortgageProjectDao extends BaseDao<MortgageProject, Long> {

}
