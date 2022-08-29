/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-05 17:44:32 
 */
package hry.business.cf.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.model.CfFacilityContract;
import hry.business.cf.service.CfFacilityContractService;
import hry.business.ct.model.CtContractTemplate;
import hry.business.ct.service.CtContractTemplateService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import hry.util.contract.ContractCodeUtil;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p> CfFacilityContractController </p>
 *
 * @author: yaoz
 * @Date: 2020-08-05 17:44:32 
 */
@Api(value = "授信合同签署", tags = "授信合同签署", description = "授信合同签署")
@RestController
@RequestMapping("/cf/cffacilitycontract")
public class CfFacilityContractController extends BaseController {

	@Autowired
	private CfFacilityContractService cfFacilityContractService;


    @Autowired
    private CtContractTemplateService ctContractTemplateService;

	/**
     * <p> 授信合同签署-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-05 17:44:32 
     */
    @ApiOperation(value = "授信合同签署-id查询", notes = "授信合同签署-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CfFacilityContract cfFacilityContract = cfFacilityContractService.get(id);
        if (cfFacilityContract != null) {
            jsonResult.setObj(cfFacilityContract);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 授信合同签署-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-05 17:44:32 
     */
    @ApiOperation(value = "授信合同签署-添加", notes = "授信合同签署-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CfFacilityContract cfFacilityContract) {
        JsonResult jsonResult = new JsonResult();
        try {
            //查询合同模板信息
            CtContractTemplate ctContractTemplate = ctContractTemplateService.get(cfFacilityContract.getId());
            if (ctContractTemplate==null){
                return jsonResult.setMsg("模板信息不存在");
            }
            BeanUtils.copyProperties(cfFacilityContract,ctContractTemplate);
            cfFacilityContract.setId(null);
            cfFacilityContract.setContractCode(ContractCodeUtil.getCode(ctContractTemplate.getCodeFormat()));
            cfFacilityContractService.save(cfFacilityContract);
            jsonResult.setSuccess(true).setMsg("成功");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    /**
     * <p> 授信合同签署-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-05 17:44:32 
     */
    @ApiOperation(value = "授信合同签署-修改", notes = "授信合同签署-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CfFacilityContract cfFacilityContract) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityContractService.update(cfFacilityContract);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信合同签署-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-05 17:44:32 
     */
    @ApiOperation(value = "授信合同签署-id删除", notes = "授信合同签署-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityContractService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信合同签署-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-08-05 17:44:32 
     */
    @ApiOperation(value = "授信合同签署-分页查询", notes = "授信合同签署-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CfFacilityContract.class, request);
        return cfFacilityContractService.findPageResult(filter);
    }

    @ApiOperation(value = "授信合同签署-查询", notes = "授信合同签署-查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listAll")
    public JsonResult listAll (
            @ApiParam(name = "projectId", value = "授信id", required = true) @RequestParam Long projectId,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter filter = new QueryFilter(CfFacilityContract.class, request);
        filter.addFilter("projectId=",projectId);
        List<CfFacilityContract> cfFacilityContracts = cfFacilityContractService.find(filter);
        return jsonResult.setSuccess(true).setObj(cfFacilityContracts);
    }

}
