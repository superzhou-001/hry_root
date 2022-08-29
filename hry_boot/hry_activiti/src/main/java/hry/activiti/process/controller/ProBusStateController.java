/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 11:00:17
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProBusState;
import hry.activiti.process.model.ProBusTable;
import hry.activiti.process.service.ProBusStateService;
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
 * <p> ProBusStateController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 11:00:17
 */
@Api(value = "业务状态管理", tags = "业务状态管理", description = "业务状态管理")
@RestController
@RequestMapping("/process/probusstate")
public class ProBusStateController extends BaseController {

	@Autowired
	private ProBusStateService proBusStateService;

	@Autowired
    private ProBusTableService proBusTableService;

	/**
     * <p> 业务状态管理-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 11:00:17
     */
    @ApiOperation(value = "业务状态管理-id查询", notes = "业务状态管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProBusState proBusState = proBusStateService.get(id);
        if (proBusState != null) {
            jsonResult.setObj(proBusState);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 业务状态管理-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 11:00:17
     */
    @ApiOperation(value = "业务状态管理-添加", notes = "业务状态管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProBusState proBusState) {
        JsonResult jsonResult = new JsonResult();
        proBusStateService.save(proBusState);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务状态管理-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 11:00:17
     */
    @ApiOperation(value = "业务状态管理-修改", notes = "业务状态管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProBusState proBusState) {
        JsonResult jsonResult = new JsonResult();
        proBusStateService.update(proBusState);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务状态管理-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 11:00:17
     */
    @ApiOperation(value = "业务状态管理-id删除", notes = "业务状态管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proBusStateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 业务状态管理-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-18 11:00:17
     */
    @ApiOperation(value = "业务状态管理-分页查询", notes = "业务状态管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProBusState.class, request);
        PageResult pageResult = proBusStateService.findPageResult(filter);
        List<ProBusState> rows = pageResult.getRows();

        if(pageResult!=null&&rows!=null&&rows.size()>0){
            for(ProBusState proBusTable : rows){
                ProBusTable table = proBusTableService.get(proBusTable.getTableId());
                if(table!=null){
                    proBusTable.setTableName(table.getName());
                }
            }
        }
        return pageResult;
    }

    @ApiOperation(value = "业务状态管理-查询全部", notes = "业务状态管理-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProBusState> findAll (
            HttpServletRequest request) {
        return proBusStateService.findAll();
    }

}
