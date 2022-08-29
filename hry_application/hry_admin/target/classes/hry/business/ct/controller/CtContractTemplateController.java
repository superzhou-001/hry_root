/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:03
 */
package hry.business.ct.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplateElement;
import hry.business.ct.model.CtContractTemplateSeal;
import hry.business.ct.service.CtContractTemplateElementService;
import hry.business.ct.service.CtContractTemplateSealService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.business.ct.model.CtContractTemplate;
import hry.business.ct.service.CtContractTemplateService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.itext.PDFManage;
import hry.util.itext.PdfToImg;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.*;

/**
 * <p> CtContractTemplateController </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:03 
 */
@Api(value = "合同模板", tags = "合同模板", description = "合同模板")
@RestController
@RequestMapping("/ct/ctcontracttemplate")
public class CtContractTemplateController extends BaseController {

    @Autowired
    private CtContractTemplateService ctContractTemplateService;

    @Autowired
    private CtContractTemplateElementService ctContractTemplateElementService;
    @Autowired
    private CtContractTemplateSealService ctContractSealElementService;

    /**
     * <p> 合同模板-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:03 
     */
    @ApiOperation(value = "合同模板-id查询", notes = "合同模板-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CtContractTemplate ctContractTemplate = ctContractTemplateService.getContractTemplateById(id);
        if (ctContractTemplate != null) {
            //查询模板元素
            List<CtContractTemplateElement> templateElement = ctContractTemplateElementService.findElementByTemplateId(id);
            ctContractTemplate.setList(templateElement);
            jsonResult.setObj(ctContractTemplate);
            //添加模板印章
            List<CtContractTemplateSeal> templateSeals = ctContractSealElementService.findSealByTemplateId(id);
            ctContractTemplate.setSealList(templateSeals);
            jsonResult.setObj(ctContractTemplate);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    /**
     * <p> 合同模板-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:03 
     */
    @ApiOperation(value = "合同模板-添加", notes = "合同模板-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add(CtContractTemplate ctContractTemplate) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateService.save(ctContractTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:03 
     */
    @ApiOperation(value = "合同模板-修改", notes = "合同模板-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify(CtContractTemplate ctContractTemplate) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateService.update(ctContractTemplate);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:03 
     */
    @ApiOperation(value = "合同模板-id删除", notes = "合同模板-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        ctContractTemplateService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 合同模板-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-04-26 15:50:03 
     */
    @ApiOperation(value = "合同模板-分页查询", notes = "合同模板-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractTemplate.class, request);
        return ctContractTemplateService.findPageBySql(filter);
    }

    @ApiOperation(value = "合同模板-类型查询", notes = "合同模板-类型查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAllByType")
    public JsonResult listAllByType (
            @ApiParam(name = "contractTypeId", value = "当前页码", required = true) @RequestParam Long contractTypeId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CtContractTemplate.class, request);
        filter.addFilter("contractTypeId=",contractTypeId);
        return new JsonResult().setSuccess(true).setObj(ctContractTemplateService.find(filter));
    }

    @ApiOperation(value = "合同模板-生成模板", notes = "合同模板-生成模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/generateTemplateElement")
    public JsonResult generateTemplateElement(CtContractTemplate ctContractTemplate) {
        return ctContractTemplateService.updateTemplateAndElement(ctContractTemplate);
    }

    @ApiOperation(value = "合同模板-预览", notes = "合同模板-预览")
    @GetMapping("previewPdf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    public void previewPdf(@ApiParam(name = "id", value = "id", required = true) @RequestParam Long id,
                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        //byte[] bytes = PdfUtils.addText("123", 100, 10);
        /*ArrayList<String> imageUrllist = new ArrayList<String>();
        imageUrllist.add("C:\\Users\\lenovo\\Desktop\\11.png");
        imageUrllist.add("C:\\Users\\lenovo\\Desktop\\22.jpg");
        String pdfUrl = "C:\\Users\\lenovo\\Desktop\\\\33.pdf";
        byte[] bytes = PDFManage.Pdf(imageUrllist, pdfUrl);*/

        //查询模板
        CtContractTemplate ctContractTemplate = ctContractTemplateService.get(id);
        if(ctContractTemplate==null){
            return;
        }
        //查询模板要素
        List<CtContractTemplateElement> elements = ctContractTemplateElementService.findElementByTemplateId(ctContractTemplate.getId());
        byte[] bytes = PDFManage.addText(PDFManage.doGetDownload(ctContractTemplate.getFileUrl()),elements);
        if(bytes==null){
            return;
        }
        OutputStream os = response.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(bytes);
        bos.close();
        os.close();
    }

    @ApiOperation(value = "PDF转IMG", notes = "PDF转IMG")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/pdfToImg")
    public void ossUpload(
            @ApiParam(name = "file", value = "文件信息", required = false) @RequestParam("file") MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ByteArrayOutputStream> list = PdfToImg.pdfPathToImagePaths(file,"", response);
        boolean b = true;
        for (ByteArrayOutputStream byteArrayOutputStream : list) {
            String strCode = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            StringBuilder stringBuilder = new StringBuilder();
            if (!b){
                stringBuilder.append("&&&");
            }
            stringBuilder.append(strCode);
            b = false;
            response.getWriter().println(stringBuilder);
        }
    }

    @ApiOperation(value = "PDF转IMG", notes = "PDF转IMG")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/pdfUrlToImg")
    public void pdfUrlToImg(
            @ApiParam(name = "fileUrl", value = "文件路径", required = false) @RequestParam("fileUrl") String fileUrl,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<ByteArrayOutputStream> list = PdfToImg.pdfPathToImagePaths(null,fileUrl, response);
        boolean b = true;
        for (ByteArrayOutputStream byteArrayOutputStream : list) {
            String strCode = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            StringBuilder stringBuilder = new StringBuilder();
            if (!b){
                stringBuilder.append("&&&");
            }
            stringBuilder.append(strCode);
            b = false;
            response.getWriter().println(stringBuilder);
        }
    }

}
