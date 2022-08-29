/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-13 17:30:46 
 */
package hry.platform.config.service;

import hry.bean.PageResult;
import hry.core.mvc.service.BaseService;
import hry.core.util.QueryFilter;
import hry.platform.config.model.RequestLogRecord;

/**
 * <p> RequestLogRecordService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-13 17:30:46 
 */
public interface RequestLogRecordService extends BaseService<RequestLogRecord, Long> {

    /**
     * 数据添加至ES
     * */
    public void saveToEs(RequestLogRecord record);

    /**
     * 分页查询数据
     * */
    public PageResult findPageResultToEs(QueryFilter filter);
}
