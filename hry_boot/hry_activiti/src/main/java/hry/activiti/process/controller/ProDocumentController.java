/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:29
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDocument;
import hry.activiti.process.model.ProDocumentClass;
import hry.activiti.process.service.ProDocumentClassService;
import hry.activiti.process.service.ProDocumentService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import hry.util.UUIDUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProDocumentController </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:29
 */
@Api(value = "材料子项", tags = "材料子项", description = "材料子项")
@RestController
@RequestMapping("/process/prodocument")
public class ProDocumentController extends BaseController {

	@Autowired
	private ProDocumentService proDocumentService;

	@Autowired
    private ProDocumentClassService proDocumentClassService;

	/**
     * <p> 材料子项-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-id查询", notes = "材料子项-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		ProDocument proDocument = proDocumentService.get(id);
        if (proDocument != null) {
            jsonResult.setObj(proDocument);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 材料子项-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-添加", notes = "材料子项-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (ProDocument proDocument) {
        JsonResult jsonResult = new JsonResult();
        proDocument.setFileKey(UUIDUtil.getUUID());
        proDocumentService.save(proDocument);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料子项-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-修改", notes = "材料子项-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (ProDocument proDocument) {
        JsonResult jsonResult = new JsonResult();
        proDocumentService.update(proDocument);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料子项-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-id删除", notes = "材料子项-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDocumentService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 材料子项-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-分页查询", notes = "材料子项-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDocument.class, request);
        PageResult pageResult = proDocumentService.findPageResult(filter);
        if(pageResult!=null&&pageResult.getRows()!=null){
            List<ProDocument> rows = pageResult.getRows();
            if(rows!=null&&rows.size()>0){
                for(ProDocument proDocument :rows) {
                    ProDocumentClass proDocumentClass = proDocumentClassService.get(proDocument.getClassId());
                    if(proDocumentClass!=null){
                        proDocument.setClassName(proDocumentClass.getName());
                    }
                }
            }
        }
        return  pageResult;
    }


    /**
     * <p> 材料子项-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-05-12 17:54:29
     */
    @ApiOperation(value = "材料子项-查询全部", notes = "材料子项-查询全部")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<ProDocument> findAll (HttpServletRequest request) {
        List<ProDocument> all = proDocumentService.findAll();
        if(all!=null&&all.size()>0){
            for(ProDocument proDocument :all) {
                ProDocumentClass proDocumentClass = proDocumentClassService.get(proDocument.getClassId());
                if(proDocumentClass!=null){
                    proDocument.setClassName(proDocumentClass.getName());
                }
            }
        }
        return all;
    }


    @ApiOperation(value = "查全部材料--流程节点设置", notes = "查全部材料--流程节点设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findByNode")
    public List<ProDocument> findByNode (
            @ApiParam(name = "defineId", value = "流程定义id", required = true) @RequestParam Long defineId,
            @ApiParam(name = "nodeKey", value = "节点key", required = true) @RequestParam String nodeKey
    ) {
        return proDocumentService.findByNode(defineId,nodeKey);
    }

}
