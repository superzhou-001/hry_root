package hry.platform.sms;

import hry.security.jwt.annotation.UnAuthentication;
import hry.util.DrawPictureUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName CodeController
 * @Description: TODO 图像验证码
 * @Author yaozhuo
 * @Date 2020/4/28
 * @Version V1.0
 **/

@Api(value = "图像验证码类", tags = "图像验证码类", description = "图像验证码类")
@RestController
@RequestMapping("/code")
public class CodeController {

    /**
     * 注册验证码
     *
     * @param response
     * @param request
     */
    @ApiOperation(value = "注册验证码",httpMethod = "POST",notes = "注册验证码")
    @RequestMapping("/regCode")
    @UnAuthentication
    public void regCode(HttpServletResponse response, HttpServletRequest request,
                        @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        DrawPictureUtil drawPictureUtil = new DrawPictureUtil("regCode", 100, 30);
        drawPictureUtil.darw(request, response,uid);
    }

    /**
     * 登录验证码
     *
     * @param response
     * @param request
     */
    @ApiOperation(value = "登录验证码", httpMethod = "POST",notes = "登录验证码")
    @RequestMapping("/loginCode")
    @UnAuthentication
    public void loginCode(HttpServletResponse response, HttpServletRequest request,
                          @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        DrawPictureUtil drawPictureUtil = new DrawPictureUtil("loginCode", 100, 30);
        drawPictureUtil.darw(request, response,uid);
    }


    /**
     * 找回密码验证码
     *
     * @param response
     * @param request
     */
    @ApiOperation(value = "找回密码验证码",httpMethod = "POST", notes = "找回密码验证码")
    @RequestMapping("/forgetCode")
    @UnAuthentication
    public void forgetCode(HttpServletResponse response, HttpServletRequest request,
                           @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        DrawPictureUtil drawPictureUtil = new DrawPictureUtil("forgetCode", 100, 30);
        drawPictureUtil.darw(request, response,uid);
    }

    /**
     * 通用验证码
     *
     * @param response
     * @param request
     */
    @ApiOperation(value = "验证码",httpMethod = "POST", notes = "验证码")
    @RequestMapping("/getCode")
    @UnAuthentication
    public void getCode(HttpServletResponse response, HttpServletRequest request,
                           @ApiParam(name = "uid", value = "时间戳", required = true) @RequestParam String uid
    ) {
        DrawPictureUtil drawPictureUtil = new DrawPictureUtil("Mobile:regCode", 100, 30);
        drawPictureUtil.darw(request, response,uid);
    }

}
