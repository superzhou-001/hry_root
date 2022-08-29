/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:14:23 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppStationContent;
import hry.platform.website.service.AppStationContentService;
import hry.platform.website.service.AppUserStationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppStationContentController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:14:23 
 */
@Api(value = "站内信内容", tags = "站内信内容", description = "站内信内容")
@RestController
@RequestMapping("/website/appstationcontent")
public class AppStationContentController extends BaseController {

	@Autowired
	private AppStationContentService appStationContentService;

	@Autowired
    private AppUserStationService appUserStationService;


	/**
     * <p> 站内信内容-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:14:23 
     */
    @ApiOperation(value = "站内信内容-id查询", notes = "站内信内容-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppStationContent appStationContent = appStationContentService.get(id);
        if (appStationContent != null) {
            jsonResult.setObj(appStationContent);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 站内信内容-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:14:23 
     */
    @ApiOperation(value = "站内信内容-添加", notes = "站内信内容-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppStationContent appStationContent) {
        JsonResult jsonResult = new JsonResult();
        appStationContentService.save(appStationContent);
        if (1 == appStationContent.getStatus()) {
            // 保存用户信件记录
            appUserStationService.saveUserStation(appStationContent.getId(), appStationContent.getReceivers(), appStationContent.getUserType());
        }
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信内容-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:14:23 
     */
    @ApiOperation(value = "站内信内容-修改", notes = "站内信内容-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppStationContent appStationContent) {
        JsonResult jsonResult = new JsonResult();
        appStationContentService.update(appStationContent);
        if (1 == appStationContent.getStatus()) {
            // 保存用户信件记录
            appUserStationService.saveUserStation(appStationContent.getId(), appStationContent.getReceivers(), appStationContent.getUserType());
        }
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信内容-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:14:23 
     */
    @ApiOperation(value = "站内信内容-id删除", notes = "站内信内容-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appStationContentService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信内容-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:14:23 
     */
    @ApiOperation(value = "站内信内容-分页查询", notes = "站内信内容-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppStationContent.class, request);
        return appStationContentService.findPageResult(filter);
    }

}
