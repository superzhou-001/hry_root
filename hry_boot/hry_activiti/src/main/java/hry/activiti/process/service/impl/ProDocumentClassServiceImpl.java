/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:44 
 */
package hry.activiti.process.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.activiti.process.model.ProDocumentClass;
import hry.activiti.process.service.ProDocumentClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProDocumentClassService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:44 
 */
@Service("proDocumentClassService")
public class ProDocumentClassServiceImpl extends BaseServiceImpl<ProDocumentClass, Long> implements ProDocumentClassService {

	@Resource(name = "proDocumentClassDao")
	@Override
	public void setDao (BaseDao<ProDocumentClass, Long> dao) {
		super.dao = dao;
	}

}
