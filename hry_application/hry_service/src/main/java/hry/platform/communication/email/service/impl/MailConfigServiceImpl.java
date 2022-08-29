/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:56:45
 */
package hry.platform.communication.email.service.impl;

import com.alibaba.fastjson.JSON;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.communication.email.model.MailConfig;
import hry.platform.communication.email.service.MailConfigService;
import hry.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> MailConfigService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:56:45
 */
@Service("mailConfigService")
public class MailConfigServiceImpl extends BaseServiceImpl<MailConfig, Long> implements MailConfigService {

	@Resource(name = "mailConfigDao")
	@Override
	public void setDao (BaseDao<MailConfig, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	private RedisService redisService;

	@Override
	public void initCache () {
		redisService.delete("email_config");
		QueryFilter config_email = new QueryFilter(MailConfig.class);
		config_email.addFilter("status=", "1");
		MailConfig mailConfig = get(config_email);
		if (mailConfig != null) {
			redisService.save("email_config", JSON.toJSONString(mailConfig));
		}
	}
}
