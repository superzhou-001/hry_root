/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:24:37 
 */
package hry.platform.config.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.config.model.AppHolidaysYear;
import hry.platform.config.service.AppHolidaysYearService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppHolidaysYearService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:24:37 
 */
@Service("appHolidaysYearService")
public class AppHolidaysYearServiceImpl extends BaseServiceImpl<AppHolidaysYear, Long> implements AppHolidaysYearService {

	@Resource(name = "appHolidaysYearDao")
	@Override
	public void setDao (BaseDao<AppHolidaysYear, Long> dao) {
		super.dao = dao;
	}

}
