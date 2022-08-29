/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:40:57
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefineBtn;
import hry.activiti.process.service.ProDefineBtnService;
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
 * <p> ProDefineBtnController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:40:57
 */
@Api(value = "流程按钮", tags = "流程按钮", description = "流程按钮")
@RestController
@RequestMapping("/process/prodefinebtn")
public class ProDefineBtnController extends BaseController {

	@Autowired
	private ProDefineBtnService proDefineBtnService;

	/**
     * <p> 流程按钮-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:40:57
     */
    @ApiOperation(value = "流程按钮-id查询", notes = "流程按钮-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefineBtn proDefineBtn = proDefineBtnService.get(id);
        if (proDefineBtn != null) {
            jsonResult.setObj(proDefineBtn);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程按钮-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:40:57
     */
    @ApiOperation(value = "流程按钮-添加", notes = "流程按钮-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefineBtn proDefineBtn) {
        JsonResult jsonResult = new JsonResult();
        proDefineBtnService.save(proDefineBtn);
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存流程按钮", notes = "保存流程按钮")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "btnName", value = "按钮名称", required = true) @RequestParam String btnName,
            @ApiParam(name = "buTable", value = "业务主表", required = true) @RequestParam String buTable,
            @ApiParam(name = "buState", value = "业务状态", required = true) @RequestParam String buState,
            @ApiParam(name = "controlName", value = "控制器名称", required = true) @RequestParam String controlName,
            @ApiParam(name = "btnType", value = "按钮类型1通过2拒绝3打回4打回至自定义", required = true) @RequestParam Integer btnType

    ) {

        ProDefineBtn proDefineBtn = new ProDefineBtn();
        proDefineBtn.setBtnName(btnName);
        proDefineBtn.setBuTable(buTable);
        proDefineBtn.setBuState(buState);
        proDefineBtn.setControlName(controlName);
        proDefineBtn.setBtnType(btnType);



        return proDefineBtnService.saveByFlow(defineId,nodeKey,proDefineBtn);
    }

    /**
     * <p> 流程按钮-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:40:57
     */
    @ApiOperation(value = "流程按钮-修改", notes = "流程按钮-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefineBtn proDefineBtn) {
        JsonResult jsonResult = new JsonResult();
        proDefineBtnService.update(proDefineBtn);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程按钮-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:40:57
     */
    @ApiOperation(value = "流程按钮-id删除", notes = "流程按钮-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefineBtnService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程按钮-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:40:57
     */
    @ApiOperation(value = "流程按钮-分页查询", notes = "流程按钮-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefineBtn.class, request);
        return proDefineBtnService.findPageResult(filter);
    }

}
