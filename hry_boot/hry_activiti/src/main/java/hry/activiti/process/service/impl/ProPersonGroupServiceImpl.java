/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:42:39 
 */
package hry.activiti.process.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.activiti.process.model.ProPersonGroup;
import hry.activiti.process.service.ProPersonGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> ProPersonGroupService </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:42:39 
 */
@Service("proPersonGroupService")
public class ProPersonGroupServiceImpl extends BaseServiceImpl<ProPersonGroup, Long> implements ProPersonGroupService {

	@Resource(name = "proPersonGroupDao")
	@Override
	public void setDao (BaseDao<ProPersonGroup, Long> dao) {
		super.dao = dao;
	}

}
