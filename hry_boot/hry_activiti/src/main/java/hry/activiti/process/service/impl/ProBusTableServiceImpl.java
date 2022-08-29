/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 10:59:51 
 */
package hry.activiti.process.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.activiti.process.model.ProBusTable;
import hry.activiti.process.service.ProBusTableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProBusTableService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 10:59:51 
 */
@Service("proBusTableService")
public class ProBusTableServiceImpl extends BaseServiceImpl<ProBusTable, Long> implements ProBusTableService {

	@Resource(name = "proBusTableDao")
	@Override
	public void setDao (BaseDao<ProBusTable, Long> dao) {
		super.dao = dao;
	}

}
