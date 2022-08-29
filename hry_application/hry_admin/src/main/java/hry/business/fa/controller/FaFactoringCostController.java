/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 09:52:22 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFactoringCost;
import hry.business.fa.model.FaFactoringFlow;
import hry.business.fa.service.FaFactoringCostService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p> FaFactoringCostController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 09:52:22 
 */
@Api(value = "保理流程费用信息", tags = "保理流程费用信息", description = "保理流程费用信息")
@RestController
@RequestMapping("/fa/fafactoringcost")
public class FaFactoringCostController extends BaseController {

	@Autowired
	private FaFactoringCostService faFactoringCostService;

	/**
     * <p> 保理流程费用信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 09:52:22 
     */
    @ApiOperation(value = "保理流程费用信息-id查询", notes = "保理流程费用信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaFactoringCost faFactoringCost = faFactoringCostService.get(id);
        if (faFactoringCost != null) {
            jsonResult.setObj(faFactoringCost);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 保理流程费用信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 09:52:22 
     */
    @ApiOperation(value = "保理流程费用信息-添加", notes = "保理流程费用信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaFactoringCost faFactoringCost) {
        JsonResult jsonResult = new JsonResult();
        faFactoringCostService.save(faFactoringCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费用信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 09:52:22 
     */
    @ApiOperation(value = "保理流程费用信息-修改", notes = "保理流程费用信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaFactoringCost faFactoringCost) {
        JsonResult jsonResult = new JsonResult();
        faFactoringCostService.update(faFactoringCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费用信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 09:52:22 
     */
    @ApiOperation(value = "保理流程费用信息-id删除", notes = "保理流程费用信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faFactoringCostService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费用信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 09:52:22 
     */
    @ApiOperation(value = "保理流程费用信息-分页查询", notes = "保理流程费用信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringCost.class, request);
        return faFactoringCostService.findPageBySql(filter);
    }

    @ApiOperation(value = "保理流程费用信息-根据projectId查询", notes = "保理流程费用信息-根据projectId查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByProject")
    public JsonResult listByProject (
            @ApiParam(name = "projectId", value = "项目Id", required = true) @RequestParam Long projectId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringCost.class, request);
        filter.addFilter("projectId=" , projectId);
        return new JsonResult().setSuccess(true).setObj(faFactoringCostService.find(filter));
    }

    @ApiOperation(value = "保理流程费用信息-修改状态", notes = "保理流程费用信息-修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateStatus")
    public JsonResult updateLoanConfirmStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "费用状态 1流程中 2待核实 3已核实 4已否决", required = true) @RequestParam Integer status,
            @ApiParam(name = "remark", value = "备注", required = true) @RequestParam String remark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(status)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        FaFactoringCost faFactoringCost = faFactoringCostService.get(id);
        if (faFactoringCost != null) {
            FaFactoringCost faFactoringCostNew = new FaFactoringCost();
            faFactoringCostNew.setStatus(status);
            faFactoringCostNew.setId(id);
            faFactoringCostNew.setRemark(remark);
            faFactoringCostService.update(faFactoringCostNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

}
