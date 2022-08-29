/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-24 10:28:18
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.NewAppDic;
import hry.platform.config.service.NewAppDicService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> NewAppDicController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:28:18
 */
@Api(value = "数据字典", tags = "数据字典", description = "数据字典")
@RestController
@RequestMapping("/config/newappdic")
public class NewAppDicController extends BaseController {

    private final String SYSLANGUAGE = "sysLanguage";
    private final String CONST_ONE = "1";

    @Autowired
    private NewAppDicService newAppDicService;

    /**
     * <p> 数据字典-id查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-id查询", notes = "数据字典-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        NewAppDic newAppDic = newAppDicService.get(id);
        if (newAppDic != null) {
            jsonResult.setObj(newAppDic);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false).setMsg("数据字典不存在");
    }

    /**
     * <p> 数据字典-添加 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-添加分类", notes = "数据字典-添加分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/add")
    public JsonResult add (NewAppDic newAppDic) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(NewAppDic.class);
        queryFilter.addFilter("mkey=", newAppDic.getMkey());
        NewAppDic sysDic2 = newAppDicService.get(queryFilter);
        if (sysDic2 != null) {
            return new JsonResult().setMsg("标识已存在！");
        }
        //如果value 为空  设置value=mkey
        if (StringUtils.isEmpty(newAppDic.getValue())) {
            newAppDic.setValue(newAppDic.getMkey());
        }
        newAppDicService.save(newAppDic);
        newAppDicService.flushTreeRedis(newAppDic.getPkey());
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 数据字典-修改 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-修改分类", notes = "数据字典-修改分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/modify")
    public JsonResult modify (NewAppDic newAppDic) {
        JsonResult jsonResult = new JsonResult();
        String pkey = newAppDic.getPkey();
        // 清空默认字段值
        clearDefaultValue(pkey, newAppDic);
        newAppDicService.update(newAppDic);
        /*NewAppDic sysDic = newAppDicService.get(newAppDic.getId());
        //删除缓存
        //HryCache.cache_appdic.remove(sysDic.getPkey());*/
        //更新redis缓存
        newAppDicService.flushRedis();
        // 更新字典树
        if (pkey == null || "".equals(pkey)) {
            pkey = newAppDicService.get(newAppDic.getId()).getMkey();
        }
        newAppDicService.flushTreeRedis(pkey);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 数据字典-id删除 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-id删除", notes = "数据字典-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        NewAppDic dic = newAppDicService.get(id);
        newAppDicService.delete(id);
        /*//删除缓存
        HryCache.cache_appdic.remove(appDic.getPkey());*/
        //更新redis缓存
        newAppDicService.flushRedis();
        // 更新字典树
        newAppDicService.flushTreeRedis(dic.getPkey());
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 数据字典-分页查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-分页查询", notes = "数据字典-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "mkey", value = "标识key", required = true) @RequestParam String mkey,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppDic.class, request);
        if(!StringUtils.isEmpty(mkey)){
            filter.addFilter("pkey=", mkey);
        }
        return newAppDicService.findPageResult(filter);
    }

    /**
     * <p> 数据字典-树结构查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-树结构查询", notes = "数据字典-树结构查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/listTree")
    public List<NewAppDic> listTree (HttpServletRequest request) {
        return newAppDicService.findDicTree();
    }

    /**
     * <p> 数据字典-根据pkey查询 </p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:28:18
     */
    @ApiOperation(value = "数据字典-根据pkey查询", notes = "数据字典-根据pkey查询,地区数据字典pkey=addressarea")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/list")
    public List<NewAppDic> list (
            @ApiParam(name = "pkey", value = "父类字典key", required = true) @RequestParam String pkey,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(NewAppDic.class, request);
        if (!StringUtils.isEmpty(pkey)) {
            filter.addFilter("pkey=", pkey);
        }
        return newAppDicService.find(filter);
    }

    @ApiOperation(value = "数据字典-根据多pkey查询", notes = "数据字典-根据多pkey查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/findMapList")
    public Map<String, List<NewAppDic>> findMapList (@ApiParam(name = "pkeys", value = "父类字典key,多key用逗号连接", required = true) @RequestParam String pkeys,
                                                     HttpServletRequest request) {
        Map<String, List<NewAppDic>> map = new HashMap<>();
        if (!StringUtils.isEmpty(pkeys)) {
            String[] pkeyList = pkeys.split(",");
            Arrays.stream(pkeyList).forEach((pkey) -> {
               map.put(pkey, newAppDicService.findDicList(pkey));
            });
        }
        return map;
    }


    @ApiOperation(value = "数据字典-添加分类子项", notes = "数据字典-添加分类子项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/addItem")
    public JsonResult addItem (
            @ApiParam(name = "pkey", value = "父类字典key", required = true) @RequestParam String pkey,
            HttpServletRequest request, NewAppDic sysDic) {
        QueryFilter queryFilter = new QueryFilter(NewAppDic.class);
        queryFilter.addFilter("mkey=", sysDic.getMkey());
        NewAppDic sysDic2 = newAppDicService.get(queryFilter);
        if (sysDic2 != null) {
            return new JsonResult().setMsg("标识已存在！");
        }

        clearDefaultValue(pkey, sysDic);
        //生成mkey
        sysDic.setMkey(sysDic.getPkey() + "_" + RandomStringUtils.random(6, false, true));
        newAppDicService.save(sysDic);
        /*//删除缓存
        HryCache.cache_appdic.remove(sysDic.getPkey());*/
        //更新redis缓存
        newAppDicService.flushRedis();
        // 更新字典树
        newAppDicService.flushTreeRedis(pkey);
        return new JsonResult(true);
    }

    /**
     * @author: liuchenghui
     * @Date: 2020/3/30 10:41
     * @Description: 清空默认字段
     */
    private void clearDefaultValue (String pkey, NewAppDic sysDic) {
        if (SYSLANGUAGE.equals(pkey)) {
            // 清空默认字段 1-是 0或null-否
            if (CONST_ONE.equals(sysDic.getRemark3())) {
                QueryFilter filter = new QueryFilter(NewAppDic.class);
                filter.addFilter("pkey=", "sysLanguage");
                filter.addFilter("remark3=", "1");
                List<NewAppDic> ads = newAppDicService.find(filter);
                if (ads != null && ads.size() > 0) {
                    for (NewAppDic ad : ads) {
                        ad.setRemark3("0");
                        newAppDicService.update(ad);
                    }
                }
            }
        }
    }

    @ApiOperation(value = "数据字典-删除分类", notes = "数据字典-删除分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/removeGroup")
    public JsonResult removeGroup (
            @ApiParam(name = "id", value = "分类id", required = true) @RequestParam Long id) {
        NewAppDic sysDic = newAppDicService.get(id);
        if (sysDic != null) {
            QueryFilter filter = new QueryFilter(NewAppDic.class);
            filter.addFilter("pkey=", sysDic.getMkey());
            NewAppDic sysDic2 = newAppDicService.get(filter);
            if (sysDic2 != null) {
                return new JsonResult().setSuccess(false).setMsg("有子项不能删除!");
            }
            newAppDicService.delete(id);
            // 更新字典树
            newAppDicService.flushTreeRedis(sysDic.getPkey());
            return new JsonResult().setSuccess(true);
        }
        return new JsonResult().setSuccess(false).setMsg("没有此分类");
    }

}
