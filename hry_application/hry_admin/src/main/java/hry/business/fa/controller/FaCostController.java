/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 14:51:19 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.model.FaCostNlms;
import hry.business.fa.service.FaCostNlmsService;
import hry.business.fa.service.impl.FaCostNlmsServiceImpl;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.fa.model.FaCost;
import hry.business.fa.service.FaCostService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> FaCostController </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 14:51:19 
 */
@Api(value = "费用表 ", tags = "费用表 ", description = "费用表 ")
@RestController
@RequestMapping("/fa/facost")
public class FaCostController extends BaseController {

	@Autowired
	private FaCostService faCostService;
	@Autowired
    private FaCostNlmsService faCostNlmsService;

	/**
     * <p> 费用表 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:51:19 
     */
    @ApiOperation(value = "费用表 -id查询", notes = "费用表 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaCost faCost = faCostService.get(id);
        if (faCost != null) {
            jsonResult.setObj(faCost);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 费用表 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:51:19 
     */
    @ApiOperation(value = "费用表 -添加", notes = "费用表 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaCost faCost) {
        JsonResult jsonResult = new JsonResult();
        faCostService.save(faCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用表 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:51:19 
     */
    @ApiOperation(value = "费用表 -修改", notes = "费用表 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaCost faCost) {
        JsonResult jsonResult = new JsonResult();
        faCostService.update(faCost);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用表 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:51:19 
     */
    @ApiOperation(value = "费用表 -id删除", notes = "费用表 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faCostService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 费用表 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-07-17 14:51:19 
     */
    @ApiOperation(value = "费用表 -分页查询", notes = "费用表 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaCost.class, request);
        PageResult result = faCostService.findPageResult(filter);
        List<FaCost> costList = result.getRows();
        costList.forEach((cost) -> {
           if (cost.getNlmsid() != null) {
               FaCostNlms nlms = faCostNlmsService.get(cost.getNlmsid());
               cost.setNlmsname(nlms.getNlmsname());
           }
        });
        result.setRows(costList);
        return result;
    }

}
