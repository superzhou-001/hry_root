/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:21:18 
 */
package hry.rtcvideo.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.rtcvideo.model.RtcPushRecord;
import hry.rtcvideo.service.RtcPushRecordService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> RtcPushRecordController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:21:18 
 */
@Api(value = "音视频录制", tags = "音视频录制", description = "音视频录制")
@RestController
@RequestMapping("/rtcpushrecord")
public class RtcPushRecordController extends BaseController {

	@Autowired
	private RtcPushRecordService rtcPushRecordService;

	/**
     * <p> 音视频录制-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:21:18 
     */
    @ApiOperation(value = "音视频录制-id查询", notes = "音视频录制-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		RtcPushRecord rtcPushRecord = rtcPushRecordService.get(id);
        if (rtcPushRecord != null) {
            jsonResult.setObj(rtcPushRecord);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 音视频录制-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:21:18 
     */
    @ApiOperation(value = "音视频录制-添加", notes = "音视频录制-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (RtcPushRecord rtcPushRecord) {
        JsonResult jsonResult = new JsonResult();
        rtcPushRecordService.save(rtcPushRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频录制-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:21:18 
     */
    @ApiOperation(value = "音视频录制-修改", notes = "音视频录制-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (RtcPushRecord rtcPushRecord) {
        JsonResult jsonResult = new JsonResult();
        rtcPushRecordService.update(rtcPushRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频录制-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:21:18 
     */
    @ApiOperation(value = "音视频录制-id删除", notes = "音视频录制-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        rtcPushRecordService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频录制-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:21:18 
     */
    @ApiOperation(value = "音视频录制-分页查询", notes = "音视频录制-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(RtcPushRecord.class, request);
        return rtcPushRecordService.findPageResult(filter);
    }

}
