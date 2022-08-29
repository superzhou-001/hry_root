/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.controller;

import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cf.model.CfProcessContract;
import hry.business.cf.model.vo.CfProcessContractVo;
import hry.business.cf.service.CfProcessContractService;
import hry.business.ct.model.CtContractTemplate;
import hry.config.remote.ActivitiRemoteService;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.security.logger.ControllerLogger;
import hry.utils.PageUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <p> CfProcessContractController </p>
 *
 * @author: yaoz
 * @Date: 2020-07-24 17:45:18 
 */
@Api(value = "授信合同关联表", tags = "授信合同关联表", description = "授信合同关联表")
@RestController
@RequestMapping("/cf/cfprocesscontract")
public class CfProcessContractController extends BaseController {

	@Autowired
	private CfProcessContractService cfProcessContractService;
    @Autowired
    private ActivitiRemoteService activitiRemoteService;


	/**
     * <p> 授信合同关联表-添加 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-24 17:45:18 
     */
    @ApiOperation(value = "授信合同关联表-添加", notes = "授信合同关联表-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (
            @ApiParam(name = "processId", value = "processId", required = true) @RequestParam Long processId,
            @ApiParam(name = "contractIds", value = "contractIds", required = true) @RequestParam String contractIds
            ) {
        JsonResult jsonResult = new JsonResult();
        //查询已经关联的合同
        List<CtContractTemplate> listBySql = cfProcessContractService.findListBySql(processId);

        String[] ids = contractIds.split(",");
        List<CfProcessContract> list = new ArrayList<>();
        CfProcessContract cfProcessContract = null;
        for (String contractId : ids) {
            Long aLong = Long.valueOf(contractId);
            AtomicBoolean b = new AtomicBoolean(true);
            listBySql.forEach(l->{
                if(l.getId()==aLong){
                    b.set(false);
                    return;
                }
            });
            if(b.get()){
                cfProcessContract = new CfProcessContract();
                cfProcessContract.setContractId(aLong);
                cfProcessContract.setProcessId(processId);
                list.add(cfProcessContract);
            }
        }

        cfProcessContractService.saveAll(list);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信合同关联表-id删除 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-24 17:45:18 
     */
    @ApiOperation(value = "授信合同关联表-id删除", notes = "授信合同关联表-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/remove/{id}")
    public JsonResult remove (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cfProcessContractService.delete(id);
        return jsonResult.setSuccess(true);
    }

    /**
     * <p> 授信合同关联表-分页查询 </p>
     *
     * @author: yaoz
     * @Date: 2020-07-24 17:45:18 
     */
    @ApiOperation(value = "授信流程关联合同-分页查询", notes = "授信流程关联合同-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/list")
    public JsonResult list (
            @ApiParam(name = "id", value = "id", required = true) @RequestParam Long id,
            HttpServletRequest request) {
        List<CtContractTemplate> listBySql = cfProcessContractService.findListBySql(id);
        return new JsonResult().setSuccess(true).setObj(listBySql);
    }

    @ApiOperation(value = "授信流程表-分页查询", notes = "授信流程表-分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/processList")
    public PageResult processList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam int page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam int pageSize,
            HttpServletRequest request) {
        //查询所有授信流程信息
        String creditDefine = activitiRemoteService.findCreditDefine();
        List<CfProcessContractVo> list = JSONObject.parseArray(creditDefine,CfProcessContractVo.class);
        Long totalCount = Long.valueOf(list.size());
        if(list.size()>0){
            String name = request.getParameter("name");
            if(!StringUtils.isEmpty(name)){
                //过滤
                list = list.stream().filter(l->l.getName().contains(name)).collect(Collectors.toList());
            }
            list = PageUtil.startPage(list,page,pageSize);
        }
        //查询关联合同数
        list.forEach((cfProcessContractVo)->{
            QueryFilter filter = new QueryFilter(CfProcessContract.class);
            filter.addFilter("processId=",cfProcessContractVo.getId());
            Long count = cfProcessContractService.count(filter);
            cfProcessContractVo.setContractNum(String.valueOf(count));
        });
        return new PageResult(list,totalCount,page,pageSize);
    }

}
