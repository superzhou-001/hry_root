/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:57 
 */
package hry.platform.website.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.website.model.SysProtocolManage;
import hry.platform.website.service.SysProtocolManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> SysProtocolManageService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:57 
 */
@Service("sysProtocolManageService")
public class SysProtocolManageServiceImpl extends BaseServiceImpl<SysProtocolManage, Long> implements SysProtocolManageService {

	@Resource(name = "sysProtocolManageDao")
	@Override
	public void setDao (BaseDao<SysProtocolManage, Long> dao) {
		super.dao = dao;
	}

}
