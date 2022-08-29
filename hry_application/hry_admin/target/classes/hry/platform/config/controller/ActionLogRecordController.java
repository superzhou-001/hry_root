package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.ActionLogRecord;
import hry.platform.config.service.ActionLogRecordService;
import hry.security.logger.ControllerLogger;
import hry.util.httpRequest.IpUtil;
import hry.util.httpRequest.UserAgentUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouming
 * @date 2020/5/9 18:15
 * @Version 1.0
 */
@Api(value = "用户行为日志记录", tags = "用户行为日志记录", description = "用户行为日志记录")
@RestController
@RequestMapping("/config/actionlogrecord")
public class ActionLogRecordController{

    @Autowired
    private ActionLogRecordService actionLogRecordService;

    @ApiOperation(value = "行为日志记录-添加", notes = "行为日志记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (HttpServletRequest request, ActionLogRecord actionLogRecord) {
        JsonResult jsonResult = new JsonResult();
        // 获取请求ip
        String ip = IpUtil.getIp(request);
        // 请求端口
        int port = request.getRemotePort();
        // 请求来源
        String source = UserAgentUtils.getOs(request) + " " + UserAgentUtils.getBrowserName(request);
        actionLogRecord.setIp(ip);
        actionLogRecord.setPort((long)port);
        actionLogRecord.setSource(source);
        actionLogRecordService.saveToEs(actionLogRecord);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "行为日志记录-分页查询", notes = "行为日志记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ActionLogRecord.class, request);
        return actionLogRecordService.findPageResultToEs(filter);
    }

    @ApiOperation(value = "常用功能", notes = "常用功能")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/commonFunction")
    public JsonResult commonFunction() {
        return actionLogRecordService.commonFunction();
    }

    @ApiOperation(value = "最近操作", notes = "最近操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/recentHandle")
    public JsonResult recentHandle() {
        return actionLogRecordService.recentHandle();
    }

}
