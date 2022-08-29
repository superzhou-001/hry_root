/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-22 17:18:30 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.RedeemTotal;
import hry.scm.project.model.vo.RedeemTotalVo;
import hry.scm.project.service.RedeemTotalService;
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
 * <p> RedeemTotalController </p>
 *
 * @author: luyue
 * @Date: 2020-07-22 17:18:30 
 */
@Api(value = "赎货解除质押物汇总", tags = "赎货解除质押物汇总", description = "赎货解除质押物汇总")
@RestController
@RequestMapping("/project/redeemtotal")
public class RedeemTotalController extends BaseController {

	@Autowired
	private RedeemTotalService redeemTotalService;

	/**
     * <p> 赎货解除质押物汇总-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 17:18:30 
     */
    @ApiOperation(value = "赎货解除质押物汇总-id查询", notes = "赎货解除质押物汇总-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		RedeemTotal redeemTotal = redeemTotalService.get(id);
        if (redeemTotal != null) {
            jsonResult.setObj(redeemTotal);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 赎货解除质押物汇总-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 17:18:30 
     */
    @ApiOperation(value = "赎货解除质押物汇总-添加", notes = "赎货解除质押物汇总-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (RedeemTotal redeemTotal) {
        JsonResult jsonResult = new JsonResult();
        redeemTotalService.save(redeemTotal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 赎货解除质押物汇总-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 17:18:30 
     */
    @ApiOperation(value = "赎货解除质押物汇总-修改", notes = "赎货解除质押物汇总-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (RedeemTotal redeemTotal) {
        JsonResult jsonResult = new JsonResult();
        redeemTotalService.update(redeemTotal);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 赎货解除质押物汇总-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 17:18:30 
     */
    @ApiOperation(value = "赎货解除质押物汇总-id删除", notes = "赎货解除质押物汇总-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        redeemTotalService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 赎货解除质押物汇总-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-22 17:18:30 
     */
    @ApiOperation(value = "赎货解除质押物汇总-分页查询", notes = "赎货解除质押物汇总-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(RedeemTotal.class, request);
        return redeemTotalService.findPageResult(filter);
    }


    /**
     * <p> 解除质押物-查询质押物清单方法 </p>
     *
     * @author: luyue
     * @Date: 2020-07-24 16:18:30
     */
    @ApiOperation(value = "解除质押物-查询质押物清单方法 ", notes = "解除质押物-查询质押物清单方法 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @UnAuthentication
    @PostMapping(value = "/findDetail")
    public JsonResult findDetail (
            @ApiParam(name = "id", value = "选中质押物id", required = true) @RequestParam String id,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        params.put("reType","1");//后台请求
        return redeemTotalService.findDetail(params);
    }

    /**
     * <p> 解除质押物-确认解除货品抵押 </p>
     *
     * @author: luyue
     * @Date: 2020-07-24 16:18:30
     */
    @ApiOperation(value = "解除质押物-确认解除货品抵押 ", notes = "解除质押物-确认解除货品抵押 ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/confirmRelieve")
    public JsonResult confirmRelieve (
            @ApiParam(name = "id", value = "选中质押物id", required = true) @RequestParam String id,
            @ApiParam(name = "jsonStr", value = "解除质押物明细拼串", required = true) @RequestParam String jsonStr,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        return redeemTotalService.confirmRelieve(params);
    }

    /**
     * <p> 赎货记录-货品清单 </p>
     *
     * @author: luyue
     * @Date: 2020-07-24 16:18:30
     */
    @ApiOperation(value = "赎货记录-货品清单 ", notes = "赎货记录-货品清单",  response=RedeemTotalVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findRedeemTotal")
    public JsonResult findRedeemTotal (
            @ApiParam(name = "redeemId", value = "选中还款记录id", required = true) @RequestParam String redeemId,
            HttpServletRequest request) {
        Map<String, String> params = HttpServletRequestUtils.getParams(request);
        List<RedeemTotalVo> list=redeemTotalService.findRedeemTotalBySql(params);
        return new JsonResult().setSuccess(true).setObj(list).setMsg("查询成功");
    }



}
