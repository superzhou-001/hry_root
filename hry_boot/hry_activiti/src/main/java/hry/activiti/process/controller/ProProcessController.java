/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:55:47
 */
package hry.activiti.process.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import hry.activiti.process.model.ProProcess;
import hry.activiti.process.service.ActivitiService;
import hry.activiti.process.service.ProProcessService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.jwt.JWTContext;
import hry.security.logger.ControllerLogger;
import hry.util.flowModel.FlowParams;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ProProcessController </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:55:47
 */
@Api(value = "流程实例", tags = "流程实例", description = "流程实例")
@RestController
@RequestMapping("/process/proprocess")
public class ProProcessController extends BaseController {

	@Autowired
	private ProProcessService proProcessService;

	@Autowired
    private ActivitiService activitiService;


	/**
     * <p> 流程实例-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "流程实例-id查询", notes = "流程实例-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProProcess proProcess = proProcessService.get(id);
        if (proProcess != null) {
            jsonResult.setObj(proProcess);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 流程实例-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "处理流程--加载流程节点配置", notes = "处理流程--加载流程节点配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/getTaskConfig")
    public JsonResult getTaskConfig (
            @ApiParam(name = "taskId", value = "任务Id", required = true) @RequestParam String taskId,
            @ApiParam(name = "nodeKey", value = "节点key", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "processId", value = "流程Id", required = true) @RequestParam Long processId
    ) {

        return proProcessService.getTaskConfig(taskId,nodeKey,processId);

    }


    /**
     * <p> 流程实例-启动流程 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "流程实例-启动流程", notes = "流程实例-启动流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/start")
    public JsonResult start ( @ApiParam(name = "defineKey", value = "流程定义key", required = true) @RequestParam String defineKey,
                              HttpServletRequest request

                              ) {
//        @ApiParam(name = "params", value = "流程启动参数", required = false) @RequestParam String params

        String params = request.getParameter("params");

         //发起人ID
         Long userId = JWTContext.getUserId();

         Map<String,Object> map = new HashMap<>();

         if(!StringUtils.isEmpty(params)){
             params = HtmlUtils.htmlUnescape(params);
             try {
                 JSONObject paramsObj = JSONObject.parseObject(params);
                 map =  paramsObj.getInnerMap();
             }catch (Exception e){
                 return new JsonResult("启动参数格式不正确，必须为json");
             }
         }

         FlowParams flowParams = new FlowParams();
         flowParams.setStartUserId(userId);
         flowParams.setDefineKey(defineKey);
         flowParams.setAttributeMap(map);
         return  proProcessService.start(flowParams);
    }


    /**
     * <p> 查询我的任务 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "查询我的任务--未处理", notes = "查询我的任务--未处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findMyTask")
    public PageResult findMyTask (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam Integer pageSize
    ) {
        Long userId = JWTContext.getUserId();
        if(userId==null){
            userId = 1L;
        }
        return proProcessService.findMyTask(page,pageSize,userId);
    }


    /**
     * <p> 查询我的任务 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "查询我的任务--已处理", notes = "查询我的任务--已处理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findMyHisTask")
    public PageResult findMyHisTask (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam Integer pageSize
    ) {
        Long userId = JWTContext.getUserId();
        if(userId==null){
            userId = 1L;
        }
        return proProcessService.findMyHisTask(page,pageSize,userId);
    }


    /**
     * <p> 查询全部任务 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "查询全部任务", notes = "查询全部任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAllTask")
    public PageResult findAllTask (  @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam Integer page,
                                     @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam Integer pageSize) {
        return proProcessService.findAllTask(page,pageSize);
    }



    /**
     * <p> 流程节点--完成一个任务</p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "完成一个任务", notes = "完成一个任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/completeTask")
    public JsonResult completeTask (
            @ApiParam(name = "taskId", value = "任务id", required = true) @RequestParam String taskId,
            @ApiParam(name = "btnId", value = "按钮ID", required = true) @RequestParam Long btnId,
            @ApiParam(name = "params", value = "流程参数", required = true) @RequestParam String params
    ) {
        FlowParams paramsObj = JSON.parseObject(HtmlUtils.htmlUnescape(params), FlowParams.class);
        return proProcessService.completeTask(taskId,btnId,paramsObj);
    }





    /**
     * <p> 查看运行流程图 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "查看运行流程图", notes = "查看运行流程图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/flowImg")
    public void flowImg (@ApiParam(name = "piId", value = "piId", required = true) @RequestParam String piId,
                         HttpServletResponse response) {
        activitiService.genProcessDiagram(response,piId);
    }


    /**
     * <p> 流程实例-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:55:47
     */
    @ApiOperation(value = "流程实例-分页查询", notes = "流程实例-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProProcess.class, request);
        return proProcessService.findPageResult(filter);
    }


    /**
     * <p> 查看流程日志 </p>
     *
     * @author: liushilei
     */
    @ApiOperation(value = "查看流程日志", notes = "查看流程日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/flowLog")
    public List<JSONObject> flowLog (@ApiParam(name = "piId", value = "piId", required = true) @RequestParam String piId) {
        return activitiService.findFlowLog(piId);
    }

}
