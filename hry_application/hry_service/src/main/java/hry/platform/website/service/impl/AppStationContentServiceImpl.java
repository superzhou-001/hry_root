/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:14:23 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.AppStationContent;
import hry.platform.website.service.AppStationContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppStationContentService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:14:23 
 */
@Service("appStationContentService")
public class AppStationContentServiceImpl extends BaseServiceImpl<AppStationContent, Long> implements AppStationContentService {

	@Resource(name = "appStationContentDao")
	@Override
	public void setDao (BaseDao<AppStationContent, Long> dao) {
		super.dao = dao;
	}

}
