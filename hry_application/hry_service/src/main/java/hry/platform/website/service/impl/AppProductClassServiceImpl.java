/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:38:17 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.AppProductClass;
import hry.platform.website.service.AppProductClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppProductClassService </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:38:17 
 */
@Service("appProductClassService")
public class AppProductClassServiceImpl extends BaseServiceImpl<AppProductClass, Long> implements AppProductClassService {

	@Resource(name = "appProductClassDao")
	@Override
	public void setDao (BaseDao<AppProductClass, Long> dao) {
		super.dao = dao;
	}

}
