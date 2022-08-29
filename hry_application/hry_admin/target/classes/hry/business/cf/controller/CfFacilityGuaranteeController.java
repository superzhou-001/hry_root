/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-02 14:52:53 
 */
package hry.business.cf.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.model.CfFacilityGuarantee;
import hry.business.cf.service.CfFacilityGuaranteeService;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuEnterprisePersonService;
import hry.business.cu.service.CuEnterpriseService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> CfFacilityGuaranteeController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-02 14:52:53 
 */
@Api(value = "抵质押物担保人表", tags = "抵质押物担保人表", description = "抵质押物担保人表")
@RestController
@RequestMapping("/cf/cffacilityguarantee")
public class CfFacilityGuaranteeController extends BaseController {

	@Autowired
	private CfFacilityGuaranteeService cfFacilityGuaranteeService;

    @Autowired
    private CuEnterpriseService cuEnterpriseService;
    @Autowired
    private CuEnterprisePersonService cuEnterprisePersonService;

	/**
     * <p> 抵质押物担保人表-id查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-02 14:52:53 
     */
    @ApiOperation(value = "抵质押物担保人表-id查询", notes = "抵质押物担保人表-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        return cfFacilityGuaranteeService.getGuarantee(id);
    }

	/**
     * <p> 抵质押物担保人表-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-02 14:52:53 
     */
    @ApiOperation(value = "抵质押物担保人表-添加", notes = "抵质押物担保人表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (CfFacilityGuarantee cfFacilityGuarantee) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityGuaranteeService.save(cfFacilityGuarantee);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物担保人表-修改 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-02 14:52:53 
     */
    @ApiOperation(value = "抵质押物担保人表-修改", notes = "抵质押物担保人表-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (CfFacilityGuarantee cfFacilityGuarantee) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityGuaranteeService.update(cfFacilityGuarantee);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物担保人表-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-02 14:52:53 
     */
    @ApiOperation(value = "抵质押物担保人表-id删除", notes = "抵质押物担保人表-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cfFacilityGuaranteeService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 抵质押物担保人表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-02 14:52:53 
     */
    @ApiOperation(value = "抵质押物担保人表-分页查询", notes = "抵质押物担保人表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CfFacilityGuarantee.class, request);
        return cfFacilityGuaranteeService.findPageBySql(filter);
    }



    @ApiOperation(value = "抵质押物担保人表-查询担保企业信息", notes = "抵质押物担保人表-查询担保企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getGuaranteeEnterprise/{id}")
    public JsonResult getGuaranteeEnterprise (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuEnterprise cuEnterprise = cuEnterpriseService.get(id);
        Map<String ,Object> mapResult = new HashMap<>();
        if (cuEnterprise == null) {
            return jsonResult.setSuccess(false).setMsg("企业信息不存在");
        }
        //法人
        CuPerson faren = cuEnterprisePersonService.getCuEnterprisePersonByEnterpriseIdAndType(cuEnterprise.getId(), 1);
        //联系人
        CuPerson lianxiren = cuEnterprisePersonService.getCuEnterprisePersonByEnterpriseIdAndType(cuEnterprise.getId(), 3);
        mapResult.put("cuEnterprise",cuEnterprise);
        mapResult.put("faren",faren);
        mapResult.put("lianxiren",lianxiren);
        jsonResult.setObj(mapResult);
        return jsonResult.setSuccess(true).setMsg("成功");
    }

}
