/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:41:09 
 */
package hry.scm.enterprise.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.enterprise.model.UserRelation;
import hry.scm.enterprise.service.UserRelationService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> UserRelationController </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:41:09 
 */
@Api(value = "供应链用户关系管理", tags = "供应链用户关系管理", description = "供应链用户关系管理")
@RestController
@RequestMapping("/enterprise/userrelation")
public class UserRelationController extends BaseController {

	@Autowired
	private UserRelationService userRelationService;

	/**
     * <p> 供应链用户关系管理-id查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:41:09 
     */
    @ApiOperation(value = "供应链用户关系管理-id查询", notes = "供应链用户关系管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		UserRelation userRelation = userRelationService.get(id);
        if (userRelation != null) {
            jsonResult.setObj(userRelation);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 供应链用户关系管理-添加 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:41:09 
     */
    @ApiOperation(value = "供应链用户关系管理-添加", notes = "供应链用户关系管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (UserRelation userRelation) {
        JsonResult jsonResult = new JsonResult();
        userRelationService.save(userRelation);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链用户关系管理-修改 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:41:09 
     */
    @ApiOperation(value = "供应链用户关系管理-修改", notes = "供应链用户关系管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (UserRelation userRelation) {
        JsonResult jsonResult = new JsonResult();
        userRelationService.update(userRelation);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链用户关系管理-id删除 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:41:09 
     */
    @ApiOperation(value = "供应链用户关系管理-id删除", notes = "供应链用户关系管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        userRelationService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 供应链用户关系管理-分页查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-08 17:41:09 
     */
    @ApiOperation(value = "供应链用户关系管理-分页查询", notes = "供应链用户关系管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(UserRelation.class, request);
        return userRelationService.findPageResult(filter);
    }

}
