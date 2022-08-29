/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 11:46:52 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaBill;
import hry.business.fa.service.FaBillService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.security.jwt.JWTContext;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> FaBillController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 11:46:52 
 */
@Api(value = "票据信息", tags = "票据信息", description = "票据信息")
@RestController
@RequestMapping("/fa/fabill")
public class FaBillController extends BaseController {

	@Autowired
	private FaBillService faBillService;

	/**
     * <p> 票据信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 11:46:52 
     */
    @ApiOperation(value = "票据信息-id查询", notes = "票据信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaBill faBill = faBillService.get(id);
        if (faBill != null) {
            jsonResult.setObj(faBill);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 票据信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 11:46:52 
     */
    @ApiOperation(value = "票据信息-添加", notes = "票据信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaBill faBill) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if (user == null) {
            return jsonResult.setSuccess(false).setMsg("请重新登陆");
        }
        faBill.setUserId(user.getId());
        faBill.setUserName(user.getUserName());
        faBillService.save(faBill);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 票据信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 11:46:52 
     */
    @ApiOperation(value = "票据信息-修改", notes = "票据信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaBill faBill) {
        JsonResult jsonResult = new JsonResult();
        faBillService.update(faBill);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 票据信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 11:46:52 
     */
    @ApiOperation(value = "票据信息-id删除", notes = "票据信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faBillService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 票据信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-17 11:46:52 
     */
    @ApiOperation(value = "票据信息-分页查询", notes = "票据信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaBill.class, request);
        return faBillService.findPageResult(filter);
    }

    @ApiOperation(value = "票据信息-审核状态", notes = "票据信息-审核状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateStatus")
    public JsonResult updateStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单", required = true) @RequestParam Integer status,
            @ApiParam(name = "statusRemark", value = "状态变更备注", required = false) @RequestParam String statusRemark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(status)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        FaBill faBill = faBillService.get(id);
        if (faBill != null) {
            FaBill faBill1New = new FaBill();
            faBill1New.setStatus(status);
            faBill1New.setId(faBill.getId());
            if (!StringUtils.isEmpty(statusRemark)) {
                faBill1New.setStatusRemark(statusRemark);
            }
            faBillService.update(faBill1New);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "票据信息-批量提交审核", notes = "票据信息-批量提交审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateStatusByIds")
    public JsonResult updateStatusByIds(
            @ApiParam(name = "ids", value = "用户id", required = true) @RequestParam String ids
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(ids)) {
            jsonResult.setMsg("ids不能为空");
            return jsonResult;
        }
        faBillService.updateStatusByIds(ids);
        return jsonResult.setSuccess(true).setMsg("成功");
    }


}
