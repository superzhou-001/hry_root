/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:29:01
 */
package hry.platform.communication.sms.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.communication.sms.model.AppSmsTemplate;
import hry.platform.communication.sms.service.AppSmsTemplateService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppSmsTemplateController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:29:01
 */
@Api(value = "短信模版", tags = "短信模版", description = "短信模版")
@RestController
@RequestMapping("/communication/appsmstemplate")
public class AppSmsTemplateController extends BaseController {

	@Autowired
	private AppSmsTemplateService appSmsTemplateService;

	/**
     * <p> 短信模版-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:29:01
     */
    @ApiOperation(value = "短信模版-id查询", notes = "短信模版-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppSmsTemplate appSmsTemplate = appSmsTemplateService.get(id);
        jsonResult.setObj(appSmsTemplate);
        return jsonResult.setSuccess(true);
    }

	/**
     * <p> 短信模版-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:29:01
     */
    @ApiOperation(value = "短信模版-添加", notes = "短信模版-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AppSmsTemplate appSmsTemplate) {
        JsonResult jsonResult = new JsonResult();
        //根据tempType判断是否存在
        QueryFilter queryFilter = new QueryFilter(AppSmsTemplate.class);
        queryFilter.addFilter("tempType=",appSmsTemplate.getTempType());
        AppSmsTemplate appSms = appSmsTemplateService.get(queryFilter);
        if(appSms!=null){
            return jsonResult.setMsg("模板类型不能重复");
        }

        appSmsTemplateService.save(appSmsTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信模版-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:29:01
     */
    @ApiOperation(value = "短信模版-修改", notes = "短信模版-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AppSmsTemplate appSmsTemplate) {
        JsonResult jsonResult = new JsonResult();
        //根据tempType判断是否存在
        QueryFilter queryFilter = new QueryFilter(AppSmsTemplate.class);
        queryFilter.addFilter("tempType=",appSmsTemplate.getTempType());
        AppSmsTemplate appSms = appSmsTemplateService.get(queryFilter);
        if(appSms!=null&&!appSms.getId().equals(appSmsTemplate.getId())){
            return jsonResult.setMsg("模板类型不能重复");
        }
        appSmsTemplateService.update(appSmsTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信模版-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:29:01
     */
    @ApiOperation(value = "短信模版-id删除", notes = "短信模版-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appSmsTemplateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 短信模版-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-23 15:29:01
     */
    @ApiOperation(value = "短信模版-分页查询", notes = "短信模版-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppSmsTemplate.class, request);
        return appSmsTemplateService.findPageResult(filter);
    }

}
