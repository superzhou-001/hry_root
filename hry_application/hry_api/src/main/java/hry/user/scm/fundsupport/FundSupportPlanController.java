/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:37:09 
 */
package hry.user.scm.fundsupport;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.fundsupport.model.FundSupportPlan;
import hry.scm.fundsupport.service.FundSupportPlanService;
import hry.security.jwt.JWTContext;
import hry.security.logger.ControllerLogger;
import hry.user.scm.access.RoleAccess;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> FundSupportPlanController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:37:09 
 */
@Api(value = "资金方案", tags = "资金方案", description = "资金方案")
@RestController
@RequestMapping("/fundsupportplan")
public class FundSupportPlanController extends BaseController {

	@Autowired
	private FundSupportPlanService fundSupportPlanService;


    /**
     * <p> 资金方案-list查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09
     */
    @ApiOperation(value = "资金方案-list查询", notes = "资金方案-list查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public List<FundSupportPlan> list (
            @ApiParam(name = "fundSupportId", value = "资金方Id", required = true) @RequestParam String fundSupportId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupportPlan.class, request);
        filter.addFilter("fundSupportId=",fundSupportId);
        filter.addFilter("isEnable=",2);
        return fundSupportPlanService.find(filter);
    }
	/**
     * <p> 资金方案-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09 
     */
    @ApiOperation(value = "资金方案-id查询", notes = "资金方案-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FundSupportPlan fundSupportPlan = fundSupportPlanService.get(id);
        if (fundSupportPlan != null) {
            jsonResult.setObj(fundSupportPlan);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 资金方案-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09 
     */
    @RoleAccess(roleType= "fundSupport")
    @ApiOperation(value = "资金方案-添加", notes = "资金方案-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FundSupportPlan fundSupportPlan) {
        JsonResult jsonResult = new JsonResult();
        fundSupportPlanService.save(fundSupportPlan);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方案-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09 
     */
    @RoleAccess(roleType= "fundSupport")
    @ApiOperation(value = "资金方案-修改", notes = "资金方案-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FundSupportPlan fundSupportPlan) {
        JsonResult jsonResult = new JsonResult();
        fundSupportPlanService.update(fundSupportPlan);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方案-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09 
     */
    @ApiOperation(value = "资金方案-id删除", notes = "资金方案-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        FundSupportPlan fundSupportPlan = fundSupportPlanService.get(id);
        fundSupportPlan.setIsEnable(1);
        fundSupportPlanService.update(fundSupportPlan);
        //fundSupportPlanService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方案-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:37:09 
     */
    @ApiOperation(value = "资金方案-分页查询", notes = "资金方案-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByPage")
    public PageResult listByPage (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupportPlan.class, request);
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Long fundSupportId = user.getInfoId();
        filter.addFilter("isEnable=",2);
        filter.addFilter("fundSupportId=",fundSupportId);
        return fundSupportPlanService.findPageResult(filter);
    }

}
