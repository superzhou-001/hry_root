/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-21 11:19:36
 */
package hry.activiti.process.controller;

import hry.activiti.process.model.ProDocumentItem;
import hry.activiti.process.service.ProDocumentItemService;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import hry.util.upload.FileInfo;
import hry.util.upload.FileUploadContext;
import hry.util.upload.FileUploadUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> ProDocumentItemController </p>
 *
 * @author: liushilei
 * @Date: 2020-07-21 11:19:36
 */
@Api(value = "流程材料文件", tags = "流程材料文件", description = "流程材料文件")
@RestController
@RequestMapping("/process/prodocumentitem")
public class ProDocumentItemController extends BaseController {

    @Autowired
    private ProDocumentItemService proDocumentItemService;

    @Autowired
    private FileUploadContext fileUploadContext;

    /**
     * <p> 文件上传-阿里云</p>
     *
     * @author: liuchenghui
     * @Date: 2020-03-24 10:27:25
     */
    @ApiOperation(value = "文件上传-阿里云，返回文件上传路径，多个逗号隔开", notes = "文件上传-阿里云，返回文件上传路径，多个逗号隔开")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/upload")
    public JsonResult upload(
            @ApiParam(name = "processId", value = "流程id", required = true) @RequestParam Long processId,
            @ApiParam(name = "projectId", value = "业务流程id", required = true) @RequestParam Long projectId,
            @ApiParam(name = "classId", value = "分类id", required = true) @RequestParam Long classId,
            @ApiParam(name = "documentId", value = "材料id", required = true) @RequestParam Long documentId,
            @ApiParam(name = "file", value = "文件信息", required = true) @RequestParam("file") MultipartFile[] files) {
        // 这里使用阿里云的上传方式，如需其他上传方式，修改第三个参数即可
        List<FileInfo> pathImg = FileUploadUtil.uploadFileInfo(fileUploadContext, files, "ossFileUploadStrategy", true);
        if (pathImg != null && pathImg.size() > 0) {
            for (FileInfo fileInfo : pathImg) {
                ProDocumentItem item = new ProDocumentItem();
                item.setProcessId(processId);
                item.setProjectId(projectId);
                item.setFileName(fileInfo.getFileName());
                item.setFilePath(fileInfo.getFilePath());
                item.setFileSize(fileInfo.getFileSize());
                item.setClassId(classId);
                item.setDocumentId(documentId);
                item.setSource(1);//后台
                proDocumentItemService.save(item);

                fileInfo.setFileViewPath(FileUploadUtil.getFilePath(fileUploadContext, "ossFileUploadStrategy", item.getFilePath(), true));
            }

            return new JsonResult(true).setMsg("上传成功").setObj(pathImg);
        }

        return new JsonResult(false).setMsg("上传失败");
    }


    /**
     * <p> 流程材料文件-id查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "流程材料文件-id查询", notes = "流程材料文件-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ProDocumentItem proDocumentItem = proDocumentItemService.get(id);
        if (proDocumentItem != null) {
            jsonResult.setObj(proDocumentItem);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 流程材料文件-添加 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "流程材料文件-添加", notes = "流程材料文件-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add(ProDocumentItem proDocumentItem) {
        JsonResult jsonResult = new JsonResult();
        proDocumentItemService.save(proDocumentItem);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程材料文件-修改 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "流程材料文件-修改", notes = "流程材料文件-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify(ProDocumentItem proDocumentItem) {
        JsonResult jsonResult = new JsonResult();
        proDocumentItemService.update(proDocumentItem);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程材料文件-id删除 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "流程材料文件-id删除", notes = "流程材料文件-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        proDocumentItemService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 流程材料文件-分页查询 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "流程材料文件-分页查询", notes = "流程材料文件-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDocumentItem.class, request);
        return proDocumentItemService.findPageResult(filter);
    }


    /**
     * <p> 查询流程材料文件 </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "查询流程材料文件", notes = "查询流程材料文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findByProjectId")
    public List<ProDocumentItem> findByProjectId(
            @ApiParam(name = "projectId", value = "业务流程id", required = true) @RequestParam Long projectId,
            @ApiParam(name = "documentId", value = "材料id", required = true) @RequestParam Long documentId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(ProDocumentItem.class, request);
        filter.addFilter("projectId=", projectId);
        filter.addFilter("documentId=", documentId);
        List<ProDocumentItem> list = proDocumentItemService.find(filter);
        if (list != null && list.size() > 0) {
            for (ProDocumentItem item : list) {
                item.setFileViewPath(FileUploadUtil.getFilePath(fileUploadContext, "ossFileUploadStrategy", item.getFilePath(), true));
            }
        }
        return list;
    }

    /**
     * <p> 审核文件通过材料id </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "获取材料回显图片", notes = "获取材料回显图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/getViewPathByDocumentId")
    public JsonResult getViewPathByDocumentId(
            @ApiParam(name = "projectId", value = "业务流程id", required = true) @RequestParam Long projectId,
            @ApiParam(name = "documentId", value = "材料id", required = true) @RequestParam String documentId) {

        QueryFilter filter = new QueryFilter(ProDocumentItem.class);
        filter.addFilter("projectId=", projectId);
        filter.addFilter("documentId=", documentId);
        List<ProDocumentItem> list = proDocumentItemService.find(filter);
        String fileViewPath = "";
        if (list != null && list.size() > 0) {
            for (ProDocumentItem item : list) {

                if (item.getFilePath().contains(".jpg") ||
                        item.getFilePath().contains(".jpeg") ||
                        item.getFilePath().contains(".png") ||
                        item.getFilePath().contains(".gif")
                ) {
                    fileViewPath = FileUploadUtil.getFilePath(fileUploadContext, "ossFileUploadStrategy", item.getFilePath(), true);
                    break;
                }
            }
        }
        return new JsonResult().setSuccess(true).setObj(fileViewPath);
    }

    /**
     * <p> 审核文件通过材料id </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "审核文件通过材料id", notes = "审核文件通过材料id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/checkByDocumentId")
    public JsonResult checkByDocumentId(
            @ApiParam(name = "projectId", value = "业务流程id", required = true) @RequestParam Long projectId,
            @ApiParam(name = "documentId", value = "材料id", required = true) @RequestParam String documentId,
            @ApiParam(name = "isCheck", value = "审核结果1不通过2通过", required = true) @RequestParam Integer isCheck,
            HttpServletRequest request) {

        QueryFilter filter = new QueryFilter(ProDocumentItem.class, request);
        filter.addFilter("projectId=", projectId);
        filter.addFilter("documentId_in", documentId);
        List<ProDocumentItem> list = proDocumentItemService.find(filter);
        if (list != null && list.size() > 0) {
            for (ProDocumentItem item : list) {
                item.setIsCheck(isCheck);//审核
                proDocumentItemService.update(item);
            }
        }
        return new JsonResult().setSuccess(true);
    }


    /**
     * <p> 审核文件通过文件id </p>
     *
     * @author: liushilei
     * @Date: 2020-07-21 11:19:36
     */
    @ApiOperation(value = "审核文件通过文件id", notes = "审核文件通过文件id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/checkById")
    public JsonResult checkById(
            @ApiParam(name = "id", value = "业务流程id", required = true) @RequestParam String id,
            @ApiParam(name = "isCheck", value = "审核结果1不通过2通过", required = true) @RequestParam Integer isCheck,
            HttpServletRequest request) {

        QueryFilter filter = new QueryFilter(ProDocumentItem.class, request);
        filter.addFilter("id_in", id);
        List<ProDocumentItem> list = proDocumentItemService.find(filter);
        if (list != null && list.size() > 0) {
            for (ProDocumentItem item : list) {
                item.setIsCheck(isCheck);//审核
                proDocumentItemService.update(item);
            }
        }
        return new JsonResult().setSuccess(true);
    }


}
