/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:59:19 
 */
package hry.platform.communication.email.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.communication.email.model.MailTemplate;
import hry.platform.communication.email.service.MailTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> MailTemplateService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:59:19 
 */
@Service("mailTemplateService")
public class MailTemplateServiceImpl extends BaseServiceImpl<MailTemplate, Long> implements MailTemplateService {

	@Resource(name = "mailTemplateDao")
	@Override
	public void setDao (BaseDao<MailTemplate, Long> dao) {
		super.dao = dao;
	}

}
