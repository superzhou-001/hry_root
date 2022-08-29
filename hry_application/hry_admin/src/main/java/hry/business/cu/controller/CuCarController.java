/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:45:28 
 */
package hry.business.cu.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCar;
import hry.business.cu.service.CuCarService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CuCarController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:45:28 
 */
@Api(value = "车辆信息表", tags = "车辆信息表", description = "车辆信息表")
@RestController
@RequestMapping("/cu/cucar")
public class CuCarController extends BaseController {

	@Autowired
	private CuCarService cuCarService;

	/**
     * <p> 车辆信息表-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:45:28 
     */
    @ApiOperation(value = "车辆信息表-id查询", notes = "车辆信息表-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CuCar cuCar = cuCarService.get(id);
        if (cuCar != null) {
            jsonResult.setObj(cuCar);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 车辆信息表-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:45:28 
     */
    @ApiOperation(value = "车辆信息表-添加", notes = "车辆信息表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CuCar cuCar) {
        JsonResult jsonResult = new JsonResult();
        cuCarService.save(cuCar);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 车辆信息表-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:45:28 
     */
    @ApiOperation(value = "车辆信息表-修改", notes = "车辆信息表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CuCar cuCar) {
        JsonResult jsonResult = new JsonResult();
        cuCarService.update(cuCar);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 车辆信息表-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:45:28 
     */
    @ApiOperation(value = "车辆信息表-id删除", notes = "车辆信息表-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuCarService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 车辆信息表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-01 16:45:28 
     */
    @ApiOperation(value = "车辆信息表-分页查询", notes = "车辆信息表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuCar.class, request);
        return cuCarService.findPageResult(filter);
    }

}
