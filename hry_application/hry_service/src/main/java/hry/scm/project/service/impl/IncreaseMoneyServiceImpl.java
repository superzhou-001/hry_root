/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-28 16:37:12 
 */
package hry.scm.project.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.scm.project.model.IncreaseMoney;
import hry.scm.project.service.IncreaseMoneyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> IncreaseMoneyService </p>
 *
 * @author: luyue
 * @Date: 2020-07-28 16:37:12 
 */
@Service("increaseMoneyService")
public class IncreaseMoneyServiceImpl extends BaseServiceImpl<IncreaseMoney, Long> implements IncreaseMoneyService {

	@Resource(name = "increaseMoneyDao")
	@Override
	public void setDao (BaseDao<IncreaseMoney, Long> dao) {
		super.dao = dao;
	}

}
