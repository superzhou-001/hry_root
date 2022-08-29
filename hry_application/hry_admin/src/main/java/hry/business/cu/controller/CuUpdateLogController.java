/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 10:19:39 
 */
package hry.business.cu.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuUpdateLog;
import hry.business.cu.service.CuUpdateLogService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CuUpdateLogController </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 10:19:39 
 */
@Api(value = "信息修改记录", tags = "信息修改记录", description = "信息修改记录")
@RestController
@RequestMapping("/cu/cuupdatelog")
public class CuUpdateLogController extends BaseController {

	@Autowired
	private CuUpdateLogService cuUpdateLogService;

	/**
     * <p> 信息修改记录-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-26 10:19:39 
     */
    @ApiOperation(value = "信息修改记录-id查询", notes = "信息修改记录-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CuUpdateLog cuUpdateLog = cuUpdateLogService.get(id);
        if (cuUpdateLog != null) {
            jsonResult.setObj(cuUpdateLog);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 信息修改记录-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-26 10:19:39 
     */
    @ApiOperation(value = "信息修改记录-添加", notes = "信息修改记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CuUpdateLog cuUpdateLog) {
        JsonResult jsonResult = new JsonResult();
        cuUpdateLogService.save(cuUpdateLog);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 信息修改记录-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-26 10:19:39 
     */
    @ApiOperation(value = "信息修改记录-修改", notes = "信息修改记录-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CuUpdateLog cuUpdateLog) {
        JsonResult jsonResult = new JsonResult();
        cuUpdateLogService.update(cuUpdateLog);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 信息修改记录-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-26 10:19:39 
     */
    @ApiOperation(value = "信息修改记录-id删除", notes = "信息修改记录-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuUpdateLogService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 信息修改记录-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-26 10:19:39 
     */
    @ApiOperation(value = "信息修改记录-分页查询", notes = "信息修改记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "cuIntentionId", value = "意向客户Id", required = false) @RequestParam Long cuIntentionId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuUpdateLog.class, request);
        if(!StringUtils.isEmpty(cuIntentionId)){
            filter.addFilter("tableId=", cuIntentionId);
        }
        return cuUpdateLogService.findPageResult(filter);
    }

}
