/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:27:25
 */
package hry.platform.config.controller;

import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.AppConfig;
import hry.platform.config.service.AppConfigService;
import hry.platform.config.service.NewAppDicService;
import hry.security.jwt.annotation.UnAuthentication;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> AppConfigController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:27:25
 */
@Api(value = "网站参数配置", tags = "网站参数配置", description = "网站参数配置")
@RestController
@RequestMapping("/config/appconfig")
public class AppConfigController extends BaseController {

    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private NewAppDicService newAppDicService;

    /**
     * <p> 网站参数配置-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "网站参数配置-id查询", notes = "网站参数配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        AppConfig appConfig = appConfigService.get(id);
        appConfig.setValue(HtmlUtils.htmlUnescape(appConfig.getValue()));
        jsonResult.setObj(appConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站参数配置-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "网站参数配置-单个添加", notes = "网站参数配置-单个添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppConfig appConfig) {
        JsonResult jsonResult = new JsonResult();
        appConfig.setValue(HtmlUtils.htmlEscape(appConfig.getValue()));
        appConfigService.save(appConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站参数配置-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "网站参数配置-单个修改", notes = "网站参数配置-单个修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppConfig appConfig) {
        JsonResult jsonResult = new JsonResult();
        appConfig.setValue(HtmlUtils.htmlEscape(appConfig.getValue()));
        appConfigService.update(appConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站参数配置-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "网站参数配置-id删除", notes = "网站参数配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appConfigService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站参数配置-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "网站参数配置-分页查询", notes = "网站参数配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppConfig.class, request);
        PageResult pageResult = appConfigService.findPageResult(filter);
        if (pageResult != null) {
            List<AppConfig> configList = pageResult.getRows();
            if (configList != null && configList.size() > 0) {
                configList.stream().forEach(appConfig -> {
                    appConfig.setValue(HtmlUtils.htmlUnescape(appConfig.getValue()));
                });
            }
        }
        return pageResult;
    }

    @ApiOperation(value = "网站参数配置-根据分类加载数据", notes = "网站参数配置-根据分类加载数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @UnAuthentication
    @GetMapping(value = "/loadConfigData")
    public Map<String, Object> loadConfigData (
            @ApiParam(name = "typeKey", value = "typeKey", required = true) @RequestParam String typeKey,
            HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<>();
        QueryFilter filter = new QueryFilter(AppConfig.class);
        if (!"all".equals(typeKey)) {
            filter.addFilter("typekey=", typeKey);
        }
        filter.addFilter("ishidden=", "1");
        filter.setOrderby("created asc");
        List<AppConfig> appConfiglist = appConfigService.find(filter);
        if (appConfiglist != null && appConfiglist.size() > 0) {
            appConfiglist.stream().forEach(appConfig -> {
                appConfig.setValue(HtmlUtils.htmlUnescape(appConfig.getValue()));
            });
        }
        returnMap.put("configList", appConfiglist);
        returnMap.put("typeKey", typeKey);
        return returnMap;
    }

    @ApiOperation(value = "网站参数配置-保存配置数据-json格式数据保存", notes = "网站参数配置-保存配置数据-json格式数据保存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/saveConfig")
    public JsonResult saveConfig (
            @ApiParam(name = "jsonData", value = "保存数据", required = true) @RequestParam String jsonData,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        if (!StringUtils.isEmpty(jsonData)) {
            jsonData = HtmlUtils.htmlUnescape(jsonData);
            List<AppConfig> configs = JSONObject.parseArray(jsonData, AppConfig.class);
            if (configs != null && configs.size() > 0) {
                configs.stream().forEach(appConfig -> {
                    appConfig.setValue(HtmlUtils.htmlEscape(appConfig.getValue()));
                });
                // 批量保存
                appConfigService.batchUpdate(configs);

                //修改完后更新缓存
                appConfigService.initCache();
                jsonResult.setSuccess(true);
                return jsonResult;
            }
        }
        jsonResult.setSuccess(false);
        jsonResult.setMsg("参数错误");
        return jsonResult;

    }
}
