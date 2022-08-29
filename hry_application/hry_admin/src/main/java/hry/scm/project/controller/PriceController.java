/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-17 15:08:55 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.Price;
import hry.scm.project.service.PriceService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.HttpServletRequestUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> PriceController </p>
 *
 * @author: luyue
 * @Date: 2020-07-17 15:08:55 
 */
@Api(value = "价格记录管理", tags = "价格记录管理", description = "价格记录管理")
@RestController
@RequestMapping("/project/price")
public class PriceController extends BaseController {

	@Autowired
	private PriceService priceService;

	/**
     * <p> 价格记录管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55 
     */
/*    @ApiOperation(value = "价格记录管理-id查询", notes = "价格记录管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		Price price = priceService.get(id);
        if (price != null) {
            jsonResult.setObj(price);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	*//**
     * <p> 价格记录管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55 
     *//*
    @ApiOperation(value = "价格记录管理-添加", notes = "价格记录管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (Price price) {
        JsonResult jsonResult = new JsonResult();
        priceService.save(price);
        return jsonResult.setSuccess(true);
    }

    *//**
     * <p> 价格记录管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55 
     *//*
    @ApiOperation(value = "价格记录管理-修改", notes = "价格记录管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (Price price) {
        JsonResult jsonResult = new JsonResult();
        priceService.update(price);
        return jsonResult.setSuccess(true);
    }

    *//**
     * <p> 价格记录管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55 
     *//*
    @ApiOperation(value = "价格记录管理-id删除", notes = "价格记录管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        priceService.delete(id);
        return jsonResult.setSuccess(true);
    }

    *//**
     * <p> 价格记录管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55 
     *//*
    @ApiOperation(value = "价格记录管理-分页查询", notes = "价格记录管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(Price.class, request);
        return priceService.findPageResult(filter);
    }*/

    /**
     * <p> 最近60天价格记录 </p>
     *
     * @author: luyue
     * @Date: 2020-07-17 15:08:55
     */
    @ApiOperation(value = "最近60天价格记录", notes = "最近60天价格记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public JsonResult findList (
            @ApiParam(name = "goodsName", value = "货品名称", required = true) @RequestParam String goodsName,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return priceService.findList(params);
    }

}
