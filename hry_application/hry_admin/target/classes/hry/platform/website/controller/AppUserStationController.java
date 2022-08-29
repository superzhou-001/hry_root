/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-13 14:55:30 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppStationContent;
import hry.platform.website.model.AppUserStation;
import hry.platform.website.service.AppStationContentService;
import hry.platform.website.service.AppUserStationService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p> AppUserStationController </p>
 *
 * @author: zhouming
 * @Date: 2020-08-13 14:55:30 
 */
@Api(value = "用户信件表 ", tags = "用户信件表 ", description = "用户信件表 ")
@RestController
@RequestMapping("/website/appuserstation")
public class AppUserStationController extends BaseController {

	@Autowired
	private AppUserStationService appUserStationService;
	@Autowired
    private AppStationContentService appStationContentService;

	/**
     * <p> 用户信件表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-13 14:55:30 
     */
    @ApiOperation(value = "用户信件表 -获取信件内容", notes = "用户信件表 -获取信件内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        AppUserStation appUserStation = appUserStationService.get(id);
        if (appUserStation != null) {
            AppStationContent content = appStationContentService.get(appUserStation.getStationId());
            jsonResult.setObj(content);
            // 修改用户信件转态
            appUserStation.setStatus(1);
            appUserStation.setReadTime(new Date());
            appUserStationService.update(appUserStation);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 用户信件表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-13 14:55:30 
     */
    @ApiOperation(value = "用户信件表 -id删除", notes = "用户信件表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        AppUserStation station = new AppUserStation();
        station.setId(id);
        station.setStatus(2);
        appUserStationService.update(station);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 用户信件表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-13 14:55:30 
     */
    @ApiOperation(value = "用户信件表 -分页查询", notes = "用户信件表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppUserStation.class, request);
        return appUserStationService.findUserStationPage(filter);
    }


}
