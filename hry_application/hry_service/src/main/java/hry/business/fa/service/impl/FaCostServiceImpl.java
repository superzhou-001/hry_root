/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:23 
 */
package hry.business.fa.service.impl;

import hry.business.fa.dao.FaCostDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaCost;
import hry.business.fa.service.FaCostService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> FaCostService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:23 
 */
@Service("faCostService")
public class FaCostServiceImpl extends BaseServiceImpl<FaCost, Long> implements FaCostService {

	@Resource(name = "faCostDao")
	@Override
	public void setDao (BaseDao<FaCost, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<FaCost> findFaCostList(Long productId) {
		return ((FaCostDao)dao).findFaCostList(productId);
	}
}
