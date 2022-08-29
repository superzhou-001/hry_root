/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 16:04:36
 */
package hry.platform.newuser.controller;

import com.sun.javafx.binding.StringConstant;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppMenu;
import hry.platform.newuser.model.NewAppMenuTop;
import hry.platform.newuser.service.NewAppMenuService;
import hry.platform.newuser.service.NewAppMenuTopService;
import hry.platform.newuser.service.NewAppRoleMenuService;
import hry.redis.RedisService;
import hry.security.jwt.JWTUtil;
import hry.util.StringUtil;
import io.swagger.annotations.*;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> NewAppMenuTopController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 16:04:36
 */
@Api(value = "顶部菜单配置", tags = "顶部菜单配置", description = "顶部菜单配置")
@RestController
@RequestMapping("/newuser/newappmenutop")
public class NewAppMenuTopController extends BaseController {

	@Autowired
	private NewAppMenuTopService newAppMenuTopService;
	@Autowired
    private NewAppMenuService newAppMenuService;
	@Autowired
    private NewAppRoleMenuService newAppRoleMenuService;
	@Autowired
    private RedisService redisService;

    @ApiOperation(value = "顶部菜单配置-分页查询", notes = "顶部菜单配置-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppMenuTop.class, request);
        return newAppMenuTopService.findPageResult(filter);
    }

    /**
     * 顶部菜单配置-查询所有父分类-用作下拉列表
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-查询所有父分类-用作下拉列表", notes = "顶部菜单配置-查询所有父分类-用作下拉列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/loadParentNode")
    public List<NewAppMenuTop> loadParentNode (HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppMenuTop.class, request);
        filter.addFilter("pkey=", "root");
        filter.setOrderby("orderno");
        return newAppMenuTopService.find(filter);
    }

    /**
     * 顶部菜单配置-查询所有顶部菜单-用作导航展示
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-查询所有顶部菜单-用作导航展示", notes = "顶部菜单配置-查询所有顶部菜单-用作导航展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/loadTopMenu")
    public List<NewAppMenuTop> loadTopMenu (
            @ApiParam(name = "roleId", value = "角色id", required = false) @RequestParam(required = false) Long roleId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppMenuTop.class, request);
        filter.addFilter("pkey!=", "root");
        filter.setOrderby("orderno");
        List<NewAppMenuTop> topLists = newAppMenuTopService.find(filter);
        if (topLists != null && topLists.size() > 0) {
            // 如果角色id不为空，表示角色菜单中导航用
            if (roleId != null) {
                // 查询角色拥有的权限
                List<NewAppMenu> appMenus = newAppRoleMenuService.loadmenubyroleid(roleId);
                if (appMenus != null && appMenus.size() > 0) {
                    // 将顶级菜单权限过滤出来
                    List<String> collect = new ArrayList<>();
                    appMenus.stream().forEach(menu -> {
                        if ("root".equals(menu.getPkey())) {
                            collect.add(menu.getMkey());
                        }
                    });
                    Iterator<NewAppMenuTop> ltr = topLists.iterator();
                    while (ltr.hasNext()) {
                        if (collect.contains(ltr.next().getMkey())) {
                            ltr.remove();
                        }
                    }
                }
            }
        }
        return topLists;
    }

    /**
     * 查询树结构
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-查询树结构", notes = "顶部菜单配置-查询树结构")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/loadTree")
    public List<NewAppMenuTop> loadTree (HttpServletRequest request) {
        // 获取用户的权限组
        String token = request.getHeader("token");
        String shiroUrl = redisService.get(JWTUtil.getManageShiroUrlsKey(token));
        // 查询分类菜单
        QueryFilter p_filter = new QueryFilter(NewAppMenuTop.class);
        p_filter.addFilter("pkey=", "root");
        p_filter.setOrderby("orderno");
        List<NewAppMenuTop> pTopList = newAppMenuTopService.find(p_filter);
        if (pTopList != null && pTopList.size() > 0) {
            // 获取分类子集
            pTopList.stream().forEach(menuTop -> getChildTree(menuTop,shiroUrl));
            // 删除没有子的顶部菜单
            for (int i = 0; i < pTopList.size(); i++) {
                if (pTopList.get(i).getChildren().size() == 0) {
                    pTopList.remove(i);
                    i--;
                }
            }
            return pTopList;
        }
        return null;
    }

    /**
     *  @author: liuchenghui
     *  @Date: 2020/4/8 16:39
     *  @Description: 获取子集
     */
    private void getChildTree(NewAppMenuTop menuTop,String shiroUrl) {
        // 根据父分类查询子菜单
        QueryFilter p_filter = new QueryFilter(NewAppMenuTop.class);
        p_filter.addFilter("pkey=", menuTop.getMkey());
        p_filter.setOrderby("orderno");
        List<NewAppMenuTop> topList = newAppMenuTopService.find(p_filter);
        if (topList != null && topList.size() > 0) {
            // 删除没有权限子菜单
            topList.removeIf((top) -> shiroUrl.indexOf(top.getMkey()) == -1);
            // 递归查询子
            topList.stream().forEach(top -> {
                getChildTree(top,shiroUrl);
            });
        }
        menuTop.setChildren(topList);
    }

    /**
     * <p> 顶部菜单配置-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-id查询", notes = "顶部菜单配置-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        NewAppMenuTop newAppMenuTop = newAppMenuTopService.get(id);
        if (newAppMenuTop != null) {
            jsonResult.setObj(newAppMenuTop);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 顶部菜单配置-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-添加", notes = "顶部菜单配置-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (NewAppMenuTop newAppMenuTop) {
        try {
            // 获取父级key，如果pkey为空，则默认为root
            String pkey = newAppMenuTop.getPkey();
            if (StringUtils.isEmpty(pkey)) {
                pkey = "root";
            }
            newAppMenuTop.setPkey(pkey);

            // 获取排序序号
            Integer maxOrderNo = newAppMenuTopService.getMaxOrderNo(newAppMenuTop.getPkey());
            newAppMenuTop.setOrderno(maxOrderNo);

            // 验证key是否重复
            QueryFilter topQf = new QueryFilter(NewAppMenuTop.class);
            topQf.addFilter("mkey=", newAppMenuTop.getMkey());
            NewAppMenuTop newAppMenuTop1 = newAppMenuTopService.get(topQf);
            if (newAppMenuTop1 != null) {
                return new JsonResult().setSuccess(false).setMsg("菜单key不能重复");
            }

            // 将顶部菜单添加到菜单表中
            // 如果不是父级分类，才往菜单中同步
            if (!"root".equals(pkey)) {
                newAppMenuService.syncMenu(newAppMenuTop);
            }
            // 保存顶部菜单
            newAppMenuTopService.save(newAppMenuTop);
            return new JsonResult().setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false);
        }
    }

    /**
     * <p> 顶部菜单配置-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-修改", notes = "顶部菜单配置-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (NewAppMenuTop newAppMenuTop) {
        NewAppMenuTop appMenuTop = newAppMenuTopService.get(newAppMenuTop.getId());
        JsonResult jsonResult = new JsonResult();
        if (appMenuTop != null) {
            // 判断排序字段是否修改过
            if (appMenuTop.getOrderno().compareTo(newAppMenuTop.getOrderno()) != 0) {
                QueryFilter qf_orderno = new QueryFilter(NewAppMenuTop.class);
                qf_orderno.addFilter("pkey=", newAppMenuTop.getPkey());
                qf_orderno.addFilter("orderno=", newAppMenuTop.getOrderno());
                NewAppMenuTop menuTop = newAppMenuTopService.get(qf_orderno);
                if (menuTop != null) {
                    return new JsonResult().setSuccess(false).setMsg("排序字段重复");
                }
            }
            appMenuTop.setMkey(newAppMenuTop.getMkey());
            appMenuTop.setPkey(newAppMenuTop.getPkey());
            appMenuTop.setName(newAppMenuTop.getName());
            appMenuTop.setIcon(newAppMenuTop.getIcon());
            appMenuTop.setOrderno(newAppMenuTop.getOrderno());
            newAppMenuTopService.updateNull(appMenuTop);
            // 如果不是父级分类，才往菜单中同步
            if (!"root".equals(newAppMenuTop.getPkey())) {
                newAppMenuService.syncMenu(newAppMenuTop);
            }
            return jsonResult.setSuccess(true).setMsg("修改成功").setObj(newAppMenuTop.getId());
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 顶部菜单配置-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-31 16:04:36
     */
    @ApiOperation(value = "顶部菜单配置-id删除", notes = "顶部菜单配置-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        try {
            // 删除自己同时如果是父节点，也删除子节点, 同时删除菜单中的节点
            NewAppMenuTop newAppMenuTop = newAppMenuTopService.get(new Long(id));
            if (newAppMenuTop != null) {
                // 级联删除
                String[] rt = newAppMenuTopService.cascadeDeleteMenu(newAppMenuTop);
                if (String.valueOf(1).equals(rt[0])) {
                    return new JsonResult().setSuccess(true).setMsg("删除成功");
                }
                return new JsonResult().setSuccess(false).setMsg(rt[1]);
            }
            return new JsonResult().setSuccess(false).setMsg("菜单不存在");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new JsonResult().setSuccess(false).setMsg("删除失败");
        }
    }
}
