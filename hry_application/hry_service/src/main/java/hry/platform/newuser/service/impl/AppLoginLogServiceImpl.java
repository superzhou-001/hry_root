/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-14 14:18:11
 */
package hry.platform.newuser.service.impl;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.model.RequestLogRecord;
import hry.platform.elastic.service.ElasticSearchService;
import hry.platform.newuser.model.AppLoginLog;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.AppLoginLogService;
import hry.platform.utils.ConfigEnum;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.JWTUtil;
import hry.util.SpringUtil;
import hry.util.StringUtil;
import hry.util.httpRequest.IpUtil;
import hry.util.rmq.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zxp.esclientrhl.repository.PageList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p> AppLoginLogService </p>
 *
 * @author: zhouming
 * @Date: 2020-08-14 14:18:11
 */
@Service("appLoginLogService")
public class AppLoginLogServiceImpl extends BaseServiceImpl<AppLoginLog, Long> implements AppLoginLogService {

	@Autowired
	private ElasticSearchService elasticSearchService;

	@Autowired
	private RedisService redisService;

	@Autowired
	private RabbitMQProducer rabbitMQProducer;


	@Resource(name = "appLoginLogDao")
	@Override
	public void setDao (BaseDao<AppLoginLog, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findLoginLogPageToES(QueryFilter filter) {
		String userId = filter.getRequest().getParameter("userId");
		PageResult pageResult = new PageResult();
		if (StringUtil.isNull(userId)) {
			filter.addFilter("userId=", userId);
		}
		filter.setOrderby("loginDate desc");
		PageList<AppLoginLog> listToEs = elasticSearchService.findPageListToEs(filter);
		return new PageResult(listToEs.getList(), listToEs.getTotalElements(), listToEs.getCurrentPage(), listToEs.getPageSize());
	}

	@Override
	public void saveToEs(AppLoginLog log) {
		elasticSearchService.saveToEs(log);
	}

	@Override
	public void updateToEs(AppLoginLog log) {
		elasticSearchService.updateToEs(log);
	}

	@Override
	public synchronized void updateLoginLogTimer() {
		redisService.lock(JWTUtil.getManageAllKey()+"lock");
		try {
			Map<String, String> map = redisService.hgetAll(JWTUtil.getManageAllKey());
			if (map != null && map.size() > 0) {
				Set<String> keySet = map.keySet();
				for(String key:keySet){
					String refreshTime = redisService.get("JWT:token:pc:manage:refreshTime:" + key);
					if(StringUtils.isEmpty(refreshTime)){
						//发消息更新日志
						String loginId = map.get(key);
						rabbitMQProducer.sendMsgByQueue("updateLoginLogger", loginId);
						//删除
						redisService.hdel(JWTUtil.getManageAllKey(),key);
					}
				}
			}
		} catch (Exception e){

		} finally {
			redisService.unLock(JWTUtil.getManageAllKey()+"lock");
		}

	}
}
