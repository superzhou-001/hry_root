/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:40 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.ct.model.CtContractSeal;
import hry.business.ct.service.CtContractSealService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtContractSealController </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:40 
 */
@Api(value = "印章配置", tags = "印章配置", description = "印章配置")
@RestController
@RequestMapping("/ct/ctcontractseal")
public class CtContractSealController extends BaseController {

	@Autowired
	private CtContractSealService ctContractSealService;

	/**
     * <p> 印章配置-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40 
     */
    @ApiOperation(value = "印章配置-id查询", notes = "印章配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtContractSeal ctContractSeal = ctContractSealService.get(id);
        if (ctContractSeal != null) {
            jsonResult.setObj(ctContractSeal);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 印章配置-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40 
     */
    @ApiOperation(value = "印章配置-添加", notes = "印章配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtContractSeal ctContractSeal) {
        JsonResult jsonResult = new JsonResult();
        ctContractSealService.save(ctContractSeal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 印章配置-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40 
     */
    @ApiOperation(value = "印章配置-修改", notes = "印章配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtContractSeal ctContractSeal) {
        JsonResult jsonResult = new JsonResult();
        ctContractSealService.update(ctContractSeal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 印章配置-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40 
     */
    @ApiOperation(value = "印章配置-id删除", notes = "印章配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractSealService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 印章配置-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40 
     */
    @ApiOperation(value = "印章配置-分页查询", notes = "印章配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractSeal.class, request);
        return ctContractSealService.findPageResult(filter);
    }

    /**
     * <p> 印章配置-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:40
     */
    @ApiOperation(value = "印章配置-列表查询", notes = "印章配置-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractSeal.class, request);
        return new JsonResult().setSuccess(true).setObj(ctContractSealService.findAll());
    }

}
