/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:17:01 
 */
package hry.business.cu.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuHouse;
import hry.business.cu.service.CuHouseService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CuHouseController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:17:01 
 */
@Api(value = "房产信息表", tags = "房产信息表", description = "房产信息表")
@RestController
@RequestMapping("/cu/cuhouse")
public class CuHouseController extends BaseController {

	@Autowired
	private CuHouseService cuHouseService;

	/**
     * <p> 房产信息表-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:17:01 
     */
    @ApiOperation(value = "房产信息表-id查询", notes = "房产信息表-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CuHouse cuHouse = cuHouseService.get(id);
        if (cuHouse != null) {
            jsonResult.setObj(cuHouse);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 房产信息表-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:17:01 
     */
    @ApiOperation(value = "房产信息表-添加", notes = "房产信息表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CuHouse cuHouse) {
        JsonResult jsonResult = new JsonResult();
        cuHouseService.save(cuHouse);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 房产信息表-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:17:01 
     */
    @ApiOperation(value = "房产信息表-修改", notes = "房产信息表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CuHouse cuHouse) {
        JsonResult jsonResult = new JsonResult();
        cuHouseService.update(cuHouse);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 房产信息表-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:17:01 
     */
    @ApiOperation(value = "房产信息表-id删除", notes = "房产信息表-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuHouseService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 房产信息表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:17:01 
     */
    @ApiOperation(value = "房产信息表-分页查询", notes = "房产信息表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuHouse.class, request);
        return cuHouseService.findPageResult(filter);
    }

}
