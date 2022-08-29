/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:07
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefineForm;
import hry.activiti.process.service.ProDefineFormService;
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
 * <p> ProDefineFormController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:07
 */
@Api(value = "流程表单", tags = "流程表单", description = "流程表单")
@RestController
@RequestMapping("/process/prodefineform")
public class ProDefineFormController extends BaseController {

	@Autowired
	private ProDefineFormService proDefineFormService;


	/**
     * <p> 流程表单-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:07
     */
    @ApiOperation(value = "流程表单-id查询", notes = "流程表单-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefineForm proDefineForm = proDefineFormService.get(id);
        if (proDefineForm != null) {
            jsonResult.setObj(proDefineForm);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程表单-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:07
     */
    @ApiOperation(value = "流程表单-添加", notes = "流程表单-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefineForm proDefineForm) {
        JsonResult jsonResult = new JsonResult();
        proDefineFormService.save(proDefineForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存流程表单", notes = "保存流程表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "formId", value = "表单ID", required = true) @RequestParam Long formId,
            @ApiParam(name = "type", value = "表单类型1主表单2子表单", required = true) @RequestParam Integer type

    ) {

        ProDefineForm form = new ProDefineForm();
        form.setFormId(formId);
        form.setType(type);

        return proDefineFormService.saveByFlow(defineId,nodeKey,form);
    }



    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "删除流程表单", notes = "删除流程表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/deleteByFlow")
    public JsonResult deleteByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "formId", value = "表单ID", required = true) @RequestParam Long formId,
            @ApiParam(name = "type", value = "表单类型1主表单2子表单", required = true) @RequestParam Integer type

    ) {

        return proDefineFormService.deleteByFlow(defineId,nodeKey,formId,type);
    }


    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "是否编辑", notes = "是否编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/setEdit")
    public JsonResult setEdit (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "formId", value = "表单ID", required = true) @RequestParam Long formId,
            @ApiParam(name = "type", value = "表单类型1主表单2子表单", required = true) @RequestParam Integer type,
            @ApiParam(name = "isEdit", value = "0不可编辑,可编辑", required = true) @RequestParam Integer isEdit

    ) {

        return proDefineFormService.setEdit(defineId,nodeKey,formId,type,isEdit);
    }


    /**
     * <p> 流程表单-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:07
     */
    @ApiOperation(value = "流程表单-修改", notes = "流程表单-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefineForm proDefineForm) {
        JsonResult jsonResult = new JsonResult();
        proDefineFormService.update(proDefineForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程表单-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:07
     */
    @ApiOperation(value = "流程表单-id删除", notes = "流程表单-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefineFormService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程表单-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:07
     */
    @ApiOperation(value = "流程表单-分页查询", notes = "流程表单-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefineForm.class, request);
        return proDefineFormService.findPageResult(filter);
    }

}
