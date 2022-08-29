/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.scm.enterprise.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ScmEnterpriseController </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:35:56 
 */
@Api(value = "供应链企业信息管理", tags = "供应链企业信息管理", description = "供应链企业信息管理")
@RestController
@RequestMapping("/enterprise/scmenterprise")
public class ScmEnterpriseController extends BaseController {

	@Autowired
	private ScmEnterpriseService scmEnterpriseService;

	@Autowired
    private UserRelationService userRelationService;
    @Autowired
    private EnterpriseQuotaService enterpriseQuotaService;

	/**
     * <p> 供应链企业信息管理-id查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-id查询", notes = "供应链企业信息管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ScmEnterprise scmEnterprise = scmEnterpriseService.get(id);
        if (scmEnterprise != null) {
            //查询企业管理员
            CuCustomer cuCustomer = userRelationService.getCustomer(scmEnterprise.getId(),1);
            Map<String, Object> map = new HashMap<>();
            map.put("scmEnterprise",scmEnterprise);
            map.put("cuCustomer",cuCustomer);
            jsonResult.setObj(map);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 供应链企业信息管理-添加 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-添加", notes = "供应链企业信息管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ScmEnterprise scmEnterprise) {
        JsonResult jsonResult = new JsonResult();
        scmEnterpriseService.save(scmEnterprise);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链企业信息管理-修改 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-修改", notes = "供应链企业信息管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ScmEnterprise scmEnterprise) {
        JsonResult jsonResult = new JsonResult();
        scmEnterpriseService.update(scmEnterprise);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链企业信息管理-id删除 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-id删除", notes = "供应链企业信息管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        scmEnterpriseService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链企业信息管理-分页查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-分页查询", notes = "供应链企业信息管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "status", value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单", required = false) @RequestParam String status,
            @ApiParam(name = "enterpriseName", value = "企业名称", required = false) @RequestParam String enterpriseName,
            @ApiParam(name = "username", value = "管理员姓名", required = false) @RequestParam String username,
            @ApiParam(name = "mobile", value = "管理员手机号码", required = false) @RequestParam String mobile,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ScmEnterprise.class, request);
        return scmEnterpriseService.findPageBySql(filter);
    }

    /**
     * <p> 注册用户管理-查看关联企业 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56
     */
    @ApiOperation(value = "注册用户管理-查看关联企业", notes = "注册用户管理-查看关联企业",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public JsonResult findList (
            @ApiParam(name = "customerId", value = "注册用户Id", required = true) @RequestParam Long customerId,
            HttpServletRequest request) {
        QueryFilter filter1 = new QueryFilter(UserRelation.class);
        filter1.addFilter("customerId=",customerId);
        List<UserRelation> relationList = userRelationService.find(filter1);
        List<ScmEnterprise> enterpriseList = new ArrayList<>();
        for(UserRelation userRelation : relationList){
            ScmEnterprise scmEnterprise = scmEnterpriseService.get(userRelation.getInfoId());
            scmEnterprise.setRole(userRelation.getRole());
            enterpriseList.add(scmEnterprise);
        }
        return new JsonResult().setSuccess(true).setObj(enterpriseList);
    }

    /**
     * <p> 注册用户管理-取消关联企业 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56
     */
    @ApiOperation(value = "注册用户管理-取消关联企业", notes = "注册用户管理-取消关联企业",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/unbindEnterprise")
    public JsonResult unbindEnterprise (
            @ApiParam(name = "customerId", value = "注册用户Id", required = true) @RequestParam Long customerId,
            @ApiParam(name = "infoId", value = "企业用户Id", required = true) @RequestParam Long infoId,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(customerId)){
            return jsonResult.setSuccess(false).setMsg("注册用户Id不能为空");
        }
        if(StringUtils.isEmpty(infoId)){
            return jsonResult.setSuccess(false).setMsg("企业用户Id不能为空");
        }
        QueryFilter filter = new QueryFilter(UserRelation.class);
        filter.addFilter("customerId=",customerId);
        filter.addFilter("infoId=",infoId);
        UserRelation userRelation = userRelationService.get(filter);
        if(StringUtils.isEmpty(userRelation)){
            return jsonResult.setSuccess(false).setMsg("取消关联关系失败");
        }
        if(userRelation.getRole() == 1){
            return jsonResult.setSuccess(false).setMsg("企业管理员不能取消关联");
        }
        userRelationService.delete(userRelation.getId());
        return jsonResult.setSuccess(true).setMsg("取消关联关系成功");
    }

    @ApiOperation(value = "修改企业状态", notes = "修改企业状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value="/updateStatus")
    public JsonResult updateStatus(
            @ApiParam(name = "id", value = "企业id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单", required = true) @RequestParam Integer status,
            @ApiParam(name = "remarks", value = "描述（打回原因）", required = false) @RequestParam String remarks
    ){
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(status)){
            jsonResult.setMsg("状态不能为空");
            return  jsonResult;
        }

        ScmEnterprise scmEnterprise = scmEnterpriseService.get(id);
        if (scmEnterprise != null) {
            scmEnterprise.setStatus(status);
            if(!StringUtils.isEmpty(remarks)){
                scmEnterprise.setRemarks(remarks);
            }
            scmEnterpriseService.update(scmEnterprise);

            //企业开户审批通过，初始化额度
            if(scmEnterprise.getStatus() == 2){
                enterpriseQuotaService.initEnterpriseQuota(scmEnterprise.getId());
            }

            return jsonResult.setSuccess(true).setMsg("审批成功");
        }

        return jsonResult;
    }



}
