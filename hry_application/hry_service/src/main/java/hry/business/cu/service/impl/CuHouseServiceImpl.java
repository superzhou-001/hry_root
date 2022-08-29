/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:17:01 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuHouse;
import hry.business.cu.service.CuHouseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuHouseService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:17:01 
 */
@Service("cuHouseService")
public class CuHouseServiceImpl extends BaseServiceImpl<CuHouse, Long> implements CuHouseService {

	@Resource(name = "cuHouseDao")
	@Override
	public void setDao (BaseDao<CuHouse, Long> dao) {
		super.dao = dao;
	}

}
