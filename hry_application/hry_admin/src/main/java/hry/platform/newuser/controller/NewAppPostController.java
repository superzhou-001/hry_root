/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:10:37 
 */
package hry.platform.newuser.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppPost;
import hry.platform.newuser.model.NewAppUserOrganization;
import hry.platform.newuser.model.NewAppUserPost;
import hry.platform.newuser.service.NewAppPostService;
import hry.platform.newuser.service.NewAppUserPostService;
import hry.security.logger.ControllerLogger;
import hry.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> NewAppPostController </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:10:37 
 */
@Api(value = "用户岗位配置", tags = "用户岗位配置", description = "用户岗位配置")
@RestController
@RequestMapping("/newuser/newapppost")
public class NewAppPostController extends BaseController {

	@Autowired
	private NewAppPostService newAppPostService;

	@Autowired
    private NewAppUserPostService newAppUserPostService;

	/**
     * <p> 岗位配置-id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37 
     */
    @ApiOperation(value = "岗位配置-id查询", notes = "岗位配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		NewAppPost newAppPost = newAppPostService.get(id);
        if (newAppPost != null) {
            jsonResult.setObj(newAppPost);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 岗位配置-添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37 
     */
    @ApiOperation(value = "岗位配置-添加", notes = "岗位配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (NewAppPost newAppPost) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(NewAppPost.class);
        filter.addFilter("anothername=", newAppPost.getAnothername());
        NewAppPost post = newAppPostService.get(filter);
        if (post != null) {
            return jsonResult.setSuccess(false).setMsg("添加岗位失败，岗位别名已存在");
        }
        newAppPost.setType("post"); // 设置类型 岗位
        newAppPostService.save(newAppPost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 岗位配置-修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37 
     */
    @ApiOperation(value = "岗位配置-修改", notes = "岗位配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (NewAppPost newAppPost) {
        JsonResult jsonResult = new JsonResult();
        newAppPostService.update(newAppPost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 岗位配置-id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37 
     */
    @ApiOperation(value = "岗位配置-id删除", notes = "岗位配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        // 校验岗位下是否有子岗位
        QueryFilter childFilter = new QueryFilter(NewAppPost.class);
        childFilter.addFilter("pid=", id);
        NewAppPost post = newAppPostService.get(childFilter);
        if (post != null) {
            return jsonResult.setSuccess(false).setMsg("该岗位下存在子节点,请先删除子节点");
        }
        // 查询岗位下是否有人员相关联
        QueryFilter filter = new QueryFilter(NewAppUserPost.class);
        filter.addFilter("postid=", id);
        NewAppUserPost newAppUserPost = newAppUserPostService.get(filter);
        if (newAppUserPost != null) {
            return jsonResult.setSuccess(false).setMsg("该岗位下存在用户,请先删除用户");
        }
        newAppPostService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * 查询所有岗位
     * */
    @ApiOperation(value = "查询所有岗位", notes = "查询所有岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/findPostList")
    public JsonResult findPostList(HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppPost.class);
        filter.addFilter("type !=", "root");
        List<NewAppPost> postList = newAppPostService.find(filter);
        return new JsonResult(true).setObj(postList);
    }

    /**
     * <p> 岗位配置-查询岗位树 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37 
     */
    @ApiOperation(value = "岗位配置-查询岗位树", notes = "岗位配置-查询岗位树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/loadRoot")
    public List<NewAppPost> loadRoot (HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppPost.class);
        filter.addFilter("type=", "root");
        List<NewAppPost> newAppPosts = newAppPostService.find(filter);
        if (newAppPosts != null && newAppPosts.size() > 0) {
            newAppPosts.stream().forEach(org -> {
                org.setType("root");
                createTreeData(org);
            });
        }
        return newAppPosts;
    }
    /**
     * 递归创建岗位树
     * */
    private void createTreeData (NewAppPost org) {
        QueryFilter org_filter = new QueryFilter(NewAppPost.class);
        org_filter.addFilter("pid=", org.getId());
        List<NewAppPost> newAppPostList = newAppPostService.find(org_filter);
        if (newAppPostList != null && newAppPostList.size() > 0) {
            org.setChildren(newAppPostList);
            newAppPostList.stream().forEach(orgs -> {
                createTreeData(orgs);
            });
        }
    }

    /**
     * <p> 岗位添加人员 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37
     */
    @ApiOperation(value = "岗位添加人员", notes = "岗位添加人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addPostUser")
    public JsonResult addPostUser (
            @ApiParam(name = "postId", value = "岗位Id", required = true) @RequestParam String postId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(postId)) {
            return new JsonResult(false).setMsg("岗位Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppUserPostService.addPostUser(Long.parseLong(postId), userIds);
    }

    /**
     * <p> 移除岗位人员 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:10:37
     */
    @ApiOperation(value = "移除岗位人员", notes = "移除岗位人员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/removePostUser")
    public JsonResult removePostUser (
            @ApiParam(name = "postId", value = "岗位Id", required = true) @RequestParam String postId,
            @ApiParam(name = "userIds", value = "用户Id集合，用英文逗号连接", required = true) @RequestParam String userIds,
            HttpServletRequest request) {
        if (!StringUtil.isNull(postId)) {
            return new JsonResult(false).setMsg("岗位Id不能为空");
        }
        if (!StringUtil.isNull(userIds)) {
            return new JsonResult(false).setMsg("用户Id集合不能为空");
        }
        return newAppUserPostService.removePostUser(Long.parseLong(postId), userIds);
    }

    /**
     * <p> 岗位关联人集合 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-06-28 15:17:29
     */
    @ApiOperation(value = "岗位关联人集合 -分页查询", notes = "岗位关联人集合 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findPostUserList")
    public PageResult findPostUserList (
            @ApiParam(name = "postId", value = "岗位Id", required = true) @RequestParam String postId,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppUserPost.class, request);
        return newAppUserPostService.findPostUserPageList(filter);
    }

    /**
     * 查询用户所属岗位
     * */
    @ApiOperation(value = "查询用户所属岗位", notes = "查询用户所属岗位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping("/findUserPost")
    public JsonResult findUserPost(@ApiParam(name = "userid", value = "userid", required = true) @RequestParam String userid,
                                          HttpServletRequest request) {
        return newAppUserPostService.findUserPost(userid);
    }



}
