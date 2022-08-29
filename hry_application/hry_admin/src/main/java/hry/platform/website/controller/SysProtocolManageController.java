/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:57 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.model.SysProtocolManage;
import hry.platform.website.service.SysProtocolManageService;
import hry.security.jwt.JWTContext;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> SysProtocolManageController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:57 
 */
@Api(value = "系统协议管理", tags = "系统协议管理", description = "系统协议管理")
@RestController
@RequestMapping("/website/sysprotocolmanage")
public class SysProtocolManageController extends BaseController {

	@Autowired
	private SysProtocolManageService sysProtocolManageService;

	/**
     * <p> 系统协议管理-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:57 
     */
    @ApiOperation(value = "系统协议管理-id查询", notes = "系统协议管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		SysProtocolManage sysProtocolManage = sysProtocolManageService.get(id);
        if (sysProtocolManage != null) {
            jsonResult.setObj(sysProtocolManage);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 系统协议管理-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:57 
     */
    @ApiOperation(value = "系统协议管理-添加", notes = "系统协议管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (SysProtocolManage sysProtocolManage) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        sysProtocolManage.setOperator(user.getUserName()); // 添加操作人信息
        sysProtocolManageService.save(sysProtocolManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 系统协议管理-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:57 
     */
    @ApiOperation(value = "系统协议管理-修改", notes = "系统协议管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (SysProtocolManage sysProtocolManage) {
        JsonResult jsonResult = new JsonResult();
        sysProtocolManageService.update(sysProtocolManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 系统协议管理-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:57 
     */
    @ApiOperation(value = "系统协议管理-id删除", notes = "系统协议管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        sysProtocolManageService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 系统协议管理-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:08:57 
     */
    @ApiOperation(value = "系统协议管理-分页查询", notes = "系统协议管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(SysProtocolManage.class, request);
        return sysProtocolManageService.findPageResult(filter);
    }

}
