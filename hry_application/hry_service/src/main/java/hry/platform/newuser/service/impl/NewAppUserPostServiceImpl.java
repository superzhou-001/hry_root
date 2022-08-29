/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:13:47 
 */
package hry.platform.newuser.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuPerson;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppUserPostDao;
import hry.platform.newuser.model.NewAppUserPost;
import hry.platform.newuser.service.NewAppUserPostService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> NewAppUserPostService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:13:47 
 */
@Service("newAppUserPostService")
public class NewAppUserPostServiceImpl extends BaseServiceImpl<NewAppUserPost, Long> implements NewAppUserPostService {

	@Resource(name = "newAppUserPostDao")
	@Override
	public void setDao (BaseDao<NewAppUserPost, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult addPostUser(long postId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppUserPost.class);
			filter.addFilter("postid=", postId);
			filter.addFilter("userid=", userId);
			if (super.get(filter) == null) {
				NewAppUserPost userPost = new NewAppUserPost();
				userPost.setPostid(postId);
				userPost.setUserid(Long.parseLong(userId));
				super.save(userPost);
			}
		});
		return new JsonResult(true);
	}

	@Override
	public JsonResult removePostUser(long postId, String userIds) {
		String[] userIdList = userIds.split(",");
		Arrays.stream(userIdList).forEach((userId) -> {
			QueryFilter filter = new QueryFilter(NewAppUserPost.class);
			filter.addFilter("postid=", postId);
			filter.addFilter("userid=", userId);
			super.delete(filter);
		});
		return new JsonResult(true);
	}

	@Override
	public PageResult findPostUserPageList(QueryFilter filter) {
		Page<NewAppUserPost> page = PageFactory.getPage(filter);
		String postid = filter.getRequest().getParameter("postId");
		String name = filter.getRequest().getParameter("name");
		String startTime = filter.getRequest().getParameter("startTime");
		String endTime = filter.getRequest().getParameter("endTime");
		Map<String, Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(name)){
			map.put("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(endTime)){
			map.put("endTime",endTime);
		}
		if(!StringUtils.isEmpty(startTime)){
			map.put("startTime",startTime);
		}
		map.put("postid", postid);
		((NewAppUserPostDao)dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult findUserPost(String userid) {
		List<NewAppUserPost> userPosts = ((NewAppUserPostDao)dao).findUserPostList(userid);
		return new JsonResult(true).setObj(userPosts);
	}
}
