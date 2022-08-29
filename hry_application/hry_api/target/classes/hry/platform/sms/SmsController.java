package hry.platform.sms;

import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.util.SpringUtil;
import hry.util.email.EmailParam;
import hry.util.email.EmailRunnable;
import hry.util.email.EmailUtils;
import hry.util.httpRequest.IpUtil;
import hry.util.properties.PropertiesUtils;
import hry.util.sms.SmsSendUtils;
import hry.util.sms.SmsSendVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.ThreadPool;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName SmsController
 * @Description: TODO
 * @Author yaozhuo
 * @Date 2020/4/28
 * @Version V1.0
 **/
@Api(value = "消息类", tags = "消息类", description = "消息类")
@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private CuCustomerService cuCustomerService;

    @Autowired
    private RedisService redisService;

    //短信限流次数
    private static final Integer LIMIT_SMS = 3;

    private String tel = "tel:";

    /**
     * 注册发送短信验证码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "注册发送手机验证码", notes = "注册发送手机验证码")
    @PostMapping("/user/sendPhoneValidCode")
    @UnAuthentication
    public JsonResult sendPhoneValidCode(
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile,
            @ApiParam(name = "regCode", value = "图形验证码", required = true) @RequestParam String regCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        // 验证图形验证码
        String code = redisService.get("Mobile:regCode" + uid);
        if(StringUtils.isEmpty(code)){
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        }else{
            if (code == null || !regCode.equals(code.toString())) {
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }
        //限流
        jsonResult = checkSmsSend(request);
        if (jsonResult.getSuccess().equals(false)) {
            return jsonResult;
        }
        if (!verificationOrder(request).getSuccess()) {
            return verificationOrder(request);
        }

        if (StringUtils.isEmpty(mobile)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("电话号码不能为空");
            return jsonResult;
        }

        // 发送短信验证码
        SmsSendVo smsSendParam = new SmsSendVo(mobile, "", "1", null);
        //String sendSmsCode = SmsSendUtils.sendSms(smsSendParam);
        redisService.save("SMS:setPhone:reg:" + mobile, "111111", 120);

        jsonResult.setSuccess(true);
        jsonResult.setMsg("短信发送成功");
        return jsonResult;
    }

    /**
     * 获得手机找回密码短信验证码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "忘记密码-找回密码短信验证码", notes = "忘记密码-找回密码短信验证码")
    @PostMapping("/smsForgetMobile")
    @UnAuthentication
    public JsonResult smsForgetMobile(
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile,
            @ApiParam(name = "forgetCode", value = "图形验证码", required = false) @RequestParam String forgetCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();

        // 验证图形验证码
        String code = redisService.get("Mobile:regCode" + uid);
        if (StringUtils.isEmpty(forgetCode)) {
            jsonResult.setMsg("图形验证码不能为空");
            return jsonResult;
        } else {
            if (code == null || !forgetCode.equals(code.toString())) {
                jsonResult.setMsg("图形验证码错误");
                return jsonResult;
            }
        }

        //手机号，国家不能为空
        if (StringUtils.isEmpty(mobile)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("手机号码不能为空");
            return jsonResult;
        }

        //查看手机号是否存在
        Boolean b = cuCustomerService.getCustomerByMobile(mobile);
        if (b) {
            return jsonResult.setSuccess(false).setMsg("手机号不存在");
        }


        //限流
        jsonResult = checkSmsSend(request);
        if (jsonResult.getSuccess().equals(false)) {
            return jsonResult;
        }
        if (!verificationOrder(request).getSuccess()) {
            return verificationOrder(request);
        }

        // 发送短信验证码
        SmsSendVo smsSendParam = new SmsSendVo(mobile, "", "1", null);
        //String sendSmsCode = SmsSendUtils.sendSms(smsSendParam);
        redisService.save("SMS:smsForgetMobile:" + mobile, "111111", 120);

        //删除图形验证码
        redisService.delete("Mobile:regCode" + uid);

        jsonResult.setSuccess(true);
        jsonResult.setMsg("发送成功");
        return jsonResult;
    }

    /**
     * 发送短信验证码
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "发送手机验证码", notes = "发送手机验证码")
    @PostMapping("/sendPhoneCode")
    @UnAuthentication
    public JsonResult sendPhoneCode(
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile,
            @ApiParam(name = "viewCode", value = "图形验证码", required = true) @RequestParam String viewCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(mobile)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("电话号码不能为空");
            return jsonResult;
        }
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

        //限流
        jsonResult = checkSmsSend(request);
        if (jsonResult.getSuccess().equals(false)) {
            return jsonResult;
        }
        if (!verificationOrder(request).getSuccess()) {
            return verificationOrder(request);
        }

        // 发送短信验证码
        SmsSendVo smsSendParam = new SmsSendVo(mobile, "+86", "1", null);
        String sendSmsCode = SmsSendUtils.sendSms(smsSendParam);
        redisService.save("SMS:setPhone:" + mobile, sendSmsCode, 120);

        jsonResult.setSuccess(true);
        jsonResult.setMsg("短信发送成功");
        return jsonResult;
    }


    @ApiOperation(value = "发送手机验证码-需要登录", notes = "发送手机验证码-需要登录")
    @PostMapping("/user/sendUpdatePhone")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult sendUpdatePhone(
            @ApiParam(name = "viewCode", value = "图形验证码", required = true) @RequestParam String viewCode,
            @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid,
            HttpServletRequest request) {
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
        //限流
        jsonResult = checkSmsSend(request);
        if (jsonResult.getSuccess().equals(false)) {
            return jsonResult;
        }
        if (!verificationOrder(request).getSuccess()) {
            return verificationOrder(request);
        }

        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        String mobile = user.getMobile();

        // 发送短信验证码
        SmsSendVo smsSendParam = new SmsSendVo(mobile, "+86", "1", null);
        String sendSmsCode = SmsSendUtils.sendSms(smsSendParam);
        redisService.save("SMS:setPhone:" + mobile, sendSmsCode, 120);

        jsonResult.setSuccess(true);
        jsonResult.setMsg("短信发送成功");
        return jsonResult;
    }


    /**
     * 用户注册发送验证码---邮箱
     * @param request
     * @return
     */
    @ApiOperation(value = "用户注册邮箱发送验证码", notes = "用户注册邮箱发送验证码")
    @PostMapping("/registEmailCode")
    @ResponseBody
    @UnAuthentication
    public JsonResult registEmailCode(
            @ApiParam(name = "email", value = "邮箱号", required = true) @RequestParam("email") String email,
            @ApiParam(name = "regCode", value = "图形验证码", required = false) @RequestParam("regCode") String regCode,
            HttpServletRequest request) {
        if (!verificationOrder(request).getSuccess()) {
            return verificationOrder(request);
        }
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isEmpty(email)) {// 邮箱号判空
            jsonResult.setSuccess(false);
            return jsonResult;
        }
        // 发送邮件
        List<String> toList = new ArrayList<>();
        toList.add(email);
        EmailParam param = new EmailParam("hryEmailServiceImpl",toList,"zh_CN","1");
        EmailUtils.sendEmailByThread(param);

        jsonResult.setSuccess(true);
        return jsonResult;
    }


    /**
     * 短信限流
     *
     * @param request
     * @return
     */
    private static JsonResult checkSmsSend(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        RedisService redisService = SpringUtil.getBean("redisService");
        String ipAdrress = getIpAdrress(request);
        SimpleDateFormat sp = new SimpleDateFormat("HHmm");
        String fdate = sp.format(new Date());
        String count = redisService.get("resetPwd:" + ipAdrress + ":" + fdate);
        if (count == null) {
            redisService.save("resetPwd:" + ipAdrress + ":" + fdate, "1", 60);
        } else {
            if (Integer.valueOf(count) > LIMIT_SMS) {
                jsonResult.setSuccess(false);
                jsonResult.setMsg("短信发送频繁");
                return jsonResult;
            } else {
                redisService.save("resetPwd:" + ipAdrress + ":" + fdate, (Integer.valueOf(count) + 1) + "", 120);
            }
        }
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    /**
     * 获取Ip地址
     *
     * @param request
     * @return
     */
    public static String getIpAdrress(HttpServletRequest request) {
        String Xip = request.getHeader("X-Real-IP");
        String XFor = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = XFor.indexOf(",");
            if (index != -1) {
                return XFor.substring(0, index);
            } else {
                return XFor;
            }
        }
        XFor = Xip;
        if (!StringUtils.isEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
            return XFor;
        }
        if (!StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.isEmpty(XFor) || "unknown".equalsIgnoreCase(XFor)) {
            XFor = request.getRemoteAddr();
        }
        return XFor;
    }

    private JsonResult verificationOrder(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        String ip = IpUtil.getIp(request);
        RedisService redisService = SpringUtil.getBean("redisService");

        Integer telTime = 5;
        Integer telCount = 3;
        String telValue = redisService.get(tel + ip);
        if (telValue == null || "".equals(telValue)) {
            redisService.save(tel + ip, "1", telTime);
        } else {
            Integer num = Integer.valueOf(telValue);
            if (num >= telCount) {
                jsonResult.setCode("0000");
                jsonResult.setMsg("发送频繁");
                jsonResult.setSuccess(false);
                return jsonResult;
            }
            num++;
            redisService.save(tel + ip, String.valueOf(num), telTime);
        }
        jsonResult.setSuccess(true);
        return jsonResult;
    }


}
