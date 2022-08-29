/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:29:14 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuPersonService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:29:14 
 */
@Service("cuPersonService")
public class CuPersonServiceImpl extends BaseServiceImpl<CuPerson, Long> implements CuPersonService {

	@Resource(name = "cuPersonDao")
	@Override
	public void setDao (BaseDao<CuPerson, Long> dao) {
		super.dao = dao;
	}

}
