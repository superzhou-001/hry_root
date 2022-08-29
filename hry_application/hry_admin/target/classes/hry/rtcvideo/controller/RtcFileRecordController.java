/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-01 15:24:59 
 */
package hry.rtcvideo.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.rtcvideo.model.RtcFileRecord;
import hry.rtcvideo.service.RtcFileRecordService;
import hry.security.logger.ControllerLogger;
import hry.util.upload.FileUploadContext;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> RtcFileRecordController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-01 15:24:59 
 */
@Api(value = "音视频文件", tags = "音视频文件", description = "音视频文件")
@RestController
@RequestMapping("/rtcfilerecord")
public class RtcFileRecordController extends BaseController {

	@Autowired
	private RtcFileRecordService rtcFileRecordService;

    @Autowired
    private FileUploadContext fileUploadContext;

    /**
     * <p> 文件地址获取-阿里云</p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "文件地址获取-阿里云，返回文件上传路径，多个逗号隔开", notes = "文件地址获取-阿里云，返回文件上传路径")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/getOssUploadUrl")
    public JsonResult getOssUploadUrl (
            @ApiParam(name = "filePath", value = "文件路径", required = true) @RequestParam("filePath") String filePath,
            @ApiParam(name = "isPrivate", value = "是否私有加密", required = true) @RequestParam("isPrivate") boolean isPrivate) {
        // 这里使用阿里云的上传方式，如需其他上传方式，修改第三个参数即可
        String fileUrl = fileUploadContext.getFileUrl("ossRtcFileStrategy", filePath, isPrivate);
        return new JsonResult(true).setObj(fileUrl).setMsg("获取成功");
    }



    /**
     * <p> 音视频文件-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:24:59 
     */
    @ApiOperation(value = "音视频文件-id查询", notes = "音视频文件-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		RtcFileRecord rtcFileRecord = rtcFileRecordService.get(id);
        if (rtcFileRecord != null) {
            jsonResult.setObj(rtcFileRecord);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 音视频文件-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:24:59 
     */
    @ApiOperation(value = "音视频文件-添加", notes = "音视频文件-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (RtcFileRecord rtcFileRecord) {
        JsonResult jsonResult = new JsonResult();
        rtcFileRecordService.save(rtcFileRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频文件-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:24:59 
     */
    @ApiOperation(value = "音视频文件-修改", notes = "音视频文件-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (RtcFileRecord rtcFileRecord) {
        JsonResult jsonResult = new JsonResult();
        rtcFileRecordService.update(rtcFileRecord);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频文件-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:24:59 
     */
    @ApiOperation(value = "音视频文件-id删除", notes = "音视频文件-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        rtcFileRecordService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 音视频文件-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-01 15:24:59 
     */
    @ApiOperation(value = "音视频文件-分页查询", notes = "音视频文件-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(RtcFileRecord.class, request);
        return rtcFileRecordService.findPageResult(filter);
    }

}
