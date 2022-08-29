/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-09 11:30:50 
 */
package hry.business.ct.service.impl;

import hry.business.ct.dao.CtContractTemplateElementDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtContractTemplateElement;
import hry.business.ct.service.CtContractTemplateElementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> CtContractTemplateElementService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-09 11:30:50 
 */
@Service("ctContractTemplateElementService")
public class CtContractTemplateElementServiceImpl extends BaseServiceImpl<CtContractTemplateElement, Long> implements CtContractTemplateElementService {

	@Resource(name = "ctContractTemplateElementDao")
	@Override
	public void setDao (BaseDao<CtContractTemplateElement, Long> dao) {
		super.dao = dao;
	}

	@Override
	public List<CtContractTemplateElement> findElementByTemplateId(Long templateId) {
		return ((CtContractTemplateElementDao)dao).findElementByTemplateId(templateId);
	}
}
