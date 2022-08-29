package hry.platform.elastic.service;

import com.alibaba.fastjson.JSONArray;
import hry.core.util.QueryFilter;
import org.zxp.esclientrhl.repository.PageList;

import java.util.List;
import java.util.Map;

/**
 * @author zhouming
 * @date 2020/4/26 11:08
 * @Version 1.0
 */
public interface ElasticSearchService<T> {

    /**
    * 添加数据至ES
    * */
    public void saveToEs(T t);

    /**
    * Es批量添加数据
    * */
    public void bullSaveToEs(List<T> list);

    /**
     * Es统计数量
     * */
    public Long count(T t);

    /**
     * 按条件分页查询
     * */
    public PageList<T> findPageListToEs(QueryFilter filter);

    /**
     * 指定sql查询
     * */
    public JSONArray findSqlList(String sql);

    /**
     * ES修改
     * */
    public void updateToEs(T t);



}
