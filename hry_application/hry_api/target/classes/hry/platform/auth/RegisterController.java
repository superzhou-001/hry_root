package hry.platform.auth;

import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.controller.BaseController;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.SpringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName RegisterController
 * @Description: TODO
 * @Author yaozhuo
 * @Date 2020/4/27
 * @Version V1.0
 **/
@RestController
@RequestMapping("/reg")
@Api(value = "注册认证类", description = "注册认证类", tags = "注册认证类")
public class RegisterController extends BaseController {

    @Resource
    private CuCustomerService cuCustomerService;


    @ApiOperation(value = "注册方法", notes = "注册方法")
    @PostMapping("/regist")
    @UnAuthentication
    public JsonResult regist(
            @ApiParam(name = "regCode", value = "图形验证码", required = true) @RequestParam String regCode,
            @ApiParam(name = "verifyCode", value = "短信验证码", required = true) @RequestParam String verifyCode,
            @ApiParam(name = "passWord", value = "密码", required = true) @RequestParam String passWord,
            @ApiParam(name = "repeatpassWord", value = "重复密码", required = true) @RequestParam String repeatpassWord,
            @ApiParam(name = "mobile", value = "手机", required = true) @RequestParam String mobile,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isEmpty(mobile)) {
            jsonResult.setMsg("手机号码不能为空");
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

        RedisService redisService = SpringUtil.getBean("redisService");
        // 验证图形验证码
        String code = redisService.get("Mobile:regCode"+uid);
        if(StringUtils.isEmpty(code)){
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        }else{
            if (code == null || !regCode.equals(code.toString())) {
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }


        //获得手机验证码
        String session_pwSmsCode = redisService.get("SMS:setPhone:reg:" + mobile);
        //判断验证码是否正确
        if (!verifyCode.equals(session_pwSmsCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("短信验证码错误");
            return jsonResult;
        }

        CuCustomer user = cuCustomerService.checkMobile(mobile);
        if (user != null) {
            return jsonResult.setSuccess(false).setMsg("此用户已存在，请勿重复注册");
        }


        jsonResult = cuCustomerService.regist(null,null,passWord,null,mobile);

        // 删除验证码
        redisService.delete("SMS:setPhone:reg:" + mobile);
        //删除图形验证码
        redisService.delete("Mobile:regCode"+uid);

        return jsonResult;
    }



}
