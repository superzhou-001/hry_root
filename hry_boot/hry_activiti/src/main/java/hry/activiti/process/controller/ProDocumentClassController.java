/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:44
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDocumentClass;
import hry.activiti.process.service.ProDocumentClassService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProDocumentClassController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:44
 */
@Api(value = "材料分类", tags = "材料分类", description = "材料分类")
@RestController
@RequestMapping("/process/prodocumentclass")
public class ProDocumentClassController extends BaseController {

	@Autowired
	private ProDocumentClassService proDocumentClassService;

	/**
     * <p> 材料分类-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:44
     */
    @ApiOperation(value = "材料分类-id查询", notes = "材料分类-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDocumentClass proDocumentClass = proDocumentClassService.get(id);
        if (proDocumentClass != null) {
            jsonResult.setObj(proDocumentClass);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 材料分类-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:44
     */
    @ApiOperation(value = "材料分类-添加", notes = "材料分类-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDocumentClass proDocumentClass) {
        JsonResult jsonResult = new JsonResult();
        proDocumentClassService.save(proDocumentClass);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料分类-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:44
     */
    @ApiOperation(value = "材料分类-修改", notes = "材料分类-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDocumentClass proDocumentClass) {
        JsonResult jsonResult = new JsonResult();
        proDocumentClassService.update(proDocumentClass);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料分类-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:44
     */
    @ApiOperation(value = "材料分类-id删除", notes = "材料分类-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDocumentClassService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料分类-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:44
     */
    @ApiOperation(value = "材料分类-分页查询", notes = "材料分类-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDocumentClass.class, request);
        return proDocumentClassService.findPageResult(filter);
    }



    /**
     * <p> 材料子项-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料分类-查询全部", notes = "材料分类-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProDocumentClass> findAll (HttpServletRequest request) {
        return  proDocumentClassService.findAll();
    }


}
