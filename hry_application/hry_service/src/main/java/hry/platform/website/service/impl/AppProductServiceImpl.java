/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:37:13 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.AppProduct;
import hry.platform.website.service.AppProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppProductService </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:37:13 
 */
@Service("appProductService")
public class AppProductServiceImpl extends BaseServiceImpl<AppProduct, Long> implements AppProductService {

	@Resource(name = "appProductDao")
	@Override
	public void setDao (BaseDao<AppProduct, Long> dao) {
		super.dao = dao;
	}

}
