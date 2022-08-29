/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:22:58 
 */
package hry.business.ct.service.impl;

import hry.business.ct.dao.CtTableDao;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.business.ct.model.CtTable;
import hry.business.ct.service.CtTableService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p> CtTableService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:22:58 
 */
@Service("ctTableService")
public class CtTableServiceImpl extends BaseServiceImpl<CtTable, Long> implements CtTableService {

	@Resource(name = "ctTableDao")
	@Override
	public void setDao (BaseDao<CtTable, Long> dao) {
		super.dao = dao;
	}

	@Override
	public boolean listTableColumn(String tableName) {
		String table = ((CtTableDao) dao).listTableColumn(tableName);
		return StringUtils.isEmpty(table);
	}
}
