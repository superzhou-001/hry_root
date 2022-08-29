/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:12
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.WebsiteNavigationManage;
import hry.platform.website.service.WebsiteNavigationManageService;
import hry.util.UUIDUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> WebsiteNavigationManageController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:12
 */
@Api(value = "网站导航管理", tags = "网站导航管理", description = "网站导航管理")
@RestController
@RequestMapping("/website/websitenavigationmanage")
public class WebsiteNavigationManageController extends BaseController {

	@Autowired
	private WebsiteNavigationManageService websiteNavigationManageService;

	/**
     * <p> 网站导航管理-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:12
     */
    @ApiOperation(value = "网站导航管理-id查询", notes = "网站导航管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		WebsiteNavigationManage websiteNavigationManage = websiteNavigationManageService.get(id);
        if (websiteNavigationManage != null) {
            jsonResult.setObj(websiteNavigationManage);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 网站导航管理-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:12
     */
    @ApiOperation(value = "网站导航管理-添加", notes = "网站导航管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (WebsiteNavigationManage websiteNavigationManage) {
        JsonResult jsonResult = new JsonResult();
        websiteNavigationManage.setNkey(UUIDUtil.getUUID());
        websiteNavigationManageService.save(websiteNavigationManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站导航管理-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:12
     */
    @ApiOperation(value = "网站导航管理-修改", notes = "网站导航管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (WebsiteNavigationManage websiteNavigationManage) {
        JsonResult jsonResult = new JsonResult();
        websiteNavigationManageService.update(websiteNavigationManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站导航管理-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:12
     */
    @ApiOperation(value = "网站导航管理-id删除", notes = "网站导航管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        websiteNavigationManageService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 网站导航管理-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:12
     */
    @ApiOperation(value = "网站导航管理-分页查询", notes = "网站导航管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "nkey", value = "标识key", required = true) @RequestParam String nkey,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(WebsiteNavigationManage.class, request);
        filter.addFilter("pkey=", nkey);
        return websiteNavigationManageService.findPageResult(filter);
    }

    @ApiOperation(value = "网站导航管理-加载菜单树数据", notes = "网站导航管理-加载菜单树数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/loadTree")
    public List<WebsiteNavigationManage> loadTree (HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(WebsiteNavigationManage.class, request);
        return websiteNavigationManageService.findWebSiteTree();
    }


}
