/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 14:11:12 
 */
package hry.business.fa.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.ct.model.CtContractTemplate;
import hry.business.ct.service.CtContractTemplateService;
import hry.business.fa.model.FaFactoringContract;
import hry.business.fa.service.FaFactoringContractService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * <p> FaFactoringContractController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 14:11:12 
 */
@Api(value = "保理合同签署信息", tags = "保理合同签署信息", description = "保理合同签署信息")
@RestController
@RequestMapping("/fa/fafactoringcontract")
public class FaFactoringContractController extends BaseController {

	@Autowired
	private FaFactoringContractService faFactoringContractService;
	@Autowired
	private CtContractTemplateService ctContractTemplateService;


	/**
     * <p> 保理合同签署信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 14:11:12 
     */
    @ApiOperation(value = "保理合同签署信息-id查询", notes = "保理合同签署信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		FaFactoringContract faFactoringContract = faFactoringContractService.get(id);
        if (faFactoringContract != null) {
            jsonResult.setObj(faFactoringContract);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 保理合同签署信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 14:11:12 
     */
    @ApiOperation(value = "保理合同签署信息-添加", notes = "保理合同签署信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (FaFactoringContract faFactoringContract) {
        JsonResult jsonResult = new JsonResult();
        try {
            //查询合同模板信息
            CtContractTemplate ctContractTemplate = ctContractTemplateService.get(faFactoringContract.getId());
            if (ctContractTemplate==null){
                return jsonResult.setMsg("模板信息不存在");
            }
            BeanUtils.copyProperties(faFactoringContract,ctContractTemplate);
            faFactoringContract.setId(null);
            faFactoringContractService.save(faFactoringContract);
            jsonResult.setSuccess(true).setMsg("成功");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * <p> 保理合同签署信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 14:11:12 
     */
    @ApiOperation(value = "保理合同签署信息-修改", notes = "保理合同签署信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (FaFactoringContract faFactoringContract) {
        JsonResult jsonResult = new JsonResult();
        faFactoringContractService.update(faFactoringContract);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理合同签署信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 14:11:12 
     */
    @ApiOperation(value = "保理合同签署信息-id删除", notes = "保理合同签署信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        faFactoringContractService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 保理合同签署信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-20 14:11:12 
     */
    @ApiOperation(value = "保理合同签署信息-分页查询", notes = "保理合同签署信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(FaFactoringContract.class, request);
        return faFactoringContractService.findPageResult(filter);
    }

}
