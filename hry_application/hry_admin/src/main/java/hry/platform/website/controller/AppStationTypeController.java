/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:13:14 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppStationType;
import hry.platform.website.service.AppStationTypeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppStationTypeController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:13:14 
 */
@Api(value = "站内信类型", tags = "站内信类型", description = "站内信类型")
@RestController
@RequestMapping("/website/appstationtype")
public class AppStationTypeController extends BaseController {

	@Autowired
	private AppStationTypeService appStationTypeService;

	/**
     * <p> 站内信类型-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:13:14 
     */
    @ApiOperation(value = "站内信类型-id查询", notes = "站内信类型-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppStationType appStationType = appStationTypeService.get(id);
        if (appStationType != null) {
            jsonResult.setObj(appStationType);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 站内信类型-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:13:14 
     */
    @ApiOperation(value = "站内信类型-添加", notes = "站内信类型-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppStationType appStationType) {
        JsonResult jsonResult = new JsonResult();
        appStationTypeService.save(appStationType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信类型-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:13:14 
     */
    @ApiOperation(value = "站内信类型-修改", notes = "站内信类型-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppStationType appStationType) {
        JsonResult jsonResult = new JsonResult();
        appStationTypeService.update(appStationType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信类型-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:13:14 
     */
    @ApiOperation(value = "站内信类型-id删除", notes = "站内信类型-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appStationTypeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 站内信类型-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:13:14 
     */
    @ApiOperation(value = "站内信类型-分页查询", notes = "站内信类型-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppStationType.class, request);
        return appStationTypeService.findPageResult(filter);
    }

}
