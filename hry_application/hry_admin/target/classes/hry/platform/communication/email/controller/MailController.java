/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:54:27
 */
package hry.platform.communication.email.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.email.model.Mail;
import hry.platform.communication.email.service.MailService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> MailController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:54:27
 */
@Api(value = "邮件发送记录", tags = "邮件发送记录", description = "邮件发送记录")
@RestController
@RequestMapping("/communication/email/mail")
public class MailController extends BaseController {

	@Autowired
	private MailService mailService;

	/**
     * <p> 邮件发送记录-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:54:27
     */
    @ApiOperation(value = "邮件发送记录-id查询", notes = "邮件发送记录-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		Mail mail = mailService.get(id);
		if (mail != null) {
		    mail.setContent(HtmlUtils.htmlUnescape(mail.getContent()));
            jsonResult.setObj(mail);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 邮件发送记录-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:54:27
     */
    @ApiOperation(value = "邮件发送记录-添加", notes = "邮件发送记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (Mail mail) {
        JsonResult jsonResult = new JsonResult();
        mailService.save(mail);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件发送记录-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:54:27
     */
    @ApiOperation(value = "邮件发送记录-修改", notes = "邮件发送记录-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (Mail mail) {
        JsonResult jsonResult = new JsonResult();
        mailService.update(mail);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件发送记录-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:54:27
     */
    @ApiOperation(value = "邮件发送记录-id删除", notes = "邮件发送记录-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mailService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 邮件发送记录-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 18:54:27
     */
    @ApiOperation(value = "邮件发送记录-分页查询", notes = "邮件发送记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(Mail.class, request);
        PageResult pageResult = mailService.findPageResult(filter);
        if (pageResult != null) {
            List<Mail> mailList = pageResult.getRows();
            if (mailList != null && mailList.size() > 0) {
                mailList.stream().forEach(mail -> {
                    mail.setContent(HtmlUtils.htmlUnescape(mail.getContent()));
                });
            }
        }
        return pageResult;
    }

}
