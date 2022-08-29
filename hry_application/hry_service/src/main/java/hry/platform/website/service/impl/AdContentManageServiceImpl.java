/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:10:31 
 */
package hry.platform.website.service.impl;

import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cu.dao.CuEnterpriseDao;
import hry.business.cu.model.CuEnterprise;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.dao.AdContentManageDao;
import hry.platform.website.model.AdContentManage;
import hry.platform.website.service.AdContentManageService;
import hry.security.jwt.JWTContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> AdContentManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:10:31 
 */
@Service("adContentManageService")
public class AdContentManageServiceImpl extends BaseServiceImpl<AdContentManage, Long> implements AdContentManageService {

	@Resource(name = "adContentManageDao")
	@Override
	public void setDao (BaseDao<AdContentManage, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findPageBySql(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<AdContentManage> page = hry.core.util.PageFactory.getPage(filter);
		String adContentTitle = filter.getRequest().getParameter("adContentTitle");
		String adPositionName = filter.getRequest().getParameter("adPositionName");
		String adPositionKey = filter.getRequest().getParameter("adPositionKey");

		Map<String, Object> map = new HashMap<String, Object>();

		if (!StringUtils.isEmpty(adContentTitle)) {
			map.put("adContentTitle", "%" + adContentTitle + "%");
		}
		if (!StringUtils.isEmpty(adPositionName)) {
			map.put("adPositionName", "%" + adPositionName + "%");
		}
		if (!StringUtils.isEmpty(adPositionKey)) {
			map.put("adPositionKey", "%" + adPositionKey + "%");
		}

		((AdContentManageDao) dao).findPageBySql(map);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}
}
