/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:35:04
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProBusTable;
import hry.activiti.process.model.ProDefine;
import hry.activiti.process.service.ActivitiService;
import hry.activiti.process.service.ProBusTableService;
import hry.activiti.process.service.ProDefineService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProDefineController </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:35:04
 */
@Api(value = "流程定义", tags = "流程定义", description = "流程定义")
@RestController
@RequestMapping("/process/prodefine")
public class ProDefineController extends BaseController {

	@Autowired
	private ProDefineService proDefineService;

	@Autowired
    private ActivitiService activitiService;

	@Autowired
    private ProBusTableService proBusTableService;

	/**
     * <p> 流程定义-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:35:04
     */
    @ApiOperation(value = "流程定义-id查询", notes = "流程定义-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefine proDefine = proDefineService.get(id);
		proDefine.setDefineText(HtmlUtils.htmlUnescape(proDefine.getDefineText()));
        if (proDefine != null) {
            jsonResult.setObj(proDefine);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程定义-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:35:04
     */
    @ApiOperation(value = "流程定义-添加", notes = "流程定义-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefine proDefine) {
       return  proDefineService.saveAndNode(proDefine);
    }

    /**
     * <p> 流程定义-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:35:04
     */
    @ApiOperation(value = "流程定义-修改", notes = "流程定义-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefine proDefine) {
        return proDefineService.updateAndNode(proDefine,true);
    }

    /**
     * <p> 流程定义-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:35:04
     */
    @ApiOperation(value = "流程定义-id删除", notes = "流程定义-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ProDefine proDefine = proDefineService.get(id);
        if(proDefine.getIsDeploy()==1){
            return  jsonResult.setMsg("已发布的流程不能删除");
        }
        proDefineService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程定义-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:35:04
     */
    @ApiOperation(value = "流程定义-分页查询", notes = "流程定义-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefine.class, request);
        PageResult pageResult = proDefineService.findPageResult(filter);

        //查询业务表名称
        if(pageResult!=null&&pageResult.getRows()!=null){
            List<ProDefine> rows = pageResult.getRows();
            for(ProDefine proDefine : rows){
                ProBusTable proBusTable = proBusTableService.get(proDefine.getBusTableId());
                proDefine.setBusTableName(proBusTable.getName());

            }
        }
        return pageResult;
    }

    /**
     * <p> 流程定义-部署 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:36:51
     */
    @ApiOperation(value = "流程定义-部署发布", notes = "流程定义-部署发布")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/deploy/{id}")
    @UnAuthentication
    public JsonResult deploy (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        ProDefine proDefine = proDefineService.get(id);
        if(proDefine!=null) {
            return proDefineService.deploy(proDefine);
        }
        return new JsonResult();
    }


    /**
     * <p> 查询已发布的流程 </p>
     *
     * @author: liushilei
     * @Date: 2020-04-14 14:36:51
     */
    @ApiOperation(value = "查询已发布的流程", notes = "查询已发布的流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findDeploy")
    @UnAuthentication
    public List<ProDefine> findDeploy () {
        return  proDefineService.find(new QueryFilter(ProDefine.class).addFilter("isDeploy=",1));
    }



}
