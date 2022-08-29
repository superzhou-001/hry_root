/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:21:35 
 */
package hry.scm.fundsupport.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.controller.BaseController;
import hry.core.thread.ThreadPoolUtils;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.scm.fundsupport.util.InitEnterpriseQuotaRunnable;
import hry.scm.fundsupport.util.UpdateEnterpriseQuotaRunnable;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.PasswordHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FundSupportController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:21:35 
 */
@Api(value = "资金方", tags = "资金方", description = "资金方")
@RestController
@RequestMapping("/fundsupport/fundsupport")
public class FundSupportController extends BaseController {

	@Autowired
	private FundSupportService fundSupportService;

	@Autowired
    CuCustomerService cuCustomerService;

	@Autowired
    UserRelationService userRelationService;

	/**
     * <p> 资金方-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:21:35 
     */
    @ApiOperation(value = "资金方-id查询", notes = "资金方-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FundSupport fundSupport = fundSupportService.get(id);
        if (fundSupport != null) {
            jsonResult.setObj(fundSupport);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 资金方-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:21:35 
     */
    @ApiOperation(value = "资金方-添加", notes = "资金方-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FundSupport fundSupport,@ApiParam(name = "passWord", value = "资金方密码", required = true) @RequestParam String passWord) {
        JsonResult jsonResult = new JsonResult();
        //开通账号
        String mobile = fundSupport.getPrincipalMobile();
        jsonResult = cuCustomerService.regist(null,null,passWord,null,mobile);
        //初始化企业额度数据
        if(!jsonResult.getSuccess())
            return jsonResult;
        fundSupport.setUserRelationId((long) jsonResult.getObj());
        fundSupportService.save(fundSupport);
        userRelationService.saveUserRelation((long) jsonResult.getObj(),fundSupport.getId(),2,1);
        ThreadPoolUtils.execute(new InitEnterpriseQuotaRunnable(fundSupport));
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:21:35 
     */
    @ApiOperation(value = "资金方-修改", notes = "资金方-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FundSupport fundSupport) {
        JsonResult jsonResult = new JsonResult();
        fundSupportService.update(fundSupport);
        return jsonResult.setSuccess(true);
    }
    /**
     * <p> 资金方-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:21:35
     */
    @ApiOperation(value = "资金方-id,禁用，启用", notes = "资金方-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updataStatus/{id}")
    public JsonResult updataStatus (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id,
                              @ApiParam(name = "isEnable", value = "禁用1，启用2", required = true) @RequestParam Integer isEnable) {
        JsonResult jsonResult = new JsonResult();
        FundSupport fundSupport = fundSupportService.get(id);
        fundSupport.setIsEnable(isEnable);
        fundSupportService.update(fundSupport);
        ThreadPoolUtils.execute(new UpdateEnterpriseQuotaRunnable(fundSupport));
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 资金方-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:21:35 
     */
    @ApiOperation(value = "资金方-分页查询", notes = "资金方-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupport.class, request);
        filter.setOrderby("created desc");
        return fundSupportService.findPageResult(filter);
    }

}
