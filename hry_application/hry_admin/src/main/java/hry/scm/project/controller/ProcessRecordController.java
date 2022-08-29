/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-15 11:58:19 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.ProcessRecord;
import hry.scm.project.service.ProcessRecordService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ProcessRecordController </p>
 *
 * @author: luyue
 * @Date: 2020-07-15 11:58:19 
 */
@Api(value = "订单处理记录管理", tags = "订单处理记录管理", description = "订单处理记录管理")
@RestController
@RequestMapping("/project/processrecord")
public class ProcessRecordController extends BaseController {

	@Autowired
	private ProcessRecordService processRecordService;

	/**
     * <p> 订单处理记录管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 11:58:19 
     */
    @ApiOperation(value = "订单处理记录管理-id查询", notes = "订单处理记录管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProcessRecord processRecord = processRecordService.get(id);
        if (processRecord != null) {
            jsonResult.setObj(processRecord);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 订单处理记录管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 11:58:19 
     */
    @ApiOperation(value = "订单处理记录管理-添加", notes = "订单处理记录管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProcessRecord processRecord) {
        JsonResult jsonResult = new JsonResult();
        processRecordService.save(processRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 订单处理记录管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 11:58:19 
     */
    @ApiOperation(value = "订单处理记录管理-修改", notes = "订单处理记录管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProcessRecord processRecord) {
        JsonResult jsonResult = new JsonResult();
        processRecordService.update(processRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 订单处理记录管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 11:58:19 
     */
    @ApiOperation(value = "订单处理记录管理-id删除", notes = "订单处理记录管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        processRecordService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 订单处理记录管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 11:58:19 
     */
    @ApiOperation(value = "订单处理记录管理-分页查询", notes = "订单处理记录管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProcessRecord.class, request);
        return processRecordService.findPageResult(filter);
    }

}
