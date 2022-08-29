/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:53:14
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDefineDocument;
import hry.activiti.process.service.ProDefineDocumentService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> ProDefineDocumentController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:53:14
 */
@Api(value = "流程材料", tags = "流程材料", description = "流程材料")
@RestController
@RequestMapping("/process/prodefinedocument")
public class ProDefineDocumentController extends BaseController {

	@Autowired
	private ProDefineDocumentService proDefineDocumentService;

	/**
     * <p> 流程材料-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:53:14
     */
    @ApiOperation(value = "流程材料-id查询", notes = "流程材料-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDefineDocument ProDefineDocument = proDefineDocumentService.get(id);
        if (ProDefineDocument != null) {
            jsonResult.setObj(ProDefineDocument);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 流程材料-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:53:14
     */
    @ApiOperation(value = "流程材料-添加", notes = "流程材料-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDefineDocument ProDefineDocument) {
        JsonResult jsonResult = new JsonResult();
        proDefineDocumentService.save(ProDefineDocument);
        return jsonResult.setSuccess(true);
    }


    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "保存流程材料", notes = "保存流程材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/saveByFlow")
    public JsonResult saveByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "fileKey", value = "fileKey", required = true) @RequestParam String fileKey
    ) {

        if(!StringUtils.isEmpty(fileKey)&&fileKey.contains(",")){
            String[] split = fileKey.split(",");
            for(String fk:split){
                ProDefineDocument document = new ProDefineDocument();
                document.setFileKey(fk);
                proDefineDocumentService.saveByFlow(defineId,nodeKey,document);
            }
        }else if(!StringUtils.isEmpty(fileKey)) {
            ProDefineDocument document = new ProDefineDocument();
            document.setFileKey(fileKey);
            proDefineDocumentService.saveByFlow(defineId,nodeKey,document);
        }

        return new JsonResult().setSuccess(true);
    }

    /**
     * <p> 流程节点-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "删除流程材料", notes = "删除流程材料")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/deleteByFlow")
    public JsonResult deleteByFlow (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "fileKey", value = "fileKey", required = true) @RequestParam String fileKey

    ) {

        return proDefineDocumentService.deleteByFlow(defineId,nodeKey,fileKey);
    }


    /**
     * <p> 是否必传 </p>
     *
     * @author: liushileif
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "是否必传", notes = "是否必传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/setReuqire")
    public JsonResult setReuqire (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "fileKey", value = "fileKey", required = true) @RequestParam String fileKey,
            @ApiParam(name = "isRequire", value = "是否必传0否1是", required = true) @RequestParam Integer isRequire

    ) {
        return proDefineDocumentService.setReuqire(defineId,nodeKey,fileKey,isRequire);
    }


    /**
     * <p> 是否必传 </p>
     *
     * @author: liushileif
     * @Date: 2020-05-12 13:41:21
     */
    @ApiOperation(value = "设置显示状态", notes = "设置显示状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/setViewModel")
    public JsonResult setViewModel (
            @ApiParam(name = "defineId", value = "defineId", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "nodeKey", required = true) @RequestParam String nodeKey,
            @ApiParam(name = "fileKey", value = "fileKey", required = true) @RequestParam String fileKey,
            @ApiParam(name = "viewModel", value = "1编辑2审核3预览", required = true) @RequestParam Integer viewModel

    ) {
        return proDefineDocumentService.setViewModel(defineId,nodeKey,fileKey,viewModel);
    }


    /**
     * <p> 流程材料-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:53:14
     */
    @ApiOperation(value = "流程材料-修改", notes = "流程材料-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDefineDocument ProDefineDocument) {
        JsonResult jsonResult = new JsonResult();
        proDefineDocumentService.update(ProDefineDocument);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程材料-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:53:14
     */
    @ApiOperation(value = "流程材料-id删除", notes = "流程材料-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDefineDocumentService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程材料-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:53:14
     */
    @ApiOperation(value = "流程材料-分页查询", notes = "流程材料-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDefineDocument.class, request);
        return proDefineDocumentService.findPageResult(filter);
    }

}
