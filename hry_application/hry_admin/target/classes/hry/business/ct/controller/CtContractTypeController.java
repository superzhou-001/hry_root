/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:48:43 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.ct.model.CtContractType;
import hry.business.ct.service.CtContractTypeService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtContractTypeController </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:48:43 
 */
@Api(value = "合同分类", tags = "合同分类", description = "合同分类")
@RestController
@RequestMapping("/ct/ctcontracttype")
public class CtContractTypeController extends BaseController {

	@Autowired
	private CtContractTypeService ctContractTypeService;

	/**
     * <p> 合同分类-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43 
     */
    @ApiOperation(value = "合同分类-id查询", notes = "合同分类-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtContractType ctContractType = ctContractTypeService.get(id);
        if (ctContractType != null) {
            jsonResult.setObj(ctContractType);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同分类-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43 
     */
    @ApiOperation(value = "合同分类-添加", notes = "合同分类-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtContractType ctContractType) {
        JsonResult jsonResult = new JsonResult();
        ctContractTypeService.save(ctContractType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同分类-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43 
     */
    @ApiOperation(value = "合同分类-修改", notes = "合同分类-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtContractType ctContractType) {
        JsonResult jsonResult = new JsonResult();
        ctContractTypeService.update(ctContractType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同分类-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43 
     */
    @ApiOperation(value = "合同分类-id删除", notes = "合同分类-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractTypeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同分类-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43 
     */
    @ApiOperation(value = "合同分类-分页查询", notes = "合同分类-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractType.class, request);
        return ctContractTypeService.findPageResult(filter);
    }

    /**
     * <p> 合同分类-列表查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:48:43
     */
    @ApiOperation(value = "合同分类-列表查询", notes = "合同分类-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            HttpServletRequest request) {
        return new JsonResult().setSuccess(true).setObj(ctContractTypeService.findAll());
    }

}
