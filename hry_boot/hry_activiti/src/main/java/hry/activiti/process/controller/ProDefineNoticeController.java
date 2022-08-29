/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:56
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefineNotice;
import hry.activiti.process.service.ProDefineNoticeService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ProDefineNoticeController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:56
 */
@Api(value = "流程消息", tags = "流程消息", description = "流程消息")
@RestController
@RequestMapping("/process/prodefinenotice")
public class ProDefineNoticeController extends BaseController {

	@Autowired
	private ProDefineNoticeService proDefineNoticeService;

	/**
     * <p> 流程消息-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:56
     */
    @ApiOperation(value = "流程消息-id查询", notes = "流程消息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefineNotice proDefineNotice = proDefineNoticeService.get(id);
        if (proDefineNotice != null) {
            jsonResult.setObj(proDefineNotice);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程消息-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:56
     */
    @ApiOperation(value = "流程消息-添加", notes = "流程消息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefineNotice proDefineNotice) {
        JsonResult jsonResult = new JsonResult();
        proDefineNoticeService.save(proDefineNotice);
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存流程通知", notes = "保存流程通知")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "type", value = "通知类型", required = true) @RequestParam Integer type,
            @ApiParam(name = "templateId", value = "模板名称", required = true) @RequestParam Long templateId,
            @ApiParam(name = "personValue", value = "通知人", required = true) @RequestParam String personValue

    ) {

        ProDefineNotice proDefineNotice = new ProDefineNotice();
        proDefineNotice.setType(type);
        proDefineNotice.setTemplateId(templateId);
        proDefineNotice.setPersonValue(personValue);

        return proDefineNoticeService.saveByFlow(defineId,nodeKey,proDefineNotice);
    }


    /**
     * <p> 流程消息-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:56
     */
    @ApiOperation(value = "流程消息-修改", notes = "流程消息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefineNotice proDefineNotice) {
        JsonResult jsonResult = new JsonResult();
        proDefineNoticeService.update(proDefineNotice);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程消息-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:56
     */
    @ApiOperation(value = "流程消息-id删除", notes = "流程消息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefineNoticeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程消息-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:56
     */
    @ApiOperation(value = "流程消息-分页查询", notes = "流程消息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefineNotice.class, request);
        return proDefineNoticeService.findPageResult(filter);
    }

}
