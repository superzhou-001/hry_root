/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:32:45 
 */
package hry.business.fa.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.fa.model.FaCostNlms;
import hry.business.fa.service.FaCostNlmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> FaCostNlmsService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:32:45 
 */
@Service("faCostNlmsService")
public class FaCostNlmsServiceImpl extends BaseServiceImpl<FaCostNlms, Long> implements FaCostNlmsService {

	@Resource(name = "faCostNlmsDao")
	@Override
	public void setDao (BaseDao<FaCostNlms, Long> dao) {
		super.dao = dao;
	}

}
