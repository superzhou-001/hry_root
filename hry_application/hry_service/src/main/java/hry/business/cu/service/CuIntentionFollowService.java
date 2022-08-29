/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-19 15:21:33 
 */
package hry.business.cu.service;

import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.business.cu.model.CuIntentionFollow;
import hry.core.util.QueryFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p> CuIntentionFollowService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-19 15:21:33 
 */
public interface CuIntentionFollowService extends BaseService<CuIntentionFollow, Long> {

    /**
     * 返回结果带评论信息查询(递归查询评论)
     * @param filter
     * @return
     */
    List<CuIntentionFollow> selectCuIntentionFollowAndCommentByFollowId(QueryFilter filter);

    /**
     * 查询跟进信息 附带评论(关联父节点查询)
     * @param intentionId
     * @return
     */
    List<CuIntentionFollow> findFollowAndCommentByIntentionId(Long intentionId);

}
