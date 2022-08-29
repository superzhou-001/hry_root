package hry.business.manage.controller;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.*;
import hry.business.cu.service.*;
import hry.core.mvc.controller.BaseController;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppUserService;
import hry.redis.RedisService;
import hry.security.jwt.JWTContext;
import hry.security.jwt.annotation.UnAuthentication;
import hry.security.logger.ControllerLogger;
import hry.util.JavaBeanUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yaozh
 * @Description:意向客户
 * @Date:
 */
@Api(value = "意向客户管理", tags = "意向客户管理", description = "意向客户管理")
@RestController
@RequestMapping("/manage/intention")
public class IntentionController  extends BaseController {

    @Autowired
    private CuIntentionService cuIntentionService;
    @Autowired
    private CuIntentionUserService cuIntentionUserService;
    @Autowired
    private CuIntentionInfoService cuIntentionInfoService;
    @Autowired
    private CuIntentionPersonService cuIntentionPersonService;
    @Autowired
    private CuUpdateLogService cuUpdateLogService;
    @Autowired
    private CuIntentionFollowService cuIntentionFollowService;
    @Autowired
    private CuIntentionFollowCommentService cuIntentionFollowCommentService;
    @Autowired
    private CuPersonService cuPersonService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private NewAppUserService newAppUserService;

    private static final String CUSTOMER_LABEL_PRE = "intention:customerLabel:";
    private static final String CUSTOMER_LABEL_ALL_PRE = "intention:customerLabel:all";

    @ApiOperation(value = "意向客户-根据条件查询", notes = "意向客户-根据条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/intentionList")
    public PageResult intentionList(
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "status", value = "客户状态 1.未跟进 2.跟进中 3.排除 4.拉黑 5.转正式", required = false) @RequestParam String status,
            @ApiParam(name = "intentionCode", value = "客户编码", required = false) @RequestParam String intentionCode,
            @ApiParam(name = "type", value = "客户类型 1.个人 2.企业", required = false) @RequestParam String type,
            @ApiParam(name = "enterpriseContacts", value = "企业联系人", required = false) @RequestParam String enterpriseContacts,
            @ApiParam(name = "movePhone", value = "移动电话", required = false) @RequestParam String movePhone,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuIntention.class, request);
        return cuIntentionService.findPageBySql(filter);
    }

    @ApiOperation(value = "意向客户-修改状态", notes = "意向客户-修改状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/updateStatus")
    public JsonResult updateStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "客户状态 1.未跟进 2.跟进中 3.排除 4.拉黑 5.转正式", required = true) @RequestParam Integer status,
            @ApiParam(name = "excludeType", value = "排除原因", required = true) @RequestParam Integer excludeType,
            @ApiParam(name = "statusRemark", value = "状态变更说明", required = false) @RequestParam String statusRemark
    ) {
        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isEmpty(status)) {
            jsonResult.setMsg("不能为空");
            return jsonResult;
        }
        if (status == 1) {
            cuIntentionService.updateStatusFormal(id);
        }
        CuIntention cuEnterprise = cuIntentionService.get(id);
        if (cuEnterprise != null) {
            CuIntention cuIntentionNew = new CuIntention();
            cuIntentionNew.setStatus(status);
            cuIntentionNew.setId(cuEnterprise.getId());
            cuIntentionNew.setExcludeType(excludeType);
            if (!StringUtils.isEmpty(statusRemark)) {
                cuIntentionNew.setStatusRemark(statusRemark);
            }
            cuIntentionService.update(cuIntentionNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }
        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "意向客户-分配", notes = "意向客户-分配")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/add")
    public JsonResult add (@ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam String intentionId,
                           @ApiParam(name = "userIds", value = "选中用户id，逗号隔开", required = true) @RequestParam String userIds) {
        JsonResult jsonResult = new JsonResult();
        return cuIntentionUserService.add(Long.valueOf(intentionId),userIds);
    }

    @ApiOperation(value = "建档页面-添加客户信息", notes = "建档页面-添加客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addIntention")
    public JsonResult addIntention(CuIntention cuIntention) {
        return cuIntentionService.addIntention(cuIntention);
    }

    @ApiOperation(value = "跟进页面-根据id查询意向客户信息", notes = "跟进页面-根据id查询意向客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getIntention/{id}")
    public JsonResult getIntention(@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuIntention cuIntention = cuIntentionService.get(id);
        if (cuIntention != null) {
            QueryFilter filter = new QueryFilter(CuIntentionInfo.class);
            filter.setOrderby("id desc");
            CuIntentionInfo cuIntentionInfo = cuIntentionInfoService.get(filter);
            if(cuIntentionInfo!=null){
                cuIntention.setCuIntentionInfo(cuIntentionInfo);
            }
            //查询主要联系人
            CuPerson cuIntentionPerson = cuIntentionPersonService.getIntentionPersonByIntentionId(cuIntention.getId());
            if(cuIntentionPerson!=null){
                cuIntention.setCuIntentionPerson(cuIntentionPerson);
            }
            jsonResult.setObj(cuIntention);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    @ApiOperation(value = "跟进页面-联系人信息-id删除", notes = "跟进页面-联系人信息-id删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/removeIntentionPerson/{id}")
    public JsonResult removeIntentionPerson (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        cuIntentionPersonService.delete(id);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-联系人信息-添加", notes = "跟进页面-联系人信息-添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addIntentionPerson")
    public JsonResult addIntentionPerson (CuIntentionPerson cuIntentionPerson) {
        JsonResult jsonResult = new JsonResult();
        if(!StringUtils.isEmpty(cuIntentionPerson.getPersonId())){
            cuIntentionPersonService.save(cuIntentionPerson);
            return jsonResult.setSuccess(true).setMsg("成功");
        }

        CuPerson cuPerson = JSON.parseObject(cuIntentionPerson.getCuPerson().toString(),CuPerson.class);;
        if(!StringUtils.isEmpty(cuPerson)){
            //添加人员信息
            cuPersonService.save(cuPerson);
        }
        cuIntentionPerson.setPersonId(cuPerson.getId());
        cuIntentionPersonService.save(cuIntentionPerson);

        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-联系人信息-列表查询", notes = "跟进页面-联系人信息-列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/intentionPersonList")
    public JsonResult intentionPersonList (
            @ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam Long intentionId,
            HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        return jsonResult.setSuccess(true).setObj(cuIntentionPersonService.findIntentionPersonByIntentionId(intentionId));
    }

    @ApiOperation(value = "跟进页面-修改意向客户信息", notes = "跟进页面-修改意向客户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modifyIntention")
    public JsonResult modifyIntention(CuIntention cuIntention) {
        JsonResult jsonResult = new JsonResult();
        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if (user == null) {
            return jsonResult.setSuccess(false).setMsg("登陆用户异常");
        }
        /**添加修改日志*/
        //查询数据库数据
        CuIntention cuI = cuIntentionService.get(cuIntention.getId());
        //忽略对比字段
        String[] ignoreArr = {"created","modified","userName","userId","cuIntentionInfo","cuIntentionPerson"};
        //对比修改内容
        Map<String, List<Object>> map = JavaBeanUtil.compareFields(cuIntention, cuI, ignoreArr);
        //获取ApiModelProperty注解内容
        Map<String, String> mapColumn = JavaBeanUtil.getColumnName(CuIntention.class);
        //遍历对比结果
        List<CuUpdateLog> listArray = new ArrayList<>();
        for (Map.Entry<String, List<Object>> entry : map.entrySet()) {
            //修改的字段
            String field = entry.getKey();
            List<Object> list = entry.getValue();
            if (!StringUtils.isEmpty(list.get(0))){
                //插入修改日志
                CuUpdateLog cuUpdateLog = new CuUpdateLog();
                cuUpdateLog.setTableField(field);
                cuUpdateLog.setFieldReamrk(mapColumn.get(field));
                cuUpdateLog.setNewValue((String) list.get(0));
                cuUpdateLog.setOldValue((String) list.get(1));
                cuUpdateLog.setTableTabel("cu_intention");
                cuUpdateLog.setTableId(cuIntention.getId());
                cuUpdateLog.setUserId(user.getId());
                cuUpdateLog.setUserName(user.getUserName());
                cuUpdateLog.setRemark(cuIntention.getName());
                listArray.add(cuUpdateLog);
            }
        }
        if(listArray.size()>0){
            cuUpdateLogService.saveAll(listArray);
        }
        cuIntentionService.update(cuIntention);

        //保存意向信息
        if(null != cuIntention.getCuIntentionInfo() && !"".equals(cuIntention.getCuIntentionInfo())){
            CuIntentionInfo cuIntentionInfo = JSON.parseObject((String) cuIntention.getCuIntentionInfo(), CuIntentionInfo.class);
            if (cuIntentionInfo.getId()!=null&&!cuIntentionInfo.getId().equals("")) {
                cuIntentionInfoService.update(cuIntentionInfo);
            }
        }

        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-查询意向信息列表", notes = "跟进页面-查询意向信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/intentionInfoList")
    public PageResult intentionInfoList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam String intentionId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuIntentionInfo.class, request);
        filter.addFilter("intentionId=",intentionId);
        return cuIntentionInfoService.findPageResult(filter);
    }

    @ApiOperation(value = "跟进页面-查询跟进信息列表带评论", notes = "跟进页面-查询跟进信息列表带评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/followAndCommentList")
    public JsonResult followAndCommentList (
            @ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam String intentionId,
            @ApiParam(name = "intentionInfoId", value = "意向信息id", required = false) @RequestParam String intentionInfoId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuIntentionFollow.class, request);
        List<CuIntentionFollow> list = cuIntentionFollowService.selectCuIntentionFollowAndCommentByFollowId(filter);
        return new JsonResult().setSuccess(true).setObj(list);
    }

    @ApiOperation(value = "跟进页面-添加评论", notes = "跟进页面-添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addFollowComment")
    public JsonResult addFollowComment (CuIntentionFollowComment cuIntentionFollowComment) {
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(cuIntentionFollowComment.getIntentionId())){
            jsonResult.setMsg("intentionId不能为空");
            return  jsonResult;
        }
        if(StringUtils.isEmpty(cuIntentionFollowComment.getIntentionfollowId())){
            jsonResult.setMsg("intentionfollowId不能为空");
            return  jsonResult;
        }
        cuIntentionFollowCommentService.save(cuIntentionFollowComment);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-查询跟进信息-评论列表(关联父节点查询)", notes = "跟进页面-查询跟进信息列表带评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/findCommentByIntentionId")
    public JsonResult findCommentByIntentionId (
            @ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam Long intentionId,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuIntentionFollow.class, request);
        List<CuIntentionFollow> list = cuIntentionFollowService.findFollowAndCommentByIntentionId(intentionId);
        return new JsonResult().setSuccess(true).setObj(list);
    }

    @ApiOperation(value = "跟进页面-添加意向信息", notes = "跟进页面-添加意向信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addIntentionInfo")
    public JsonResult addIntentionInfo(CuIntentionInfo cuIntentionInfo) {
        JsonResult jsonResult = new JsonResult();

        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if(user==null){
            return jsonResult.setSuccess(false).setMsg("请从新登录");
        }
        if(StringUtils.isEmpty(cuIntentionInfo.getIntentionId())){
            return jsonResult.setSuccess(false).setMsg("意向客户不能为空");
        }
        cuIntentionInfo.setUserId(user.getId());
        cuIntentionInfo.setUserName(user.getUserName());
        cuIntentionInfoService.save(cuIntentionInfo);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-修改意向信息", notes = "跟进页面-修改意向信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modifyIntentionInfo")
    public JsonResult modifyIntentionInfo (CuIntentionInfo cuIntentionInfo) {
        JsonResult jsonResult = new JsonResult();
        cuIntentionInfoService.update(cuIntentionInfo);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-意向跟进信息-id查询", notes = "跟进页面-意向跟进信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getFollow/{id}")
    public JsonResult getFollow (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuIntentionFollow cuIntentionFollow = cuIntentionFollowService.get(id);
        if (cuIntentionFollow != null) {
            jsonResult.setObj(cuIntentionFollow);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }

    @ApiOperation(value = "跟进页面-修改意向信息状态", notes = "跟进页面-修改意向信息状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value="/updateIntentionInfoStatus")
    public JsonResult updateIntentionInfoStatus(
            @ApiParam(name = "id", value = "用户id", required = true) @RequestParam Long id,
            @ApiParam(name = "status", value = "意向状态 1.跟进 2.排除 3.转已签", required = true) @RequestParam Integer status,
            @ApiParam(name = "statusRemark", value = "状态变更说明", required = false) @RequestParam String statusRemark
    ){
        JsonResult jsonResult = new JsonResult();
        if(StringUtils.isEmpty(status)){
            jsonResult.setMsg("不能为空");
            return  jsonResult;
        }

        CuIntentionInfo cuIntentionInfo = cuIntentionInfoService.get(id);
        if (cuIntentionInfo != null) {
            CuIntentionInfo cuIntentionInfoNew = new CuIntentionInfo();
            cuIntentionInfoNew.setStatus(status);
            cuIntentionInfoNew.setId(cuIntentionInfo.getId());
            if(!StringUtils.isEmpty(statusRemark)){
                cuIntentionInfoNew.setStatusRemark(statusRemark);
            }
            cuIntentionInfoService.update(cuIntentionInfoNew);
            return jsonResult.setSuccess(true).setMsg("成功");
        }

        return jsonResult.setSuccess(false).setMsg("失败");
    }

    @ApiOperation(value = "跟进页面-查看意向信息-id查询", notes = "跟进页面-查看意向信息-id查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @GetMapping(value = "/getIntentionInfo/{id}")
    public JsonResult getIntentionInfo (@ApiParam(name = "id", value = "id", required = true) @PathVariable Long id) {
        JsonResult jsonResult = new JsonResult();
        CuIntentionInfo cuIntentionInfo = cuIntentionInfoService.get(id);
        if (cuIntentionInfo != null) {
            jsonResult.setObj(cuIntentionInfo);
            return jsonResult.setSuccess(true);
        }
        return jsonResult.setSuccess(false);
    }


    @ApiOperation(value = "跟进页面-跟进信息查询", notes = "跟进页面-跟进信息查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/intentionFollowList")
    public PageResult intentionFollowList (
            @ApiParam(name = "page", value = "当前页码", required = true) @RequestParam String page,
            @ApiParam(name = "pageSize", value = "每页条数", required = true) @RequestParam String pageSize,
            @ApiParam(name = "intentionId", value = "意向客户id", required = true) @RequestParam Long intentionId,
            @ApiParam(name = "planFollowStatus", value = "计划跟进状态 1.待跟进 2.已完成", required = true) @RequestParam Integer planFollowStatus,
            HttpServletRequest request) {
        QueryFilter filter = new QueryFilter(CuIntentionFollow.class, request);
        filter.addFilter("intentionId=",intentionId);
        filter.addFilter("planFollowStatus=",planFollowStatus);
        return cuIntentionFollowService.findPageResult(filter);
    }

    @ApiOperation(value = "跟进页面-跟进信息修改", notes = "跟进页面-跟进信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/modifyIntentionFollow")
    public JsonResult modifyIntentionFollow (CuIntentionFollow cuIntentionFollow) {
        JsonResult jsonResult = new JsonResult();
        cuIntentionFollowService.update(cuIntentionFollow);
        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "跟进页面-跟进信息添加", notes = "跟进页面-跟进信息添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @ControllerLogger
    @PostMapping(value = "/addIntentionFollow")
    public JsonResult addIntentionFollow (CuIntentionFollow cuIntentionFollow) {
        JsonResult jsonResult = new JsonResult();

        NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
        if(user==null){
            return jsonResult.setSuccess(false).setMsg("请从新登录");
        }
        cuIntentionFollow.setFollowUserId(user.getId());
        cuIntentionFollow.setFollowUserName(user.getUserName());


        if(!StringUtils.isEmpty(cuIntentionFollow.getPlanFollowUserId())){
            NewAppUser userPlan = newAppUserService.get(cuIntentionFollow.getPlanFollowUserId());
            if(userPlan!=null){
                cuIntentionFollow.setPlanFollowUserId(userPlan.getId());
                cuIntentionFollow.setPlanFollowUserName(userPlan.getUserName());
            }
        }
        /*if(StringUtil.isNull(cuIntentionFollow.getFollowContent())){
            cuIntentionFollow.setFollowContent(JsoupUtil.clean(cuIntentionFollow.getFollowContent()));
        }

        if (StringUtil.isNull(cuIntentionFollow.getPlanFollowContent())) {
            cuIntentionFollow.setPlanFollowContent(JsoupUtil.clean(cuIntentionFollow.getPlanFollowContent()));
        }*/

        cuIntentionFollowService.save(cuIntentionFollow);
        return jsonResult.setSuccess(true);
    }


    @ApiOperation(value = "客户画像-查询列表", notes = "客户画像-查询列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @GetMapping(value = "/selectCustomerLabel")
    public Set<String> selectCustomerLabel(
            @ApiParam(name = "id", value = "用户id", required = false) @RequestParam Long id
    ) {
        Set<String> labelAll;
        if (StringUtils.isEmpty(id)) {
            labelAll = redisService.smembers(CUSTOMER_LABEL_ALL_PRE);
        } else {
            labelAll = redisService.smembers(CUSTOMER_LABEL_PRE + id);
        }
        return labelAll;
    }


    @ApiOperation(value = "客户画像-新增客户画像标签", notes = "客户画像-新增客户画像标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/addCustomerLabel")
    public JsonResult addCustomerLabel(
            @ApiParam(name = "id", value = "用户id", required = false) @RequestParam Long id,
            @ApiParam(name = "labels", value = "标签数组，使用逗号分隔", required = true) @RequestParam String labels
    ) {
        JsonResult jsonResult = new JsonResult();
        String[] labelArray = labels.split(",");
        for (String label : labelArray) {
            if (StringUtils.isEmpty(id)) {
                redisService.sadd(CUSTOMER_LABEL_ALL_PRE, label);
            } else {
                redisService.sadd(CUSTOMER_LABEL_PRE + id, label);
            }
        }

        return jsonResult.setSuccess(true);
    }

    @ApiOperation(value = "客户画像-删除客户画像标签", notes = "客户画像-删除客户画像标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token令牌", required = true, paramType = "header"),
    })
    @PostMapping(value = "/deleteCustomerLabel")
    public JsonResult deleteCustomerLabel(
            @ApiParam(name = "id", value = "用户id", required = false) @RequestParam Long id,
            @ApiParam(name = "label", value = "标签", required = true) @RequestParam String label
    ) {
        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isEmpty(id)) {
            redisService.srem(CUSTOMER_LABEL_ALL_PRE, label);
        } else {
            redisService.srem(CUSTOMER_LABEL_PRE + id, label);
        }
        return jsonResult.setSuccess(true);
    }


}
