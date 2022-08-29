/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
 */
package hry.user.scm.project;

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
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        filter.addFilter("fundSupportId=",user.getInfoId());
        filter.setOrderby("id desc");
        return redeemRecordService.findPageResult(filter);
    }


    /**
     * <p> 还款赎货申请-输入还款日期计算金额 </p>
     *
     * @author: luyue
     * @Date: 2020-07-21 16:41:25
     */
    @ApiOperation(value = "还款赎货申请-输入还款日期计算金额", notes = "还款赎货申请-输入还款日期计算金额")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/calculateFee")
    public JsonResult calculateFee (
            @ApiParam(name = "projectId", value = "订单id", required = true) @RequestParam String projectId,
            @ApiParam(name = "payDate", value = "还款日期", required = true) @RequestParam String payDate,
            @ApiParam(name = "goodsWorth", value = "赎货总价值", required = true) @RequestParam String goodsWorth,
            HttpServletRequest request) {
           JsonResult jsonResult =null;
           Map<String, String> params = HttpServletRequestUtils.getParams(request);
           jsonResult=redeemRecordService.calculateFee(params);
           return jsonResult;
    }

    /**
     * <p> 还款赎货申请-提交申请 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 16:53:16
     */
    @ApiOperation(value = "还款赎货申请-提交申请", notes = "还款赎货申请-提交申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/apply")
    public JsonResult apply (
            @ApiParam(name = "projectId", value = "订单id", required = true) @RequestParam String projectId,
            @ApiParam(name = "payDate", value = "还款日期", required = true) @RequestParam String payDate,
            @ApiParam(name = "jsonStr", value = "质押物清单json", required = true) @RequestParam String jsonStr,
            @ApiParam(name = "payImageUrl", value = "还款凭证照片路径", required = true) @RequestParam String payImageUrl,
            HttpServletRequest request) {
        JsonResult jsonResult =null;
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("userId",user.getId().toString());//申请账号id
       // params.put("userId","28");//申请账号id
        jsonResult=redeemRecordService.apply(params);
        return jsonResult;
    }

    /**
     * <p> 还款赎货申请-确认收款 </p>
     *
     * @author: luyue
     * @Date: 2020-07-23 15:18:16
     */
    @ApiOperation(value = "还款赎货申请-确认收款", notes = "还款赎货申请-确认收款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @UnAuthentication
    @PostMapping(value = "/confirmReceive")
    public JsonResult confirmReceive (
            @ApiParam(name = "redeemId", value = "赎货记录id", required = true) @RequestParam String redeemId,
            @ApiParam(name = "opinion", value = "处理意见1同意2拒绝", required = true) @RequestParam String opinion,
            HttpServletRequest request) {
        JsonResult jsonResult =null;
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("userId",user.getId().toString());//申请账号id
        params.put("username",user.getMobile());
    /*    params.put("userId","28");//申请账号id
        params.put("username","张三");*/
        params.put("reType","2");//前台请求
        jsonResult=redeemRecordService.nextStep(params);
        return jsonResult;
    }

    /**
     * <p> 解除质押物-资方确认收款</p>
     *
     * @author: luyue
     * @Date: 2020-07-24 16:18:30
     */
    @ApiOperation(value = "解除质押物-资方确认收款详情页 ", notes = "解除质押物-资方确认收款详情页 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @UnAuthentication
    @PostMapping(value = "/recordDetail")
    public JsonResult recordDetail (
            @ApiParam(name = "redeemId", value = "选中订单id", required = true) @RequestParam String redeemId,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("reType","2");//前台请求

        return redeemRecordService.recordDetail(params);
    }
}
