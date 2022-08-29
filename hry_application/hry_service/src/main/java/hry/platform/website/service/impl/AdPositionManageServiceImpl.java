/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:09:41 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.AdPositionManage;
import hry.platform.website.service.AdPositionManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AdPositionManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:09:41 
 */
@Service("adPositionManageService")
public class AdPositionManageServiceImpl extends BaseServiceImpl<AdPositionManage, Long> implements AdPositionManageService {

	@Resource(name = "adPositionManageDao")
	@Override
	public void setDao (BaseDao<AdPositionManage, Long> dao) {
		super.dao = dao;
	}

}
