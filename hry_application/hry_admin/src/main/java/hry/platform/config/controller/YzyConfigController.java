/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 16:12:18
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.sms.service.impl.HrySmsServiceImpl;
import hry.platform.config.model.YzyConfig;
import hry.platform.config.service.YzyConfigService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.sms.SmsSendUtils;
import hry.util.sms.SmsSendVo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> YzyConfigController </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 16:12:18
 */
@Api(value = "易租云产品", tags = "易租云产品", description = "易租云产品")
@RestController
@RequestMapping("/config/yzyconfig")
public class YzyConfigController extends BaseController {

    @Autowired
    private YzyConfigService yzyConfigService;
    @Autowired
    private HrySmsServiceImpl hrySmsServiceImpl;

    /**
     * <p> 易租云产品-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-28 16:12:18
     */
    @ApiOperation(value = "易租云产品-id查询", notes = "易租云产品-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        YzyConfig yzyConfig = yzyConfigService.get(id);
        if (yzyConfig != null) {
            jsonResult.setObj(yzyConfig);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 易租云产品-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-28 16:12:18
     */
    @ApiOperation(value = "易租云产品-添加", notes = "易租云产品-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add(YzyConfig yzyConfig) {
        JsonResult jsonResult = new JsonResult();
        yzyConfigService.save(yzyConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 易租云产品-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-28 16:12:18
     */
    @ApiOperation(value = "易租云产品-修改", notes = "易租云产品-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify(YzyConfig yzyConfig) {
        JsonResult jsonResult = new JsonResult();
        yzyConfigService.update(yzyConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 易租云产品-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-28 16:12:18
     */
    @ApiOperation(value = "易租云产品-id删除", notes = "易租云产品-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        yzyConfigService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 易租云产品-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-05-28 16:12:18
     */
    @ApiOperation(value = "易租云产品-分页查询", notes = "易租云产品-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(YzyConfig.class, request);
        return yzyConfigService.findPageResult(filter);
    }


    @ApiOperation(value = "易租云产品-修改开启关闭", notes = "易租云产品-修改开启关闭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateOpen")
    public JsonResult updateOpen(
            @ApiParam(name = "id", value = "id", required = true) @RequestParam Long id,
            @ApiParam(name = "isOpen", value = "是否开启 1是 2.否", required = true) @RequestParam Integer isOpen
    ) {
        JsonResult jsonResult = new JsonResult();
        YzyConfig yzyConfig = new YzyConfig();
        yzyConfig.setId(id);
        yzyConfig.setIsOpen(isOpen);
        yzyConfigService.update(yzyConfig);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "易租云产品-获取产品信息", notes = "易租云产品-获取产品信息")
    @ControllerLogger
    @PostMapping(value = "/getYzyProduct")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult getYzyProduct() {
        return yzyConfigService.updateYzyProduct();
    }

    @ApiOperation(value = "易租云产品-短信测试", notes = "易租云产品-短信测试")
    @ControllerLogger
    @PostMapping(value = "/testSms")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public JsonResult testSms(
            @ApiParam(name = "mobile", value = "手机号", required = true) @RequestParam String mobile) {
        Map<String, String> param = new HashMap<>();
        //6位短信验证码
        int length = 6;
        //生成随机码
        String smsCode = RandomStringUtils.random(length, false, true);
        param.put("code",smsCode);
        SmsSendVo smsSendParam = new SmsSendVo(mobile, "+86", "1", param);
        return hrySmsServiceImpl.smsSend(smsSendParam);
    }

}
