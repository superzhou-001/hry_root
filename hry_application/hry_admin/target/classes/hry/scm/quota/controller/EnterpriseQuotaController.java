/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:39:59 
 */
package hry.scm.quota.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> EnterpriseQuotaController </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:39:59 
 */
@Api(value = "企业额度", tags = "企业额度", description = "企业额度")
@RestController
@RequestMapping("/quota/enterprisequota")
public class EnterpriseQuotaController extends BaseController {

	@Autowired
	private EnterpriseQuotaService enterpriseQuotaService;


    /**
     * <p> 企业额度-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:39:59
     */
    @ApiOperation(value = "查看资方授信企业额度-分页查询", notes = "查看资方授信企业额度-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listByFundSupportId")
    public PageResult listByFundSupportId (
            @ApiParam(name = "fundSupportId", value = "资方授信Id", required = true) @RequestParam Long fundSupportId,
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(EnterpriseQuota.class, request);
        Map<String,Object> map = new HashMap<String,Object>(2);
        map.put("fundSupportId",fundSupportId);
        map.put("aduitStatus",2);
        return enterpriseQuotaService.getEnterpriseQuotaList(filter,map);
    }


	/**
     * <p> 企业额度-id查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:39:59 
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
     * @Date: 2020-07-08 14:39:59 
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
     * <p> 企业额度-修改 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:39:59 
     */
    @ApiOperation(value = "企业额度-修改", notes = "企业额度-修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modify")
    public JsonResult modify (EnterpriseQuota enterpriseQuota) {
        JsonResult jsonResult = new JsonResult();
        enterpriseQuotaService.update(enterpriseQuota);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 企业额度-id删除 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:39:59 
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

    /**
     * <p> 企业额度-分页查询 </p>
     *
     * @author: zengzhiyan
     * @Date: 2020-07-08 14:39:59 
     */
    @ApiOperation(value = "企业额度-分页查询", notes = "企业额度-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public PageResult list (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(EnterpriseQuota.class, request);
        return enterpriseQuotaService.findPageResult(filter);
    }



}
