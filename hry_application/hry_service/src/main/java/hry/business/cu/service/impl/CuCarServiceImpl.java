/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:45:28 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuCar;
import hry.business.cu.service.CuCarService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuCarService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:45:28 
 */
@Service("cuCarService")
public class CuCarServiceImpl extends BaseServiceImpl<CuCar, Long> implements CuCarService {

	@Resource(name = "cuCarDao")
	@Override
	public void setDao (BaseDao<CuCar, Long> dao) {
		super.dao = dao;
	}

}
