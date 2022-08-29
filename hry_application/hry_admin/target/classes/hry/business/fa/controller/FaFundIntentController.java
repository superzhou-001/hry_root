/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-22 13:45:22 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFundIntent;
import hry.business.fa.service.FaFundIntentService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> FaFundIntentController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-22 13:45:22 
 */
@Api(value = "还款计划相关操作 ", tags = "还款计划相关操作 ", description = "还款计划相关操作 ")
@RestController
@RequestMapping("/fa/fafundintent")
public class FaFundIntentController extends BaseController {

	@Autowired
	private FaFundIntentService faFundIntentService;

    /**
     * <p> 还款计划 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-22 13:45:22 
     */
    @ApiOperation(value = "还款计划 -分页查询", notes = "还款计划 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/refundList")
    public PageResult refundList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "type", value = "1 正常还款待确认 2 逾期还款待确认", required = true) @RequestParam String type,
            @ApiParam(name = "projectCode", value = "项目编号", required = false) @RequestParam String projectCode,
            @ApiParam(name = "sellEnterpriseName", value = "卖方企业名称", required = false) @RequestParam String sellEnterpriseName,
            @ApiParam(name = "projectManagerId", value = "项目经理Id", required = false) @RequestParam String projectManagerId,
            @ApiParam(name = "startTime", value = "还款开始时间", required = false) @RequestParam String startTime,
            @ApiParam(name = "endTime", value = "还款结束时间", required = false) @RequestParam String endTime,
            @ApiParam(name = "status", value = "还款状态 1 正常待还、2 已逾期、3 待核实、4 已驳回、5 已还款", required = true) @RequestParam String status,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFundIntent.class, request);
        return faFundIntentService.findPageFundIntent(filter);
    }


    /**
     * <p> 还款计划表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-22 13:45:22
     */
    @ApiOperation(value = "还款计划 -修改", notes = "还款计划表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateStatus")
    public JsonResult updateStatus (@ApiParam(name = "factoringId", value = "保理id", required = true) @RequestParam String factoringId,
                                    @ApiParam(name = "payintentPeriod", value = "当前期数", required = true) @RequestParam String payintentPeriod,
                                    @ApiParam(name = "status", value = "状态", required = true) @RequestParam String status,
                                    @ApiParam(name = "refundImage", value = "还款凭证", required = false) @RequestParam String refundImage,
                                    @ApiParam(name = "refundDate", value = "还款日期", required = true) @RequestParam String refundDate,
                                    @ApiParam(name = "remark", value = "还款备注", required = true) @RequestParam String remark,
                                    HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        Map<String, String> param = new HashMap<>();
        param.put("factoringId", factoringId);
        param.put("payintentPeriod", payintentPeriod);
        param.put("status", status);
        param.put("refundImage", refundImage);
        param.put("refundDate", refundDate);
        param.put("remark", remark);
        return faFundIntentService.updateStatus(param);
    }

    /**
     * <p> 还款计划表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-22 13:45:22
     */
    @ApiOperation(value = "还款计划 -查看期数费用", notes = "还款计划表 -查看期数费用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findRefundCostList")
    public JsonResult findRefundCostList (@ApiParam(name = "factoringId", value = "保理id", required = true) @RequestParam String factoringId,
                                    @ApiParam(name = "payintentPeriod", value = "当前期数", required = true) @RequestParam String payintentPeriod,
                                    HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFundIntent.class);
        filter.addFilter("factoringId=", factoringId);
        filter.addFilter("payintentPeriod=", payintentPeriod);
        filter.addFilter("fundType !=","principalLending");
        List<FaFundIntent> intentList = faFundIntentService.find(filter);
        return new JsonResult(true).setObj(intentList);
    }

}
