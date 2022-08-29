/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:22:49 
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.AppHolidaysYear;
import hry.platform.config.service.AppHolidaysInfoService;
import hry.platform.config.service.AppHolidaysYearService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> AppHolidaysYearController </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:22:49 
 */
@Api(value = "节假日年份 ", tags = "节假日年份 ", description = "节假日年份 ")
@RestController
@RequestMapping("/config/appholidaysyear")
public class AppHolidaysYearController extends BaseController {

	@Autowired
	private AppHolidaysYearService appHolidaysYearService;

	@Autowired
    private AppHolidaysInfoService appHolidaysInfoService;

	/**
     * <p> 节假日年份 -id查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-19 11:22:49 
     */
    @ApiOperation(value = "节假日年份 -id查询", notes = "节假日年份 -id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		AppHolidaysYear appHolidaysYear = appHolidaysYearService.get(id);
        if (appHolidaysYear != null) {
            jsonResult.setObj(appHolidaysYear);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 节假日年份 -添加 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-19 11:22:49 
     */
    @ApiOperation(value = "节假日年份 -添加", notes = "节假日年份 -添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (AppHolidaysYear appHolidaysYear) {
        JsonResult jsonResult = new JsonResult();
        appHolidaysYearService.save(appHolidaysYear);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 节假日年份 -修改 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-19 11:22:49 
     */
    @ApiOperation(value = "节假日年份 -修改", notes = "节假日年份 -修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (AppHolidaysYear appHolidaysYear) {
        JsonResult jsonResult = new JsonResult();
        appHolidaysYearService.update(appHolidaysYear);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 节假日年份 -id删除 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-19 11:22:49 
     */
    @ApiOperation(value = "节假日年份 -id删除", notes = "节假日年份 -id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        appHolidaysYearService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 节假日年份 -分页查询 </p>
     *
     * @author: zhouming
     * @Date: 2020-08-19 11:22:49 
     */
    @ApiOperation(value = "节假日年份 -分页查询", notes = "节假日年份 -分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(AppHolidaysYear.class, request);
        return appHolidaysYearService.findPageResult(filter);
    }


    /**
     * 节假日年份---网络同步节假日明细
     * */
    @ApiOperation(value = "节假日年份---网络同步", notes = "节假日年份---网络同步")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/networkSyncDate")
    public JsonResult networkSyncDate(HttpServletRequest request,
                                      @ApiParam(name = "yearId", value = "年份Id", required = true) @RequestParam Long yearId) {
        return appHolidaysInfoService.networkSyncDate(yearId);
    }
}
