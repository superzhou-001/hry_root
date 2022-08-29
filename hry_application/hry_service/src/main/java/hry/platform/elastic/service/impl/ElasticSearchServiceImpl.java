package hry.platform.elastic.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.core.util.QueryFilter;
import hry.platform.elastic.service.ElasticSearchService;
import hry.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.zxp.esclientrhl.enums.SqlFormat;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;
import org.zxp.esclientrhl.repository.PageList;
import org.zxp.esclientrhl.repository.PageSortHighLight;
import org.zxp.esclientrhl.repository.Sort;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author zhouming
 * @date 2020/4/26 11:10
 * @Version 1.0
 */
@Service("elasticSearchService")
@Slf4j
public class ElasticSearchServiceImpl<T> implements ElasticSearchService<T> {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public void saveToEs(T t) {
        try{
            elasticsearchTemplate.save(t);
            log.info("---es数据添加成功");
        } catch (Exception e) {
            log.error("---es数据添加异常"+e.getLocalizedMessage());
        }
    }

    @Override
    public void bullSaveToEs(List<T> list) {
        try{
            elasticsearchTemplate.save(list);
            log.info("---es批量数据添加成功");
        } catch (Exception e) {
            log.error("---es批量数据添加异常"+e.getLocalizedMessage());
        }
    }

    @Override
    public Long count(T t) {
        long count = 0L;
        try {
            count = elasticsearchTemplate.count(new MatchAllQueryBuilder(), t.getClass());
        } catch (Exception e) {
            log.error("---es批量count统计异常"+e.getLocalizedMessage());
        }
        return count;
    }

    /**
     * 默认只查询 10000的 数据量 （可修改）
     * */
    @Override
    public PageList<T> findPageListToEs(QueryFilter filter) {
        try {
            // 分页属性配置
            Class entityClazz = filter.getExample().getEntityClass();
            PageSortHighLight psh = new PageSortHighLight(filter.getPage(), filter.getPageSize());
            // 额外条件配置
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            List<Example.Criteria> criteria = filter.getExample().getOredCriteria();
            // 简单组合查询 andOr
            criteria.forEach(cr -> {
                if ("and".equals(cr.getAndOr())) {
                    cr.getCriteria().forEach(c -> {
                        String key = c.getCondition().replace("=", "").replace("_like", "");
                        Object value = c.getValue();
                        QueryBuilder query = QueryBuilders.termQuery(key,value);
                        queryBuilder.must(query);
                    });
                } else {
                    cr.getCriteria().forEach(c -> {
                        String key = c.getCondition().replace("=", "").replace("_like", "");
                        Object value = c.getValue();
                        QueryBuilder query = QueryBuilders.termQuery(key,value);
                        queryBuilder.should(query);
                    });
                }
            });
            // 排序
            String orderByStr = filter.getExample().getOrderByClause();
            if (StringUtil.isNull(orderByStr)) {
                String[] str = orderByStr.split(",");
                Sort sort = new Sort();
                Arrays.stream(str).forEach(st -> {
                    String[] s = st.split(" ");
                    String sorter = s[0];
                    if (s[1].equalsIgnoreCase("ASC")) {
                        Sort.Order order = new Sort.Order(SortOrder.ASC,sorter);
                        sort.and(new Sort(order));
                    } else {
                        Sort.Order order = new Sort.Order(SortOrder.DESC,sorter);
                        sort.and(new Sort(order));
                    }
                });
                psh.setSort(sort);
            }
            PageList<T> pageList = elasticsearchTemplate.search(queryBuilder, psh, entityClazz);
            return pageList;
        } catch (Exception e) {
            log.error("---es分页查询异常"+e.getLocalizedMessage());
        }
        return null;
    }

    /**
    * sql查询返回格式
    *
    * CSV("csv","text/csv"),
    * JSON("json","application/json"),
    * TSV("tsv","text/tab-separated-values"),
    *  TXT("txt","text/plain"),
    * YAML("yaml","application/yaml"),
    * CBOR("cbor","application/cbor"),
    * SMILE("smile","application/smile");
    * */
    @Override
    public JSONArray findSqlList(String sql) {
        try{
            String result = elasticsearchTemplate.queryBySQL(sql, SqlFormat.YAML);
            return formatResult(result);
        } catch (Exception e){
            log.error("---esSql异常"+e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 格式化内容
     * */
    private JSONArray formatResult(String result) {
        JSONArray array = new JSONArray();
        Yaml yaml = new Yaml();
        Map<String,Object> map = yaml.load(result);
        // 获取头
        List<String> header = new ArrayList<>();
        List<Map<String, Object>> columns = (List<Map<String, Object>>)map.get("columns");
        List<List<String>> rows = (List<List<String>>)map.get("rows");
        columns.stream().forEach(m->header.add(m.get("name").toString()));
        rows.stream().forEach(row -> {
            JSONObject obj = new JSONObject();
            for (int i = 0; i < row.size(); i++) {
                obj.put(header.get(i), row.get(i));
            }
            array.add(obj);
        });
        return array;
    }

    @Override
    public void updateToEs(T t) {
        // 跟新对象中存在的值
        try {
            elasticsearchTemplate.update(t);
            log.info("---es数据添加成功修改成功");
        } catch (Exception e) {
            log.error("---es更新异常"+e.getLocalizedMessage());
        }

    }
}
