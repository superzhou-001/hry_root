/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:59:19 
 */
package hry.platform.communication.email.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.email.model.MailTemplate;
import hry.platform.communication.email.service.MailTemplateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> MailTemplateController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:59:19 
 */
@Api(value = "邮件模版", tags = "邮件模版", description = "邮件模版")
@RestController
@RequestMapping("/communication/email/mailtemplate")
public class MailTemplateController extends BaseController {

	@Autowired
	private MailTemplateService mailTemplateService;

	/**
     * <p> 邮件模版-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:59:19 
     */
    @ApiOperation(value = "邮件模版-id查询", notes = "邮件模版-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		MailTemplate mailTemplate = mailTemplateService.get(id);
        jsonResult.setObj(mailTemplate);
        return jsonResult.setSuccess(true);
    }

	/**
     * <p> 邮件模版-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:59:19 
     */
    @ApiOperation(value = "邮件模版-添加", notes = "邮件模版-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (MailTemplate mailTemplate) {
        JsonResult jsonResult = new JsonResult();
        mailTemplateService.save(mailTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件模版-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:59:19 
     */
    @ApiOperation(value = "邮件模版-修改", notes = "邮件模版-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (MailTemplate mailTemplate) {
        JsonResult jsonResult = new JsonResult();
        mailTemplateService.update(mailTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件模版-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:59:19 
     */
    @ApiOperation(value = "邮件模版-id删除", notes = "邮件模版-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mailTemplateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件模版-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:59:19 
     */
    @ApiOperation(value = "邮件模版-分页查询", notes = "邮件模版-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MailTemplate.class, request);
        return mailTemplateService.findPageResult(filter);
    }

}
