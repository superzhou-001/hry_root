/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:45:12 
 */
package hry.user.scm.quota;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.user.scm.access.RoleAccess;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> EnterpriseQuotaController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:45:12
 */
@Api(value = "企业额度", tags = "企业额度", description = "企业额度")
@RestController
@RequestMapping("/quota/enterprisequota")
public class EnterpriseQuotaController extends BaseController {

	@Autowired
	private EnterpriseQuotaService enterpriseQuotaService;
    /**
     * <p> 企业额度-查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @ApiOperation(value = "企业额度资金方-查询", notes = "企业额度资金方-查询,isValid=1时，不能申请授信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listValid")
    public List<EnterpriseQuota> listValid (@ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam Long enterpriseId,
                            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(EnterpriseQuota.class);
        queryFilter.addFilter("enterpriseId=",enterpriseId);
        queryFilter.addFilter("isValid=",2);
        return enterpriseQuotaService.find(queryFilter);
    }
    /**
     * <p> 企业额度-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @ApiOperation(value = "企业额度资金方-查询", notes = "企业额度资金方-查询,isValid=1时，不能申请授信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public JsonResult list (@ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam Long enterpriseId,
                            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(EnterpriseQuota.class);
        queryFilter.addFilter("enterpriseId=",enterpriseId);
        List<EnterpriseQuota> enterpriseQuotaList = enterpriseQuotaService.find(queryFilter);
        Map<String, Object> map = new HashMap<String, Object>(4);
        EnterpriseQuota enterpriseQuota = enterpriseQuotaService.getEnterpriseSumQuota(enterpriseId);
        map.put("sumQuotaValue",enterpriseQuota.getSumQuotaValue());
        map.put("usedQuotaValue",enterpriseQuota.getUsedQuotaValue());
        map.put("surplusQuotaValue",enterpriseQuota.getSurplusQuotaValue());
        map.put("enterpriseQuotaList",enterpriseQuotaList);
        return jsonResult.setObj(map);
    }
    /**
     * <p> 企业额度-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @UnAuthentication
    @ApiOperation(value = "企业额度授信审批列表-查询", notes = "企业额度资金方-查询,isValid=1时，不能申请授信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByStatus")
    public PageResult listByStatus (
                                    @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
                                    @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
                                    @ApiParam(name = "fundSupportId", value = "授信审批Id", required = true) @RequestParam Long fundSupportId,
                                    @ApiParam(name = "aduitStatus", value = "审批状态：1-申请中，2-已审批", required = true) @RequestParam Integer aduitStatus,
                                    HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(EnterpriseQuota.class, request);
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("fundSupportId",fundSupportId);
        map.put("aduitStatus",aduitStatus);
        return enterpriseQuotaService.getEnterpriseQuotaList(queryFilter,map);

    }

    /**
     * <p> 企业额度-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @RoleAccess(roleType= "fundSupport")
    @ApiOperation(value = "企业额度授信审批", notes = "企业额度授信审批")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modifyAduitStatus")
    public JsonResult modifyAduitStatus (@ApiParam(name = "enterpriseId", value = "企业Id", required = true) @RequestParam Long enterpriseId,
                              @ApiParam(name = "fundSupportId", value = "资金方Id", required = true) @RequestParam Long fundSupportId,
                              @ApiParam(name = "aduitStatus", value = "审批状态：资方授信申请-申请中-1,审核通过-2,审核拒绝-3", required = true) @RequestParam Integer aduitStatus,
                              @ApiParam(name = "quotaValue", value = "额度", required = false) @RequestParam String quotaValue,
                              @ApiParam(name = "remark", value = "拒绝原因", required = false) @RequestParam String remark) {
        JsonResult jsonResult = new JsonResult();
        QueryFilter queryFilter = new QueryFilter(EnterpriseQuota.class);
        queryFilter.addFilter("enterpriseId=",enterpriseId);
        queryFilter.addFilter("fundSupportId=",fundSupportId);
        EnterpriseQuota enterpriseQuota = enterpriseQuotaService.get(queryFilter);
        enterpriseQuota.setAduitStatus(aduitStatus);
        if(aduitStatus==2){
            enterpriseQuota.setSumQuotaValue(new BigDecimal(quotaValue));
            enterpriseQuota.setSurplusQuotaValue(new BigDecimal(quotaValue));
        }
        if(aduitStatus==3){
            enterpriseQuota.setRemark(remark);
        }
        enterpriseQuotaService.update(enterpriseQuota);
        return jsonResult.setSuccess(true);
    }
	/**
     * <p> 企业额度-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @ApiOperation(value = "企业额度-id查询", notes = "企业额度-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
		EnterpriseQuota enterpriseQuota = enterpriseQuotaService.get(id);
        if (enterpriseQuota != null) {
            jsonResult.setObj(enterpriseQuota);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

	/**
     * <p> 企业额度-添加 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @ApiOperation(value = "企业额度-添加", notes = "企业额度-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (EnterpriseQuota enterpriseQuota) {
        JsonResult jsonResult = new JsonResult();
        enterpriseQuotaService.save(enterpriseQuota);
        return jsonResult.setSuccess(true);
    }



    /**
     * <p> 企业额度-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:45:12
     */
    @ApiOperation(value = "企业额度-id删除", notes = "企业额度-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        enterpriseQuotaService.delete(id);
        return jsonResult.setSuccess(true);
    }




}
