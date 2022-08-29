package hry.platform;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.service.AppConfigService;
import hry.platform.website.model.*;
import hry.platform.website.service.*;
import hry.security.jwt.annotation.UnAuthentication;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luyue
 * @date 2020/7/3 16:03
 */
@Api(value = "网站配置信息查询", tags = "网站配置信息查询", description = "网站配置信息查询")
@RestController
@RequestMapping("/appConfig")
public class AppConfigController  extends BaseController {
    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AdContentManageService adContentManageService;
    @Autowired
    private AdPositionManageService adPositionManageService;

    @Autowired
    private WebsiteNavigationManageService websiteNavigationManageService;

    @Autowired
    private AppArticleCategoryService appArticleCategoryService;
    @Autowired
    private AppArticleContentService appArticleContentService;

    @Autowired
    private SysProtocolManageService sysProtocolManageService;

    @ApiOperation(value = "读取配置的网站参数", notes = "读取配置的网站参数")
    @GetMapping(value = "/dataByTypeKey")
    @UnAuthentication
    public JsonResult dataByTypeKey (
            @ApiParam(name = "typeKey", value = "typeKey(logo等参数：extraParamConfig)", required = true) @RequestParam String typeKey
    ) {
        JsonResult jsonResult = appConfigService.dataByTypeKey(typeKey);
        return jsonResult;
    }

    @ApiOperation(value = "广告内容查询", notes = "广告内容查询", response = AdPositionManage.class )
    @GetMapping(value = "/getByAdkey")
    @UnAuthentication
    public JsonResult getByAdkey (
            @ApiParam(name = "adkey", value = "广告位获取标识", required = true) @RequestParam String adkey) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(AdPositionManage.class);
        filter.addFilter("adPositionKey=",adkey);
        AdPositionManage adPositionManage = adPositionManageService.get(filter);
        if(adPositionManage == null){
            return jsonResult.setSuccess(false).setMsg("此广告位不存在");
        }

        QueryFilter filter2 = new QueryFilter(AdContentManage.class);
        filter2.addFilter("adpId=",adPositionManage.getId());
        List<AdContentManage> list = adContentManageService.find(filter2);
        return jsonResult.setSuccess(true).setObj(list);
    }


    @ApiOperation(value = "网站导航管理-导航key", notes = "网站导航管理-导航key", response = WebsiteNavigationManage.class )
    @GetMapping(value = "/getByKey")
    @UnAuthentication
    public JsonResult getByKey (
            @ApiParam(name = "nkey", value = "导航key（nkey:top-顶部,down-底部）", required = true) @RequestParam String nkey
    ) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(WebsiteNavigationManage.class);
        queryFilter.addFilter("pkey=",nkey);
        List<WebsiteNavigationManage> navigationManageList = websiteNavigationManageService.find(queryFilter);
        if (navigationManageList != null) {
            jsonResult.setObj(navigationManageList);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    @ApiOperation(value = "网站协议查询", notes = "网站协议查询", response = SysProtocolManage.class )
    @GetMapping(value = "/getBySpmkey")
    @UnAuthentication
    public JsonResult getBySpmkey (
            @ApiParam(name = "spmkey", value = "网站协议获取标识", required = true) @RequestParam String spmkey) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(SysProtocolManage.class);
        filter.addFilter("protocolKey=",spmkey);
        filter.addFilter("isDisable=",1);
        SysProtocolManage sysProtocolManage = sysProtocolManageService.get(filter);
        if(sysProtocolManage == null){
            return jsonResult.setSuccess(false).setMsg("此协议不存在或未启用");
        }
        sysProtocolManage.setProtocolContent(HtmlUtils.htmlUnescape(sysProtocolManage.getProtocolContent()));
        return jsonResult.setSuccess(true).setObj(sysProtocolManage);
    }

    /**
     * 查询首页平台公告和行业动态
     * @return
     */
    @ApiOperation(value = "查询首页媒体报道和平台公告",  notes = "查询首页媒体报道和平台公告等",response=AppArticleContent.class)
    @GetMapping(value = "/loadNoticeInfo")
    @UnAuthentication
    public JsonResult loadNoticeInfo(
            @ApiParam(name = "typeName", value = "类型（平台公告, 媒体报道）",required = true) @RequestParam("typeName") String typeName
    ) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(AppArticleCategory.class);
        filter.addFilter("categoryName=",typeName);
        AppArticleCategory appArticleCategory = appArticleCategoryService.get(filter);
        if(appArticleCategory == null){
            return jsonResult.setSuccess(false).setMsg("此分类不存在");
        }
        QueryFilter cfilter = new QueryFilter(AppArticleContent.class);
        cfilter.addFilter("categoryId=",appArticleCategory.getId());
        cfilter.addFilter("isShow=",1);
        cfilter.setOrderby("id desc");
        List<AppArticleContent> list = appArticleContentService.find(cfilter);
        for(AppArticleContent appArticleContent : list){
            appArticleContent.setContent(HtmlUtils.htmlUnescape(appArticleContent.getContent()));
        }
        return jsonResult.setSuccess(true).setObj(list);
    }


    @ApiOperation(value = "网站文章列表-分页查询", notes = "网站文章列表-分页查询",response = AppArticleContent.class)
    @GetMapping(value = "/listArticle")
    @UnAuthentication
    public PageResult listArticle (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "categoryKey", value = "当前分类key", required = true) @RequestParam String categoryKey,
            @ApiParam(name = "labelName", value = "标签", required = false) @RequestParam String labelName,
            @ApiParam(name = "keyword", value = "搜索关键字", required = false) @RequestParam String keyword,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppArticleContent.class,request);
        return appArticleContentService.findPageBySql(filter);
    }


    @ApiOperation(value = "查询文章分类",  notes = "查询文章分类",response=AppArticleCategory.class)
    @GetMapping(value = "/findArticleCategory")
    @UnAuthentication
    public JsonResult findArticleCategory(
            @ApiParam(name = "categoryKey", value = "当前分类key",required = true) @RequestParam String categoryKey
    ) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(AppArticleCategory.class);
        filter.addFilter("pCategoryKey=", categoryKey);
        filter.addFilter("isShow=",1);
        filter.setOrderby("sort ASC");
        List<AppArticleCategory>  list = appArticleCategoryService.find(filter);
        return jsonResult.setSuccess(true).setObj(list);
    }


    @ApiOperation(value = "查询文章详情-id查询", notes = "查询文章详情-id查询",response = AppArticleContent.class)
    @GetMapping(value = "/getArticle")
    @UnAuthentication
    public JsonResult getArticle (@ApiParam(name = "id", value = "id", required = true) @RequestParam Long id) {
        JsonResult jsonResult = new JsonResult();
        AppArticleContent appArticleContent = appArticleContentService.get(id);
        if (appArticleContent != null) {
            appArticleContent.setContent(HtmlUtils.htmlUnescape(appArticleContent.getContent()));
            AppArticleCategory appArticleCategory = appArticleCategoryService.get(appArticleContent.getCategoryId());
            appArticleContent.setCategoryName(appArticleCategory.getCategoryName());
            jsonResult.setObj(appArticleContent);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }







}
