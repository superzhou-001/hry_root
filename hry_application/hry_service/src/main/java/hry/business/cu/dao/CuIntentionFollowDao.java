/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-19 15:21:33 
 */
package hry.business.cu.dao;

import hry.core.mvc.dao.BaseDao;
import hry.business.cu.model.CuIntentionFollow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CuIntentionFollowDao </p>
 *
 * @author: yaoz
 * @Date: 2020-05-19 15:21:33 
 */
@Mapper
public interface CuIntentionFollowDao extends BaseDao<CuIntentionFollow, Long> {

    /**
     * 查询跟进信息 附带评论
     * @param intentionId
     * @return
     */
    List<CuIntentionFollow> findFollowAndCommentByIntentionId(@Param("intentionId") Long intentionId);

}
