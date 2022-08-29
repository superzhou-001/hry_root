/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 16:09:20 
 */
package hry.business.cu.service.impl;

import hry.business.cu.dao.CuIntentionPersonDao;
import hry.business.cu.model.CuPerson;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuIntentionPerson;
import hry.business.cu.service.CuIntentionPersonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> CuIntentionPersonService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 16:09:20 
 */
@Service("cuIntentionPersonService")
public class CuIntentionPersonServiceImpl extends BaseServiceImpl<CuIntentionPerson, Long> implements CuIntentionPersonService {

	@Resource(name = "cuIntentionPersonDao")
	@Override
	public void setDao (BaseDao<CuIntentionPerson, Long> dao) {
		super.dao = dao;
	}

	@Override
	public CuPerson getIntentionPersonByIntentionId(Long cuIntentionId) {
		return ((CuIntentionPersonDao)dao).getIntentionPersonByIntentionId(cuIntentionId);
	}

	@Override
	public List<CuPerson> findIntentionPersonByIntentionId(Long cuIntentionId) {
		return ((CuIntentionPersonDao)dao).findIntentionPersonByIntentionId(cuIntentionId);
	}
}
