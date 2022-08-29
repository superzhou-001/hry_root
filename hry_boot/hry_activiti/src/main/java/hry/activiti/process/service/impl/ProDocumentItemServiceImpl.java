/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-21 11:19:36 
 */
package hry.activiti.process.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.activiti.process.model.ProDocumentItem;
import hry.activiti.process.service.ProDocumentItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProDocumentItemService </p>
 *
 * @author: liushilei
 * @Date: 2020-07-21 11:19:36 
 */
@Service("proDocumentItemService")
public class ProDocumentItemServiceImpl extends BaseServiceImpl<ProDocumentItem, Long> implements ProDocumentItemService {

	@Resource(name = "proDocumentItemDao")
	@Override
	public void setDao (BaseDao<ProDocumentItem, Long> dao) {
		super.dao = dao;
	}

}
