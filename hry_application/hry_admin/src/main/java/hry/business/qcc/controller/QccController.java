package hry.business.qcc.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.qcc.model.*;
import hry.business.qcc.service.*;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.service.AppConfigService;
import hry.security.logger.ControllerLogger;
import hry.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */

@Api(value = "企查查", tags = "企查查", description = "企查查")
@RestController
@RequestMapping("/qcc/qccapi")
public class QccController extends BaseController {

    @Autowired
    private CuEnterpriseService enterpriseService;

    @Autowired
    private QccApiService qccApiService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private QccEnterpriseBasicDetailsService qccEnterpriseBasicDetailsService;
    @Autowired
    private QccEnterpriseBranchService qccEnterpriseBranchService;
    @Autowired
    private QccEnterpriseCaseFilingService qccEnterpriseCaseFilingService;
    @Autowired
    private QccEnterpriseChangeService qccEnterpriseChangeService;
    @Autowired
    private QccEnterpriseCourtannouncementService qccEnterpriseCourtannouncementService;
    @Autowired
    private QccEnterpriseCourtnoticeService qccEnterpriseCourtnoticeService;
    @Autowired
    private QccEnterpriseEmployeeService qccEnterpriseEmployeeService;
    @Autowired
    private QccEnterpriseExceptionService qccEnterpriseExceptionService;
    @Autowired
    private QccEnterpriseInvestmentService qccEnterpriseInvestmentService;
    @Autowired
    private QccEnterpriseJudgmentdocService qccEnterpriseJudgmentdocService;
    @Autowired
    private QccEnterpriseJudicialAssistanceService qccEnterpriseJudicialAssistanceService;
    @Autowired
    private QccEnterpriseLegalProcCountService qccEnterpriseLegalProcCountService;
    @Autowired
    private QccEnterpriseOverviewInfoService qccEnterpriseOverviewInfoService;
    @Autowired
    private QccEnterprisePartnerService qccEnterprisePartnerService;
    @Autowired
    private QccEnterpriseZhixingService qccEnterpriseZhixingService;

    @ApiOperation(value = "企查查-企业信息获取", notes = "企查查-企业信息获取")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/refreshQccInfo/{enterpriseId}/{productClassify}")
    public JsonResult refreshBasicsByQcc(
            @ApiParam(name = "enterpriseId", value = "enterpriseId", required = true) @PathVariable Long enterpriseId,
            @ApiParam(name = "productClassify", value = "产品分类 1.工商信息 2.法律诉讼 3.风险识别", required = true) @PathVariable Integer productClassify){
        JsonResult jsonResult = new JsonResult();
        //根据enterpriseId 查询录入的企业信息
        CuEnterprise cuEnterprise = enterpriseService.get(enterpriseId);
        if (cuEnterprise == null) {
            return jsonResult.setSuccess(false).setMsg("录入企业信息不存在!");
        }
        if (!StringUtil.isNull(cuEnterprise.getCreditCode())) {
            return jsonResult.setSuccess(false).setMsg("企业社会统一信用代码不能为空！");
        }

        jsonResult = qccApiService.refreshBasicsByQcc(cuEnterprise.getCreditCode(),enterpriseId,productClassify,cuEnterprise.getName());

        return jsonResult;
    }

    @ApiOperation(value = "企查查-工商信息页面所有列表", notes = "企查查-工商信息页面所有列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/pageAlllist1")
    public JsonResult pageAlllist1 (
            @ApiParam(name = "enterpriseId", value = "enterpriseId", required = true) @RequestParam Long enterpriseId,
            HttpServletRequest request) {
        QueryFilter filter1 = new QueryFilter(QccEnterpriseBasicDetails.class);
        filter1.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseBasicDetails> qccEnterpriseBasicDetails = qccEnterpriseBasicDetailsService.find(filter1);

        QueryFilter filter2 = new QueryFilter(QccEnterpriseException.class);
        filter2.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseException> qccEnterpriseExceptions = qccEnterpriseExceptionService.find(filter2);

        QueryFilter filter3 = new QueryFilter(QccEnterprisePartner.class);
        filter3.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterprisePartner> qccEnterprisePartners = qccEnterprisePartnerService.find(filter3);

        QueryFilter filter4 = new QueryFilter(QccEnterpriseEmployee.class);
        filter4.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseEmployee> qccEnterpriseEmployees = qccEnterpriseEmployeeService.find(filter4);

        QueryFilter filter5 = new QueryFilter(QccEnterpriseInvestment.class);
        filter5.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseInvestment> qccEnterpriseInvestments = qccEnterpriseInvestmentService.find(filter5);

        QueryFilter filter6 = new QueryFilter(QccEnterpriseBranch.class);
        filter6.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseBranch> qccEnterpriseBranches = qccEnterpriseBranchService.find(filter6);

        QueryFilter filter7 = new QueryFilter(QccEnterpriseChange.class);
        filter7.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseChange> qccEnterpriseChanges = qccEnterpriseChangeService.find(filter7);

        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("qccEnterpriseBasicDetails",qccEnterpriseBasicDetails);
        mapResult.put("qccEnterpriseExceptions",qccEnterpriseExceptions);
        mapResult.put("qccEnterprisePartners",qccEnterprisePartners);
        mapResult.put("qccEnterpriseEmployees",qccEnterpriseEmployees);
        mapResult.put("qccEnterpriseInvestments",qccEnterpriseInvestments);
        mapResult.put("qccEnterpriseBranches",qccEnterpriseBranches);
        mapResult.put("qccEnterpriseChanges",qccEnterpriseChanges);

        return new JsonResult().setSuccess(true).setObj(mapResult);
    }


    @ApiOperation(value = "企查查-法律诉讼页面所有列表", notes = "企查查-法律诉讼页面所有列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/pageAlllist2")
    public JsonResult pageAlllist2 (
            @ApiParam(name = "enterpriseId", value = "enterpriseId", required = true) @RequestParam Long enterpriseId,
            HttpServletRequest request) {
        QueryFilter filter1 = new QueryFilter(QccEnterpriseCourtnotice.class);
        filter1.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseCourtnotice> qccEnterpriseCourtnotices = qccEnterpriseCourtnoticeService.find(filter1);

        QueryFilter filter2 = new QueryFilter(QccEnterpriseJudgmentdoc.class);
        filter2.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseJudgmentdoc> qccEnterpriseJudgmentdocs = qccEnterpriseJudgmentdocService.find(filter2);

        QueryFilter filter3 = new QueryFilter(QccEnterpriseCourtannouncement.class);
        filter3.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseCourtannouncement> qccEnterpriseCourtannouncements = qccEnterpriseCourtannouncementService.find(filter3);

        QueryFilter filter4 = new QueryFilter(QccEnterpriseZhixing.class);
        filter4.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseZhixing> qccEnterpriseZhixings = qccEnterpriseZhixingService.find(filter4);

        QueryFilter filter5 = new QueryFilter(QccEnterpriseJudicialAssistance.class);
        filter5.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseJudicialAssistance> qccEnterpriseJudicialAssistances = qccEnterpriseJudicialAssistanceService.find(filter5);

        QueryFilter filter6 = new QueryFilter(QccEnterpriseLegalProcCount.class);
        filter6.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseLegalProcCount> qccEnterpriseLegalProcCounts = qccEnterpriseLegalProcCountService.find(filter6);

        QueryFilter filter = new QueryFilter(QccEnterpriseCaseFiling.class);
        filter.addFilter("enterpriseId=",enterpriseId);
        List<QccEnterpriseCaseFiling> qccEnterpriseCaseFilings = qccEnterpriseCaseFilingService.find(filter);

        Map<String,Object> mapResult = new HashMap<>();
        mapResult.put("qccEnterpriseCourtnotices",qccEnterpriseCourtnotices);
        mapResult.put("qccEnterpriseJudgmentdocs",qccEnterpriseJudgmentdocs);
        mapResult.put("qccEnterpriseCourtannouncements",qccEnterpriseCourtannouncements);
        mapResult.put("qccEnterpriseZhixings",qccEnterpriseZhixings);
        mapResult.put("qccEnterpriseJudicialAssistances",qccEnterpriseJudicialAssistances);
        mapResult.put("qccEnterpriseLegalProcCounts",qccEnterpriseLegalProcCounts);
        mapResult.put("qccEnterpriseCaseFilings",qccEnterpriseCaseFilings);
        return new JsonResult().setSuccess(true).setObj(mapResult);

    }

    @ApiOperation(value = "企查查-工商风险扫描查询", notes = "企查查-工商风险扫描查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/pageAlllist3")
    public JsonResult pageAlllist3 (
            @ApiParam(name = "enterpriseId", value = "enterpriseId", required = true) @RequestParam Long enterpriseId,
                            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(QccEnterpriseOverviewInfo.class, request);
        filter.addFilter("enterpriseId=",enterpriseId);
        return new JsonResult().setSuccess(true).setObj(qccEnterpriseOverviewInfoService.get(filter));
    }



}
