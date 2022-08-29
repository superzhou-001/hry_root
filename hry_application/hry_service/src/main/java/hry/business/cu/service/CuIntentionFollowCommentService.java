/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 10:04:39 
 */
package hry.business.cu.service;

import hry.core.mvc.service.BaseService;
import hry.business.cu.model.CuIntentionFollowComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CuIntentionFollowCommentService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 10:04:39 
 */
public interface CuIntentionFollowCommentService extends BaseService<CuIntentionFollowComment, Long> {
    /**
     * 递归查询评论信息
     * @param intentionfollowId
     * @return
     */
    List<CuIntentionFollowComment> findAllCommentByFollowId(@Param("intentionfollowId") Long intentionfollowId);

    /**
     * 查询评论信息
     * @param intentionId
     * @return
     */
    List<CuIntentionFollowComment> findCommentByIntentionId(@Param("intentionId") Long intentionId);

}
