/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:37:13
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppProduct;
import hry.platform.website.service.AppProductService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppProductController </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:37:13
 */
@Api(value = "产品", tags = "产品", description = "产品")
@RestController
@RequestMapping("/website/appproduct")
public class AppProductController extends BaseController {

	@Autowired
	private AppProductService appProductService;

	/**
     * <p> 产品-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-id查询", notes = "产品-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppProduct appProduct = appProductService.get(id);
        if (appProduct != null) {
//            if(!StringUtils.isEmpty(appProduct.getProductShow())){
//                appProduct.setProductShow(HtmlUtils.htmlUnescape(appProduct.getProductShow()));
//            }
//            if(!StringUtils.isEmpty(appProduct.getProductApproval())){
//                appProduct.setProductApproval(HtmlUtils.htmlUnescape(appProduct.getProductApproval()));
//            }
            jsonResult.setObj(appProduct);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-添加", notes = "产品-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (AppProduct appProduct) {
        JsonResult jsonResult = new JsonResult();
        appProductService.save(appProduct);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-修改", notes = "产品-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (AppProduct appProduct) {
        JsonResult jsonResult = new JsonResult();
        appProductService.update(appProduct);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-id删除", notes = "产品-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appProductService.delete(id);
        return jsonResult.setSuccess(true);
    }



    /**
     * <p> 产品-复制 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-复制", notes = "产品-复制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/copy/{id}")
    public JsonResult copy (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        AppProduct appProduct = appProductService.get(id);
        appProduct.setId(null);
        appProductService.save(appProduct);
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 产品-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:37:13
     */
    @ApiOperation(value = "产品-分页查询", notes = "产品-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppProduct.class, request);
        filter.setOrderby("orderNum");
        return appProductService.findPageResult(filter);
    }

}
