/**
 * Copyright:    互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2017-06-01 19:44:41
 */
package hry.platform.newuser.controller;


import hry.bean.JsonResult;
import hry.core.mvc.controller.BaseController;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.service.NewAppMenuService;
import hry.platform.newuser.service.NewAppUserService;
import hry.redis.RedisService;
import hry.security.jwt.JWTUtil;
import hry.security.logger.ControllerLogger;
import hry.util.UUIDUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Copyright:   互融云
 * @author: liushilei
 * @version: V1.0
 * @Date: 2017-06-01 19:44:41
 */
@Api(value = "用户菜单", tags = "用户菜单", description = "用户菜单")
@RestController
@RequestMapping("/menu/newappmenu")
public class NewAppMenuController extends BaseController {


    @Autowired
    private NewAppMenuService newAppMenuService;
    @Autowired
    private NewAppUserService newAppUserService;
    @Autowired
    private RedisService redisService;


    @ApiOperation(value = "添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (NewAppMenu NewAppMenu, HttpServletRequest request) {
        NewAppMenu.setMkey(UUIDUtil.getUUID());
        NewAppMenu.setShirourl(StringEscapeUtils.unescapeHtml(NewAppMenu.getShirourl()));
        NewAppMenu.setUrl(StringEscapeUtils.unescapeHtml(NewAppMenu.getUrl()));
        newAppMenuService.save(NewAppMenu);
        // 更新当前用户权限缓存
        newAppUserService.updateUserShiroUrl(request);
        return new JsonResult().setSuccess(true);
    }

    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (HttpServletRequest request, NewAppMenu NewAppMenu) {
        if (StringUtils.isEmpty(NewAppMenu.getType())) {
            return new JsonResult().setSuccess(false).setMsg("请选择类型");
        }
        NewAppMenu _appmenu = newAppMenuService.get(NewAppMenu.getId());
        _appmenu.setName(NewAppMenu.getName());
        _appmenu.setUrl(StringEscapeUtils.unescapeHtml(NewAppMenu.getUrl()));
        _appmenu.setShirourl(StringEscapeUtils.unescapeHtml(NewAppMenu.getShirourl()));
        _appmenu.setOrderno(NewAppMenu.getOrderno());
        _appmenu.setType(NewAppMenu.getType());
        _appmenu.setIcon(NewAppMenu.getIcon());
        newAppMenuService.updateNull(_appmenu);
        // 更新当前用户权限缓存
        newAppUserService.updateUserShiroUrl(request);
        return new JsonResult().setSuccess(true).setObj(NewAppMenu.getId());
    }


    @ApiOperation(value = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id,HttpServletRequest request) {
        // 删除菜单，并级联删除其子菜单
        NewAppMenu appMenu = newAppMenuService.get(id);
        if (appMenu != null) {
            // rt[0]: 1-成功，0-失败；rt[1]: 操作结果
            String[] rt = newAppMenuService.cascadeDeleteMenu(appMenu);
            if (String.valueOf(1).equals(rt[0])) {
                // 更新当前用户权限缓存
                newAppUserService.updateUserShiroUrl(request);
                return new JsonResult().setSuccess(true).setMsg("删除成功");
            }

            return new JsonResult().setSuccess(false).setMsg(rt[1]);
        }
        return new JsonResult().setSuccess(false).setMsg("菜单不存在");
    }


    @ApiOperation(value = "查询树结构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/loadtree")
    public List<NewAppMenu> loadtree (
            @ApiParam(name = "appName", value = "菜单key", required = true) @RequestParam String appName,
            @ApiParam(name = "isCheck", value = "是否校验菜单 1 校验 2 不校验 ", required = true) @RequestParam String isCheck,
            HttpServletRequest request) {
        String shiroUrl = null;
        if ("1".equals(isCheck)) {
            // 获取用户的权限组
            String token = request.getHeader("token");
            shiroUrl = redisService.get(JWTUtil.getManageShiroUrlsKey(token));
        }
        return newAppMenuService.findTree(appName, shiroUrl);
    }
}
