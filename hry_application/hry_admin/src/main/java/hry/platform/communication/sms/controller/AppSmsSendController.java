/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:27:23
 */
package hry.platform.communication.sms.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.sms.model.AppSmsSend;
import hry.platform.communication.sms.service.AppSmsSendService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppSmsSendController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:27:23
 */
@Api(value = "短信发送记录", tags = "短信发送记录", description = "短信发送记录")
@RestController
@RequestMapping("/communication/appsmssend")
public class AppSmsSendController extends BaseController {

	@Autowired
	private AppSmsSendService appSmsSendService;

	/**
     * <p> 短信发送记录-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:27:23
     */
    @ApiOperation(value = "短信发送记录-id查询", notes = "短信发送记录-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppSmsSend appSmsSend = appSmsSendService.get(id);
        jsonResult.setObj(appSmsSend);
        return jsonResult.setSuccess(true);
    }

	/**
     * <p> 短信发送记录-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:27:23
     */
    @ApiOperation(value = "短信发送记录-添加", notes = "短信发送记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppSmsSend appSmsSend) {
        JsonResult jsonResult = new JsonResult();
        appSmsSendService.save(appSmsSend);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信发送记录-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:27:23
     */
    @ApiOperation(value = "短信发送记录-修改", notes = "短信发送记录-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppSmsSend appSmsSend) {
        JsonResult jsonResult = new JsonResult();
        appSmsSendService.update(appSmsSend);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信发送记录-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:27:23
     */
    @ApiOperation(value = "短信发送记录-id删除", notes = "短信发送记录-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appSmsSendService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信发送记录-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:27:23
     */
    @ApiOperation(value = "短信发送记录-分页查询", notes = "短信发送记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppSmsSend.class, request);
        filter.setOrderby("id desc");
        return appSmsSendService.findPageResult(filter);
    }

}
