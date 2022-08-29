/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-10 11:34:53 
 */
package hry.scm.project.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.project.model.Storage;
import hry.scm.project.service.StorageService;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p> StorageController </p>
 *
 * @author: luyue
 * @Date: 2020-07-10 11:34:53 
 */
@Api(value = "仓库存储管理", tags = "仓库存储管理", description = "仓库存储管理")
@RestController
@RequestMapping("/project/storage")
public class StorageController extends BaseController {

	@Autowired
	private StorageService storageService;

	/**
     * <p> 仓库存储管理-id查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-10 11:34:53 
     */
    @ApiOperation(value = "仓库存储管理-id查询", notes = "仓库存储管理-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		Storage storage = storageService.get(id);
        if (storage != null) {
            jsonResult.setObj(storage);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 仓库存储管理-添加 </p>
     *
     * @author: luyue
     * @Date: 2020-07-10 11:34:53 
     */
    @ApiOperation(value = "仓库存储管理-添加", notes = "仓库存储管理-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (Storage storage) {
        JsonResult jsonResult = new JsonResult();
        storageService.save(storage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 仓库存储管理-修改 </p>
     *
     * @author: luyue
     * @Date: 2020-07-10 11:34:53 
     */
    @ApiOperation(value = "仓库存储管理-修改", notes = "仓库存储管理-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (Storage storage) {
        JsonResult jsonResult = new JsonResult();
        storageService.update(storage);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 仓库存储管理-id删除 </p>
     *
     * @author: luyue
     * @Date: 2020-07-10 11:34:53 
     */
    @ApiOperation(value = "仓库存储管理-id删除", notes = "仓库存储管理-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        storageService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 仓库存储管理-分页查询 </p>
     *
     * @author: luyue
     * @Date: 2020-07-10 11:34:53 
     */
    @ApiOperation(value = "仓库存储管理-分页查询", notes = "仓库存储管理-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(Storage.class, request);
        return storageService.findPageResult(filter);
    }


    @ApiOperation(value = "导入excel库存信息", notes = "导入excel库存信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/importExcel")
    public JsonResult importExcel (
            @ApiParam(name = "file", value = "文件信息", required = true) @RequestParam("file") MultipartFile file) throws IOException {
                if(file.isEmpty()){
                    return   new JsonResult().setSuccess(false).setMsg("请选择要上传的文件");
                }
         long startTime=System.currentTimeMillis();   //获取开始时间
          JsonResult  jsonResult=storageService.importExcel(file);

         long endTime=System.currentTimeMillis(); //获取结束时间
         System.out.println("程序运行时间： "+(endTime - startTime)+"ms");
          return jsonResult;
    }

}
