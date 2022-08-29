/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:33:28 
 */
package hry.business.cu.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.cu.model.CuBank;
import hry.business.cu.service.CuBankService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> CuBankController </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:33:28 
 */
@Api(value = "银行开户信息", tags = "银行开户信息", description = "银行开户信息")
@RestController
@RequestMapping("/cu/cubank")
public class CuBankController extends BaseController {

	@Autowired
	private CuBankService cuBankService;

	/**
     * <p> 银行开户信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28 
     */
    @ApiOperation(value = "银行开户信息-id查询", notes = "银行开户信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CuBank cuBank = cuBankService.get(id);
        if (cuBank != null) {
            jsonResult.setObj(cuBank);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 银行开户信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28 
     */
    @ApiOperation(value = "银行开户信息-添加", notes = "银行开户信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CuBank cuBank) {
        JsonResult jsonResult = new JsonResult();
        cuBankService.save(cuBank);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 银行开户信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28 
     */
    @ApiOperation(value = "银行开户信息-修改", notes = "银行开户信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CuBank cuBank) {
        JsonResult jsonResult = new JsonResult();
        cuBankService.update(cuBank);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 银行开户信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28 
     */
    @ApiOperation(value = "银行开户信息-id删除", notes = "银行开户信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuBankService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 银行开户信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28 
     */
    @ApiOperation(value = "银行开户信息-分页查询", notes = "银行开户信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuBank.class, request);
        return cuBankService.findPageResult(filter);
    }

    /**
     * <p> 企业银行开户信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:33:28
     */
    @ApiOperation(value = "企业银行开户信息-分页查询", notes = "企业银行开户信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public PageResult findList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "subjectId", value = "主体id type=1 person表Id，type=2 enterprise表Id", required = true) @RequestParam String subjectId,
            @ApiParam(name = "type", value = "客户类型 1.个人 2.企业", required = true) @RequestParam String type,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuBank.class, request);
        filter.addFilter("subjectId=",subjectId);
        filter.addFilter("type=",type);
        return cuBankService.findPageResult(filter);
    }

}
