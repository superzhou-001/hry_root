/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:11:54
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.model.AppArticleCategory;
import hry.platform.website.model.AppArticleContent;
import hry.platform.website.service.AppArticleCategoryService;
import hry.platform.website.service.AppArticleContentService;
import hry.security.jwt.JWTContext;
import hry.util.UUIDUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> AppArticleCategoryController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:11:54
 */
@Api(value = "网站内容分类", tags = "网站内容分类", description = "网站内容分类")
@RestController
@RequestMapping("/website/apparticlecategory")
public class AppArticleCategoryController extends BaseController {

	@Autowired
	private AppArticleCategoryService appArticleCategoryService;
	@Autowired
    private AppArticleContentService appArticleContentService;

	/**
     * <p> 网站内容分类-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:11:54
     */
    @ApiOperation(value = "网站内容分类-id查询", notes = "网站内容分类-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppArticleCategory appArticleCategory = appArticleCategoryService.get(id);
        if (appArticleCategory != null) {
            jsonResult.setObj(appArticleCategory);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 网站内容分类-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:11:54
     */
    @ApiOperation(value = "网站内容分类-添加", notes = "网站内容分类-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppArticleCategory appArticleCategory) {
        JsonResult jsonResult = new JsonResult();
        if ("root".equals(appArticleCategory.getPCategoryKey())) {
            appArticleCategory.setPCategoryName("no");
            appArticleCategory.setIsPage(2);
        } else {
            QueryFilter filter = new QueryFilter(AppArticleCategory.class);
            filter.addFilter("categoryKey=", appArticleCategory.getPCategoryKey());
            AppArticleCategory category = appArticleCategoryService.get(filter);
            appArticleCategory.setPCategoryName(category.getCategoryName());
        }
        appArticleCategory.setCategoryKey(UUIDUtil.getUUID());
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        appArticleCategory.setOperator(user.getUserName());
        appArticleCategoryService.save(appArticleCategory);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容分类-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:11:54
     */
    @ApiOperation(value = "网站内容分类-修改", notes = "网站内容分类-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppArticleCategory appArticleCategory) {
        JsonResult jsonResult = new JsonResult();
        appArticleCategoryService.update(appArticleCategory);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容分类-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:11:54
     */
    @ApiOperation(value = "网站内容分类-id删除", notes = "网站内容分类-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        // 查询自身key
        AppArticleCategory category = appArticleCategoryService.get(id);
        // 获取该文章类型的子类
        QueryFilter filter = new QueryFilter(AppArticleCategory.class);
        filter.addFilter("pCategoryKey=", category.getCategoryKey());
        List<AppArticleCategory> categoryList = appArticleCategoryService.find(filter);
        if (categoryList != null && categoryList.size() > 0) {
            return jsonResult.setSuccess(false).setMsg("分类下有子分类，请先删除子分类");
        }
        // 判断是否有文章列表
        QueryFilter filter1 = new QueryFilter(AppArticleContent.class);
        filter1.addFilter("categoryId=", id);
        List<AppArticleContent> contentList = appArticleContentService.find(filter1);
        if (contentList != null && contentList.size() > 0 && "0".equals(category.getIsPage())) {
            return jsonResult.setSuccess(false).setMsg("分类关联着文章内容，请先删除分类下文章内容");
        }
        // 删除分类
        appArticleCategoryService.delete(id);
        // 删除分类下内容
        appArticleContentService.delete(filter1);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站内容分类-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:11:54
     */
    @ApiOperation(value = "网站内容分类-分页查询", notes = "网站内容分类-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "categoryKey", value = "当前分类key", required = true) @RequestParam String categoryKey,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppArticleCategory.class, request);
        filter.addFilter("pCategoryKey=", categoryKey);
        return appArticleCategoryService.findPageResult(filter);
    }

    @ApiOperation(value = "网站内容分类-加载菜单树数据", notes = "网站内容分类-加载菜单树数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/loadTree")
    public List<AppArticleCategory> loadTree (HttpServletRequest request) {
        return appArticleCategoryService.findArticleTree();
    }

}
