/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:33:52
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProMainForm;
import hry.activiti.process.service.ProMainFormService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProMainFormController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:33:52
 */
@Api(value = "主表单", tags = "主表单", description = "主表单")
@RestController
@RequestMapping("/process/promainform")
public class ProMainFormController extends BaseController {

	@Autowired
	private ProMainFormService proMainFormService;

	/**
     * <p> 主表单-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-id查询", notes = "主表单-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProMainForm proMainForm = proMainFormService.get(id);
        if (proMainForm != null) {
            jsonResult.setObj(proMainForm);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 主表单-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-添加", notes = "主表单-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProMainForm proMainForm) {
        JsonResult jsonResult = new JsonResult();
        ProMainForm form = proMainFormService.get(new QueryFilter(ProMainForm.class).addFilter("formKey=", proMainForm.getFormKey()));
        if(form!=null){
            return  jsonResult.setMsg("表单key不能重复");
        }
        proMainFormService.save(proMainForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 主表单-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-修改", notes = "主表单-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProMainForm proMainForm) {
        JsonResult jsonResult = new JsonResult();
        proMainFormService.update(proMainForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 主表单-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-id删除", notes = "主表单-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proMainFormService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 主表单-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-分页查询", notes = "主表单-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProMainForm.class, request);
        return proMainFormService.findPageResult(filter);
    }

    /**
     * <p> 主表单-查询全部 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:33:52
     */
    @ApiOperation(value = "主表单-查询全部", notes = "主表单-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProMainForm> findAll (HttpServletRequest request) {
        return proMainFormService.findAll();
    }


    @ApiOperation(value = "查全部主表单--流程节点设置", notes = "查全部主表单--流程节点设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findByNode")
    public List<ProMainForm> findByNode (
            @ApiParam(name = "defineId", value = "流程定义id", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "节点key", required = true) @RequestParam String nodeKey
    ) {
        return proMainFormService.findByNode(defineId,nodeKey);
    }




}
