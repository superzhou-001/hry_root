/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:02:46 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.MortgageDetail;
import hry.scm.project.service.MortgageDetailService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> MortgageDetailController </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:02:46 
 */
@Api(value = "抵质押物详细管理", tags = "抵质押物详细管理", description = "抵质押物详细管理")
@RestController
@RequestMapping("/project/mortgagedetail")
public class MortgageDetailController extends BaseController {

	@Autowired
	private MortgageDetailService mortgageDetailService;

	/**
     * <p> 抵质押物详细管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:02:46 
     */
    @ApiOperation(value = "抵质押物详细管理-id查询", notes = "抵质押物详细管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		MortgageDetail mortgageDetail = mortgageDetailService.get(id);
        if (mortgageDetail != null) {
            jsonResult.setObj(mortgageDetail);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 抵质押物详细管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:02:46 
     */
    @ApiOperation(value = "抵质押物详细管理-添加", notes = "抵质押物详细管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (MortgageDetail mortgageDetail) {
        JsonResult jsonResult = new JsonResult();
        mortgageDetailService.save(mortgageDetail);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物详细管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:02:46 
     */
    @ApiOperation(value = "抵质押物详细管理-修改", notes = "抵质押物详细管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (MortgageDetail mortgageDetail) {
        JsonResult jsonResult = new JsonResult();
        mortgageDetailService.update(mortgageDetail);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物详细管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:02:46 
     */
    @ApiOperation(value = "抵质押物详细管理-id删除", notes = "抵质押物详细管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mortgageDetailService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物详细管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:02:46 
     */
    @ApiOperation(value = "抵质押物详细管理-分页查询", notes = "抵质押物详细管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MortgageDetail.class, request);
        return mortgageDetailService.findPageResult(filter);
    }

}
