/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:12:26 
 */
package hry.platform.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.redis.RedisService;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.fundsupport.service.FundSupportService;
import hry.security.jwt.JWTToken;
import hry.security.jwt.JWTUtil;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.PasswordHelper;
import hry.util.SpringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 功能描述: 登录认证类
 * @auther: yaozh
 * @date: 2020/4/27 16:44
 */
@Api(value = "登录认证类", tags = "登录认证类", description = "登录认证类")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

	@Autowired
	private CuCustomerService cuCustomerService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private FundSupportService fundSupportService;


    @ApiOperation(value = "登录方法", notes = "登录方法")
    @PostMapping("/login")
    @UnAuthentication
    public JsonResult login(
            @ApiParam(name = "loginCode", value = "图形验证码", required = true) @RequestParam String loginCode,
            @ApiParam(name = "userName", value = "用户名", required = true) @RequestParam String userName,
            @ApiParam(name = "passWord", value = "密码", required = true) @RequestParam String passWord,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            @ApiParam(name = "userType", value = "用户类型(1:采购方，2:资金方,3:供应商)", required = true) @RequestParam Integer userType,
            HttpServletRequest request, HttpServletResponse response) {
        JsonResult jsonResult = new JsonResult();
        // 用户名不能为空
        if (StringUtils.isEmpty(userName)) {
            jsonResult.setMsg("用户名不能为空");
            return jsonResult;
        }
        // 用户名不能为空
        if (StringUtils.isEmpty(passWord)) {
            jsonResult.setMsg("密码不能为空");
            return jsonResult;
        }
        // 验证图形验证码
        String code = redisService.get("Mobile:regCode"+uid);
        if(StringUtils.isEmpty(loginCode)){
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        }else{
            if(code==null||!loginCode.equals(code.toString())){
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }
        //验证用户是否存在
        CuCustomer cuCustomer = cuCustomerService.checkMobile(userName);
        if (cuCustomer == null) {
            return new JsonResult().setMsg("用户名/密码错误");
        }

        //判断密码是否正确
        if(!PasswordHelper.validatePassword(cuCustomer.getPassword(),passWord,cuCustomer.getSalt())){
            return new JsonResult().setMsg("用户名/密码错误");
        }

        //判断用户是否被禁用
        if(cuCustomer.getStatus()==0){
            return new JsonResult().setMsg("用户被禁用");
        }


        if(userType == 2){
            //查询用户关系，确定用户身份
            //1:采购方，2:资金方,3:供应商
            QueryFilter filter = new QueryFilter(UserRelation.class);
            filter.addFilter("customerId=",cuCustomer.getId());
            filter.addFilter("userType=",userType);
            UserRelation userRelation = userRelationService.get(filter);
            if(StringUtils.isEmpty(userRelation)){
                return new JsonResult().setMsg("此资金方不存在");
            }else{
                cuCustomer.setInfoId(userRelation.getInfoId());
                cuCustomer.setRole(userRelation.getRole());
            }
        }else if(userType == 3){
            //待定

        }
        cuCustomer.setUserType(userType);


        //生成签名
        String token = JWTUtil.sign(userName, JWTToken.SOURCE_PC,JWTToken.TYPE_CUSTOMER,cuCustomer.getPassword());

        // 将token存储到redis中作为刷新token的凭证，并设置key的过期时间作为刷新有效期
        redisService.save(JWTUtil.getCustomerRefreshTimeKey(token),JSON.toJSONString(cuCustomer), JWTUtil.REFRESH_TIME);
        redisService.save(JWTUtil.getCustomerUserKey(token), JSON.toJSONString(cuCustomer),JWTUtil.EXPIRE_TIME);
        //JWTContext.setLogin(token);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token", token);

        //删除图形验证码
        redisService.delete("Mobile:regCode"+uid);

        return new JsonResult().setSuccess(true).setObj(jsonObject);

    }

    @PostMapping(value = "/findPassword")
    @ApiOperation(value = "手机找回密码", notes = "手机找回密码")
    @ResponseBody
    @UnAuthentication
    public JsonResult findPassword(
            HttpServletRequest request,
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile,
            @ApiParam(name = "newPassWord", value = "新密码", required = true) @RequestParam String newPassWord,
            @ApiParam(name = "reNewPassWord", value = "确认密码", required = true) @RequestParam String reNewPassWord,
            @ApiParam(name = "verifyCode", value = "短信验证码", required = true) @RequestParam String verifyCode
    ) {

        JsonResult jsonResult = new JsonResult();
        //查看手机号是否存在
        Boolean b = cuCustomerService.getCustomerByMobile(mobile);
        if (b) {
            return jsonResult.setSuccess(false).setMsg("手机号不存在");
        }

        if (StringUtils.isEmpty(newPassWord)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("新密码不能为空");
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

        RedisService redisService = SpringUtil.getBean("redisService");
        //获得手机验证码
        String session_pwSmsCode = redisService.get("SMS:smsForgetMobile:" + mobile);
        //判断验证码是否正确
        if (!verifyCode.equals(session_pwSmsCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("验证码错误");
            return jsonResult;
        }

        jsonResult = cuCustomerService.updatePasswordByMobile(mobile,newPassWord);

        return jsonResult;
    }

}
