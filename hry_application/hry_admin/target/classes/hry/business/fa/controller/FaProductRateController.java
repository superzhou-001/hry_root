/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:25:35 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaProductRate;
import hry.business.fa.service.FaProductRateService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaProductRateController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:25:35 
 */
@Api(value = "产品费率表 ", tags = "产品费率表 ", description = "产品费率表 ")
@RestController
@RequestMapping("/fa/faproductrate")
public class FaProductRateController extends BaseController {

	@Autowired
	private FaProductRateService faProductRateService;

	/**
     * <p> 产品费率表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:25:35 
     */
    @ApiOperation(value = "产品费率表 -id查询", notes = "产品费率表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaProductRate faProductRate = faProductRateService.get(id);
        if (faProductRate != null) {
            jsonResult.setObj(faProductRate);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品费率表 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:25:35 
     */
    @ApiOperation(value = "产品费率表 -添加", notes = "产品费率表 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaProductRate faProductRate) {
        JsonResult jsonResult = new JsonResult();
        faProductRateService.save(faProductRate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费率表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:25:35 
     */
    @ApiOperation(value = "产品费率表 -修改", notes = "产品费率表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaProductRate faProductRate) {
        JsonResult jsonResult = new JsonResult();
        faProductRateService.update(faProductRate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费率表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:25:35 
     */
    @ApiOperation(value = "产品费率表 -id删除", notes = "产品费率表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faProductRateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品费率表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:25:35 
     */
    @ApiOperation(value = "产品费率表 -分页查询", notes = "产品费率表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProductRate.class, request);
        return faProductRateService.findPageResult(filter);
    }

}
