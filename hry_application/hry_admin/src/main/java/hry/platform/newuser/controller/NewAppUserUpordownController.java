/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:15:44 
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.model.NewAppUserUpordown;
import hry.platform.newuser.service.NewAppUserUpordownService;
import hry.security.jwt.JWTContext;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> NewAppUserUpordownController </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:15:44 
 */
@Api(value = "用户上下级配置 ", tags = "用户上下级配置 ", description = "用户上下级配置 ")
@RestController
@RequestMapping("/newuser/newappuserupordown")
public class NewAppUserUpordownController extends BaseController {

	@Autowired
	private NewAppUserUpordownService newAppUserUpordownService;

	/**
     * <p> 用户上下级配置 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:15:44 
     */
    @ApiOperation(value = "用户上下级配置-添加", notes = "用户上下级配置 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addUserUpOrDown")
    public JsonResult addUserUpOrDown (@ApiParam(name = "userid", value = "用户Id", required = true) @RequestParam String userid,
                           @ApiParam(name = "upOtheruserids", value = "上级其他用户Id集合,用英文逗号连接", required = true) @RequestParam String upOtheruserids,
                           @ApiParam(name = "downOtheruserids", value = "下级其他用户Id集合,用英文逗号连接", required = true) @RequestParam String downOtheruserids,
                           HttpServletRequest request) {
        return newAppUserUpordownService.addUserUpOrDown(userid, upOtheruserids, downOtheruserids);
    }

    /**
     * 获取用户上下级集合
     * @author: zhouming
     * @Date: 2020-06-28 15:15:44
     * */
    @ApiOperation(value = "获取用户上下级集合", notes = "获取用户上下级集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findUserUpOrDown")
    public JsonResult findUserUpOrDown (@ApiParam(name = "userid", value = "用户Id", required = true) @RequestParam String userid,
                                       HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userid", userid);
        return newAppUserUpordownService.findUserUpOrDownList(paramMap);
    }
    /**
     * <p> 当前用户上下级查询 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:15:44 
     */
    @ApiOperation(value = "当前用户上下级查询 -分页查询", notes = "用户上下级配置 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "type", value = "上级 0 下级 1", required = true) @RequestParam String type,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppUserUpordown.class, request);
        return newAppUserUpordownService.findPageUserUpOrDownList(filter);
    }

    /**
     * <p> 当前用户上下级配置 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:15:44
     */
    @ApiOperation(value = "当前用户上下级配置 -id删除", notes = "用户上下级配置 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove")
    public JsonResult remove (@ApiParam(name = "otheruserids", value = "其他用户Id集合,用英文逗号连接", required = true) @RequestParam String otheruserids,
                              HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        // 当前用户
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        // 添加最新的上下级
        String[] otheruseridlist = otheruserids.split(",");
        Arrays.asList(otheruseridlist).forEach((otheruserid) -> {
            QueryFilter filter = new QueryFilter(NewAppUserUpordown.class);
            NewAppUserUpordown userUpordown = new NewAppUserUpordown();
            filter.addFilter("userid=", user.getId());
            filter.addFilter("otheruserid=", otheruserid);
            newAppUserUpordownService.delete(filter);
        });
        return jsonResult.setSuccess(true);
    }
    /**
     * <p> 当前用户上下级配置 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:15:44
     */
    @ApiOperation(value = "当前用户上下级配置 -添加", notes = "当前用户上下级配置 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (@ApiParam(name = "otheruserids", value = "其他用户Id集合,用英文逗号连接", required = true) @RequestParam String otheruserids,
                           @ApiParam(name = "type", value = "上级 0 下级 1", required = true) @RequestParam String type,
                              HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        // 当前用户
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        // 添加最新的上下级
        String[] otheruseridlist = otheruserids.split(",");
        Arrays.asList(otheruseridlist).forEach((otheruserid) -> {
            NewAppUserUpordown userUpordown = new NewAppUserUpordown();
            userUpordown.setUserid(user.getId());
            userUpordown.setOtheruserid(Long.parseLong(otheruserid));
            userUpordown.setType(Integer.parseInt(type));
            newAppUserUpordownService.save(userUpordown);
        });
        return jsonResult.setSuccess(true);
    }
}
