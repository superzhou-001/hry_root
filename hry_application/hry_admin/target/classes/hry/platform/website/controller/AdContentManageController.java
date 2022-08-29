/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:10:31 
 */
package hry.platform.website.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.website.model.AdContentManage;
import hry.platform.website.model.AdPositionManage;
import hry.platform.website.service.AdContentManageService;
import hry.platform.website.service.AdPositionManageService;
import hry.security.jwt.JWTContext;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> AdContentManageController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:10:31 
 */
@Api(value = "广告内容管理", tags = "广告内容管理", description = "广告内容管理")
@RestController
@RequestMapping("/website/adcontentmanage")
public class AdContentManageController extends BaseController {

	@Autowired
	private AdContentManageService adContentManageService;
    @Autowired
    private AdPositionManageService adPositionManageService;

	/**
     * <p> 广告内容管理-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:10:31 
     */
    @ApiOperation(value = "广告内容管理-id查询", notes = "广告内容管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AdContentManage adContentManage = adContentManageService.get(id);
        if (adContentManage != null) {
            jsonResult.setObj(adContentManage);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 广告内容管理-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:10:31 
     */
    @ApiOperation(value = "广告内容管理-添加", notes = "广告内容管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (AdContentManage adContentManage) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        adContentManage.setOperator(user.getUserName()); // 添加操作人
        adContentManageService.save(adContentManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告内容管理-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:10:31 
     */
    @ApiOperation(value = "广告内容管理-修改", notes = "广告内容管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (AdContentManage adContentManage) {
        JsonResult jsonResult = new JsonResult();
        adContentManageService.update(adContentManage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告内容管理-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:10:31 
     */
    @ApiOperation(value = "广告内容管理-id删除", notes = "广告内容管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        adContentManageService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 广告内容管理-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-04-01 17:10:31 
     */
    @ApiOperation(value = "广告内容管理-分页查询", notes = "广告内容管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AdContentManage.class, request);
        return adContentManageService.findPageBySql(filter);
    }

}
