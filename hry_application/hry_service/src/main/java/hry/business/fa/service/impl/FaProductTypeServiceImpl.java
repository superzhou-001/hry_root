/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:27:30 
 */
package hry.business.fa.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaProductType;
import hry.business.fa.service.FaProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FaProductTypeService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:27:30 
 */
@Service("faProductTypeService")
public class FaProductTypeServiceImpl extends BaseServiceImpl<FaProductType, Long> implements FaProductTypeService {

	@Resource(name = "faProductTypeDao")
	@Override
	public void setDao (BaseDao<FaProductType, Long> dao) {
		super.dao = dao;
	}

}
