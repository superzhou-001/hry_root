/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:37:17 
 */
package hry.business.cu.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.cu.model.CuIntentionInfo;
import hry.business.cu.service.CuIntentionInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> CuIntentionInfoService </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:37:17 
 */
@Service("cuIntentionInfoService")
public class CuIntentionInfoServiceImpl extends BaseServiceImpl<CuIntentionInfo, Long> implements CuIntentionInfoService {

	@Resource(name = "cuIntentionInfoDao")
	@Override
	public void setDao (BaseDao<CuIntentionInfo, Long> dao) {
		super.dao = dao;
	}

}
