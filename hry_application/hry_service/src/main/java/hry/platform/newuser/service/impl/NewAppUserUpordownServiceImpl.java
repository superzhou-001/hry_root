/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:16:11 
 */
package hry.platform.newuser.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.dao.NewAppUserUpordownDao;
import hry.platform.newuser.model.NewAppUserUpordown;
import hry.platform.newuser.service.NewAppUserUpordownService;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> NewAppUserUpordownService </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:16:11 
 */
@Service("newAppUserUpordownService")
public class NewAppUserUpordownServiceImpl extends BaseServiceImpl<NewAppUserUpordown, Long> implements NewAppUserUpordownService {

	@Resource(name = "newAppUserUpordownDao")
	@Override
	public void setDao (BaseDao<NewAppUserUpordown, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult addUserUpOrDown(String userid, String upOtheruserids, String downOtheruserids) {
		// 根据userid和type 删除当前用户对应上级或者下级
		QueryFilter delfilter = new QueryFilter(NewAppUserUpordown.class);
		delfilter.addFilter("userid=", userid);
		super.delete(delfilter);
		// 添加最新的上级
		if (StringUtil.isNull(upOtheruserids)) {
			String[] upOtheruseridlist = upOtheruserids.split(",");
			Arrays.asList(upOtheruseridlist).forEach((otheruserid) -> {
				NewAppUserUpordown userUpordown = new NewAppUserUpordown();
				userUpordown.setUserid(Long.parseLong(userid));
				userUpordown.setOtheruserid(Long.parseLong(otheruserid));
				userUpordown.setType(0);
				super.save(userUpordown);
			});
		}
		// 添加最新的下架
		if (StringUtil.isNull(downOtheruserids)) {
			String[] downOtheruseridlist = downOtheruserids.split(",");
			Arrays.asList(downOtheruseridlist).forEach((otheruserid) -> {
				NewAppUserUpordown userUpordown = new NewAppUserUpordown();
				userUpordown.setUserid(Long.parseLong(userid));
				userUpordown.setOtheruserid(Long.parseLong(otheruserid));
				userUpordown.setType(1);
				super.save(userUpordown);
			});
		}
		return new JsonResult(true);
	}

	@Override
	public JsonResult findUserUpOrDownList(Map<String, Object> paramMap) {
		List<NewAppUserUpordown> upordownList = ((NewAppUserUpordownDao)dao).findUserUpOrDownList(paramMap);
		return new JsonResult(true).setObj(upordownList);
	}

	@Override
	public PageResult findPageUserUpOrDownList(QueryFilter filter) {
		Page<NewAppUserUpordown> page = PageFactory.getPage(filter);
		String name = filter.getRequest().getParameter("name");
		String type = filter.getRequest().getParameter("type");
		Map<String, Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(name)){
			map.put("name", "%"+name+"%");
		}
		if(!StringUtils.isEmpty(type)){
			map.put("type", type);
		}
		((NewAppUserUpordownDao)dao).findUserUpOrDownList(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
