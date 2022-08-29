/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 16:04:23 
 */
package hry.business.cu.service.impl;

import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuIntentionUser;
import hry.business.cu.service.CuIntentionUserService;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppRole;
import hry.platform.newuser.model.NewAppRoleMenu;
import hry.platform.newuser.model.NewAppUser;
import hry.security.jwt.JWTContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * <p> CuIntentionUserService </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 16:04:23 
 */
@Service("cuIntentionUserService")
public class CuIntentionUserServiceImpl extends BaseServiceImpl<CuIntentionUser, Long> implements CuIntentionUserService {

	@Resource(name = "cuIntentionUserDao")
	@Override
	public void setDao (BaseDao<CuIntentionUser, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult add(Long intentionId, String userIds) {
		JsonResult jsonResult = new JsonResult();
		//判空
		if(StringUtils.isEmpty(userIds)){
			jsonResult.setSuccess(false);
			jsonResult.setMsg("用户不能为空");
			return jsonResult.setMsg("失败");
		}else{
			String[] ids = org.apache.commons.lang3.StringUtils.split(userIds, ",");
			saveIntentionUser(intentionId, ids);

			jsonResult.setSuccess(true);
			return jsonResult.setMsg("成功");
		}
	}

	private void saveIntentionUser (Long intentionId, String[] ids) {
		ArrayList<CuIntentionUser> list = new ArrayList<>();
		NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
		if(user==null){
			return;
		}
		for (String id : ids) {
			Long userId = Long.valueOf(id);
			//查询是否存在管理员是否已经拥有权限
			QueryFilter queryFilter = new QueryFilter(CuIntentionUser.class);
			queryFilter.addFilter("intentionId=",intentionId);
			queryFilter.addFilter("userId=",userId);
			CuIntentionUser cuIntentionUser = this.get(queryFilter);
			if(cuIntentionUser==null){
				cuIntentionUser = new CuIntentionUser();
				cuIntentionUser.setIntentionId(intentionId);
				cuIntentionUser.setUserId(userId);
				cuIntentionUser.setDistributionUserId(user.getId());
				list.add(cuIntentionUser);
			}
		}
		this.saveAll(list);
	}
}
