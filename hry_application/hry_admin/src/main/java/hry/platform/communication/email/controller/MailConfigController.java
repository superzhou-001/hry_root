/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:56:45 
 */
package hry.platform.communication.email.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.email.model.MailConfig;
import hry.platform.communication.email.service.MailConfigService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> MailConfigController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:56:45 
 */
@Api(value = "邮件配置", tags = "邮件配置", description = "邮件配置")
@RestController
@RequestMapping("/communication/email/mailconfig")
public class MailConfigController extends BaseController {

	@Autowired
	private MailConfigService mailConfigService;

	/**
     * <p> 邮件配置-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:56:45 
     */
    @ApiOperation(value = "邮件配置-id查询", notes = "邮件配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		MailConfig mailConfig = mailConfigService.get(id);
        jsonResult.setObj(mailConfig);
        return jsonResult.setSuccess(true);
    }

	/**
     * <p> 邮件配置-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:56:45 
     */
    @ApiOperation(value = "邮件配置-添加", notes = "邮件配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (MailConfig mailConfig) {
        JsonResult jsonResult = new JsonResult();
        mailConfigService.save(mailConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件配置-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:56:45 
     */
    @ApiOperation(value = "邮件配置-修改", notes = "邮件配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (MailConfig mailConfig) {
        JsonResult jsonResult = new JsonResult();
        mailConfigService.update(mailConfig);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件配置-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:56:45 
     */
    @ApiOperation(value = "邮件配置-id删除", notes = "邮件配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mailConfigService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件配置-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:56:45 
     */
    @ApiOperation(value = "邮件配置-分页查询", notes = "邮件配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MailConfig.class, request);
        return mailConfigService.findPageResult(filter);
    }

}
