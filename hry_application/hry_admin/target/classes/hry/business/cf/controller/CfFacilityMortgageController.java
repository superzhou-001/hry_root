/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 15:55:35 
 */
package hry.business.cf.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.model.CfFacilityMortgage;
import hry.business.cf.service.CfFacilityMortgageService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CfFacilityMortgageController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 15:55:35 
 */
@Api(value = "抵质押物表", tags = "抵质押物表", description = "抵质押物表")
@RestController
@RequestMapping("/cf/cffacilitymortgage")
public class CfFacilityMortgageController extends BaseController {

	@Autowired
	private CfFacilityMortgageService cfFacilityMortgageService;

    /**
     * <p> 抵质押物表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 15:55:35 
     */
    @ApiOperation(value = "抵质押物表-分页查询", notes = "抵质押物表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CfFacilityMortgage.class, request);
        return cfFacilityMortgageService.findPageBySql(filter);
    }


    @ApiOperation(value = "抵质押物-添加", notes = "抵质押物-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/mortgageAdd")
    public JsonResult add (String jsonStr) {
        return cfFacilityMortgageService.mortgageAdd(jsonStr);
    }

    @ApiOperation(value = "抵质押物-id查询", notes = "抵质押物-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getMortgage/{id}")
    public JsonResult getMortgage (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        return cfFacilityMortgageService.getMortgage(id);
    }

    @ApiOperation(value = "抵质押物表-修改", notes = "抵质押物表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateMortgage")
    public JsonResult updateMortgage (String jsonStr) {
        return cfFacilityMortgageService.updateMortgage(jsonStr);
    }

    @ApiOperation(value = "抵质押物-id删除", notes = "抵质押物-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/removeMortgage/{id}")
    public JsonResult removeMortgage (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityMortgageService.delete(id);
        return jsonResult.setSuccess(true);
    }

}
