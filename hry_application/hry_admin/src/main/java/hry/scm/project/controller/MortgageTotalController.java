/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:01:27 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.MortgageTotal;
import hry.scm.project.service.MortgageProjectService;
import hry.scm.project.service.MortgageTotalService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.HttpServletRequestUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p> MortgageTotalController </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:01:27 
 */
@Api(value = "抵质押物汇总管理", tags = "抵质押物汇总管理", description = "抵质押物汇总管理")
@RestController
@RequestMapping("/project/mortgagetotal")
public class MortgageTotalController extends BaseController {

	@Autowired
	private MortgageTotalService mortgageTotalService;
	@Autowired
    private MortgageProjectService mortgageProjectService;

	/**
     * <p> 抵质押物汇总管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27 
     */
   /* @ApiOperation(value = "抵质押物汇总管理-id查询", notes = "抵质押物汇总管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		MortgageTotal mortgageTotal = mortgageTotalService.get(id);
        if (mortgageTotal != null) {
            jsonResult.setObj(mortgageTotal);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }*/

	/**
     * <p> 抵质押物汇总管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27 
     */
/*    @ApiOperation(value = "抵质押物汇总管理-添加", notes = "抵质押物汇总管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (MortgageTotal mortgageTotal) {
        JsonResult jsonResult = new JsonResult();
        mortgageTotalService.save(mortgageTotal);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 抵质押物汇总管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27 
     */
/*    @ApiOperation(value = "抵质押物汇总管理-修改", notes = "抵质押物汇总管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (MortgageTotal mortgageTotal) {
        JsonResult jsonResult = new JsonResult();
        mortgageTotalService.update(mortgageTotal);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 抵质押物汇总管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27 
     */
/*    @ApiOperation(value = "抵质押物汇总管理-id删除", notes = "抵质押物汇总管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        mortgageTotalService.delete(id);
        return jsonResult.setSuccess(true);
    }*/

    /**
     * <p> 现货质押价格管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27 
     */
    @ApiOperation(value = "现货质押价格管理-分页查询", notes = "现货质押价格管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "projectNumber", value = "订单编号", required = false) @RequestParam String projectNumber,
            @ApiParam(name = "goodsName", value = "货品名称", required = false) @RequestParam String goodsName,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(MortgageTotal.class, request);
        return mortgageTotalService.list(filter);
    }

    /**
     * <p> 查询企业库存 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27
     */
    @ApiOperation(value = "查询质押物库存清单", notes = "查询质押物库存清单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findStorage")
    public JsonResult findStorage (@ApiParam(name = "totalId", value = "所选汇总记录id", required = true) @RequestParam String totalId,
                                      HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        jsonResult=mortgageTotalService.findStorage(params);
        return jsonResult;
    }
    /**
     * <p> 现货质押价格管理-修改价格 </p>
     *
     * @author: luyue
     * @Date: 2020-07-13 19:01:27
     */
    @ApiOperation(value = "现货质押价格管理-修改价格", notes = "现货质押价格管理-修改价格")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/changePrice")
    public JsonResult changePrice (
            @ApiParam(name = "totalId", value = "该记录id", required = true) @RequestParam String totalId,
            @ApiParam(name = "price", value = "修改价格", required = true) @RequestParam String price,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return mortgageProjectService.changePrice(params);
    }

    /**
     * <p> 查询质押物详细库存信息 </p>
     *
     * @author: luyue
     * @Date: 2020-07-28 17:52:27
     */
    @ApiOperation(value = "质押物汇总记录-查询质押物详细库存信息", notes = "质押物汇总记录-查询质押物详细库存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findDetail")
    public JsonResult findDetail (@ApiParam(name = "totalId", value = "所选汇总记录id", required = true) @RequestParam String totalId,
                                   HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        jsonResult=mortgageTotalService.findDetail(params);
        return jsonResult;
    }





}
