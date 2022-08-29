/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-05-09 14:12:32 
 */
package hry.platform.config.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.config.model.AppBanner;
import hry.platform.config.service.AppBannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppBannerService </p>
 *
 * @author: zhouming
 * @Date: 2020-05-09 14:12:32 
 */
@Service("appBannerService")
public class AppBannerServiceImpl extends BaseServiceImpl<AppBanner, Long> implements AppBannerService {

	@Resource(name = "appBannerDao")
	@Override
	public void setDao (BaseDao<AppBanner, Long> dao) {
		super.dao = dao;
	}

}
