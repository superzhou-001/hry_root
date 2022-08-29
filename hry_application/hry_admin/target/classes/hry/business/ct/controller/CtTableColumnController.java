/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:23:51 
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtTable;
import hry.business.ct.model.CtTableColumn;
import hry.business.ct.service.CtTableColumnService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CtTableColumnController </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:23:51 
 */
@Api(value = "合同要素配置_字段", tags = "合同要素配置_字段", description = "合同要素配置_字段")
@RestController
@RequestMapping("/ct/cttablecolumn")
public class CtTableColumnController extends BaseController {

	@Autowired
	private CtTableColumnService ctTableColumnService;

	/**
     * <p> 合同要素配置-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:23:51 
     */
    @ApiOperation(value = "合同要素配置-id查询", notes = "合同要素配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CtTableColumn ctTableColumn = ctTableColumnService.get(id);
        if (ctTableColumn != null) {
            jsonResult.setObj(ctTableColumn);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 合同要素配置-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:23:51 
     */
    @ApiOperation(value = "合同要素配置-添加", notes = "合同要素配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CtTableColumn ctTableColumn) {
        JsonResult jsonResult = new JsonResult();
        ctTableColumnService.save(ctTableColumn);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:23:51 
     */
    @ApiOperation(value = "合同要素配置-修改", notes = "合同要素配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CtTableColumn ctTableColumn) {
        JsonResult jsonResult = new JsonResult();
        ctTableColumnService.update(ctTableColumn);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:23:51 
     */
    @ApiOperation(value = "合同要素配置-id删除", notes = "合同要素配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctTableColumnService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同要素配置-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 11:23:51 
     */
    @ApiOperation(value = "合同要素配置-分页查询", notes = "合同要素配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtTableColumn.class, request);
        String tableId = request.getParameter("tableId");
        if(!StringUtils.isEmpty(tableId)){
            filter.addFilter("tableId=",Long.valueOf(tableId));
        }
        String tableColumnName = request.getParameter("tableColumnName");
        if(!StringUtils.isEmpty(tableColumnName)){
            filter.addFilter("tableColumnName=",tableColumnName);
        }
        return ctTableColumnService.findPageResult(filter);
    }

    @ApiOperation(value = "合同要素配置-列表查询", notes = "合同要素配置-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            @ApiParam(name = "tableId", value = "表id", required = true) @RequestParam Long tableId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtTableColumn.class, request);
        filter.addFilter("tableId=",tableId);
        filter.addFilter("isOpen=",1);
        return new JsonResult().setSuccess(true).setObj(ctTableColumnService.find(filter));
    }

    @ApiOperation(value = "合同要素配置-修改开启关闭", notes = "合同要素配置-修改开启关闭")
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
        CtTableColumn ctTableColumn = new CtTableColumn();
        ctTableColumn.setId(id);
        ctTableColumn.setIsOpen(isOpen);
        ctTableColumnService.update(ctTableColumn);
        return jsonResult.setSuccess(true);
    }


    @ApiOperation(value = "合同要素配置-获取表中字段自动添加", notes = "合同要素配置-获取表中字段自动添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addFieldAll")
    public JsonResult addFieldAll (@ApiParam(name = "tableId", value = "id", required = true) @RequestParam String tableId) {
        return ctTableColumnService.addFieldAll(Long.valueOf(tableId));
    }

}
