/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-19 15:21:33 
 */
package hry.business.cu.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.dao.CuIntentionFollowDao;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.model.CuIntentionFollowComment;
import hry.business.cu.service.CuIntentionFollowCommentService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuIntentionFollow;
import hry.business.cu.service.CuIntentionFollowService;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CuIntentionFollowService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-19 15:21:33 
 */
@Service("cuIntentionFollowService")
public class CuIntentionFollowServiceImpl extends BaseServiceImpl<CuIntentionFollow, Long> implements CuIntentionFollowService {

	@Resource(name = "cuIntentionFollowDao")
	@Override
	public void setDao (BaseDao<CuIntentionFollow, Long> dao) {
		super.dao = dao;
	}
	@Autowired
	private CuIntentionFollowCommentService cuIntentionFollowCommentService;

	@Override
	public List<CuIntentionFollow> selectCuIntentionFollowAndCommentByFollowId(QueryFilter filter) {
		String intentionInfoId = filter.getRequest().getParameter("intentionInfoId");
		String intentionId = filter.getRequest().getParameter("intentionId");

		if (!StringUtils.isEmpty(intentionId)) {
			filter.addFilter("intentionId=",intentionId);
		}
		if (!StringUtils.isEmpty(intentionInfoId)) {
			filter.addFilter("intentionInfoId=",intentionInfoId);
		}
		List<CuIntentionFollow> cuIntentionFollows = super.find(filter);
		if(cuIntentionFollows!=null){
			List<CuIntentionFollowComment> comments = null;
			for (CuIntentionFollow cuIntentionFollow : cuIntentionFollows) {
				comments = cuIntentionFollowCommentService.findAllCommentByFollowId(cuIntentionFollow.getId());
				cuIntentionFollow.setList(comments);
			}
		}

		return cuIntentionFollows;
	}

	@Override
	public List<CuIntentionFollow> findFollowAndCommentByIntentionId(Long intentionId) {
		return ((CuIntentionFollowDao)dao).findFollowAndCommentByIntentionId(intentionId);
	}
}
