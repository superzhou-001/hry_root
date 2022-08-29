/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-05-09 14:12:32 
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.AppBanner;
import hry.platform.config.service.AppBannerService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> AppBannerController </p>
 *
 * @author: zhouming
 * @Date: 2020-05-09 14:12:32 
 */
@Api(value = "banner配置", tags = "banner配置", description = "banner配置")
@RestController
@RequestMapping("/config/appbanner")
public class AppBannerController extends BaseController {

	@Autowired
	private AppBannerService appBannerService;

	/**
     * <p> banner配置-id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-05-09 14:12:32 
     */
    @ApiOperation(value = "banner配置-id查询", notes = "banner配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppBanner appBanner = appBannerService.get(id);
        if (appBanner != null) {
            jsonResult.setObj(appBanner);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> banner配置-添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-05-09 14:12:32 
     */
    @ApiOperation(value = "banner配置-添加", notes = "banner配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (AppBanner appBanner) {
        JsonResult jsonResult = new JsonResult();
        appBannerService.save(appBanner);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> banner配置-修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-05-09 14:12:32 
     */
    @ApiOperation(value = "banner配置-修改", notes = "banner配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (AppBanner appBanner) {
        JsonResult jsonResult = new JsonResult();
        appBannerService.update(appBanner);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> banner配置-id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-05-09 14:12:32 
     */
    @ApiOperation(value = "banner配置-id删除", notes = "banner配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appBannerService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> banner配置-分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-05-09 14:12:32 
     */
    @ApiOperation(value = "banner配置-分页查询", notes = "banner配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppBanner.class, request);
        return appBannerService.findPageResult(filter);
    }


    /**
     * 查询指定key的数据
     * */
    @GetMapping("/findBanner")
    @ApiOperation(value = "获取指定区域banner图", notes = "获取指定区域banner图")
    @UnAuthentication
    public JsonResult findBanner( @ApiParam(name = "bannerKey", value = "banner类型key", required = true) @RequestParam String bannerKey) {
        QueryFilter filter = new QueryFilter(AppBanner.class);
        filter.addFilter("bannerKey=", bannerKey);
        filter.addFilter("bannerType=", 0);
        List<AppBanner> appBannerList = appBannerService.find(filter);
        return new JsonResult(true).setObj(appBannerList);
    }

}
