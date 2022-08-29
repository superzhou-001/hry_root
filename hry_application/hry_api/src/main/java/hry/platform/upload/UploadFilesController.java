package hry.platform.upload;

import hry.bean.JsonResult;
import hry.util.upload.FileUploadContext;
import hry.util.upload.FileUploadUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p> UploadFilesController </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-24 10:27:25
 */
@Api(value = "文件上传功能", tags = "文件上传功能", description = "文件上传功能")
@RestController
@RequestMapping("/uploadFiles")
public class UploadFilesController {

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
    @PostMapping(value = "/ossUpload")
    public JsonResult ossUpload (
            @ApiParam(name = "file", value = "文件信息", required = true) @RequestParam("file") MultipartFile[] files,
            @ApiParam(name = "isPrivate", value = "是否私有加密", required = true) @RequestParam("isPrivate") boolean isPrivate) {
        // 这里使用阿里云的上传方式，如需其他上传方式，修改第三个参数即可
        List<String> pathImg = FileUploadUtil.uploadFile(fileUploadContext, files, "ossFileUploadStrategy", isPrivate);
        if (pathImg != null && pathImg.size() > 0) {
            return new JsonResult(true).setMsg("上传成功").setObj(StringUtils.join(pathImg, ","));
        }
        return new JsonResult(false).setMsg("上传失败");
    }

}
