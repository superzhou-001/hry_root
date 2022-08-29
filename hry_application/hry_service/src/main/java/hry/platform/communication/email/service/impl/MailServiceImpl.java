/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:54:27 
 */
package hry.platform.communication.email.service.impl;

import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.communication.email.model.Mail;
import hry.platform.communication.email.service.MailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> MailService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:54:27 
 */
@Service("mailService")
public class MailServiceImpl extends BaseServiceImpl<Mail, Long> implements MailService {

	@Resource(name = "mailDao")
	@Override
	public void setDao (BaseDao<Mail, Long> dao) {
		super.dao = dao;
	}

}
