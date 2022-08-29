/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-11 14:14:15 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaFactoringBill;
import hry.business.fa.service.FaBillService;
import hry.business.fa.service.FaFactoringBillService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaFactoringBillController </p>
 *
 * @author: yaoz
 * @Date: 2020-08-11 14:14:15 
 */
@Api(value = "保理业务关联票据", tags = "保理业务关联票据", description = "保理业务关联票据")
@RestController
@RequestMapping("/fa/fafactoringbill")
public class FaFactoringBillController extends BaseController {

	@Autowired
	private FaFactoringBillService faFactoringBillService;

    @Autowired
    private FaBillService faBillService;

	/**
     * <p> 保理业务关联票据-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-11 14:14:15 
     */
    @ApiOperation(value = "保理业务关联票据-id查询", notes = "保理业务关联票据-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaFactoringBill faFactoringBill = faFactoringBillService.get(id);
        if (faFactoringBill != null) {
            jsonResult.setObj(faFactoringBill);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 保理业务关联票据-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-11 14:14:15 
     */
    @ApiOperation(value = "保理业务关联票据-添加", notes = "保理业务关联票据-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaFactoringBill faFactoringBill) {
        JsonResult jsonResult = new JsonResult();
        faFactoringBillService.save(faFactoringBill);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理业务关联票据-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-11 14:14:15 
     */
    @ApiOperation(value = "保理业务关联票据-修改", notes = "保理业务关联票据-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaFactoringBill faFactoringBill) {
        JsonResult jsonResult = new JsonResult();
        faFactoringBillService.update(faFactoringBill);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理业务关联票据-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-11 14:14:15 
     */
    @ApiOperation(value = "保理业务关联票据-id删除", notes = "保理业务关联票据-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faFactoringBillService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理业务关联票据-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-11 14:14:15 
     */
    @ApiOperation(value = "保理业务关联票据-分页查询", notes = "保理业务关联票据-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringBill.class, request);
        return faFactoringBillService.findPageResult(filter);
    }

    @ApiOperation(value = "保理业务关联票据-项目id查询", notes = "保理业务关联票据-项目id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByProjectId")
    public JsonResult list (
            @ApiParam(name = "projectId", value = "项目id", required = true) @RequestParam Long projectId,
            HttpServletRequest request) {
        return new JsonResult().setSuccess(true).setObj(faBillService.findBillByProjectId(projectId));
    }

}
