/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:27:23
 */
package hry.platform.communication.sms.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.communication.sms.model.AppSmsSend;
import hry.platform.communication.sms.service.AppSmsSendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppSmsSendService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:27:23
 */
@Service("appSmsSendService")
public class AppSmsSendServiceImpl extends BaseServiceImpl<AppSmsSend, Long> implements AppSmsSendService {

	@Resource(name = "appSmsSendDao")
	@Override
	public void setDao (BaseDao<AppSmsSend, Long> dao) {
		super.dao = dao;
	}

}
