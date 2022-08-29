/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.user.scm.project;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.MortgageProject;
import hry.scm.project.service.MortgageProjectService;
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
 * <p> MortgageProjectController </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:03:52 
 */
@Api(value = "现货质押项目管理", tags = "现货质押项目管理", description = "现货质押项目管理")
@RestController
@RequestMapping("/project/mortgageproject")
public class MortgageProjectController extends BaseController {

	@Autowired
	private MortgageProjectService mortgageProjectService;

    /**
     * <p> 现货质押项目管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52
     */
    @ApiOperation(value = "订单详情-id查询", notes = "订单详情-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getProjectInfo/{id}")
    public JsonResult getProjectInfo (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> map = mortgageProjectService.getProjectProcessInfo(id,"apply");
        if (map != null) {
            jsonResult.setObj(map);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 现货质押项目管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52 
     */
    @ApiOperation(value = "现货质押项目管理-id查询", notes = "现货质押项目管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		MortgageProject mortgageProject = mortgageProjectService.get(id);
        if (mortgageProject != null) {
            jsonResult.setObj(mortgageProject);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 现货质押项目管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52 
     */
  /*  @ApiOperation(value = "现货质押项目管理-添加", notes = "现货质押项目管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (MortgageProject mortgageProject) {
        JsonResult jsonResult = new JsonResult();
        mortgageProjectService.save(mortgageProject);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 现货质押项目管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52 
     */
/*    @ApiOperation(value = "现货质押项目管理-修改", notes = "现货质押项目管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (MortgageProject mortgageProject) {
        JsonResult jsonResult = new JsonResult();
        mortgageProjectService.update(mortgageProject);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 现货质押项目管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52 
     */
/*    @ApiOperation(value = "现货质押项目管理-id删除", notes = "现货质押项目管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mortgageProjectService.delete(id);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 现货质押项目管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52 
     */
    @ApiOperation(value = "现货质押项目管理-分页查询", notes = "现货质押项目管理-分页查询",response = MortgageProject.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "projectStatus", value = "订单状态：待处理-1,处理中-2，已放款/质押中-3,已关闭-4，已逾期-5，已结清-6", required = true) @RequestParam Integer projectStatus,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MortgageProject.class, request);
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        String role = "";
        if(user.getUserType()==1){
            role = "enterprise";
            filter.addFilter("enterpriseId=",user.getInfoId());
        }else if(user.getUserType()==2){
            role = "fundSupport";
            filter.addFilter("fundSupportId=",user.getInfoId());
        }
        return mortgageProjectService.listByStatusAndRole(filter,role,projectStatus);
    }

    /**
     * <p> 现货质押项目管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:03:52
     */
    @ApiOperation(value = "授信项目管理-分页查询", notes = "授信项目管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/creditList")
    public PageResult creditList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam String enterpriseId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MortgageProject.class, request);
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        filter.addFilter("fundSupportId=",user.getInfoId());
        filter.addFilter("enterpriseId=",user.getInfoId());
        filter.addFilter("status_in","50,70");
        return mortgageProjectService.findPageResult(filter);
    }

    /**
     * <p> 现货质押项目管理-融资申请 </p>
     *
     * @author: luyue
     * @Date: 2020-07-14 15:03:52
     * { "project" :{ "projectType" : 1,
     *  "fundSupportId" : 35,
     *  "fundPlanId" : 16,
     *  "loanMoney" : 1000,
     *  "remark" : "我要借钱借钱" }, "storage" :[{ "goodsName" : "冻羊肉",
     *  "totalWeight":1680.28,"mortWeight":200,"mortPrice":20,"mortTotalPrice":4000},{"goodsName" : "冻猪心",
     *  "totalWeight":1630.27,"mortWeight":100,"mortPrice":10,"mortTotalPrice":2000}]}
     */
    @ApiOperation(value = "现货质押项目申请-融资申请", notes = "现货质押项目申请-融资申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/apply")
    public JsonResult apply (
            @ApiParam(name = "jsonStr", value = "对象json包含（project，storage）", required = true) @RequestParam String jsonStr,
            HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("userId",user.getId().toString());//申请账号id
        params.put("creditCode",user.getCreditCode());//企业社会信用代码
        params.put("enterpriseId",user.getInfoId().toString());//企业id
        params.put("username",user.getUsername());
     /*   params.put("userId","28");//申请账号id
        params.put("creditCode","111111");//企业社会信用代码
        params.put("enterpriseId","3");
        params.put("username","张三");*/
        JsonResult jsonResult =mortgageProjectService.apply(params) ;
        return jsonResult;
    }

    /**
     * <p> 现货质押项目管理-确认质押 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 16:55:10
     */
    @ApiOperation(value = "现货质押项目申请-确认质押", notes = "现货质押项目管理-确认质押")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/nextStep")
    public JsonResult nextStep (
            @ApiParam(name = "projectId", value = "所选订单id", required = true) @RequestParam String projectId,
            @ApiParam(name = "opinion", value = "处理意见1同意2拒绝", required = true) @RequestParam String opinion,
            @ApiParam(name = "loanMoney", value = "借款金额", required = true) @RequestParam String loanMoney,
            HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("userId",user.getId().toString());//申请账号id
        params.put("username",user.getUsername());
   /*     params.put("userId","28");//申请账号id
        params.put("username","张三");*/
        params.put("reType","2");//前台请求
        JsonResult jsonResult =mortgageProjectService.nextStep(params) ;
        return jsonResult;
    }

    /**
     * <p> 现货质押项目管理-确认质押 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 16:55:10
     */
    @ApiOperation(value = "现货质押项目申请-确认放款", notes = "现货质押项目申请-确认放款")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/confirmLoan")
    public JsonResult confirmLoan (
            @ApiParam(name = "projectId", value = "所选订单id", required = true) @RequestParam String projectId,
            @ApiParam(name = "opinion", value = "处理意见1同意2拒绝", required = true) @RequestParam String opinion,
            @ApiParam(name = "loanImageUrl", value = "放款凭证照片路径", required = true) @RequestParam String loanImageUrl,
            HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("userId",user.getId().toString());//申请账号id
        params.put("username",user.getMobile());
     /*   params.put("userId","28");//申请账号id
        params.put("username","张三");*/
        params.put("reType","2");//前台请求
        JsonResult jsonResult =mortgageProjectService.nextStep(params) ;
        return jsonResult;
    }


}
