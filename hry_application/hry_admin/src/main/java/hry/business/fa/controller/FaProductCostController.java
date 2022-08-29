/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:52:06 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaProductCost;
import hry.business.fa.service.FaProductCostService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaProductCostController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:52:06 
 */
@Api(value = "产品费用表 ", tags = "产品费用表 ", description = "产品费用表 ")
@RestController
@RequestMapping("/fa/faproductcost")
public class FaProductCostController extends BaseController {

	@Autowired
	private FaProductCostService faProductCostService;

	/**
     * <p> 产品费用表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:52:06 
     */
    @ApiOperation(value = "产品费用表 -id查询", notes = "产品费用表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaProductCost faProductCost = faProductCostService.get(id);
        if (faProductCost != null) {
            jsonResult.setObj(faProductCost);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品费用表 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:52:06 
     */
    @ApiOperation(value = "产品费用表 -添加", notes = "产品费用表 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaProductCost faProductCost) {
        JsonResult jsonResult = new JsonResult();
        faProductCostService.save(faProductCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费用表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:52:06 
     */
    @ApiOperation(value = "产品费用表 -修改", notes = "产品费用表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaProductCost faProductCost) {
        JsonResult jsonResult = new JsonResult();
        faProductCostService.update(faProductCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费用表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:52:06 
     */
    @ApiOperation(value = "产品费用表 -id删除", notes = "产品费用表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faProductCostService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费用表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:52:06 
     */
    @ApiOperation(value = "产品费用表 -分页查询", notes = "产品费用表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProductCost.class, request);
        return faProductCostService.findPageResult(filter);
    }




}
