/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:17:29 
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppGroup;
import hry.platform.newuser.model.NewAppUserGroup;
import hry.platform.newuser.service.NewAppGroupService;
import hry.platform.newuser.service.NewAppUserGroupService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> NewAppGroupController </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:17:29 
 */
@Api(value = "用户组配置 ", tags = "用户组配置 ", description = "用户组配置 ")
@RestController
@RequestMapping("/newuser/newappgroup")
public class NewAppGroupController extends BaseController {

	@Autowired
	private NewAppGroupService newAppGroupService;

	@Autowired
    private NewAppUserGroupService newAppUserGroupService;

	/**
     * <p> 用户组配置 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29 
     */
    @ApiOperation(value = "用户组配置 -id查询", notes = "用户组配置 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		NewAppGroup newAppGroup = newAppGroupService.get(id);
        if (newAppGroup != null) {
            // 查询组成员
            List<NewAppUserGroup> userGroups = newAppUserGroupService.findUserGroup(id);
            newAppGroup.setUserGroupList(userGroups);
            jsonResult.setObj(newAppGroup);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 用户组配置 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29 
     */
    @ApiOperation(value = "用户组配置 -添加", notes = "用户组配置 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (NewAppGroup newAppGroup) {
        // 校验组别名是否存在
        QueryFilter filter = new QueryFilter(NewAppGroup.class);
        filter.addFilter("anothername=", newAppGroup);
        NewAppGroup group = newAppGroupService.get(filter);
        if (group != null) {
            return new JsonResult(false).setMsg("添加组失败，组别名已存在");
        }
        return newAppGroupService.addGroup(newAppGroup);
    }

    /**
     * <p> 用户组配置 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29 
     */
    @ApiOperation(value = "用户组配置 -修改", notes = "用户组配置 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (NewAppGroup newAppGroup) {
        return newAppGroupService.addGroup(newAppGroup);
    }

    /**
     * <p> 用户组配置 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29 
     */
    @ApiOperation(value = "用户组配置 -id删除", notes = "用户组配置 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        // 校验组下是否有成员
        QueryFilter filter = new QueryFilter(NewAppUserGroup.class);
        filter.addFilter("groupid=", id);
        NewAppUserGroup newAppUserGroup = newAppUserGroupService.get(filter);
        if (newAppUserGroup != null) {
            return new JsonResult(false).setMsg("该组下存在用户,请先删除用户");
        }
        newAppGroupService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 用户组配置 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29 
     */
    @ApiOperation(value = "用户组配置 -分页查询", notes = "用户组配置 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppGroup.class, request);
        return newAppGroupService.findPageResult(filter);
    }

    /**
     * <p> 查询所有用户组</p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "查询所有用户组", notes = "查询所有用户组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findGroupList")
    public JsonResult findGroupList (
            HttpServletRequest request) {
        List<NewAppGroup> appGroups = newAppGroupService.findAll();
        return new JsonResult(true).setObj(appGroups);
    }

    /**
     * 查询用户所属组
     * */
    @ApiOperation(value = "查询用户所属组", notes = "查询用户所属组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/findUserGroup")
    public JsonResult findUserGroup(@ApiParam(name = "userid", value = "userid", required = true) @RequestParam String userid,
                                  HttpServletRequest request) {
        return newAppUserGroupService.findUserGroup(userid);
    }
}
