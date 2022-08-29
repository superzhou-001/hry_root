package hry.platform.config.service.impl;

import com.alibaba.fastjson.JSONArray;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.util.QueryFilter;
import hry.platform.config.model.ActionLogRecord;
import hry.platform.config.service.ActionLogRecordService;
import hry.platform.elastic.service.ElasticSearchService;
import hry.platform.newuser.model.NewAppUser;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxp.esclientrhl.repository.PageList;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhouming
 * @date 2020/5/9 18:12
 * @Version 1.0
 */
@Service("actionLogRecordService")
public class ActionLogRecordServiceImpl implements ActionLogRecordService {

    @Autowired
    private ElasticSearchService elasticSearchService;
    @Autowired
    private RedisService redisService;

    @Override
    public void saveToEs(ActionLogRecord record) {
        // 获取自增Id
        Long actionId = redisService.setIncr("esActionId",0);
        record.setId(actionId);
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        record.setId(actionId);
        record.setHandlers(user.getUserName());
        record.setHandlersId(user.getId());
        record.setCreatedTime(System.currentTimeMillis());
        elasticSearchService.saveToEs(record);
    }

    @Override
    public JsonResult saveToEsList(ActionLogRecord record) {
        List<ActionLogRecord> list = new Vector<>();
        for (int i = 0; i < 5000; i++) {
            // 获取自增Id
            Long actionId = redisService.setIncr("esActionRecordId",0);
            record.setId(actionId);
            record.setCreatedTime(System.currentTimeMillis());
            list.add(record);
        }
        elasticSearchService.bullSaveToEs(list);
        return new JsonResult(true);
    }

    @Override
    public PageResult findPageResultToEs(QueryFilter filter) {
        PageList<ActionLogRecord> listToEs = elasticSearchService.findPageListToEs(filter);
        return new PageResult(listToEs.getList(), listToEs.getTotalElements(), listToEs.getCurrentPage(), listToEs.getPageSize());
    }

    @Override
    public JsonResult commonFunction() {
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        String sql = "select menuName, url, count(1) as number from actionlogs where handlersId="+user.getId()+" and url is not null group by menuName,url order by number desc limit 30";
        JSONArray array = elasticSearchService.findSqlList(sql);
        String aryStr = array != null ? array.toJSONString() : "";
        List<ActionLogRecord> recordList = JSONArray.parseArray(aryStr, ActionLogRecord.class);
        return new JsonResult(true).setObj(recordList);
    }

    @Override
    public JsonResult recentHandle() {
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        Date date = DateUtil.addDay(new Date(), -10);
        Long dateTime = date.getTime();
        String sql = "select * from actionlogs where handlersId="+user.getId()+" and createdTime > "+dateTime+" and url is not null order by createdTime desc limit 30";
        JSONArray array = elasticSearchService.findSqlList(sql);
        String aryStr = array != null ? array.toJSONString() : "";
        List<ActionLogRecord> recordList = JSONArray.parseArray(aryStr, ActionLogRecord.class);
        // 去重
        recordList = recordList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() ->
                new TreeSet<>(Comparator.comparing(ActionLogRecord::getUrl))), ArrayList::new));
        // 降序
        Collections.sort(recordList, Comparator.comparing(ActionLogRecord::getCreatedTime).reversed());
        return new JsonResult(true).setObj(recordList);
    }



}
