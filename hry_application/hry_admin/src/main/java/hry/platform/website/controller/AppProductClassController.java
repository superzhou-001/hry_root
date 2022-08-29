/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:38:17
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.website.model.AppProductClass;
import hry.platform.website.service.AppProductClassService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> AppProductClassController </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:38:17
 */
@Api(value = "产品分类", tags = "产品分类", description = "产品分类")
@RestController
@RequestMapping("/website/appproductclass")
public class AppProductClassController extends BaseController {

	@Autowired
	private AppProductClassService appProductClassService;

	/**
     * <p> 产品分类-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "产品分类-id查询", notes = "产品分类-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppProductClass appProductClass = appProductClassService.get(id);
        if (appProductClass != null) {
            jsonResult.setObj(appProductClass);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 产品分类-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "产品分类-添加", notes = "产品分类-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (AppProductClass appProductClass) {
        JsonResult jsonResult = new JsonResult();
        appProductClassService.save(appProductClass);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品分类-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "产品分类-修改", notes = "产品分类-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (AppProductClass appProductClass) {
        JsonResult jsonResult = new JsonResult();
        appProductClassService.update(appProductClass);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品分类-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "产品分类-id删除", notes = "产品分类-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appProductClassService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 产品分类-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "产品分类-分页查询", notes = "产品分类-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppProductClass.class, request);
        return appProductClassService.findPageResult(filter);
    }



    /**
     * <p> 查询全部分类 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-24 10:38:17
     */
    @ApiOperation(value = "查询全部分类", notes = "查询全部分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<AppProductClass> findAll () {
        return appProductClassService.findAll();
    }


}
