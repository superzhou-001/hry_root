/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:33:28 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuBank;
import hry.business.cu.service.CuBankService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuBankService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:33:28 
 */
@Service("cuBankService")
public class CuBankServiceImpl extends BaseServiceImpl<CuBank, Long> implements CuBankService {

	@Resource(name = "cuBankDao")
	@Override
	public void setDao (BaseDao<CuBank, Long> dao) {
		super.dao = dao;
	}

}
