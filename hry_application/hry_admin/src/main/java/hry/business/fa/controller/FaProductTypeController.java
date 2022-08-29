/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:22:19 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaProductType;
import hry.business.fa.service.FaProductTypeService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> FaProductTypeController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:22:19 
 */
@Api(value = "产品类型表 ", tags = "产品类型表 ", description = "产品类型表 ")
@RestController
@RequestMapping("/fa/faproducttype")
public class FaProductTypeController extends BaseController {

	@Autowired
	private FaProductTypeService faProductTypeService;

	/**
     * <p> 产品类型表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19 
     */
    @ApiOperation(value = "产品类型表 -id查询", notes = "产品类型表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaProductType faProductType = faProductTypeService.get(id);
        if (faProductType != null) {
            jsonResult.setObj(faProductType);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品类型表 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19 
     */
    @ApiOperation(value = "产品类型表 -添加", notes = "产品类型表 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaProductType faProductType) {
        JsonResult jsonResult = new JsonResult();
        faProductTypeService.save(faProductType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品类型表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19 
     */
    @ApiOperation(value = "产品类型表 -修改", notes = "产品类型表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaProductType faProductType) {
        JsonResult jsonResult = new JsonResult();
        faProductTypeService.update(faProductType);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品类型表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19 
     */
    @ApiOperation(value = "产品类型表 -id删除", notes = "产品类型表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faProductTypeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品类型表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19 
     */
    @ApiOperation(value = "产品类型表 -分页查询", notes = "产品类型表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaProductType.class, request);
        return faProductTypeService.findPageResult(filter);
    }

    /**
     * <p> 产品类型表---查询所有 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:22:19
     */
    @ApiOperation(value = "产品类型表 -查询所有", notes = "产品类型表 -查询所有")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public JsonResult findList (HttpServletRequest request) {
        List<FaProductType> type = faProductTypeService.findAll();
        return new JsonResult(true).setObj(type);
    }


}
