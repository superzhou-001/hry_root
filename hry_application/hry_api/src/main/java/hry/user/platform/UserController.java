package hry.user.platform;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.util.QueryFilter;
import hry.redis.RedisService;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.enterprise.service.UserRelationService;
import hry.security.jwt.JWTContext;
import hry.security.logger.ControllerLogger;
import hry.util.SpringUtil;
import io.swagger.annotations.*;
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
 * @ClassName UserController
 * @Description: TODO
 * @Author yaozhuo
 * @Date 2020/4/28
 * @Version V1.0
 **/

@Api(value = "用户信息类", tags = "用户信息类", description = "用户信息类")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CuCustomerService cuCustomerService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private ScmEnterpriseService scmEnterpriseService;
    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "获取当前登录用户", notes = "获取当前登录用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/getUser")
    public Map<String, Object> getUser(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        map.put("user", user);
        return map;
    }

    @PostMapping(value = "/updateNickName")
    @ApiOperation(value = "昵称设置", httpMethod = "POST", notes = "nName：昵称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult updateNickName(HttpServletRequest request, @RequestParam String nName) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        user.setNickname(nName);
        CuCustomer cuUpdate = new CuCustomer();
        cuUpdate.setId(user.getId());
        cuUpdate.setNickname(nName);
        cuCustomerService.update(cuUpdate);
        JWTContext.updateUser(request, JSON.toJSONString(user));
        return new JsonResult().setSuccess(true).setMsg("设置成功");
    }

    @PostMapping(value = "/updatePassword")
    @ApiOperation(value = "修改登陆密码", httpMethod = "POST", notes = "修改登陆密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult updatePassword(
            HttpServletRequest request,
            @ApiParam(name = "oldPassWord", value = "原密码", required = true) @RequestParam String oldPassWord,
            @ApiParam(name = "newPassWord", value = "新密码", required = true) @RequestParam String newPassWord,
            @ApiParam(name = "reNewPassWord", value = "确认密码", required = true) @RequestParam String reNewPassWord) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(oldPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("原密码不能为空");
            return jsonResult;
        }
        if (StringUtils.isEmpty(newPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("新密码不能为空");
            return jsonResult;
        }
        if (oldPassWord.equals(newPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("原密码与新密码不能相同");
            return jsonResult;
        }
        if (StringUtils.isEmpty(reNewPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("确认密码不能为空");
            return jsonResult;
        }
        if (!newPassWord.equals(reNewPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("两次密码不一致");
            return jsonResult;
        }
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        jsonResult = cuCustomerService.updatePassword(user.getId(), oldPassWord, newPassWord);

        return jsonResult;
    }

    @PostMapping(value = "/checkMobile")
    @ApiOperation(value = "修改手机号-验证已绑定手机号", httpMethod = "POST", notes = "修改手机号-验证已绑定手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult checkMobile(
            HttpServletRequest request,
            @ApiParam(name = "verifyCode", value = "短信验证码", required = true) @RequestParam("verifyCode") String verifyCode,
            @ApiParam(name = "viewCode", value = "图形验证码", required = true) @RequestParam String viewCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        JsonResult jsonResult = new JsonResult();

        // 验证图形验证码
        String code = redisService.get("Mobile:regCode" + uid);
        if (StringUtils.isEmpty(viewCode)) {
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        } else {
            if (code == null || !viewCode.equals(code.toString())) {
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }

        //获得手机验证码
        String session_pwSmsCode = redisService.get("SMS:setPhone:" + user.getMobile());
        //判断验证码是否正确
        if (!verifyCode.equals(session_pwSmsCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("验证码错误");
            return jsonResult;
        }

        //保存验证结果
        redisService.save("SMS:checkPhone:" + user.getMobile(), user.getMobile(), 300);

        // 删除验证码
        redisService.delete("SMS:setPhone:" + user.getMobile());
        //删除图形验证码
        redisService.delete("Mobile:regCode"+uid);

        return new JsonResult().setSuccess(true).setMsg("验证成功");
    }

    @PostMapping(value = "/updateMobile")
    @ApiOperation(value = "修改手机号-设置新的手机号", httpMethod = "POST", notes = "修改手机号-设置新的手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult updateMobile(
            HttpServletRequest request,
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam("mobile") String mobile,
            @ApiParam(name = "verifyCode", value = "短信验证码", required = true) @RequestParam("verifyCode") String verifyCode,
            @ApiParam(name = "viewCode", value = "图形验证码", required = true) @RequestParam String viewCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        //更新手机号
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        String oldMobile = user.getMobile();
        JsonResult jsonResult = new JsonResult();
        // 验证图形验证码
        String code = redisService.get("Mobile:regCode" + uid);
        if (StringUtils.isEmpty(viewCode)) {
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        } else {
            if (code == null || !viewCode.equals(code.toString())) {
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }

        //检验之前绑定的手机号是否验证通过
        String registSmsVerify = redisService.get("SMS:checkPhone:" + oldMobile);
        if (StringUtils.isEmpty(registSmsVerify)) {
            return jsonResult.setSuccess(false).setMsg("非法操作");
        }


        //查看手机号是否存在
        Boolean b = cuCustomerService.getCustomerByMobile(mobile);
        if (!b) {
            return jsonResult.setSuccess(false).setMsg("手机号已被绑定");
        }

        //获得手机验证码
        String session_pwSmsCode = redisService.get("SMS:setPhone:" + mobile);
        //判断验证码是否正确
        if (!verifyCode.equals(session_pwSmsCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("验证码错误");
            return jsonResult;
        }

        user.setMobile(mobile);
        CuCustomer cuUpdate = new CuCustomer();
        cuUpdate.setId(user.getId());
        cuUpdate.setMobile(mobile);
        cuCustomerService.update(cuUpdate);
        //刷新用户信息
        JWTContext.updateUser(request, JSON.toJSONString(user));

        //删除校验标识
        redisService.delete("SMS:checkPhone:"+oldMobile);
        // 删除验证码
        redisService.delete("SMS:setPhone:" + mobile);
        //删除图形验证码
        redisService.delete("Mobile:regCode"+uid);

        return new JsonResult().setSuccess(true).setMsg("修改成功");
    }

    @PostMapping(value = "/setHeadPortrait")
    @ApiOperation(value = "头像设置", httpMethod = "POST", notes = "头像设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult setHeadPortrait(HttpServletRequest request,
            @ApiParam(name = "headPortraitUrl", value = "头像", required = true) @RequestParam String headPortraitUrl
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(headPortraitUrl)) {
            return jsonResult.setSuccess(false).setMsg("头像不能为空");
        }
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        user.setHeadPortraitUrl(headPortraitUrl);
        CuCustomer cuUpdate = new CuCustomer();
        cuUpdate.setId(user.getId());
        cuUpdate.setHeadPortraitUrl(headPortraitUrl);
        cuCustomerService.update(cuUpdate);
        JWTContext.updateUser(request, JSON.toJSONString(user));
        return new JsonResult().setSuccess(true).setMsg("设置成功");
    }

    @PostMapping(value = "/setUserInfo")
    @ApiOperation(value = "基本信息设置", httpMethod = "POST", notes = "基本信息设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult setUserInfo(HttpServletRequest request,
                  @ApiParam(name = "username", value = "姓名", required = true) @RequestParam String username,
                  @ApiParam(name = "gender", value = "性别:0男；1女", required = true) @RequestParam Integer gender,
                  @ApiParam(name = "province", value = "省", required = true) @RequestParam String province,
                  @ApiParam(name = "city", value = "市", required = true) @RequestParam String city,
                  @ApiParam(name = "county", value = "县/区", required = true) @RequestParam String county,
                  @ApiParam(name = "address", value = "详细地址", required = true) @RequestParam String address,
                  @ApiParam(name = "email", value = "邮箱", required = true) @RequestParam String email
    ) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        CuCustomer cuUpdate = new CuCustomer();
        cuUpdate.setId(user.getId());
        if(!StringUtils.isEmpty(username)){
            user.setUsername(username);
            cuUpdate.setUsername(username);
        }
        if(!StringUtils.isEmpty(gender)){
            user.setGender(gender);
            cuUpdate.setGender(gender);
        }
        if(!StringUtils.isEmpty(province)){
            user.setProvince(province);
            cuUpdate.setProvince(province);
        }
        if(!StringUtils.isEmpty(city)){
            user.setCity(city);
            cuUpdate.setCity(city);
        }
        if(!StringUtils.isEmpty(county)){
            user.setCounty(county);
            cuUpdate.setCounty(county);
        }
        if(!StringUtils.isEmpty(address)){
            user.setAddress(address);
            cuUpdate.setAddress(address);
        }
        if(!StringUtils.isEmpty(email)){
            user.setEmail(email);
            cuUpdate.setEmail(email);
        }
        cuCustomerService.update(cuUpdate);
        JWTContext.updateUser(request, JSON.toJSONString(user));
        return new JsonResult().setSuccess(true).setMsg("设置成功");
    }



    @PostMapping(value = "/intoHome")
    @ApiOperation(value = "选择企业主体", httpMethod = "POST", notes = "选择企业主体")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult intoHome(HttpServletRequest request,
                             @ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam Long enterpriseId) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        QueryFilter filter = new QueryFilter(ScmEnterprise.class);
        filter.addFilter("id=",enterpriseId);
        ScmEnterprise scmEnterprise = scmEnterpriseService.get(filter);
        if(scmEnterprise.getStatus() == 1){
            return new JsonResult().setSuccess(false).setMsg("审核中");
        }else if(scmEnterprise.getStatus() == 3){
            return new JsonResult().setSuccess(false).setMsg("未通过");
        }else if(scmEnterprise.getStatus() == 4){
            return new JsonResult().setSuccess(false).setMsg("已禁用");
        }
        QueryFilter filter2= new QueryFilter(UserRelation.class);
        filter2.addFilter("customerId=",user.getId());
        filter2.addFilter("infoId=",scmEnterprise.getId());
        UserRelation relation = userRelationService.get(filter2);
        user.setInfoId(scmEnterprise.getId());
        user.setCreditCode(scmEnterprise.getCreditCode());
        user.setRole(relation.getRole());
        JWTContext.updateUser(request, JSON.toJSONString(user));
        return new JsonResult().setSuccess(true).setMsg("设置成功");
    }

    @PostMapping(value = "/unbind")
    @ApiOperation(value = "关联企业管理-解除绑定", httpMethod = "POST", notes = "关联企业管理-解除绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult unbind(HttpServletRequest request,
                             @ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam Long enterpriseId) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        if(enterpriseId == user.getInfoId() && user.getRole() == 1){
            return new JsonResult().setSuccess(false).setMsg("企业管理员不能解绑");
        }
        QueryFilter filter = new QueryFilter(UserRelation.class);
        filter.addFilter("infoId=",enterpriseId);
        filter.addFilter("customerId=",user.getId());
        userRelationService.delete(filter);
        return new JsonResult().setSuccess(true).setMsg("设置成功");
    }


    @ApiOperation(value = "关联企业管理-查询列表", notes = "关联企业管理-查询列表",response = ScmEnterprise.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/getList")
    public JsonResult getList ( HttpServletRequest request) {
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        List<ScmEnterprise> list = new ArrayList<>();
        List<UserRelation> relationList = userRelationService.getRelationList(user);
        for(UserRelation userRelation : relationList){
            QueryFilter filter = new QueryFilter(ScmEnterprise.class);
            filter.addFilter("id=",userRelation.getInfoId());
            ScmEnterprise scmEnterprise = scmEnterpriseService.get(filter);
            if(scmEnterprise.getStatus() == 2 || scmEnterprise.getStatus() == 4){//2.已通过，4.加入黑名单
                scmEnterprise.setRole(userRelation.getRole());
                list.add(scmEnterprise);
            }
        }
        return new JsonResult().setSuccess(true).setObj(list);
    }





}
