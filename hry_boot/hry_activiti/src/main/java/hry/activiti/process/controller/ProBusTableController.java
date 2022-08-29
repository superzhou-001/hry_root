/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 10:59:51
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProBusTable;
import hry.activiti.process.service.ProBusTableService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProBusTableController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 10:59:51
 */
@Api(value = "业务表管理", tags = "业务表管理", description = "业务表管理")
@RestController
@RequestMapping("/process/probustable")
public class ProBusTableController extends BaseController {

	@Autowired
	private ProBusTableService proBusTableService;

	/**
     * <p> 业务表管理-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 10:59:51
     */
    @ApiOperation(value = "业务表管理-id查询", notes = "业务表管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProBusTable proBusTable = proBusTableService.get(id);
        if (proBusTable != null) {
            jsonResult.setObj(proBusTable);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 业务表管理-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 10:59:51
     */
    @ApiOperation(value = "业务表管理-添加", notes = "业务表管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProBusTable proBusTable) {
        JsonResult jsonResult = new JsonResult();
        proBusTableService.save(proBusTable);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务表管理-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 10:59:51
     */
    @ApiOperation(value = "业务表管理-修改", notes = "业务表管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProBusTable proBusTable) {
        JsonResult jsonResult = new JsonResult();
        proBusTableService.update(proBusTable);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务表管理-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 10:59:51
     */
    @ApiOperation(value = "业务表管理-id删除", notes = "业务表管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proBusTableService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务表管理-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 10:59:51
     */
    @ApiOperation(value = "业务表管理-分页查询", notes = "业务表管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProBusTable.class, request);
        return proBusTableService.findPageResult(filter);
    }

    @ApiOperation(value = "业务表管理-查询全部", notes = "业务表管理-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProBusTable> findAll (
            HttpServletRequest request) {
        return proBusTableService.findAll();
    }

}
