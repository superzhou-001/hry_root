/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.RedeemRecord;
import hry.scm.project.service.RedeemRecordService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.HttpServletRequestUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> RedeemRecordController </p>
 *
 * @author: luyue
 * @Date: 2020-07-21 16:41:25 
 */
@Api(value = "赎货记录管理", tags = "赎货记录管理", description = "赎货记录管理")
@RestController
@RequestMapping("/project/redeemrecord")
public class RedeemRecordController extends BaseController {

	@Autowired
	private RedeemRecordService redeemRecordService;

	/**
     * <p> 赎货记录管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25 
     */
    @ApiOperation(value = "赎货记录管理-id查询", notes = "赎货记录管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		RedeemRecord redeemRecord = redeemRecordService.get(id);
        if (redeemRecord != null) {
            jsonResult.setObj(redeemRecord);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 赎货记录管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25 
     *//*
    @ApiOperation(value = "赎货记录管理-添加", notes = "赎货记录管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (RedeemRecord redeemRecord) {
        JsonResult jsonResult = new JsonResult();
        redeemRecordService.save(redeemRecord);
        return jsonResult.setSuccess(true);
    }

    *//**
     * <p> 赎货记录管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25 
     *//*
    @ApiOperation(value = "赎货记录管理-修改", notes = "赎货记录管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (RedeemRecord redeemRecord) {
        JsonResult jsonResult = new JsonResult();
        redeemRecordService.update(redeemRecord);
        return jsonResult.setSuccess(true);
    }

    *//**
     * <p> 赎货记录管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25 
     *//*
    @ApiOperation(value = "赎货记录管理-id删除", notes = "赎货记录管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        redeemRecordService.delete(id);
        return jsonResult.setSuccess(true);
    }
*/
    /**
     * <p> 赎货记录管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25 
     */
    @ApiOperation(value = "赎货记录管理-分页查询", notes = "赎货记录管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(RedeemRecord.class, request);
        filter.setOrderby("id desc");
        return redeemRecordService.findPageResult(filter);
    }

    /**
     * <p> 解除质押物-订单详情</p>
     *
     * @author: luyue
     * @Date: 2020-07-24 16:18:30
     */
    @ApiOperation(value = "解除质押物-订单详情 ", notes = "解除质押物-订单详情 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/recordDetail")
    public JsonResult recordDetail (
            @ApiParam(name = "redeemId", value = "选中订单id", required = true) @RequestParam String redeemId,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("reType","1");//后台请求
        return redeemRecordService.recordDetail(params);
    }

    /**
     * <p> 还款赎货申请-解除质押提交 </p>
     *
     * @author: luyue
     * @Date: 2020-07-23 15:18:16
     */
    @ApiOperation(value = "还款赎货申请详-解除质押提交", notes = "还款赎货申请-解除质押提交")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/confirmRedeem")
    public JsonResult confirmRedeem (
            @ApiParam(name = "redeemId", value = "赎货记录id", required = true) @RequestParam String redeemId,
            @ApiParam(name = "opinion", value = "处理意见1同意2拒绝", required = true) @RequestParam String opinion,
            HttpServletRequest request) {
        JsonResult jsonResult =null;
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("reType","1");//后台请求
        jsonResult=redeemRecordService.nextStep(params);
        return jsonResult;
    }

}
