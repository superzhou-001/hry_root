/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:09:41 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.model.AdPositionManage;
import hry.platform.website.service.AdPositionManageService;
import hry.security.jwt.JWTContext;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AdPositionManageController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:09:41 
 */
@Api(value = "广告位置管理", tags = "广告位置管理", description = "广告位置管理")
@RestController
@RequestMapping("/website/adpositionmanage")
public class AdPositionManageController extends BaseController {

	@Autowired
	private AdPositionManageService adPositionManageService;

	/**
     * <p> 广告位置管理-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:09:41 
     */
    @ApiOperation(value = "广告位置管理-id查询", notes = "广告位置管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AdPositionManage adPositionManage = adPositionManageService.get(id);
        if (adPositionManage != null) {
            jsonResult.setObj(adPositionManage);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 广告位置管理-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:09:41 
     */
    @ApiOperation(value = "广告位置管理-添加", notes = "广告位置管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AdPositionManage adPositionManage) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        adPositionManage.setOperator(user.getUserName()); // 添加操作人信息
        adPositionManageService.save(adPositionManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告位置管理-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:09:41 
     */
    @ApiOperation(value = "广告位置管理-修改", notes = "广告位置管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AdPositionManage adPositionManage) {
        JsonResult jsonResult = new JsonResult();
        adPositionManageService.update(adPositionManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告位置管理-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:09:41 
     */
    @ApiOperation(value = "广告位置管理-id删除", notes = "广告位置管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        adPositionManageService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告位置管理-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:09:41 
     */
    @ApiOperation(value = "广告位置管理-分页查询", notes = "广告位置管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AdPositionManage.class, request);
        return adPositionManageService.findPageResult(filter);
    }

}
