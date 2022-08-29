/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2015年9月21日 上午11:30:01
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppRole;
import hry.platform.newuser.service.NewAppRoleService;
import hry.platform.newuser.service.NewAppUserRoleService;
import hry.platform.newuser.service.NewAppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p> TODO</p>
 * @author: Liu Shilei
 * @Date :          2015年9月21日 上午11:30:01
 */
@Api(value = "菜单角色关系", tags = "菜单角色关系", description = "菜单角色关系")
@RestController
@RequestMapping("/user/newapprole")
public class NewAppRoleController extends BaseController {

    @Autowired
    private NewAppRoleService newAppRoleService;
    @Autowired
    private NewAppUserRoleService newAppUserRoleService;
    @Autowired
    private NewAppUserService newAppUserService;

    /**
     * <p> 顶部菜单配置-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "菜单角色关系-id查询", notes = "菜单角色关系-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        NewAppRole newAppRole = newAppRoleService.get(id);
        if (newAppRole != null) {
            jsonResult.setObj(newAppRole);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    @ApiOperation(value = "菜单角色关系-添加", notes = "菜单角色关系-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add(HttpServletRequest request, NewAppRole appRole,
                          @ApiParam(name = "menuIds", value = "菜单树，选中菜单id，逗号隔开", required = true) @RequestParam String menuIds
    ) {
        JsonResult result = newAppRoleService.add(appRole,menuIds);
        // 更新当前用户权限缓存
        newAppUserService.updateUserShiroUrl(request);
        return result;
    }

    @ApiOperation(value = "菜单角色关系-修改", notes = "菜单角色关系-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify(HttpServletRequest request, NewAppRole appRole) {
        JsonResult result = newAppRoleService.modify(request, appRole);
        // 更新当前用户权限缓存
        newAppUserService.updateUserShiroUrl(request);
        return result;
    }

    @ApiOperation(value = "菜单角色关系-删除", notes = "菜单角色关系-删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove")
    public JsonResult remove(String ids) {
        return newAppRoleService.remove(ids);
    }

    @ApiOperation(value = "菜单角色关系-分页查询", notes = "菜单角色关系-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/list")
    public PageResult list(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppRole.class, request);
        return newAppRoleService.findPageResult(filter);
    }

    @ApiOperation(value = "菜单角色关系-根据角色id获取菜单ids", notes = "菜单角色关系-根据角色id获取菜单ids")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/loadmenubyroleid")
    public JsonResult loadmenubyroleid(Long roleid) {
        return newAppRoleService.loadmenubyroleid(roleid);
    }

    @ApiOperation(value = "菜单角色关系-获取所有的角色", notes = "菜单角色关系-获取所有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/getAllRoles")
    public List<NewAppRole> getAllRoles() {
        return newAppRoleService.findAll();
    }

    /**
     * 查询用户所属角色
     * */
    @ApiOperation(value = "查询用户所属角色", notes = "查询用户所属角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/findUserRole")
    public JsonResult findUserRole(@ApiParam(name = "userid", value = "userid", required = true) @RequestParam String userid,
                                  HttpServletRequest request) {
        return newAppUserRoleService.findUserRole(userid);
    }

}
