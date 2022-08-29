/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 17:01:01 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFactoringFlow;
import hry.business.fa.service.FaFactoringFlowService;
import hry.business.fa.service.FaFundIntentService;
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
 * <p> FaFactoringFlowController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 17:01:01 
 */
@Api(value = "保理信息", tags = "保理信息", description = "保理信息")
@RestController
@RequestMapping("/fa/fafactoringflow")
public class FaFactoringFlowController extends BaseController {

	@Autowired
	private FaFactoringFlowService faFactoringFlowService;
	@Autowired
    private FaFundIntentService faFundIntentService;

	/**
     * <p> 保理信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 17:01:01 
     */
    @ApiOperation(value = "保理信息-id查询", notes = "保理信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        return faFactoringFlowService.getFaFactoringFlowInfo(id);
    }

    /**
     * <p> 保理信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 17:01:01 
     */
    @ApiOperation(value = "保理信息-分页查询", notes = "保理信息-分页查询",response = FaFactoringFlow.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringFlow.class, request);
        return faFactoringFlowService.findPageResult(filter);
    }

    @ApiOperation(value = "保理信息-修改项目阶段", notes = "保理信息-修改项目阶段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateProjectStageStatus")
    public JsonResult updateProjectStageStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "projectStageStatus", value = "项目阶段 1审批流程中 2审批流程通过 3放款审批", required = true) @RequestParam Integer projectStageStatus,
            @ApiParam(name = "projectStageStatusRemark", value = "项目阶段备注", required = true) @RequestParam String projectStageStatusRemark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(projectStageStatus)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        FaFactoringFlow faFactoringFlow = faFactoringFlowService.get(id);
        if (faFactoringFlow != null) {
            FaFactoringFlow faFactoringFlowNew = new FaFactoringFlow();
            faFactoringFlowNew.setProjectStageStatus(projectStageStatus);
            faFactoringFlowNew.setId(id);
            faFactoringFlowNew.setProjectStageStatusDate(new Date());
            faFactoringFlowNew.setProjectStageStatusRemark(projectStageStatusRemark);
            //如果修改的状态是放款审批，则将放款审核状态改为2
            if(projectStageStatus==3){
                faFactoringFlowNew.setLoanExamineStatus(2);
            }
            faFactoringFlowService.update(faFactoringFlowNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "保理信息-修改放款审核状态", notes = "保理信息-修改放款审核状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateLoanExamineStatus")
    public JsonResult updateLoanExamineStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "loanExamineStatus", value = "放款审核状态 1无状态 2待审核 3已审核 4已驳回", required = true) @RequestParam Integer loanExamineStatus,
            @ApiParam(name = "loanExamineStatusRemark", value = "放款审核状态备注", required = true) @RequestParam String loanExamineStatusRemark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(loanExamineStatus)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        FaFactoringFlow faFactoringFlow = faFactoringFlowService.get(id);
        if (faFactoringFlow != null) {
            FaFactoringFlow faFactoringFlowNew = new FaFactoringFlow();
            faFactoringFlowNew.setLoanExamineStatus(loanExamineStatus);
            faFactoringFlowNew.setId(id);
            faFactoringFlowNew.setLoanExamineStatusDate(new Date());
            faFactoringFlowNew.setLoanExamineStatusRemark(loanExamineStatusRemark);
            //如果修改的状态是已审核，则将放款确认状态2
            if(loanExamineStatus==3){
                faFactoringFlowNew.setLoanConfirmStatus(2);
            }
            faFactoringFlowService.update(faFactoringFlowNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "保理信息-修改放款确认状态", notes = "保理信息-修改放款确认状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateLoanConfirmStatus")
    public JsonResult updateLoanConfirmStatus(
            @ApiParam(name = "id", value = "保理id", required = true) @RequestParam Long id,
            @ApiParam(name = "loanConfirmStatus", value = "放款确认状态 1无状态 2待确认 3已确认 ", required = true) @RequestParam Integer loanConfirmStatus,
            @ApiParam(name = "loanConfirmStatusRemark", value = "放款确认状态备注", required = true) @RequestParam String loanConfirmStatusRemark,
            @ApiParam(name = "faFundIntentListJson", value = "还款计划json串", required = true) @RequestParam String faFundIntentListJson
            ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(loanConfirmStatus)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        FaFactoringFlow faFactoringFlow = faFactoringFlowService.get(id);
        if (faFactoringFlow != null) {
            FaFactoringFlow faFactoringFlowNew = new FaFactoringFlow();
            faFactoringFlowNew.setLoanConfirmStatus(loanConfirmStatus);
            faFactoringFlowNew.setId(id);
            faFactoringFlowNew.setLoanConfirmDate(new Date());
            faFactoringFlowNew.setLoanConfirmStatusRemark(loanConfirmStatusRemark);
            faFactoringFlowService.update(faFactoringFlowNew);
            // 保存还款计划
            faFundIntentService.saveFundList(id, faFundIntentListJson);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }




}
