/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:29:01
 */
package hry.platform.communication.sms.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.communication.sms.model.AppSmsTemplate;
import hry.platform.communication.sms.service.AppSmsTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> AppSmsTemplateService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:29:01
 */
@Service("appSmsTemplateService")
public class AppSmsTemplateServiceImpl extends BaseServiceImpl<AppSmsTemplate, Long> implements AppSmsTemplateService {

	@Resource(name = "appSmsTemplateDao")
	@Override
	public void setDao (BaseDao<AppSmsTemplate, Long> dao) {
		super.dao = dao;
	}

	@Override
	public AppSmsTemplate getSmsTemplate (String smsType, String smsLang) {
		QueryFilter temp_qf = new QueryFilter(AppSmsTemplate.class);
		temp_qf.addFilter("tempType=", smsType);
		temp_qf.addFilter("tempLang=", smsLang);
		temp_qf.addFilter("tempState=", 1);
		return super.get(temp_qf);
	}
}
