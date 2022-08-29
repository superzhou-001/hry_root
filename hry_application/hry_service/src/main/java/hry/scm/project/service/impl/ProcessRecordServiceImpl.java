/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-15 11:58:19 
 */
package hry.scm.project.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.scm.project.model.ProcessRecord;
import hry.scm.project.service.ProcessRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProcessRecordService </p>
 *
 * @author: luyue
 * @Date: 2020-07-15 11:58:19 
 */
@Service("processRecordService")
public class ProcessRecordServiceImpl extends BaseServiceImpl<ProcessRecord, Long> implements ProcessRecordService {

	@Resource(name = "processRecordDao")
	@Override
	public void setDao (BaseDao<ProcessRecord, Long> dao) {
		super.dao = dao;
	}

}
