/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-12 17:38:48 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplateSeal;
import hry.business.ct.service.CtContractTemplateSealService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtContractTemplateSealController </p>
 *
 * @author: yaoz
 * @Date: 2020-06-12 17:38:48 
 */
@Api(value = "合同模板印章", tags = "合同模板印章", description = "合同模板印章")
@RestController
@RequestMapping("/ct/ctcontracttemplateseal")
public class CtContractTemplateSealController extends BaseController {

	@Autowired
	private CtContractTemplateSealService ctContractTemplateSealService;

	/**
     * <p> 合同模板印章-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48 
     */
    @ApiOperation(value = "合同模板印章-id查询", notes = "合同模板印章-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtContractTemplateSeal ctContractTemplateSeal = ctContractTemplateSealService.get(id);
        if (ctContractTemplateSeal != null) {
            jsonResult.setObj(ctContractTemplateSeal);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同模板印章-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48 
     */
    @ApiOperation(value = "合同模板印章-添加", notes = "合同模板印章-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtContractTemplateSeal ctContractTemplateSeal) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateSealService.save(ctContractTemplateSeal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板印章-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48 
     */
    @ApiOperation(value = "合同模板印章-修改", notes = "合同模板印章-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtContractTemplateSeal ctContractTemplateSeal) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateSealService.update(ctContractTemplateSeal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板印章-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48 
     */
    @ApiOperation(value = "合同模板印章-id删除", notes = "合同模板印章-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateSealService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板印章-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48 
     */
    @ApiOperation(value = "合同模板印章-分页查询", notes = "合同模板印章-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractTemplateSeal.class, request);
        return ctContractTemplateSealService.findPageResult(filter);
    }

    /**
     * <p> 合同模板印章-列表查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-12 17:38:48
     */
    @ApiOperation(value = "合同模板印章-列表查询", notes = "合同模板印章-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            HttpServletRequest request) {
        return new JsonResult().setSuccess(true).setObj(ctContractTemplateSealService.findAll());
    }



}
