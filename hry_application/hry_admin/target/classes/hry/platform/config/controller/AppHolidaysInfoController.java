/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:26:28 
 */
package hry.platform.config.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.config.model.AppHolidaysInfo;
import hry.platform.config.service.AppHolidaysInfoService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> AppHolidaysInfoController </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:26:28 
 */
@Api(value = "节假日明细 ", tags = "节假日明细 ", description = "节假日明细 ")
@RestController
@RequestMapping("/config/appholidaysinfo")
public class AppHolidaysInfoController extends BaseController {

	@Autowired
	private AppHolidaysInfoService appHolidaysInfoService;

    /**
     * 生成节假日区间
     * */
    @ApiOperation(value = "节假日明细 -生成节假日区间", notes = "节假日明细 -生成节假日区间")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findHolidayInterval")
    public JsonResult findHolidayInterval(@ApiParam(name = "yearId", value = "节假日年ID", required = true) @RequestParam Long yearId,
                                          HttpServletRequest request) {
        return appHolidaysInfoService.findHolidayInterval(yearId);
    }

    /**
     * 添加自定义节假日
     * */
    @ApiOperation(value = "节假日明细 -添加自定义节假日", notes = "节假日明细 -添加自定义节假日")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addHoliday")
    public JsonResult addHoliday(@ApiParam(name = "yearId", value = "节假日年ID", required = true) @RequestParam String yearId,
                                          @ApiParam(name = "dateName", value = "名称", required = true) @RequestParam String dateName,
                                          @ApiParam(name = "dateType", value = "日期类型 1 节假日 2 工作日", required = true) @RequestParam String dateType,
                                          @ApiParam(name = "startTime", value = "开始时间", required = true) @RequestParam String startTime,
                                          @ApiParam(name = "endTime", value = "结束时间", required = true) @RequestParam String endTime,
                                          HttpServletRequest request) {
        Map<String, String> param = new HashMap<>();
        param.put("yearId", yearId);
        param.put("dateName", dateName);
        param.put("dateType", dateType);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        return appHolidaysInfoService.addHoliday(param);
    }

    /**
     * 添加自定义节假日
     * */
    @ApiOperation(value = "节假日明细 -删除节假日", notes = "节假日明细 -删除节假日")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/delHoliday")
    public JsonResult delHoliday(@ApiParam(name = "yearId", value = "节假日年ID", required = true) @RequestParam String yearId,
                                 @ApiParam(name = "startTime", value = "开始时间", required = true) @RequestParam String startTime,
                                 @ApiParam(name = "endTime", value = "结束时间", required = true) @RequestParam String endTime,
                                 HttpServletRequest request) {
        Map<String, String> param = new HashMap<>();
        param.put("yearId", yearId);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        return appHolidaysInfoService.delHoliday(param);
    }

}
