package hry.business.manage.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import hry.util.PasswordHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yaozh
 * @Description: 注册用户管理
 * @Date:
 */
@Api(value = "注册用户管理", tags = "注册用户管理", description = "注册用户管理")
@RestController
@RequestMapping("/manage/customer")
public class CustomerController extends BaseController {
    @Autowired
    private CuCustomerService cuCustomerService;

    /**
     * <p> 用户列表查询 </p>
     * @author: yaoz
     */
    @ApiOperation(value = "用户列表查询", notes = "用户列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuCustomer.class, request);
        filter.setOrderby("id desc");
        return cuCustomerService.findPageResult(filter);
    }

    /**
     * <p> 用户信息-id查询 </p>
     *
     * @author: yaoz
     */
    @ApiOperation(value = "用户信息-id查询", notes = "用户信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuCustomer cuCustomer = cuCustomerService.get(id);
        if (cuCustomer != null) {
            jsonResult.setObj(cuCustomer);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }


    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value="/resetpwd")
    public JsonResult resetpwd(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "password", value = "新密码", required = true) @RequestParam String password,
            @ApiParam(name = "repassword", value = "确认密码", required = true) @RequestParam String repassword
    ){
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(password)){
            jsonResult.setMsg("密码不能为空");
            return  jsonResult;
        }
        if(StringUtils.isEmpty(repassword)){
            jsonResult.setMsg("密码不能为空");
            return jsonResult;
        }
        if(!password.equals(repassword)){
            jsonResult.setMsg("两次密码不一致");
            return jsonResult;
        }

        CuCustomer cuCustomer = cuCustomerService.get(id);
        if (cuCustomer != null) {
            //加密密码
            cuCustomer.setPassword(PasswordHelper.md5(password,cuCustomer.getSalt()));
            cuCustomerService.update(cuCustomer);
            return jsonResult.setSuccess(true).setMsg("成功");
        }

        return jsonResult;
    }

    @ApiOperation(value = "修改手机号", notes = "修改手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value="/updateMobile")
    public JsonResult updateMobile(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "mobile", value = "新手机号码", required = true) @RequestParam String mobile,
            @ApiParam(name = "oldMobile", value = "原手机号码", required = true) @RequestParam String oldMobile
    ){
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(mobile)){
            jsonResult.setMsg("新手机号码不能为空");
            return  jsonResult;
        }
        if(StringUtils.isEmpty(oldMobile)){
            jsonResult.setMsg("新手机号码不能为空");
            return jsonResult;
        }
        if(mobile.equals(oldMobile)){
            jsonResult.setMsg("新手机号与原手机号不能相同");
            return jsonResult;
        }

        CuCustomer cuCustomer = cuCustomerService.get(id);
        if (cuCustomer != null) {
            //加密密码
            cuCustomer.setMobile(mobile);
            cuCustomerService.update(cuCustomer);
            return jsonResult.setSuccess(true).setMsg("成功");
        }

        return jsonResult;
    }

    @ApiOperation(value = "修改用户状态", notes = "修改用户状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value="/updateStatus")
    public JsonResult updateStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "状态 0禁用；1启用", required = true) @RequestParam Integer status
    ){
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(status)){
            jsonResult.setMsg("不能为空");
            return  jsonResult;
        }

        CuCustomer cuCustomer = cuCustomerService.get(id);
        if (cuCustomer != null) {
            //加密密码
            cuCustomer.setStatus(status);
            cuCustomerService.update(cuCustomer);
            return jsonResult.setSuccess(true).setMsg("成功");
        }

        return jsonResult;
    }

    @ApiOperation(value = "查询没有绑定企业的用户", notes = "查询没有绑定企业的用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findCustomerNotEnterprise")
    public PageResult findCustomerNotEnterprise (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuCustomer.class, request);
        return cuCustomerService.findCustomerNotEnterprise(filter);
    }




}
