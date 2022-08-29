/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-22 13:50:08 
 */
package hry.helpLoan;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.helpLoan.apply.model.LoanIntention;
import hry.helpLoan.apply.service.LoanIntentionService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> LoanIntentionController </p>
 *
 * @author: huanggh
 * @Date: 2020-07-22 13:50:08 
 */
@Api(value = "助贷意向管理", tags = "助贷意向管理", description = "助贷意向管理")
@RestController
@RequestMapping("/apply/loanintention")
public class LoanIntentionController extends BaseController {

	@Autowired
	private LoanIntentionService loanIntentionService;

	/**
     * <p> 助贷意向管理-id查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-22 13:50:08 
     */
    @ApiOperation(value = "助贷意向管理-id查询", notes = "助贷意向管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		LoanIntention loanIntention = loanIntentionService.get(id);
        if (loanIntention != null) {
            jsonResult.setObj(loanIntention);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 助贷意向管理-添加 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-22 13:50:08 
     */
    @ApiOperation(value = "助贷意向管理-添加", notes = "助贷意向管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (LoanIntention loanIntention) {
        JsonResult jsonResult = new JsonResult();
        loanIntentionService.save(loanIntention);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 助贷意向管理-修改 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-22 13:50:08 
     */
    @ApiOperation(value = "助贷意向管理-修改", notes = "助贷意向管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (LoanIntention loanIntention) {
        JsonResult jsonResult = new JsonResult();
        loanIntentionService.update(loanIntention);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 助贷意向管理-id删除 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-22 13:50:08 
     */
    @ApiOperation(value = "助贷意向管理-id删除", notes = "助贷意向管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        loanIntentionService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 助贷意向管理-分页查询 </p>
     *
     * @author: huanggh
     * @Date: 2020-07-22 13:50:08 
     */
    @ApiOperation(value = "助贷意向管理-分页查询", notes = "助贷意向管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(LoanIntention.class, request);
        return loanIntentionService.findPageResult(filter);
    }

}
