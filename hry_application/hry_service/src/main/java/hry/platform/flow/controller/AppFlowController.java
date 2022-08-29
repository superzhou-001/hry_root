/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-28 12:05:59 
 */
package hry.platform.flow.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.flow.model.AppFlow;
import hry.platform.flow.service.AppFlowService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppFlowController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-28 12:05:59 
 */
@Api(value = "通用流程", tags = "通用流程", description = "通用流程")
@RestController
@RequestMapping("/flow/appflow")
public class AppFlowController extends BaseController {

	@Autowired
	private AppFlowService appFlowService;

	/**
     * <p> 通用流程-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-28 12:05:59 
     */
    @ApiOperation(value = "通用流程-id查询", notes = "通用流程-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppFlow appFlow = appFlowService.get(id);
        if (appFlow != null) {
            jsonResult.setObj(appFlow);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 通用流程-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-28 12:05:59 
     */
    @ApiOperation(value = "通用流程-添加", notes = "通用流程-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (AppFlow appFlow) {
        JsonResult jsonResult = new JsonResult();
        appFlowService.save(appFlow);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 通用流程-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-28 12:05:59 
     */
    @ApiOperation(value = "通用流程-修改", notes = "通用流程-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (AppFlow appFlow) {
        JsonResult jsonResult = new JsonResult();
        appFlowService.update(appFlow);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 通用流程-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-28 12:05:59 
     */
    @ApiOperation(value = "通用流程-id删除", notes = "通用流程-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appFlowService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 通用流程-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-28 12:05:59 
     */
    @ApiOperation(value = "通用流程-分页查询", notes = "通用流程-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppFlow.class, request);
        return appFlowService.findPageResult(filter);
    }

}
