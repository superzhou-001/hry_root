/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:42:39
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProPersonGroup;
import hry.activiti.process.service.ProPersonGroupService;
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
 * <p> ProPersonGroupController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:42:39
 */
@Api(value = "人员组", tags = "人员组", description = "人员组")
@RestController
@RequestMapping("/process/propersongroup")
public class ProPersonGroupController extends BaseController {

	@Autowired
	private ProPersonGroupService proPersonGroupService;

	/**
     * <p> 人员组-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-id查询", notes = "人员组-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProPersonGroup proPersonGroup = proPersonGroupService.get(id);
        if (proPersonGroup != null) {
            jsonResult.setObj(proPersonGroup);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 人员组-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-添加", notes = "人员组-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProPersonGroup proPersonGroup) {
        JsonResult jsonResult = new JsonResult();
        proPersonGroupService.save(proPersonGroup);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 人员组-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-修改", notes = "人员组-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProPersonGroup proPersonGroup) {
        JsonResult jsonResult = new JsonResult();
        proPersonGroupService.update(proPersonGroup);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 人员组-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-id删除", notes = "人员组-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proPersonGroupService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 人员组-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-分页查询", notes = "人员组-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProPersonGroup.class, request);
        return proPersonGroupService.findPageResult(filter);
    }

    /**
     * <p> 人员组-查询全部 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:42:39
     */
    @ApiOperation(value = "人员组-查询全部", notes = "人员组-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProPersonGroup> findAll (HttpServletRequest request) {
        return proPersonGroupService.findAll();
    }

}
