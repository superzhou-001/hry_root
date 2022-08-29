package hry.business.manage.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuPersonService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yaozh
 * @Description: 人员信息查询
 * @Date:
 */
@Api(value = "人员信息管理", tags = "人员信息管理", description = "人员信息管理")
@RestController
@RequestMapping("/manage/person")
public class PersonController  extends BaseController {


    @Autowired
    private CuPersonService cuPersonService;

    /**
     * <p> 人员信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:29:14
     */
    @ApiOperation(value = "人员信息-id查询", notes = "人员信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuPerson cuPerson = cuPersonService.get(id);
        if (cuPerson != null) {
            jsonResult.setObj(cuPerson);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 人员信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:29:14
     */
    @ApiOperation(value = "人员信息-添加", notes = "人员信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CuPerson cuPerson) {
        JsonResult jsonResult = new JsonResult();
        cuPersonService.save(cuPerson);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 人员信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:29:14
     */
    @ApiOperation(value = "人员信息-修改", notes = "人员信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CuPerson cuPerson) {
        JsonResult jsonResult = new JsonResult();
        cuPersonService.update(cuPerson);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 人员信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:29:14
     */
/*
    @ApiOperation(value = "人员信息-id删除", notes = "人员信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuPersonService.delete(id);
        return jsonResult.setSuccess(true);
    }
*/


    @ApiOperation(value = "人员信息-分页查询", notes = "人员信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuPerson.class, request);
        filter.setOrderby("id desc");
        return cuPersonService.findPageResult(filter);
    }






}
