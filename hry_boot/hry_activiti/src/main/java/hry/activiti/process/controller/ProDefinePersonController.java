/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:42:17
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefinePerson;
import hry.activiti.process.service.ProDefinePersonService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ProDefinePersonController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:42:17
 */
@Api(value = "流程人员", tags = "流程人员", description = "流程人员")
@RestController
@RequestMapping("/process/prodefineperson")
public class ProDefinePersonController extends BaseController {

	@Autowired
	private ProDefinePersonService proDefinePersonService;

	/**
     * <p> 流程人员-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:42:17
     */
    @ApiOperation(value = "流程人员-id查询", notes = "流程人员-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefinePerson proDefinePerson = proDefinePersonService.get(id);
        if (proDefinePerson != null) {
            jsonResult.setObj(proDefinePerson);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程人员-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:42:17
     */
    @ApiOperation(value = "流程人员-添加", notes = "流程人员-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefinePerson proDefinePerson) {
        JsonResult jsonResult = new JsonResult();
        proDefinePersonService.save(proDefinePerson);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程人员-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:42:17
     */
    @ApiOperation(value = "流程人员-修改", notes = "流程人员-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefinePerson proDefinePerson) {
        JsonResult jsonResult = new JsonResult();
        proDefinePersonService.update(proDefinePerson);
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存流程人员配置", notes = "保存流程人员配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "personType", value = "personType", required = true) @RequestParam Integer personType,
            @ApiParam(name = "personValue", value = "personValue", required = true) @RequestParam String personValue

    ) {

        ProDefinePerson proDefinePerson = new ProDefinePerson();
        proDefinePerson.setPersonType(personType);
        //1 4不保存value
        if(ProDefinePerson.PERSONTYPE_1!=personType&&ProDefinePerson.PERSONTYPE_4!=personType) {
            proDefinePerson.setPersonValue(personValue);
        }

        return proDefinePersonService.saveByFlow(defineId,nodeKey,proDefinePerson);
    }



    /**
     * <p> 流程人员-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:42:17
     */
    @ApiOperation(value = "流程人员-id删除", notes = "流程人员-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefinePersonService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程人员-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:42:17
     */
    @ApiOperation(value = "流程人员-分页查询", notes = "流程人员-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefinePerson.class, request);
        return proDefinePersonService.findPageResult(filter);
    }

}
