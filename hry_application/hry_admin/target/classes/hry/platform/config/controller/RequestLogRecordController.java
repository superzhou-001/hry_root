/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-13 17:30:46 
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.RequestLogRecord;
import hry.platform.config.service.RequestLogRecordService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> RequestLogRecordController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-13 17:30:46 
 */
@Api(value = "请求日志记录", tags = "请求日志记录", description = "请求日志记录")
@RestController
@RequestMapping("/config/requestlogrecord")
public class RequestLogRecordController extends BaseController {

	@Autowired
	private RequestLogRecordService requestLogRecordService;

	/**
     * <p> 请求日志记录-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-13 17:30:46 
     */
    @ApiOperation(value = "请求日志记录-id查询", notes = "请求日志记录-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		RequestLogRecord requestLogRecord = requestLogRecordService.get(id);
        if (requestLogRecord != null) {
            jsonResult.setObj(requestLogRecord);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 请求日志记录-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-13 17:30:46 
     */
    @ApiOperation(value = "请求日志记录-添加", notes = "请求日志记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (RequestLogRecord requestLogRecord) {
        JsonResult jsonResult = new JsonResult();
        requestLogRecordService.save(requestLogRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 请求日志记录-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-13 17:30:46 
     */
    @ApiOperation(value = "请求日志记录-修改", notes = "请求日志记录-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (RequestLogRecord requestLogRecord) {
        JsonResult jsonResult = new JsonResult();
        requestLogRecordService.update(requestLogRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 请求日志记录-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-13 17:30:46 
     */
    @ApiOperation(value = "请求日志记录-id删除", notes = "请求日志记录-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        requestLogRecordService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 请求日志记录-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-13 17:30:46 
     */
    @ApiOperation(value = "请求日志记录-分页查询", notes = "请求日志记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(RequestLogRecord.class, request);
        return requestLogRecordService.findPageResultToEs(filter);
    }

}
