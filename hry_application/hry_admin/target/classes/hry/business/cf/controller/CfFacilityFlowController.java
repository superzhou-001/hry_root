/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 16:17:44
 */
package hry.business.cf.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.model.CfFacilityFlow;
import hry.business.cf.service.CfFacilityFlowService;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.service.CuEnterpriseService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p> CfFacilityFlowController </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 16:17:44
 */
@Api(value = "授信信息", tags = "授信信息", description = "授信信息")
@RestController
@RequestMapping("/cf/cffacilityflow")
public class CfFacilityFlowController extends BaseController {

	@Autowired
	private CfFacilityFlowService cfFacilityFlowService;

	@Autowired
    private CuEnterpriseService cuEnterpriseService;

	/**
     * <p> 授信信息-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-id查询", notes = "授信信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		CfFacilityFlow cfFacilityFlow = cfFacilityFlowService.get(id);
        if (cfFacilityFlow != null) {
            jsonResult.setObj(cfFacilityFlow);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 授信信息-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-添加", notes = "授信信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CfFacilityFlow cfFacilityFlow) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityFlowService.save(cfFacilityFlow);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信信息-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-修改", notes = "授信信息-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CfFacilityFlow cfFacilityFlow) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityFlowService.update(cfFacilityFlow);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信信息-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-id删除", notes = "授信信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityFlowService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信信息-查询全部授信流程 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-查询全部授信流程", notes = "授信信息-查询全部授信流程")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findAll")
    public List<CfFacilityFlow> findAll () {
        return cfFacilityFlowService.findAll();
    }

    /**
     * <p> 授信信息-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-06-16 16:17:44
     */
    @ApiOperation(value = "授信信息-分页查询", notes = "授信信息-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "facilityType", value = "授信类型 0.全部 1.卖方 2.买方", required = true) @RequestParam String facilityType,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CfFacilityFlow.class, request);
        PageResult pageResult = cfFacilityFlowService.findPageBySql(filter);
        if(pageResult!=null){

            List<CfFacilityFlow> list = pageResult.getRows();
            if(list!=null&&list.size()>0){
                for(CfFacilityFlow cfFacilityFlow :list){

                    if(cfFacilityFlow.getCoreEnterpriseId()!=null) {
                        CuEnterprise cuEnterprise = cuEnterpriseService.get(cfFacilityFlow.getCoreEnterpriseId());
                        if(cuEnterprise!=null) {
                            cfFacilityFlow.setCoreEnterpriseName(cuEnterprise.getName());
                        }
                    }

                    if(cfFacilityFlow.getSellEnterpriseId()!=null) {
                        CuEnterprise cuEnterprise = cuEnterpriseService.get(cfFacilityFlow.getSellEnterpriseId());
                        if(cuEnterprise!=null) {
                            cfFacilityFlow.setSellEnterpriseName(cuEnterprise.getName());
                        }
                    }

                }
            }
        }

        return pageResult;


    }

}
