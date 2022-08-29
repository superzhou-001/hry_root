/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:22:58 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtTable;
import hry.business.ct.service.CtTableService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtTableController </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:22:58 
 */
@Api(value = "合同要素配置_表", tags = "合同要素配置_表", description = "合同要素配置_表")
@RestController
@RequestMapping("/ct/cttable")
public class CtTableController extends BaseController {

	@Autowired
	private CtTableService ctTableService;

	/**
     * <p> 合同要素配置表-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:22:58 
     */
    @ApiOperation(value = "合同要素配置表-id查询", notes = "合同要素配置表-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtTable ctTable = ctTableService.get(id);
        if (ctTable != null) {
            jsonResult.setObj(ctTable);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同要素配置表-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:22:58 
     */
    @ApiOperation(value = "合同要素配置表-添加", notes = "合同要素配置表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtTable ctTable) {
        JsonResult jsonResult = new JsonResult();
        if (ctTableService.listTableColumn(ctTable.getTableTable())){
            return jsonResult.setSuccess(false).setMsg("表不存在");
        }
        ctTableService.save(ctTable);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置表-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:22:58 
     */
    @ApiOperation(value = "合同要素配置表-修改", notes = "合同要素配置表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtTable ctTable) {
        JsonResult jsonResult = new JsonResult();
        ctTableService.update(ctTable);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置表-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:22:58 
     */
    @ApiOperation(value = "合同要素配置表-id删除", notes = "合同要素配置表-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctTableService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:22:58 
     */
    @ApiOperation(value = "合同要素配置表-分页查询", notes = "合同要素配置表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtTable.class, request);
        return ctTableService.findPageResult(filter);
    }

    @ApiOperation(value = "合同要素配置表-列表查询", notes = "合同要素配置表-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            @ApiParam(name = "tableType", value = "tableType", required = false) @RequestParam Integer tableType,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtTable.class);
        if(!StringUtils.isEmpty(tableType)){
            filter.addFilter("tableType=",tableType);
        }
        filter.addFilter("isOpen=",1);
        return new JsonResult().setSuccess(true).setObj(ctTableService.find(filter));
    }


    @ApiOperation(value = "合同要素配置表-修改开启关闭", notes = "合同要素配置表-修改开启关闭")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateOpen")
    public JsonResult updateOpen (
            @ApiParam(name = "id", value = "id", required = true) @RequestParam Long id,
            @ApiParam(name = "isOpen", value = "是否开启 1是 2.否", required = true) @RequestParam Integer isOpen
            ) {
        JsonResult jsonResult = new JsonResult();
        CtTable ctTable = new CtTable();
        ctTable.setId(id);
        ctTable.setIsOpen(isOpen);
        ctTableService.update(ctTable);
        return jsonResult.setSuccess(true);
    }

}
