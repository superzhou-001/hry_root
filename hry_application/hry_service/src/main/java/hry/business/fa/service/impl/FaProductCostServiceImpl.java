/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:52
 */
package hry.business.fa.service.impl;

import hry.business.fa.model.FaCost;
import hry.business.fa.service.FaCostService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaProductCost;
import hry.business.fa.service.FaProductCostService;
import hry.core.util.QueryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> FaProductCostService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:52
 */
@Service("faProductCostService")
public class FaProductCostServiceImpl extends BaseServiceImpl<FaProductCost, Long> implements FaProductCostService {

	@Resource(name = "faProductCostDao")
	@Override
	public void setDao (BaseDao<FaProductCost, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	FaCostService faCostService;

	@Override
	public List<FaCost> findByProductId(Long productId) {
		List<FaProductCost> costList = find(new QueryFilter(FaProductCost.class).addFilter("productid=", productId));
		if(costList!=null&&costList.size()>0){
			ArrayList list = new ArrayList();
			for(FaProductCost faProductCost : costList){
				list.add(faCostService.get(faProductCost.getCostid()));
			}
			return list;
		}
		return null;
	}
}
