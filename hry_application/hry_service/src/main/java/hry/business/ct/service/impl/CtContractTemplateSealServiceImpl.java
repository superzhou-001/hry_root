/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-12 17:38:48 
 */
package hry.business.ct.service.impl;

import hry.business.ct.dao.CtContractTemplateSealDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractTemplateSeal;
import hry.business.ct.service.CtContractTemplateSealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> CtContractTemplateSealService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-12 17:38:48 
 */
@Service("ctContractTemplateSealService")
public class CtContractTemplateSealServiceImpl extends BaseServiceImpl<CtContractTemplateSeal, Long> implements CtContractTemplateSealService {

	@Resource(name = "ctContractTemplateSealDao")
	@Override
	public void setDao (BaseDao<CtContractTemplateSeal, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<CtContractTemplateSeal> findSealByTemplateId(Long templateId) {
		return ((CtContractTemplateSealDao)dao).findSealByTemplateId(templateId);
	}
}
