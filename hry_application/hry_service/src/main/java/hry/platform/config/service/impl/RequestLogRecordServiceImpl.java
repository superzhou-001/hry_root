/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-13 17:30:46
 */
package hry.platform.config.service.impl;

import hry.bean.PageResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.model.RequestLogRecord;
import hry.platform.config.service.RequestLogRecordService;
import hry.platform.elastic.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.repository.PageList;

import javax.annotation.Resource;

/**
 * <p> RequestLogRecordService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-13 17:30:46
 */
@Service("requestLogRecordService")
@Slf4j
public class RequestLogRecordServiceImpl extends BaseServiceImpl<RequestLogRecord, Long> implements RequestLogRecordService {

	@Autowired
	private ElasticSearchService elasticSearchService;

	@Resource(name = "requestLogRecordDao")
	@Override
	public void setDao (BaseDao<RequestLogRecord, Long> dao) {
		super.dao = dao;
	}

	@Override
	public void saveToEs(RequestLogRecord record) {
		elasticSearchService.saveToEs(record);
	}

	@Override
	public PageResult findPageResultToEs(QueryFilter filter) {
		PageResult pageResult = new PageResult();
		//filter.setPage(1);
		//filter.setPageSize(5000);
		//filter.addFilter("id=",98);
		//filter.addFilter("ip=","172.16");
		//filter.addFilter("port=", 615);
		//filter.addFilter("methodDesc=","对象");
		//filter.or("methodName=", "loadtree");
		//ilter.setOrderby("createdTime asc");
		PageList<RequestLogRecord> listToEs = elasticSearchService.findPageListToEs(filter);
		return new PageResult(listToEs.getList(), listToEs.getTotalElements(), listToEs.getCurrentPage(), listToEs.getPageSize());
	}
}
