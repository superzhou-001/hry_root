/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-22 13:50:08 
 */
package hry.helpLoan.apply.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.helpLoan.apply.model.LoanIntention;
import hry.helpLoan.apply.service.LoanIntentionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> LoanIntentionService </p>
 *
 * @author: huanggh
 * @Date: 2020-07-22 13:50:08 
 */
@Service("loanIntentionService")
public class LoanIntentionServiceImpl extends BaseServiceImpl<LoanIntention, Long> implements LoanIntentionService {

	@Resource(name = "loanIntentionDao")
	@Override
	public void setDao (BaseDao<LoanIntention, Long> dao) {
		super.dao = dao;
	}

}
