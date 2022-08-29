/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 11:00:17 
 */
package hry.activiti.process.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.activiti.process.model.ProBusState;
import hry.activiti.process.service.ProBusStateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProBusStateService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 11:00:17 
 */
@Service("proBusStateService")
public class ProBusStateServiceImpl extends BaseServiceImpl<ProBusState, Long> implements ProBusStateService {

	@Resource(name = "proBusStateDao")
	@Override
	public void setDao (BaseDao<ProBusState, Long> dao) {
		super.dao = dao;
	}

}
