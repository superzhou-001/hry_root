/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 10:36:12 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFactoringRate;
import hry.business.fa.service.FaFactoringRateService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaFactoringRateController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 10:36:12 
 */
@Api(value = "保理流程费率信息", tags = "保理流程费率信息", description = "保理流程费率信息")
@RestController
@RequestMapping("/fa/fafactoringrate")
public class FaFactoringRateController extends BaseController {

	@Autowired
	private FaFactoringRateService faFactoringRateService;

	/**
     * <p> 保理流程费率信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 10:36:12 
     */
    @ApiOperation(value = "保理流程费率信息-id查询", notes = "保理流程费率信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaFactoringRate faFactoringRate = faFactoringRateService.get(id);
        if (faFactoringRate != null) {
            jsonResult.setObj(faFactoringRate);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 保理流程费率信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 10:36:12 
     */
    @ApiOperation(value = "保理流程费率信息-添加", notes = "保理流程费率信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaFactoringRate faFactoringRate) {
        JsonResult jsonResult = new JsonResult();
        faFactoringRateService.save(faFactoringRate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费率信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 10:36:12 
     */
    @ApiOperation(value = "保理流程费率信息-修改", notes = "保理流程费率信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaFactoringRate faFactoringRate) {
        JsonResult jsonResult = new JsonResult();
        faFactoringRateService.update(faFactoringRate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费率信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 10:36:12 
     */
    @ApiOperation(value = "保理流程费率信息-id删除", notes = "保理流程费率信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faFactoringRateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理流程费率信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 10:36:12 
     */
    @ApiOperation(value = "保理流程费率信息-分页查询", notes = "保理流程费率信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringRate.class, request);
        return faFactoringRateService.findPageResult(filter);
    }

}
