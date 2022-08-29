package hry.platform.login.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.core.mvc.controller.BaseController;
import hry.platform.newuser.model.AppLoginLog;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.AppLoginLogService;
import hry.platform.newuser.service.NewAppUserService;
import hry.platform.utils.ConfigEnum;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.JWTToken;
import hry.security.jwt.JWTUtil;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.DrawPictureUtil;
import hry.util.GoogleAuthenticatorUtil;
import hry.util.PasswordHelper;
import hry.util.UUIDUtil;
import hry.util.httpRequest.IpUtil;
import hry.util.rmq.RabbitMQProducer;
import hry.util.ukey.UkeyUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Slf4j
@Api(value = "登录认证接口", tags = "登录认证接口", description = "登录认证接口")
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private NewAppUserService newAppUserService;

    @Autowired
    private AppLoginLogService appLoginLogService;

    @Value("${ukey.username}")
    private String username;

    /**
     * 登录验证码
     *
     * @param response
     * @param request
     */
    @ApiOperation(value = "获取登录图形码", httpMethod = "POST",notes = "获取登录图形码")
    @RequestMapping("/loginCode")
    @UnAuthentication
    public void loginCode(HttpServletResponse response, HttpServletRequest request,
                          @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid) {
        DrawPictureUtil drawPictureUtil = new DrawPictureUtil("graphicCode", 100, 30);
        drawPictureUtil.darw(request, response, uid);
    }

    @ApiOperation(value = "登录验证", httpMethod = "POST",notes = "登录验证")
    @RequestMapping("/loginCheck")
    @UnAuthentication
    public JsonResult loginCheck(@ApiParam(name = "userName", value = "用户名", required = true) @RequestParam String userName,
                           @ApiParam(name = "passWord", value = "密码", required = true) @RequestParam String passWord,
                           @ApiParam(name = "graphicCode", value = "图形验证码", required = true) @RequestParam String graphicCode,
                           @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
                           HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult(false);
        // 图形验证码
        if(StringUtils.isEmpty(graphicCode)){
            return jsonResult.setMsg("图形验证码不能为空");
        }
        // 验证图形验证码
        String loginCode1 = redisService.get("Mobile:regCode"+uid);
        if(loginCode1 == null || !graphicCode.equals(loginCode1.toString())){
            return jsonResult.setMsg("图形验证码错误");
        }
        //验证用户是否存在
        NewAppUser user = newAppUserService.checkUsername(userName);
        if (user == null) {
            return jsonResult.setMsg("用户名/密码错误");
        }
        //判断密码是否正确
        if(!PasswordHelper.validatePassword(user.getPassWord(),passWord,user.getSalt())){
            return jsonResult.setMsg("用户名/密码错误");
        } else {
            // secondCheck二次验证 0 不需要 1 需要
            // googleState 0 未开启谷歌 1开启谷歌
            // uKeyState 0 未开启ukey 1开启ukey
            JSONObject obj = new JSONObject();
            int secondCheck = 0;
            if (user.getGoogleState() == 1 || user.getUkeyState() == 1) {
                secondCheck = 1;
            }
            obj.put("secondCheck", secondCheck);
            obj.put("googleState", user.getGoogleState());
            obj.put("uKeyState", user.getUkeyState());
            jsonResult.setSuccess(true).setObj(obj);
        }
        return jsonResult;
    }

    @ApiOperation(value = "登录方法", notes = "登录方法")
    @PostMapping("/login")
    @UnAuthentication
    public JsonResult login(@ApiParam(name = "userName", value = "用户名", required = true) @RequestParam String userName,
                            @ApiParam(name = "passWord", value = "密码", required = true) @RequestParam String passWord,
                            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
                            @ApiParam(name = "graphicCode", value = "图形验证码", required = true) @RequestParam String graphicCode,
                            @ApiParam(name = "googleCode", value = "谷歌验证码", required = false) @RequestParam String googleCode,
                            @ApiParam(name = "uKeyCode", value = "uKey验证码", required = false) @RequestParam String uKeyCode,
                            HttpServletRequest request) {
        /*// 图形验证码
        if(StringUtils.isEmpty(graphicCode)){
            return new JsonResult(false).setMsg("图形验证码不能为空");
        }
        // 验证图形验证码
        String loginCode1 = redisService.get("Mobile:regCode"+uid);
        if(loginCode1 == null || !graphicCode.equals(loginCode1.toString())){
            return new JsonResult(false).setMsg("图形验证码错误");
        }*/
        //验证用户是否存在
        NewAppUser user = newAppUserService.checkUsername(userName);
        if (user == null) {
            return new JsonResult(false).setMsg("用户名/密码错误");
        }
        // 校验谷歌
        if (1 == user.getGoogleState()) {
            if (StringUtils.isEmpty(googleCode)) {
                return new JsonResult(false).setMsg("谷歌验证码不能为空");
            }
            Long code = Long.parseLong(googleCode);
            long times = System.currentTimeMillis();
            GoogleAuthenticatorUtil ga = new GoogleAuthenticatorUtil();
            boolean flag = ga.check_code(user.getGoogleKey(), code, times);
            if (!flag) {
                return new JsonResult(false).setMsg("谷歌验证码不正确");
            }
        }
        // 校验uKey
        if (1 == user.getUkeyState()) {
            boolean flag = false;
            if (StringUtils.isEmpty(uKeyCode)) {
                return new JsonResult(false).setMsg("ukey验证码不能为空");
            }
            if (StringUtils.isEmpty(username)) {
                flag = UkeyUtil.validDynamicPass(user.getUserName(), uKeyCode);
            } else {
                flag = UkeyUtil.validDynamicPass(username, uKeyCode);
            }
            if (!flag) {
                return new JsonResult(false).setMsg("uKey验证码错误");
            }
        }
        //查询用户拥有的权限
        Set<String> userShiroUrl = newAppUserService.findUserShiroUrl(user.getUserName());
        Object[] shiroUrls = userShiroUrl.toArray();
        //判断密码是否正确
        if(!PasswordHelper.validatePassword(user.getPassWord(),passWord,user.getSalt())){
            return new JsonResult().setMsg("用户名/密码错误");
        }else{
            //生成签名
            String token = JWTUtil.sign(userName, JWTToken.SOURCE_PC,JWTToken.TYPE_MANAGE, user.getPassWord());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token", token);
            jsonObject.put("currentAuthority", shiroUrls);

            //登录日志id
            String loginId = UUIDUtil.getUUID();

            // 将token存储到redis中作为刷新token的凭证，并设置key的过期时间作为刷新有效期
            redisService.save(JWTUtil.getManageRefreshTimeKey(token),JSON.toJSONString(user), JWTUtil.REFRESH_TIME);
            redisService.save(JWTUtil.getManageUserKey(token), JSON.toJSONString(user),JWTUtil.EXPIRE_TIME);
            //做登录时长统计
            redisService.hset(JWTUtil.getManageAllKey(),JWTUtil.getSignId(token),loginId);
            // 将用户权限存储到redis中
            redisService.save(JWTUtil.getManageShiroUrlsKey(token), JSON.toJSONString(shiroUrls),JWTUtil.EXPIRE_TIME);

            // 存入登录日志
            AppLoginLog loginLog = new AppLoginLog();
            loginLog.setLoginId(loginId);
            loginLog.setIp(IpUtil.getIp(request));
            loginLog.setUserId(user.getId());
            loginLog.setUserName(user.getUserName());
            rabbitMQProducer.sendMsgByQueue("loginLogger", JSON.toJSONString(loginLog));
            return new JsonResult().setSuccess(true).setObj(jsonObject);
        }

    }

    @ApiOperation(value = "获取当前登录用户", notes = "获取当前登录人员信息，权限，菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/currentUser")
    public Map<String, Object> currentUser() {

        Map<String,Object> map = new HashMap<>();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        map.put("user",user);
        return map;
    }



    @ApiOperation(value = "退出登录", notes = "退出登录")
    @PostMapping("/logout")
    @UnAuthentication
    public JsonResult logout(HttpServletRequest request) {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String tokenStr = httpServletRequest.getHeader("token");
        if (!StringUtils.isEmpty(tokenStr)) {
            JWTToken token = new JWTToken(tokenStr);
            redisService.delete("JWT:token:"+token.getSource()+":"+token.getType()+":refreshTime:" + token.getSignId());
            redisService.delete("JWT:token:"+token.getSource()+":"+token.getType()+":user:" + token.getSignId());
        }

        return new JsonResult().setSuccess(true);
    }


}
