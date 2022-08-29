/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:34:08 
 */
package hry.scm.fundsupport.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.fundsupport.model.FundSupportPlan;
import hry.scm.fundsupport.service.FundSupportPlanService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FundSupportPlanController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:34:08 
 */
@Api(value = "资金方案", tags = "资金方案", description = "资金方案")
@RestController
@RequestMapping("/fundsupport/fundsupportplan")
public class FundSupportPlanController extends BaseController {

	@Autowired
	private FundSupportPlanService fundSupportPlanService;

	/**
     * <p> 资金方案-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:34:08 
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
     * @Date: 2020-07-08 14:34:08 
     */
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
     * @Date: 2020-07-08 14:34:08 
     */
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
     * @Date: 2020-07-08 14:34:08 
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
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方案-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:34:08 
     */
    @ApiOperation(value = "资金方案-分页查询", notes = "资金方案-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "fundSupportId", value = "每页条数", required = true) @RequestParam long fundSupportId,

            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupportPlan.class, request);
        filter.addFilter("isEnable=",2);
        filter.addFilter("fundSupportId=",fundSupportId);
        return fundSupportPlanService.findPageResult(filter);
    }

}
