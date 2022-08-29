/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:13:14 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.AppStationType;
import hry.platform.website.service.AppStationTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppStationTypeService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:13:14 
 */
@Service("appStationTypeService")
public class AppStationTypeServiceImpl extends BaseServiceImpl<AppStationType, Long> implements AppStationTypeService {

	@Resource(name = "appStationTypeDao")
	@Override
	public void setDao (BaseDao<AppStationType, Long> dao) {
		super.dao = dao;
	}

}
