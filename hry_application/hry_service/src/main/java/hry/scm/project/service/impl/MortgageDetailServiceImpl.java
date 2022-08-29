/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:02:46 
 */
package hry.scm.project.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.scm.project.model.MortgageDetail;
import hry.scm.project.service.MortgageDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> MortgageDetailService </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:02:46 
 */
@Service("mortgageDetailService")
public class MortgageDetailServiceImpl extends BaseServiceImpl<MortgageDetail, Long> implements MortgageDetailService {

	@Resource(name = "mortgageDetailDao")
	@Override
	public void setDao (BaseDao<MortgageDetail, Long> dao) {
		super.dao = dao;
	}

}
