/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:34:06
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProMainForm;
import hry.activiti.process.model.ProSonForm;
import hry.activiti.process.service.ProMainFormService;
import hry.activiti.process.service.ProSonFormService;
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
 * <p> ProSonFormController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:34:06
 */
@Api(value = "子表单", tags = "子表单", description = "子表单")
@RestController
@RequestMapping("/process/prosonform")
public class ProSonFormController extends BaseController {

	@Autowired
	private ProSonFormService proSonFormService;

	@Autowired
    private ProMainFormService proMainFormService;

	/**
     * <p> 子表单-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:34:06
     */
    @ApiOperation(value = "子表单-id查询", notes = "子表单-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProSonForm proSonForm = proSonFormService.get(id);
        if (proSonForm != null) {
            jsonResult.setObj(proSonForm);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 子表单-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:34:06
     */
    @ApiOperation(value = "子表单-添加", notes = "子表单-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProSonForm proSonForm) {
        JsonResult jsonResult = new JsonResult();
        ProSonForm form = proSonFormService.get(new QueryFilter(ProSonForm.class).addFilter("formKey=", proSonForm.getFormKey()));
        if(form!=null){
            return  jsonResult.setMsg("表单key不能重复");
        }
        proSonFormService.save(proSonForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 子表单-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:34:06
     */
    @ApiOperation(value = "子表单-修改", notes = "子表单-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProSonForm proSonForm) {
        JsonResult jsonResult = new JsonResult();
        proSonFormService.update(proSonForm);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 子表单-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:34:06
     */
    @ApiOperation(value = "子表单-id删除", notes = "子表单-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proSonFormService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 子表单-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 18:34:06
     */
    @ApiOperation(value = "子表单-分页查询", notes = "子表单-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProSonForm.class, request);
        PageResult pageResult = proSonFormService.findPageResult(filter);
        List<ProSonForm> list = pageResult.getRows();
        if(list!=null&&list.size()>0){
            for (ProSonForm proSonForm : list){

                ProMainForm form = proMainFormService.get(proSonForm.getMainId());
                if(form!=null){
                    proSonForm.setMainName(form.getViewName());
                }
            }
        }

        return pageResult;
    }


    /**
     * <p> 根据主表单ID查全部子表单 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-20 18:33:52
     */
    @ApiOperation(value = "根据主表单ID查全部子表单", notes = "根据主表单ID查全部子表单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findByMainId")
    public List<ProSonForm> findByMainId ( @ApiParam(name = "mainId", value = "mainId", required = true) @RequestParam Long mainId) {
        return proSonFormService.find(new QueryFilter(ProSonForm.class).addFilter("mainId=",mainId));
    }


    @ApiOperation(value = "查全部子表单--流程节点设置", notes = "查全部子表单--流程节点设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findByNode")
    public List<ProSonForm> findByNode (
            @ApiParam(name = "mainId", value = "mainId", required = true) @RequestParam Long mainId,
            @ApiParam(name = "defineId", value = "流程定义id", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "节点key", required = true) @RequestParam String nodeKey

    ) {
        return proSonFormService.findByNode(mainId,defineId,nodeKey);
    }


}
