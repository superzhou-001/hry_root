/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-09 11:30:50 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplateElement;
import hry.business.ct.service.CtContractTemplateElementService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtContractTemplateElementController </p>
 *
 * @author: yaoz
 * @Date: 2020-06-09 11:30:50 
 */
@Api(value = "合同模板要素", tags = "合同模板要素", description = "合同模板要素")
@RestController
@RequestMapping("/ct/ctcontracttemplateelement")
public class CtContractTemplateElementController extends BaseController {

	@Autowired
	private CtContractTemplateElementService ctContractTemplateElementService;

	/**
     * <p> 合同模板要素-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-09 11:30:50 
     */
    @ApiOperation(value = "合同模板要素-id查询", notes = "合同模板要素-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtContractTemplateElement ctContractTemplateElement = ctContractTemplateElementService.get(id);
        if (ctContractTemplateElement != null) {
            jsonResult.setObj(ctContractTemplateElement);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同模板要素-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-09 11:30:50 
     */
    @ApiOperation(value = "合同模板要素-添加", notes = "合同模板要素-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtContractTemplateElement ctContractTemplateElement) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateElementService.save(ctContractTemplateElement);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板要素-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-09 11:30:50 
     */
    @ApiOperation(value = "合同模板要素-修改", notes = "合同模板要素-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtContractTemplateElement ctContractTemplateElement) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateElementService.update(ctContractTemplateElement);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板要素-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-09 11:30:50 
     */
    @ApiOperation(value = "合同模板要素-id删除", notes = "合同模板要素-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateElementService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板要素-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-09 11:30:50 
     */
    @ApiOperation(value = "合同模板要素-分页查询", notes = "合同模板要素-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractTemplateElement.class, request);
        return ctContractTemplateElementService.findPageResult(filter);
    }

}
