/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-14 14:16:27 
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.AppLoginLog;
import hry.platform.newuser.service.AppLoginLogService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppLoginLogController </p>
 *
 * @author: zhouming
 * @Date: 2020-08-14 14:16:27 
 */
@Api(value = "用户登录日志 ", tags = "用户登录日志 ", description = "用户登录日志 ")
@RestController
@RequestMapping("/newuser/apploginlog")
public class AppLoginLogController extends BaseController {

	@Autowired
	private AppLoginLogService appLoginLogService;

    /**
     * <p> 用户登录日志 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-14 14:16:27 
     */
    @ApiOperation(value = "用户登录日志 -分页查询", notes = "用户登录日志 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppLoginLog.class, request);
        return appLoginLogService.findLoginLogPageToES(filter);
    }

}
