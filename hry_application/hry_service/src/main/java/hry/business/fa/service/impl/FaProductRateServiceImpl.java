/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:32:05 
 */
package hry.business.fa.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaProductRate;
import hry.business.fa.service.FaProductRateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FaProductRateService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:32:05 
 */
@Service("faProductRateService")
public class FaProductRateServiceImpl extends BaseServiceImpl<FaProductRate, Long> implements FaProductRateService {

	@Resource(name = "faProductRateDao")
	@Override
	public void setDao (BaseDao<FaProductRate, Long> dao) {
		super.dao = dao;
	}

}
