/**
 * Copyright:    互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2017-05-27 16:05:55
 */
package hry.platform.newuser.controller;


import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUserRole;
import hry.platform.newuser.service.NewAppUserRoleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2017-05-27 16:05:55
 */
@Api(value = "用户角色关联表", tags = "用户角色关联表", description = "用户角色关联表")
@RestController
@RequestMapping("/user/newappuserrole")
public class NewAppUserRoleController extends BaseController {


    @Autowired
    private NewAppUserRoleService newAppUserRoleService;

    @ApiOperation(value = "用户角色关联表-查询", notes = "用户角色关联表-查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        NewAppUserRole newAppUserRole = newAppUserRoleService.get(id);
        jsonResult.setSuccess(true);
        jsonResult.setObj(newAppUserRole);
        return jsonResult;
    }


    @ApiOperation(value = "用户角色关联表-添加", notes = "用户角色关联表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add(NewAppUserRole newAppUserRole) {
        JsonResult jsonResult = new JsonResult();
        newAppUserRoleService.save(newAppUserRole);
        jsonResult.setSuccess(true);
        return jsonResult;
    }


    @ApiOperation(value = "用户角色关联表-修改", notes = "用户角色关联表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify(NewAppUserRole newAppUserRole) {
        JsonResult jsonResult = new JsonResult();
        newAppUserRoleService.update(newAppUserRole);
        jsonResult.setSuccess(true);
        return jsonResult;

    }

    @ApiOperation(value = "用户角色关联表-删除", notes = "用户角色关联表-删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id
    ) {
        JsonResult jsonResult = new JsonResult();
        newAppUserRoleService.delete(id);
        jsonResult.setSuccess(true);
        return jsonResult;

    }


    @ApiOperation(value = "用户角色关联表-分页查询", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list(@ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
                           @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
                           HttpServletRequest request) {
        return newAppUserRoleService.findPageResult(new QueryFilter(NewAppUserRole.class, request));


    }


}
