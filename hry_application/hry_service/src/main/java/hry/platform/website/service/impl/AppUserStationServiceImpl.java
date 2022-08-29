/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-13 14:57:20 
 */
package hry.platform.website.service.impl;

import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.dao.AppUserStationDao;
import hry.platform.website.model.AppUserStation;
import hry.platform.website.service.AppUserStationService;
import hry.security.jwt.JWTContext;
import hry.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> AppUserStationService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-13 14:57:20 
 */
@Service("appUserStationService")
public class AppUserStationServiceImpl extends BaseServiceImpl<AppUserStation, Long> implements AppUserStationService {

	@Resource(name = "appUserStationDao")
	@Override
	public void setDao (BaseDao<AppUserStation, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult saveUserStation(Long stationId, String receivers, Integer userType) {
		String[] userIdList = receivers.split(",");
		Arrays.stream(userIdList).forEach(userId -> {
			AppUserStation userStation = new AppUserStation();
			userStation.setStationId(stationId);
			userStation.setUserId(Long.parseLong(userId));
			userStation.setUserType(userType);
			super.save(userStation);
		});
		return new JsonResult(true);
	}

	@Override
	public PageResult findUserStationPage(QueryFilter filter) {
		Page page = PageFactory.getPage(filter);
		String contentTitle = filter.getRequest().getParameter("contentTitle");
		String status = filter.getRequest().getParameter("status");
		String startTime = filter.getRequest().getParameter("startTime");
		String endTime = filter.getRequest().getParameter("endTime");
		Map<String, Object> param = new HashMap<>();
		// 获取当前用户信息
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		param.put("userId", user.getId());
		param.put("userType", 0); // 后台用户信件
		if (StringUtil.isNull(contentTitle)) {
			param.put("contentTitle", contentTitle + "%");
		}
		if (StringUtil.isNull(status)) {
			param.put("status", status);
		}
		if (StringUtil.isNull(startTime)) {
			param.put("startTime", startTime);
		}
		if (StringUtil.isNull(endTime)) {
			param.put("endTime", endTime);
		}
		((AppUserStationDao)dao).findUserStationList(param);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
