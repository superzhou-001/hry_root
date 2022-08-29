/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:27:25
 */
package hry.platform.config.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.config.dao.AppConfigDao;
import hry.platform.config.model.AppConfig;
import hry.platform.config.service.AppConfigService;
import hry.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> AppConfigService </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:27:25
 */
@Service("appConfigService")
public class AppConfigServiceImpl extends BaseServiceImpl<AppConfig, Long> implements AppConfigService {

    private final static String CONFIG_CACHE = "appconfig";

    @Resource(name = "appConfigDao")
    @Override
    public void setDao (BaseDao<AppConfig, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private RedisService redisService;

    /**
     * @author: liuchenghui
     * @Date: 2020/3/24 11:19
     * @Description: 从redis中根据key获取值
     */
    @Override
    public String getValueByKey (String key) {
        String value = "";
        // 查询redis，获取值
        if (StringUtils.isNotEmpty(key)) {
            String data = redisService.get(CONFIG_CACHE + ":all");
            if (null != data && !"".equals(data)) {
                JSONObject obj = JSON.parseObject(data);
                if (obj.containsKey(key)) {
                    value = obj.get(key).toString();
                }
            }
        }
        // 如果value不存在，则直接查库
        if (StringUtils.isEmpty(value)) {
            QueryFilter filter = new QueryFilter(AppConfig.class);
            filter.addFilter("configkey=", key);
            List<AppConfig> list = appConfigService.find(filter);
            if (null != list && list.size() > 0) {
                value = list.get(0).getValue();
            }
        }
        return value;
    }

    @Override
    public void initCache () {
        List<AppConfig> typeList = ((AppConfigDao) dao).findType();
        if (typeList != null && typeList.size() > 0) {
            typeList.stream().forEach(appConfig -> {
                QueryFilter queryFilter = new QueryFilter(AppConfig.class);
                queryFilter.addFilter("typekey=", appConfig.getTypekey());
                queryFilter.addFilter("ishidden=", 1);
                List<AppConfig> list = find(queryFilter);
                String data = JSON.toJSONString(list);
                redisService.save(CONFIG_CACHE + ":" + appConfig.getTypekey(), data);
            });
        }

        // 隐藏的就不需要初始化到redis中了
        List<AppConfig> list = find(new QueryFilter(AppConfig.class).addFilter("ishidden=", 1));
        Map<String, String> map = new HashMap<String, String>();
        if (list != null && list.size() > 0) {
            list.stream().forEach(appConfig -> {
                if (StringUtils.isNotEmpty(appConfig.getConfigkey())) {
                    map.put(appConfig.getConfigkey(), appConfig.getValue());
                }
            });
        }
        String data = JSON.toJSONString(map);
        redisService.save(CONFIG_CACHE + ":all", data);
    }

    @Override
    public void batchUpdate (List<AppConfig> list) {
        ((AppConfigDao) dao).batchUpdate(list);
    }

    @Override
    public JsonResult dataByTypeKey(String typeKey) {
        JsonResult jsonResult=new JsonResult();
        String data="";
        Map<String,String> map=new HashMap<String, String>();
        if(null!=typeKey&&!"".equals(typeKey)){
            data=redisService.get(CONFIG_CACHE+":"+typeKey);
            if(null!=data&&!"".equals(data)){
                JSONArray obj=JSON.parseArray(data);
                for(Object o:obj){
                    JSONObject	 oo=JSON.parseObject(o.toString());
                    map.put(oo.getString("configkey"), oo.getString("value"));
                }
            }else{
                QueryFilter filter=new QueryFilter(AppConfig.class);
                filter.addFilter("typekey=", typeKey);
                filter.addFilter("ishidden=", "1");

                List<AppConfig> list=appConfigService.find(filter);
                for (AppConfig config:list ) {
                    map.put(config.getConfigkey(), config.getValue());
                }
            }
            data=JSON.toJSONString(map);
            jsonResult.setObj(HtmlUtils.htmlUnescape(data));
            jsonResult.setSuccess(true);
        }
        return jsonResult;
    }

}
