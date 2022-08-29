/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.user.scm.enterprise;

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
import hry.scm.file.model.ScmFile;
import hry.scm.file.service.ScmFileService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.user.scm.access.RoleAccess;
import io.swagger.annotations.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private ScmFileService scmFileService;
	@Autowired
    private UserRelationService userRelationService;
    @Autowired
    private CuCustomerService cuCustomerService;

	/**
     * <p> 供应链企业信息管理-id查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-id查询", notes = "供应链企业信息管理-id查询",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        Map<String,Object> map = new HashMap<>();
		ScmEnterprise scmEnterprise = scmEnterpriseService.get(id);
        if (scmEnterprise != null) {
            map.put("scmEnterprise",scmEnterprise);
            //查询补充文件，资质或荣誉证书
            List<ScmFile> list = scmFileService.findList("honorFile",scmEnterprise.getId());
            map.put("honorFile",list);

            //查询补充文件，近期财务报表
            List<ScmFile> list2 = scmFileService.findList("financeFile",scmEnterprise.getId());
            map.put("financeFile",list2);

            //查询企业管理员
            CuCustomer cuCustomer = userRelationService.getCustomer(scmEnterprise.getId(),1);
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
    @ApiOperation(value = "供应链企业信息管理-添加", notes = "供应链企业信息管理-添加",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addEnterprise")
    public JsonResult addEnterprise (@ApiParam(name = "jsonStr", value = "对象json包含（enterprise，customer）", required = true) @RequestParam String jsonStr,
                           HttpServletRequest request, HttpServletResponse response) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        return scmEnterpriseService.addEnterprise(request,jsonStr,user);
    }

    /**
     * <p> 供应链企业信息管理-修改 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    @ApiOperation(value = "供应链企业信息管理-修改", notes = "供应链企业信息管理-修改",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateEnterprise")
    public JsonResult updateEnterprise (
            @ApiParam(name = "jsonStr", value = "对象json包含（enterprise,honorFile,financeFile,customer）", required = true) @RequestParam String jsonStr,
            @ApiParam(name = "bType", value = "类型（1:修改企业信息，2:申请授信）", required = true) @RequestParam Integer bType,
            @ApiParam(name = "fundSupportId", value = "资金方Id", required = false) @RequestParam Long fundSupportId,
            HttpServletRequest request, HttpServletResponse response
    ) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        return scmEnterpriseService.updateEnterprise(request,jsonStr,user);
    }

    /**
     * <p> 供应链企业信息管理-id删除 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    /*@ApiOperation(value = "供应链企业信息管理-id删除", notes = "供应链企业信息管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        scmEnterpriseService.delete(id);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 供应链企业信息管理-分页查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56 
     */
    /*@ApiOperation(value = "供应链企业信息管理-分页查询", notes = "供应链企业信息管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ScmEnterprise.class, request);
        return scmEnterpriseService.findPageResult(filter);
    }*/


    /**
     * <p> 供应链企业信息管理-查询关联企业列表 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:35:56
     */
    @ApiOperation(value = "供应链企业信息管理-查询关联企业列表", notes = "供应链企业信息管理-查询关联企业列表",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/getAll")
    public JsonResult getAll ( HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        List<ScmEnterprise> list = new ArrayList<>();
        List<UserRelation> relationList = userRelationService.getRelationList(user);
        for(UserRelation userRelation : relationList){
            QueryFilter filter = new QueryFilter(ScmEnterprise.class);
            filter.addFilter("id=",userRelation.getInfoId());
            ScmEnterprise scmEnterprise = scmEnterpriseService.get(filter);
            scmEnterprise.setRole(userRelation.getRole());
            list.add(scmEnterprise);
        }
        return new JsonResult().setSuccess(true).setObj(list);
    }


    @ApiOperation(value = "账号授权管理-查询企业关联用户列表", notes = "账号授权管理-查询企业关联用户列表",response = UserRelation.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findUrList")
    public JsonResult findUrList ( HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        if(user.getRole() != 1){
            return new JsonResult().setSuccess(false).setObj("非企业管理员，不能操作");
        }
        QueryFilter filter = new QueryFilter(UserRelation.class);
        filter.addFilter("infoId=",user.getInfoId());
        filter.addFilter("customerId!=",user.getId());//不能查询自己
        List<UserRelation> relationList = userRelationService.find(filter);
        for(UserRelation relation : relationList){
            CuCustomer cuCustomer = cuCustomerService.get(relation.getCustomerId());
            relation.setUsername(cuCustomer.getUsername());
            relation.setMobile(cuCustomer.getMobile());
            relation.setEmail(cuCustomer.getEmail());
        }
        return new JsonResult().setSuccess(true).setObj(relationList);
    }


    @PostMapping(value = "/bindMobile")
    @ApiOperation(value = "账号授权管理-添加授权", httpMethod = "POST", notes = "账号授权管理-添加授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult bindMobile(HttpServletRequest request,
                             @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        if(user.getRole() != 1){
            return new JsonResult().setSuccess(false).setObj("非企业管理员，不能操作");
        }
        CuCustomer cuCustomer = cuCustomerService.checkMobile(mobile);
        if(StringUtils.isEmpty(cuCustomer)){
            return new JsonResult().setSuccess(false).setMsg("此账号不存在");
        }

        UserRelation userRelation = userRelationService.getRelation(cuCustomer.getId(),user.getInfoId());
        if(!StringUtils.isEmpty(userRelation)){
            return new JsonResult().setSuccess(false).setMsg("此账号已经添加过授权，请勿重复添加");
        }


        userRelationService.saveUserRelation(cuCustomer.getId(),user.getInfoId(),1,2);
        return new JsonResult().setSuccess(true).setMsg("添加成功");
    }

    @PostMapping(value = "/unbindMobile")
    @ApiOperation(value = "账号授权管理-取消授权", httpMethod = "POST", notes = "账号授权管理-取消授权")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult unbindMobile(HttpServletRequest request,
                                 @ApiParam(name = "relationId", value = "用户关系Id", required = true) @RequestParam Long relationId) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        if(user.getRole() != 1){
            return new JsonResult().setSuccess(false).setObj("非企业管理员，不能操作");
        }
        UserRelation userRelation = userRelationService.get(relationId);
        if(StringUtils.isEmpty(userRelation)){
            return new JsonResult().setSuccess(false).setObj("取消授权失败");
        }
        if(userRelation.getInfoId() != user.getInfoId()){//防止传参，修改别人的数据
            return new JsonResult().setSuccess(false).setObj("取消授权失败");
        }
        if(userRelation.getRole() == 1){
            return new JsonResult().setSuccess(false).setMsg("企业管理员不能解绑");
        }
        userRelationService.delete(relationId);
        return new JsonResult().setSuccess(true).setMsg("添加成功");
    }

    @ApiOperation(value = "账号授权管理-新增账户", httpMethod = "POST", notes = "账号授权管理-新增账户")
    @PostMapping("/openMobile")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult openMobile(
            @ApiParam(name = "passWord", value = "密码", required = true) @RequestParam String passWord,
            @ApiParam(name = "repeatpassWord", value = "重复密码", required = true) @RequestParam String repeatpassWord,
            @ApiParam(name = "mobile", value = "手机", required = true) @RequestParam String mobile,
            @ApiParam(name = "userName", value = "姓名", required = true) @RequestParam String userName,
            HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        if (StringUtils.isEmpty(mobile)) {
            jsonResult.setMsg("手机号码不能为空");
            return jsonResult;
        }
        if (StringUtils.isEmpty(userName)) {
            jsonResult.setMsg("用户姓名不能为空");
            return jsonResult;
        }
        if (StringUtils.isEmpty(passWord)) {
            jsonResult.setMsg("密码不能为空");
            return jsonResult;
        }
        if(!passWord.equals(repeatpassWord)){
            jsonResult.setMsg("两次密码不一致");
            return jsonResult;
        }
        CuCustomer oldUser = cuCustomerService.checkMobile(mobile);
        if (oldUser != null) {
            return jsonResult.setSuccess(false).setMsg("此用户已存在，请勿新增账户");
        }
        jsonResult = cuCustomerService.regist(null,userName,passWord,null,mobile);
        userRelationService.saveUserRelation(Long.valueOf(jsonResult.getObj().toString()),user.getInfoId(),1,2);
        return jsonResult.setSuccess(true).setMsg("新增账户成功");
    }

}
