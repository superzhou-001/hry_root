/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:21
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefineNode;
import hry.activiti.process.service.ProDefineNodeService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p> ProDefineNodeController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:21
 */
@Api(value = "流程节点", tags = "流程节点", description = "流程节点")
@RestController
@RequestMapping("/process/prodefinenode")
public class ProDefineNodeController extends BaseController {

	@Autowired
	private ProDefineNodeService proDefineNodeService;

	/**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "流程节点-id查询", notes = "流程节点-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefineNode proDefineNode = proDefineNodeService.get(id);
        if (proDefineNode != null) {
            jsonResult.setObj(proDefineNode);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "查询流程节点配置", notes = "查询流程节点配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getConfig")
    public Map<String, Object> getConfig (
            @ApiParam(name = "defineKey", value = "defineKey", required = true) @RequestParam String defineKey,
                @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey

    ) {
        return proDefineNodeService.getConfig(defineKey,nodeKey);
    }


    /**
     * <p> 保存节点配置 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存节点配置", notes = "保存节点配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "nodeType", value = "节点类型1后台2前台", required = true) @RequestParam Integer nodeType,
            @ApiParam(name = "timeType", value = "时间类型1自然2工作", required = true) @RequestParam Integer timeType,
            @ApiParam(name = "timeDay", value = "业务状态", required = true) @RequestParam Integer timeDay,
            @ApiParam(name = "decideType", value = "会签决策类型", required = false) @RequestParam(required = false) Integer decideType,
            @ApiParam(name = "decideValue", value = "会签决策值", required = false) @RequestParam(required = false) Integer decideValue,
            @ApiParam(name = "personValue", value = "会签指定人", required = false) @RequestParam(required = false) String personValue

    ) {


        return proDefineNodeService.saveByFlow(defineId,nodeKey,nodeType,timeType,timeDay,decideType,decideValue,personValue);
    }



	/**
     * <p> 流程节点-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "流程节点-添加", notes = "流程节点-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefineNode proDefineNode) {
        JsonResult jsonResult = new JsonResult();
        proDefineNodeService.save(proDefineNode);
        return jsonResult.setSuccess(true);
    }




    /**
     * <p> 流程节点-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "流程节点-修改", notes = "流程节点-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefineNode proDefineNode) {
        JsonResult jsonResult = new JsonResult();
        proDefineNodeService.update(proDefineNode);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程节点-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "流程节点-id删除", notes = "流程节点-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefineNodeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程节点-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "流程节点-分页查询", notes = "流程节点-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefineNode.class, request);
        return proDefineNodeService.findPageResult(filter);
    }

}
