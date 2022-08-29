/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 10:04:39 
 */
package hry.business.cu.service.impl;

import hry.business.cu.dao.CuIntentionFollowCommentDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuIntentionFollowComment;
import hry.business.cu.service.CuIntentionFollowCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> CuIntentionFollowCommentService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 10:04:39 
 */
@Service("cuIntentionFollowCommentService")
public class CuIntentionFollowCommentServiceImpl extends BaseServiceImpl<CuIntentionFollowComment, Long> implements CuIntentionFollowCommentService {

	@Resource(name = "cuIntentionFollowCommentDao")
	@Override
	public void setDao (BaseDao<CuIntentionFollowComment, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<CuIntentionFollowComment> findAllCommentByFollowId(Long intentionfollowId) {
		return ((CuIntentionFollowCommentDao)dao).findAllCommentByFollowId(intentionfollowId);
	}

	@Override
	public List<CuIntentionFollowComment> findCommentByIntentionId(Long intentionId) {
		return ((CuIntentionFollowCommentDao)dao).findCommentByIntentionId(intentionId);
	}
}
