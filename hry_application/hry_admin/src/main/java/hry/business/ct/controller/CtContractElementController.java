/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:49:25 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.ct.model.CtContractElement;
import hry.business.ct.service.CtContractElementService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtContractElementController </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:49:25 
 */
@Api(value = "合同要素", tags = "合同要素", description = "合同要素")
@RestController
@RequestMapping("/ct/ctcontractelement")
public class CtContractElementController extends BaseController {

	@Autowired
	private CtContractElementService ctContractElementService;

	/**
     * <p> 合同要素-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25 
     */
    @ApiOperation(value = "合同要素-id查询", notes = "合同要素-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtContractElement ctContractElement = ctContractElementService.get(id);
        if (ctContractElement != null) {
            jsonResult.setObj(ctContractElement);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同要素-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25 
     */
    @ApiOperation(value = "合同要素-添加", notes = "合同要素-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtContractElement ctContractElement) {
        JsonResult jsonResult = new JsonResult();
        ctContractElementService.save(ctContractElement);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25 
     */
    @ApiOperation(value = "合同要素-修改", notes = "合同要素-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtContractElement ctContractElement) {
        JsonResult jsonResult = new JsonResult();
        ctContractElementService.update(ctContractElement);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25 
     */
    @ApiOperation(value = "合同要素-id删除", notes = "合同要素-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractElementService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25 
     */
    @ApiOperation(value = "合同要素-分页查询", notes = "合同要素-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractElement.class, request);
        return ctContractElementService.findPageResult(filter);
    }
    /**
     * <p> 合同要素-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:49:25
     */
    @ApiOperation(value = "合同要素-列表查询", notes = "合同要素-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult list (
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractElement.class, request);
        return new JsonResult().setSuccess(true).setObj(ctContractElementService.findAll());
    }

}
