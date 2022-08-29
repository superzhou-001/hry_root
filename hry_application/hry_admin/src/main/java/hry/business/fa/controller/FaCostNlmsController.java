/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:50:34 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaCostNlms;
import hry.business.fa.service.FaCostNlmsService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaCostNlmsController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:50:34 
 */
@Api(value = "费用计算公式表 ", tags = "费用计算公式表 ", description = "费用计算公式表 ")
@RestController
@RequestMapping("/fa/facostnlms")
public class FaCostNlmsController extends BaseController {

	@Autowired
	private FaCostNlmsService faCostNlmsService;

	/**
     * <p> 费用计算公式表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34 
     */
    @ApiOperation(value = "费用计算公式表 -id查询", notes = "费用计算公式表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaCostNlms faCostNlms = faCostNlmsService.get(id);
        if (faCostNlms != null) {
            jsonResult.setObj(faCostNlms);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 费用计算公式表 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34 
     */
    @ApiOperation(value = "费用计算公式表 -添加", notes = "费用计算公式表 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaCostNlms faCostNlms) {
        JsonResult jsonResult = new JsonResult();
        faCostNlmsService.save(faCostNlms);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用计算公式表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34 
     */
    @ApiOperation(value = "费用计算公式表 -修改", notes = "费用计算公式表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaCostNlms faCostNlms) {
        JsonResult jsonResult = new JsonResult();
        faCostNlmsService.update(faCostNlms);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用计算公式表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34 
     */
    @ApiOperation(value = "费用计算公式表 -id删除", notes = "费用计算公式表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faCostNlmsService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用计算公式表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34 
     */
    @ApiOperation(value = "费用计算公式表 -分页查询", notes = "费用计算公式表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaCostNlms.class, request);
        return faCostNlmsService.findPageResult(filter);
    }

    /**
     * <p> 费用计算公式表 -列表查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:50:34
     */
    @ApiOperation(value = "费用计算公式表 -列表查询", notes = "费用计算公式表 -列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public JsonResult findList (
            HttpServletRequest request) {
        return new JsonResult(true).setObj(faCostNlmsService.findAll());
    }

}
