/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-28 16:37:12 
 */
package hry.user.scm.project;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.IncreaseMoney;
import hry.scm.project.service.IncreaseMoneyService;
import hry.scm.project.service.MortgageProjectService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> IncreaseMoneyController </p>
 *
 * @author: luyue
 * @Date: 2020-07-28 16:37:12 
 */
@Api(value = "补款记录", tags = "补款记录", description = "补款记录")
@RestController
@RequestMapping("/project/increasemoney")
public class IncreaseMoneyController extends BaseController {

	@Autowired
	private IncreaseMoneyService increaseMoneyService;
	@Autowired
    private MortgageProjectService mortgageProjectService;

	/**
     * <p> 补款记录-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 16:37:12 
     */
    @ApiOperation(value = "补款记录-id查询", notes = "补款记录-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		IncreaseMoney increaseMoney = increaseMoneyService.get(id);
        if (increaseMoney != null) {
            jsonResult.setObj(increaseMoney);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 补款记录-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 16:37:12 
     */
    @ApiOperation(value = "补款记录-添加", notes = "补款记录-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (IncreaseMoney increaseMoney) {
        JsonResult jsonResult = new JsonResult();
        CuCustomer user = (CuCustomer) JWTContext.getUser(CuCustomer.class);
        increaseMoney.setApplyId(user.getId());
        String number=mortgageProjectService.createIncreaseNumber(increaseMoney.getProjectId());
        increaseMoney.setNumber(number);
        increaseMoneyService.save(increaseMoney);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 补款记录-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 16:37:12 
     */
/*
    @ApiOperation(value = "补款记录-修改", notes = "补款记录-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (IncreaseMoney increaseMoney) {
        JsonResult jsonResult = new JsonResult();
        increaseMoneyService.update(increaseMoney);
        return jsonResult.setSuccess(true);
    }
*/

    /**
     * <p> 补款记录-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 16:37:12 
     */
/*    @ApiOperation(value = "补款记录-id删除", notes = "补款记录-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        increaseMoneyService.delete(id);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 补款记录-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 16:37:12 
     */
    @ApiOperation(value = "补款记录-分页查询", notes = "补款记录-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(IncreaseMoney.class, request);
        return increaseMoneyService.findPageResult(filter);
    }

}
