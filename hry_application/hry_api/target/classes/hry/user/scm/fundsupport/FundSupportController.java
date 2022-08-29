/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:25:30 
 */
package hry.user.scm.fundsupport;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.user.scm.access.RoleAccess;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> FundSupportController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:25:30 
 */
@Api(value = "资金方", tags = "资金方", description = "资金方")
@RestController
@RequestMapping("/fundsupport")
public class FundSupportController extends BaseController {

	@Autowired
	private FundSupportService fundSupportService;

    /**
     * <p> 资金方-list查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30
     */
    @ApiOperation(value = "资金方-list查询", notes = "资金方-list查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public List<FundSupport> list (
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupport.class, request);
        filter.addFilter("isEnable=",2);
        return fundSupportService.find(filter);
    }
	/**
     * <p> 资金方-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30 
     */
    @ApiOperation(value = "资金方-id查询", notes = "资金方-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FundSupport fundSupport = fundSupportService.get(id);
        if (fundSupport != null) {
            jsonResult.setObj(fundSupport);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 资金方-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30 
     */
    @ApiOperation(value = "资金方-添加", notes = "资金方-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FundSupport fundSupport) {
        JsonResult jsonResult = new JsonResult();
        fundSupportService.save(fundSupport);

        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30 
     */
    @ApiOperation(value = "资金方-修改", notes = "资金方-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FundSupport fundSupport) {
        JsonResult jsonResult = new JsonResult();
        fundSupportService.update(fundSupport);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30 
     */
    @ApiOperation(value = "资金方-id删除", notes = "资金方-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        fundSupportService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 资金方-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:25:30 
     */
    @ApiOperation(value = "资金方-分页查询", notes = "资金方-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByPage")
    public PageResult listByPage (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FundSupport.class, request);
        return fundSupportService.findPageResult(filter);
    }

}
