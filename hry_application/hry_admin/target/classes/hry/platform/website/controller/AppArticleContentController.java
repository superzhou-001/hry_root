/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:12:35 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppArticleContent;
import hry.platform.website.service.AppArticleContentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p> AppArticleContentController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:12:35 
 */
@Api(value = "网站内容发布", tags = "网站内容发布", description = "网站内容发布")
@RestController
@RequestMapping("/website/apparticlecontent")
public class AppArticleContentController extends BaseController {

	@Autowired
	private AppArticleContentService appArticleContentService;

	/**
     * <p> 网站内容发布-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:12:35 
     */
    @ApiOperation(value = "网站内容发布-id查询", notes = "网站内容发布-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppArticleContent appArticleContent = appArticleContentService.get(id);
        if (appArticleContent != null) {
            jsonResult.setObj(appArticleContent);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 网站内容发布-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:12:35 
     */
    @ApiOperation(value = "网站内容发布-添加", notes = "网站内容发布-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppArticleContent appArticleContent) {
        JsonResult jsonResult = new JsonResult();
        appArticleContentService.save(appArticleContent);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容发布-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:12:35 
     */
    @ApiOperation(value = "网站内容发布-修改", notes = "网站内容发布-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppArticleContent appArticleContent) {
        JsonResult jsonResult = new JsonResult();
        appArticleContentService.update(appArticleContent);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容发布-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:12:35 
     */
    @ApiOperation(value = "网站内容发布-id删除", notes = "网站内容发布-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appArticleContentService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容发布-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:12:35 
     */
    @ApiOperation(value = "网站内容发布-分页查询", notes = "网站内容发布-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "categoryKey", value = "当前节点key", required = true) @RequestParam String categoryKey,
            HttpServletRequest request) {
        return appArticleContentService.findPageByResult(request);
    }


    @ApiOperation(value = "网站内容发布-分类类别", notes = "网站内容发布-分类类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/findByCategoryList")
    public List<Map<String, Object>> findByCategoryList(
            @ApiParam(name = "categoryKey", value = "当前分类key", required = true) @RequestParam String categoryKey,
            HttpServletRequest request) {
        return appArticleContentService.findByCategoryList(categoryKey);
    }

    @ApiOperation(value = "网站内容发布-单页面查询", notes = "网站内容发布-单页面查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/findSinglePage")
    public JsonResult findSinglePage(@ApiParam(name = "categoryId", value = "分类id", required = true) @RequestParam String categoryId,
                                     HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppArticleContent.class);
        filter.addFilter("categoryId=", categoryId);
        AppArticleContent articleContent = appArticleContentService.get(filter);
        return new JsonResult(true).setObj(articleContent);
    }

}
