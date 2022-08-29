package hry.platform.config.service;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.util.QueryFilter;
import hry.platform.config.model.ActionLogRecord;

/**
 * @author zhouming
 * @date 2020/5/9 18:10
 * @Version 1.0
 */
public interface ActionLogRecordService {
    /**
     * 数据添加至ES
     * */
    public void saveToEs(ActionLogRecord record);

    /**
     * 分页查询数据
     * */
    public PageResult findPageResultToEs(QueryFilter filter);

    /**
     * 常用功能
     * */
    public JsonResult commonFunction();

    /**
     * 最近操作
     * */
    public JsonResult recentHandle();

    public JsonResult saveToEsList(ActionLogRecord record);
}
