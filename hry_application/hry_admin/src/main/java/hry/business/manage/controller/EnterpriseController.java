package hry.business.manage.controller;

import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.*;
import hry.business.cu.service.*;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description: 企业信息
 * @Date:
 */
@Api(value = "企业信息管理", tags = "企业信息管理", description = "企业信息管理")
@RestController
@RequestMapping("/manage/enterprise")
public class EnterpriseController  extends BaseController {

    @Autowired
    private CuEnterpriseService cuEnterpriseService;
    @Autowired
    private CuEnterprisePersonService cuEnterprisePersonService;
    @Autowired
    private CuCustomerService cuCustomerService;
    @Autowired
    private CuPersonService cuPersonService;
    @Autowired
    private CuBankService cuBankService;

    @ApiOperation(value = "企业-修改企业审核状态", notes = "企业-修改企业审核状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateStatus")
    public JsonResult updateStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单", required = true) @RequestParam Integer status,
            @ApiParam(name = "rejectRemark", value = "驳回原因", required = false) @RequestParam String rejectRemark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(status)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        CuEnterprise cuEnterprise = cuEnterpriseService.get(id);
        if (cuEnterprise != null) {
            CuEnterprise cuEnterpriseNew = new CuEnterprise();
            cuEnterpriseNew.setStatus(status);
            cuEnterpriseNew.setId(cuEnterprise.getId());
            if (!StringUtils.isEmpty(rejectRemark)) {
                cuEnterpriseNew.setRejectRemark(rejectRemark);
            }
            cuEnterpriseService.update(cuEnterpriseNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "企业-根据条件查询企业信息", notes = "企业-根据条件查询企业信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findList")
    public PageResult findList(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuEnterprise.class, request);
        return cuEnterpriseService.findPageBySql(filter);
    }

    @ApiOperation(value = "企业-关联人信息分页查询", notes = "企业-关联人信息分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/personPage")
    public PageResult personPage (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "type", value = "人员类型:1.法人 2.控制人 3.联系人 ", required = false) @RequestParam Integer type,
            @ApiParam(name = "enterpriseId", value = "企业id", required = true) @RequestParam String enterpriseId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuEnterprisePerson.class, request);
        return cuEnterprisePersonService.findPageBySql(filter);
    }

    @ApiOperation(value = "企业-关联人信息id删除", notes = "企业-关联人信息id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuEnterprisePersonService.delete(id);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "企业-关联人信息列表查询", notes = "企业-关联人信息列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/personList")
    public JsonResult list (
            @ApiParam(name = "type", value = "人员类型:1.法人 2.控制人 3.联系人 ", required = false) @RequestParam Integer type,
            @ApiParam(name = "enterpriseId", value = "企业id", required = true) @RequestParam String enterpriseId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuEnterprisePerson.class, request);
        return new JsonResult().setSuccess(true).setObj(cuEnterprisePersonService.personList(filter));
    }

    @ApiOperation(value = "编辑页面-人员信息id查询", notes = "编辑页面-人员信息id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/get/{id}")
    public JsonResult get (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuPerson cuPerson = cuPersonService.get(id);
        if (cuPerson != null) {
            jsonResult.setObj(cuPerson);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    @ApiOperation(value = "企业-企业绑定用户", notes = "企业-企业绑定用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/bindingCustomer")
    public JsonResult bindingCustomer(
            @ApiParam(name = "id", value = "企业id", required = true) @RequestParam Long id,
            @ApiParam(name = "customerId", value = "用户id", required = true) @RequestParam Long customerId
    ) {
        JsonResult jsonResult = new JsonResult();

        CuEnterprise cuEnterprise = cuEnterpriseService.get(id);
        if (cuEnterprise == null) {
            return jsonResult.setSuccess(false).setMsg("企业信息不存在");
        } else if (!StringUtils.isEmpty(cuEnterprise.getCustomerId())) {
            return jsonResult.setSuccess(false).setMsg("企业已经绑定用户，不能重复绑定");
        }
        //查询用户信息
        CuCustomer cuCustomer = cuCustomerService.get(customerId);
        if (cuCustomer == null) {
            return jsonResult.setSuccess(false).setMsg("用户信息不存在");
        }
        //查询当前用户是否绑定过企业
        QueryFilter filter = new QueryFilter(CuEnterprise.class);
        filter.addFilter("customerId=", customerId);
        CuEnterprise cuE = cuEnterpriseService.get(filter);
        if (cuE != null) {
            return jsonResult.setSuccess(false).setMsg("用户已经绑定过企业，不能重复绑定");
        }
        CuEnterprise cuEnterpriseNew = new CuEnterprise();
        cuEnterpriseNew.setId(cuEnterprise.getId());
        cuEnterpriseNew.setCustomerId(customerId);
        cuEnterpriseService.update(cuEnterpriseNew);
        return jsonResult.setSuccess(true).setMsg("成功");
    }

    /**
     * @param request
     * @param response
     * @Author: yaozh
     * @Description: 导出excel
     * @Date: 2020/5/25 11:13
     */
    @ApiOperation(value = "企业-导出excel", notes = "企业-导出excel")
    @ControllerLogger
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/exportExcelEnterprise")
    public void exportExcelEnterprise(
            HttpServletRequest request, HttpServletResponse response) {
        cuEnterpriseService.exportExcelEnterprise(request, response);
    }


    @ApiOperation(value = "建档页面-添加企业基本信息", notes = "建档页面-添加企业基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addEnterprise")
    public JsonResult addEnterprise(CuEnterprise cuEnterprise) {
        JsonResult jsonResult = new JsonResult();
        return cuEnterpriseService.createEnterprise(cuEnterprise);
    }

    @ApiOperation(value = "建档页面-添加企业关联人信息", notes = "建档页面-添加企业关联人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addEnterprisePerson")
    public JsonResult addEnterprisePerson(CuPerson cuPerson) {
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(cuPerson.getCuEnterpriseId())){
            return jsonResult.setSuccess(false).setMsg("企业ID不能为空");
        }
        //id不为空，说明信息是在cu_person表中搜索出来的，直接建立关联,为空的话直接保存然后建立关联关系
        if (StringUtils.isEmpty(cuPerson.getId())) {
            cuPersonService.save(cuPerson);
        }
        //如果类型是法人，先判断是否已经存在关联关系
        if(cuPerson.getPersonType()==1){
            QueryFilter filter = new QueryFilter(CuEnterprisePerson.class);
            filter.addFilter("enterpriseId=",cuPerson.getCuEnterpriseId());
            filter.addFilter("type=",1);
            CuEnterprisePerson cuEnterprisePerson = cuEnterprisePersonService.get(filter);
            if(cuEnterprisePerson!=null){
                return jsonResult.setSuccess(false).setMsg("法人信息已存在");
            }
        }
        CuEnterprisePerson cep = new CuEnterprisePerson();
        cep.setEnterpriseId(cuPerson.getCuEnterpriseId());
        cep.setPersonId(cuPerson.getId());
        cep.setType(cuPerson.getPersonType());//人员类型:1.法人 2.控制人 3.联系人
        cuEnterprisePersonService.save(cep);
        return jsonResult.setSuccess(true).setMsg("成功");
    }

    @ApiOperation(value = "建档页面-添加银行开户信息", notes = "建档页面-添加银行开户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addCuBank")
    public JsonResult addCuBank (CuBank cuBank) {
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(cuBank.getSubjectId())){
            return jsonResult.setSuccess(false).setMsg("主体ID不能为空");
        }
        if(StringUtils.isEmpty(cuBank.getType())){
            return jsonResult.setSuccess(false).setMsg("客户类型不能为空");
        }
        cuBankService.save(cuBank);
        return jsonResult.setSuccess(true).setMsg("成功");
    }

    @ApiOperation(value = "编辑页面-企业信息查询", notes = "编辑页面-企业信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getEnterprise/{id}")
    public JsonResult getEnterprise(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuEnterprise cuEnterprise = cuEnterpriseService.get(id);
        Map<String ,Object> mapResult = new HashMap<>();
        if (cuEnterprise == null) {
            return jsonResult.setSuccess(false).setMsg("企业信息不存在");
        }
        //法人
        CuPerson faren = cuEnterprisePersonService.getCuEnterprisePersonByEnterpriseIdAndType(cuEnterprise.getId(), 1);
        mapResult.put("cuEnterprise",cuEnterprise);
        mapResult.put("faren",faren);
        jsonResult.setObj(mapResult);
        return jsonResult.setSuccess(true).setMsg("成功");
    }

    @ApiOperation(value = "编辑页面-企业信息修改", notes = "编辑页面-企业信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modifyCuEnterprise")
    public JsonResult modifyCuEnterprise(CuEnterprise cuEnterprise) {
        return cuEnterpriseService.updateEnterprise(cuEnterprise);
    }

    @ApiOperation(value = "编辑页面-银行信息查询", notes = "编辑页面-银行信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/listBank")
    public JsonResult listBank (
            @ApiParam(name = "subjectId", value = "主体id type=1 person表Id，type=2 enterprise表Id", required = true) @RequestParam String subjectId,
            @ApiParam(name = "type", value = "客户类型 1.个人 2.企业", required = true) @RequestParam String type,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuBank.class, request);
        filter.addFilter("subjectId=",subjectId);
        filter.addFilter("type=",type);
        return new JsonResult().setSuccess(true).setObj(cuBankService.find(filter));
    }

    @ApiOperation(value = "编辑页面-设置主要联系人", notes = "编辑页面-设置主要联系人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/updateIsMainPerson")
    public JsonResult updateIsMainPerson(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "enterpriseId", value = "企业id", required = true) @RequestParam Long enterpriseId
    ) {
        JsonResult jsonResult = new JsonResult();
        //将原有的主要联系人设置为不是主要联系人
        cuEnterprisePersonService.updatwIsMainPerson(enterpriseId);
        CuEnterprisePerson cuEnterprisePerson = new CuEnterprisePerson();
        cuEnterprisePerson.setId(id);
        cuEnterprisePerson.setIsMainPerson(1);
        cuEnterprisePersonService.update(cuEnterprisePerson);
        return jsonResult.setSuccess(true).setMsg("成功");
    }




}
