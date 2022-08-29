package hry.platform;

import hry.bean.JsonResult;
import hry.business.cu.model.*;
import hry.business.cu.service.*;
import hry.core.mvc.controller.BaseController;
import hry.redis.RedisService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
/**
 * @Author: yaozh
 * @Description:意向客户
 * @Date:
 */
@Api(value = "意向客户管理", tags = "意向客户管理", description = "意向客户管理")
@RestController
@RequestMapping("/manage/intention")
public class IntentionController extends BaseController {

    @Autowired
    private CuIntentionService cuIntentionService;
    @Autowired
    private RedisService redisService;


    @ApiOperation(value = "添加意向客户信息", notes = "添加意向客户信息")
    @ControllerLogger
    @PostMapping(value = "/addIntention")
    @UnAuthentication
    public JsonResult addIntention(CuIntention cuIntention,
                   @ApiParam(name = "verifyCode", value = "短信验证码", required = true) @RequestParam String verifyCode,
                   @ApiParam(name = "viewCode", value = "图形验证码", required = true) @RequestParam String viewCode,
                   @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid) {
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
        String session_pwSmsCode = redisService.get("SMS:setPhone:" + cuIntention.getMovePhone());
        //判断验证码是否正确
        if (!verifyCode.equals(session_pwSmsCode)) {
            jsonResult.setSuccess(false);
            jsonResult.setMsg("验证码错误");
            return jsonResult;
        }
        cuIntention.setSource(2);//默认网站
        cuIntention.setType(2);//默认企业
        jsonResult = cuIntentionService.addIntention(cuIntention);
        if(jsonResult.getSuccess()){
            // 删除验证码
            redisService.delete("SMS:setPhone:" + cuIntention.getMovePhone());
            //删除图形验证码
            redisService.delete("Mobile:regCode"+uid);
        }
        return jsonResult;
    }

}
