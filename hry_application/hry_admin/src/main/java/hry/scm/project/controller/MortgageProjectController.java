/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.model.ScmEnterprise;
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
    @ApiOperation(value = "查询订单流程信息-id查询", notes = "查询订单流程信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getProjectProcessInfo/{id}")
    public JsonResult getProjectProcessInfo (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> map = mortgageProjectService.getProjectProcessInfo(id,"aduit");
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
/*    @ApiOperation(value = "现货质押项目管理-添加", notes = "现货质押项目管理-添加")
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
 /*   @ApiOperation(value = "现货质押项目管理-修改", notes = "现货质押项目管理-修改")
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
            @ApiParam(name = "projectStatus", value = "订单状态：待处理-1,处理中-2，已放款-3,待还款-4，已逾期-5，已结清-6，已关闭-7", required = true) @RequestParam Integer projectStatus,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MortgageProject.class, request);
        return mortgageProjectService.listByStatusAndRole(filter,"admin",projectStatus);
    }

    /**
     * <p> 现货质押项目管理-业务处理下一步 </p>
     *
     * @author: luyue
     * @Date: 2020-07-14 15:03:52
     */
    @ApiOperation(value = "现货质押项目管理-业务处理下一步", notes = "现货质押项目管理-业务处理下一步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/nextStep")
    public JsonResult nextStep (
            @ApiParam(name = "projectId", value = "所选订单id", required = true) @RequestParam String projectId,
            @ApiParam(name = "opinion", value = "处理意见1同意2拒绝", required = true) @RequestParam String opinion,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("reType","1");//后台请求
        JsonResult jsonResult =mortgageProjectService.nextStep(params) ;
        return jsonResult;
    }

    /**
     * <p> 现货质押项目管理-融资申请 </p>
     *
     * @author: luyue
     * @Date: 2020-07-15 19:03:22
     */
    @ApiOperation(value = "现货质押申请-确认质押", notes = "现货质押申请-确认质押")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/confirmMortgage")
    public JsonResult confirmMortgage (
            @ApiParam(name = "jsonStr", value = "质押物明细json数组（storage）", required = true) @RequestParam String jsonStr,
            @ApiParam(name = "totalId", value = "所选汇总记录id", required = true) @RequestParam String totalId,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        JsonResult jsonResult =mortgageProjectService.confirmMortgage(params) ;
        return jsonResult;
    }
}
