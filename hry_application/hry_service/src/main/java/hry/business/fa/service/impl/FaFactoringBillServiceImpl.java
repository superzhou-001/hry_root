/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-11 14:14:15 
 */
package hry.business.fa.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaFactoringBill;
import hry.business.fa.service.FaFactoringBillService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FaFactoringBillService </p>
 *
 * @author: yaoz
 * @Date: 2020-08-11 14:14:15 
 */
@Service("faFactoringBillService")
public class FaFactoringBillServiceImpl extends BaseServiceImpl<FaFactoringBill, Long> implements FaFactoringBillService {

	@Resource(name = "faFactoringBillDao")
	@Override
	public void setDao (BaseDao<FaFactoringBill, Long> dao) {
		super.dao = dao;
	}

}
