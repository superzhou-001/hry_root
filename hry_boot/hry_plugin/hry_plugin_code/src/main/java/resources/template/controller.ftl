/**
 * Copyright: ${HRY_COMPANY}
 *
 * @author: ${codeAuth!}
 * @version: V1.0
 * @Date: ${codeDate!}
 */
package ${HRY_PACKAGE!}.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import ${HRY_PACKAGE!}.model.${HRY_MODEL!};
import ${HRY_PACKAGE!}.service.${HRY_MODEL!}Service;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ${HRY_MODEL!}Controller </p>
 *
 * @author: ${codeAuth!}
 * @Date: ${codeDate!}
 */
@Api(value = "${HRY_FUNCTION_DESC!}", tags = "${HRY_FUNCTION_DESC!}", description = "${HRY_FUNCTION_DESC!}")
@RestController
@RequestMapping("${RequestMapping!}")
public class ${HRY_MODEL!}Controller extends BaseController {

	@Autowired
	private ${HRY_MODEL!}Service ${HRY_MODEL_SMALL!}Service;

	/**
     * <p> ${HRY_FUNCTION_DESC!}-id查询 </p>
     *
     * @author: ${codeAuth!}
     * @Date: ${codeDate!}
     */
    @ApiOperation(value = "${HRY_FUNCTION_DESC!}-id查询", notes = "${HRY_FUNCTION_DESC!}-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		${HRY_MODEL!} ${HRY_MODEL_SMALL!} = ${HRY_MODEL_SMALL!}Service.get(id);
        if (${HRY_MODEL_SMALL!} != null) {
            jsonResult.setObj(${HRY_MODEL_SMALL!});
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> ${HRY_FUNCTION_DESC!}-添加 </p>
     *
     * @author: ${codeAuth!}
     * @Date: ${codeDate!}
     */
    @ApiOperation(value = "${HRY_FUNCTION_DESC!}-添加", notes = "${HRY_FUNCTION_DESC!}-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (${HRY_MODEL!} ${HRY_MODEL_SMALL!}) {
        JsonResult jsonResult = new JsonResult();
        ${HRY_MODEL_SMALL!}Service.save(${HRY_MODEL_SMALL!});
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> ${HRY_FUNCTION_DESC!}-修改 </p>
     *
     * @author: ${codeAuth!}
     * @Date: ${codeDate!}
     */
    @ApiOperation(value = "${HRY_FUNCTION_DESC!}-修改", notes = "${HRY_FUNCTION_DESC!}-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (${HRY_MODEL!} ${HRY_MODEL_SMALL!}) {
        JsonResult jsonResult = new JsonResult();
        ${HRY_MODEL_SMALL!}Service.update(${HRY_MODEL_SMALL!});
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> ${HRY_FUNCTION_DESC!}-id删除 </p>
     *
     * @author: ${codeAuth!}
     * @Date: ${codeDate!}
     */
    @ApiOperation(value = "${HRY_FUNCTION_DESC!}-id删除", notes = "${HRY_FUNCTION_DESC!}-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ${HRY_MODEL_SMALL!}Service.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> ${HRY_FUNCTION_DESC!}-分页查询 </p>
     *
     * @author: ${codeAuth!}
     * @Date: ${codeDate!}
     */
    @ApiOperation(value = "${HRY_FUNCTION_DESC!}-分页查询", notes = "${HRY_FUNCTION_DESC!}-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(${HRY_MODEL!}.class, request);
        return ${HRY_MODEL_SMALL!}Service.findPageResult(filter);
    }

}
