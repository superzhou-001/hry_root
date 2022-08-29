/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2015年9月18日 上午10:32:03
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppOrganization;
import hry.platform.newuser.model.NewAppOrganizationCharge;
import hry.platform.newuser.model.NewAppUserOrganization;
import hry.platform.newuser.model.NewAppUserPost;
import hry.platform.newuser.service.NewAppOrganizationChargeService;
import hry.platform.newuser.service.NewAppOrganizationService;
import hry.platform.newuser.service.NewAppUserOrganizationService;
import hry.security.logger.ControllerLogger;
import hry.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> TODO</p>
 *
 * @author: Liu   Shilei
 * @Date :          2015年9月18日 上午10:32:03
 */
@Api(value = "用户组织", tags = "用户组织", description = "用户组织")
@RestController
@RequestMapping("/user/newapporganization")
public class NewAppOrganizationController extends BaseController {

    @Autowired
    private NewAppOrganizationService newAppOrganizationService;
    @Autowired
    private NewAppUserOrganizationService newAppUserOrganizationService;
    @Autowired
    private NewAppOrganizationChargeService newAppOrganizationChargeService;


    @ApiOperation(value = "添加组织", notes = "添加组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/add")
    public JsonResult add(HttpServletRequest request, NewAppOrganization appOrganization) {
        // 校验组织别名是否存在
        QueryFilter filter = new QueryFilter(NewAppOrganization.class);
        filter.addFilter("anothername=", appOrganization.getAnothername());
        NewAppOrganization organization = newAppOrganizationService.get(filter);
        if (organization != null) {
            return new JsonResult(false).setMsg("添加组织失败，组织别名已存在");
        }
        newAppOrganizationService.save(appOrganization);
        return new JsonResult().setSuccess(true);
    }


    @ApiOperation(value = "查询组织", notes = "查询组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/get/{id}")
    public NewAppOrganization get(@PathVariable Long id) {
        return newAppOrganizationService.get(id);
    }


    @ApiOperation(value = "修改组织", notes = "修改组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/modify")
    public JsonResult modify(HttpServletRequest request, NewAppOrganization appOrganization) {
        NewAppOrganization organization = newAppOrganizationService.get(appOrganization.getId());
        BeanUtils.copyProperties(appOrganization, organization);
        newAppOrganizationService.update(organization);
        return new JsonResult().setSuccess(true);
    }


    @ApiOperation(value = "删除组织", notes = "删除组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove")
    public JsonResult remove(@ApiParam(name = "id", value = "id", required = true) @RequestParam Long id) {
        return newAppOrganizationService.remove(id);
    }

    @ApiOperation(value = "查询组织树结构", notes = "查询组织树结构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/loadRoot")
    public List<NewAppOrganization> loadRoot() {
        QueryFilter filter = new QueryFilter(NewAppOrganization.class);
        filter.addFilter("type=", "root");
        List<NewAppOrganization> organizations = newAppOrganizationService.find(filter);
        if (organizations != null && organizations.size() > 0) {
            organizations.stream().forEach(org -> {
                org.setPkey("root");
                createTreeData(org);
            });
        }
        return organizations;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/16 10:05
     *  @Description: 递归创建组织树
     */
    private void createTreeData (NewAppOrganization org) {
        QueryFilter org_filter = new QueryFilter(NewAppOrganization.class);
        org_filter.addFilter("pid=", org.getId());
        List<NewAppOrganization> organizationList = newAppOrganizationService.find(org_filter);
        if (organizationList != null && organizationList.size() > 0) {
            org.setChildren(organizationList);
            organizationList.stream().forEach(orgs -> {
                createTreeData(orgs);
            });
        }
    }

    /**
     * 查询用户所属组织
     * */
    @ApiOperation(value = "查询用户所属组织", notes = "查询用户所属组织")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/getUserOrganization")
    public JsonResult getUserOrganization(@ApiParam(name = "userid", value = "userid", required = true) @RequestParam String userid,
                                          HttpServletRequest request) {
        NewAppUserOrganization organization = newAppOrganizationService.getUserOrganization(userid);
        return new JsonResult().setSuccess(true).setObj(organization);
    }

    /**
     * <p> 组织添加人员 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "组织添加人员", notes = "组织添加人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addOrganizationUser")
    public JsonResult addOrganizationUser (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(organizationId)) {
            return new JsonResult(false).setMsg("组织Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppUserOrganizationService.addOrganizationUser(Long.parseLong(organizationId), userIds);
    }

    /**
     * <p> 移除组织人员 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "移除组织人员", notes = "移除组织人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/removeOrganizationUser")
    public JsonResult removeOrganizationUser (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(organizationId)) {
            return new JsonResult(false).setMsg("组织Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppUserOrganizationService.removeOrganizationUser(Long.parseLong(organizationId), userIds);
    }

    /**
     * <p> 查询组织下人员信息 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "查询组织下人员信息 -分页查询", notes = "查询组织下人员信息 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findPageUserOrganizationList")
    public PageResult findPageUserOrganizationList (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppUserOrganization.class, request);
        return newAppUserOrganizationService.findPageUserOrganization(filter);
    }

    /**
     * <p> 组织添加组织负责人 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "组织添加组织负责人", notes = "组织添加组织负责人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addOrganizationCharge")
    public JsonResult addOrganizationCharge (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(organizationId)) {
            return new JsonResult(false).setMsg("组织Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppOrganizationChargeService.addOrganizationCharge(Long.parseLong(organizationId), userIds);
    }

    /**
     * <p> 移除组织负责人 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "移除组织负责人", notes = "移除组织负责人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/removeOrganizationCharge")
    public JsonResult removeOrganizationCharge (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(organizationId)) {
            return new JsonResult(false).setMsg("组织Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppOrganizationChargeService.removeOrganizationCharge(Long.parseLong(organizationId), userIds);
    }

    /**
     * <p> 查询组织下负责人信息 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "查询组织下负责人信息 -分页查询", notes = "查询组织下负责人信息 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findPageOrganizationChargeList")
    public PageResult findPageOrganizationChargeList (
            @ApiParam(name = "organizationId", value = "组织Id", required = true) @RequestParam String organizationId,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppOrganizationCharge.class, request);
        return newAppOrganizationChargeService.findPageOrganizationCharge(filter);
    }
}
