/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 16:17:44
 */
package hry.business.cf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import hry.bean.PageResult;
import hry.business.cf.dao.CfFacilityFlowDao;
import hry.business.cf.model.CfFacilityFlow;
import hry.business.cf.service.CfFacilityFlowService;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuCustomerService;
import hry.business.cu.service.CuEnterprisePersonService;
import hry.business.cu.service.CuEnterpriseService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.flow.service.CommonFlowService;
import hry.platform.newuser.model.NewAppUser;
import hry.platform.newuser.service.NewAppUserService;
import hry.util.date.DateUtil;
import hry.util.flowModel.FlowParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> CfFacilityFlowService </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 16:17:44
 */
@Slf4j
@Service("cfFacilityFlowService")
public class CfFacilityFlowServiceImpl extends BaseServiceImpl<CfFacilityFlow, Long> implements CfFacilityFlowService, CommonFlowService {

    static class BuyCredit {
        private final static String SHOUXINEDUSHENQING = "buyCredit_shouxinedushenqing";
        private final static String FENGXIANSHENHE = "buyCredit_fengxianshenhe";
        private final static String BUMENJINGLISHENHE = "buyCredit_bumenjinglishenhe";
        private final static String HETONGQIANSHU = "buyCredit_hetongqianshu";
        private final static String FUZONGJINGLISHENPI = "buyCredit_fuzongjinglishenpi";
        private final static String ZONGJINGLISHENPI = "buyCredit_zongjinglishenpi";
    }

    static class SellCredit {
        private final static String SHOUXINEDUSHENQING = "sellCredit_shouxinedushenqing";
        private final static String FENGXIANSHENHE = "sellCredit_fengxianshenhe";
        private final static String BUMENJINGLISHENHE = "sellCredit_bumenjinglishenhe";
        private final static String HETONGQIANSHU = "sellCredit_hetongqianshu";
        private final static String FUZONGJINGLISHENPI = "sellCredit_fuzongjinglishenpi";
        private final static String ZONGJINGLISHENPI = "sellCredit_zongjinglishenpi";
    }


    @Resource(name = "cfFacilityFlowDao")
    @Override
    public void setDao(BaseDao<CfFacilityFlow, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private CuEnterpriseService cuEnterpriseService;


    @Autowired
    private NewAppUserService newAppUserService;

    @Autowired
    private CuCustomerService cuCustomerService;

    @Autowired
    private CuEnterprisePersonService cuEnterprisePersonService;

    @Override
    public PageResult findPageBySql(QueryFilter filter) {
        // ----------------------分页查询头部外壳------------------------------
        Page<CfFacilityFlow> page = PageFactory.getPage(filter);

        String facilityCode = filter.getRequest().getParameter("facilityCode");
        String sellEnterpriseName = filter.getRequest().getParameter("sellEnterpriseName");
        String coreEnterpriseName = filter.getRequest().getParameter("coreEnterpriseName");
        String facilityType = filter.getRequest().getParameter("facilityType");

        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(facilityType)) {
            map.put("facilityType", facilityType);
        }
        if (!StringUtils.isEmpty(facilityCode)) {
            map.put("facilityCode", "%" + facilityCode + "%");
        }
        if (!StringUtils.isEmpty(sellEnterpriseName)) {
            map.put("sellEnterpriseName", "%" + sellEnterpriseName + "%");
        }
        if (!StringUtils.isEmpty(coreEnterpriseName)) {
            map.put("coreEnterpriseName", "%" + coreEnterpriseName + "%");
        }
        ((CfFacilityFlowDao) dao).findPageBySql(map);
        return new PageResult(page, filter.getPage(), filter.getPageSize());
    }

    //---------------------------------------------买方授信流程start------------------------------------------------------
    @Override
    public FlowParams syedsq(FlowParams flowParams) {

        try {
            //获取表单
            JSONObject paramsList = (JSONObject) flowParams.getAttribute(BuyCredit.SHOUXINEDUSHENQING);
            //企业id
            Long enterpriseId = paramsList.getLong("enterpriseId");
            //项目经理ids
            Long projectManagerId = paramsList.getLong("projectManagerId");
            //授信用途
            String facilityPurpose = paramsList.getString("facilityPurpose");
            //业务方向
            Integer businessDirection = paramsList.getInteger("businessDirection");
            //授信金额
            BigDecimal facilityAmount = paramsList.getBigDecimal("facilityAmount");
            //周期类型
            Integer cycleType = paramsList.getInteger("cycleType");
            //开始日期
            Date guaranteeDateStart = paramsList.getDate("guaranteeDateStart");
            //结束日期
            Date guaranteeDateEnd = paramsList.getDate("guaranteeDateEnd");
            //期限
            Integer term = paramsList.getInteger("term");

            //保存流程信息
            CfFacilityFlow cfFacilityFlow = get(flowParams.getProjectId());
            cfFacilityFlow.setFacilityPurpose(facilityPurpose);
            cfFacilityFlow.setBusinessDirection(businessDirection);
            cfFacilityFlow.setFacilityAmount(facilityAmount);
            cfFacilityFlow.setSurplusAmount(facilityAmount);
            cfFacilityFlow.setCycleType(cycleType);
            cfFacilityFlow.setGuaranteeDateStart(guaranteeDateStart);
            cfFacilityFlow.setGuaranteeDateEnd(guaranteeDateEnd);
            cfFacilityFlow.setTerm(term);

            update(cfFacilityFlow);
        }catch (Exception e){
            log.error("error: {}", e.getMessage(), e);
            flowParams.setMsg(e.getClass().toString());
            return flowParams;
        }


        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams fxsh(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams bmjlsh(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams htqs(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams fzjlsp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams zjlsp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    //---------------------------------------------买方授信流程end------------------------------------------------------


    //---------------------------------------------卖方授信流程start------------------------------------------------------
    @Override
    public FlowParams sellSyedsq(FlowParams flowParams) {

        try {
            //获取表单
            JSONObject paramsList = (JSONObject) flowParams.getAttribute("buyCredit_showxinedushenqing");
            //企业id
            Long enterpriseId = paramsList.getLong("enterpriseId");
            //项目经理ids
            Long projectManagerId = paramsList.getLong("projectManagerId");
            //授信用途
            String facilityPurpose = paramsList.getString("facilityPurpose");
            //业务方向
            Integer businessDirection = paramsList.getInteger("businessDirection");
            //授信金额
            BigDecimal facilityAmount = paramsList.getBigDecimal("facilityAmount");
            //周期类型
            Integer cycleType = paramsList.getInteger("cycleType");
            //开始日期
            Date guaranteeDateStart = paramsList.getDate("guaranteeDateStart");
            //结束日期
            Date guaranteeDateEnd = paramsList.getDate("guaranteeDateEnd");
            //期限
            Integer term = paramsList.getInteger("term");

            //保存流程信息
            CfFacilityFlow cfFacilityFlow = get(flowParams.getProjectId());
            cfFacilityFlow.setFacilityPurpose(facilityPurpose);
            cfFacilityFlow.setBusinessDirection(businessDirection);
            cfFacilityFlow.setFacilityAmount(facilityAmount);
            cfFacilityFlow.setSurplusAmount(facilityAmount);
            cfFacilityFlow.setCycleType(cycleType);
            cfFacilityFlow.setGuaranteeDateStart(guaranteeDateStart);
            cfFacilityFlow.setGuaranteeDateEnd(guaranteeDateEnd);
            cfFacilityFlow.setTerm(term);

            update(cfFacilityFlow);
        }catch (Exception e){
            log.error("error: {}", e.getMessage(), e);
            flowParams.setMsg(e.getClass().toString());
            return flowParams;
        }


        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams sellFxsh(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams sellBmjlsh(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams sellHtqs(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams sellFzjlsp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams sellZjlsp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }
    //---------------------------------------------买方授信流程end------------------------------------------------------


    //---------------------------------------------买方、方法授信流程start方法------------------------------------------------------
    @Override
    public FlowParams start(FlowParams flowParams) {

        //客户类型
        String type = flowParams.getAttributeString("type");//sell /buy
        //企业id
        Long enterpriseId = flowParams.getAttributeLong("enterpriseId");
        //项目经理id
        Long projectManagerId = flowParams.getAttributeLong("projectManagerId");

        //企业对象
        CuEnterprise cuEnterprise = cuEnterpriseService.get(Long.valueOf(enterpriseId));
        if (cuEnterprise == null) {
            flowParams.setSuccess(false).setMsg("enterpriseId错误");
            return flowParams;
        }

        //项目经理
        NewAppUser projectManager = newAppUserService.get(projectManagerId);

        //创建流程项目
        CfFacilityFlow cfFacilityFlow = new CfFacilityFlow();
        //项目名称
        String projectName = cuEnterprise.getName() + DateUtil.dateToString(new Date(), "yyyyMMdd");
        //授信编号
        String facilityCode = DateUtil.dateToString(new Date(), "yyyyMMdd");
        cfFacilityFlow.setProjectName(projectName);
        cfFacilityFlow.setFacilityCode(facilityCode);
        cfFacilityFlow.setProjectManagerId(projectManagerId);
        cfFacilityFlow.setProjectManagerName(projectManager.getUserName());

        if ("1".equals(type)) {//卖方授信流程  启动用户为卖方用户s
            cfFacilityFlow.setFacilityType(1);//授信类型
            cfFacilityFlow.setSellEnterpriseId(enterpriseId);
            cfFacilityFlow.setSellEnterpriseName(cuEnterprise.getName());
        } else if ("2".equals(type)) {//买方授信流程 启动用户为核心用户
            cfFacilityFlow.setFacilityType(2);//授信类型
            cfFacilityFlow.setCoreEnterpriseId(enterpriseId);
            cfFacilityFlow.setCoreEnterpriseName(cuEnterprise.getName());
            cfFacilityFlow.setCoreEnterpriseCreditCode(cuEnterprise.getCreditCode());
        }

        save(cfFacilityFlow);

        //设置流水号--更新流程
        String number = String.format("%4d", cfFacilityFlow.getId()).replace(" ", "0");

        //流程名称
        cfFacilityFlow.setProjectName(cfFacilityFlow.getProjectName() + number);
        cfFacilityFlow.setFacilityCode(cfFacilityFlow.getFacilityCode() + number);
        update(cfFacilityFlow);


        //流程参数
        flowParams.setProjectId(cfFacilityFlow.getId());
        flowParams.setProjectName(cfFacilityFlow.getProjectName());
        flowParams.setSuccess(true);

        return flowParams;
    }

    //---------------------------------------------买方、方法授信流程view方法------------------------------------------------------
    @Override
    public FlowParams view(FlowParams flowParams) {


        Long projectId = flowParams.getProjectId();
        CfFacilityFlow cfFacilityFlow = get(projectId);

        //回显组件
        JSONObject jsonObject = new JSONObject();
        //买方授信尽职调查
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(BuyCredit.SHOUXINEDUSHENQING)) {
            jsonObject.put(BuyCredit.SHOUXINEDUSHENQING, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //买方_部门经理审核
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(BuyCredit.BUMENJINGLISHENHE)) {
            jsonObject.put(BuyCredit.BUMENJINGLISHENHE, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //买方_合同签属
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(BuyCredit.HETONGQIANSHU)) {
            jsonObject.put(BuyCredit.HETONGQIANSHU, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //买方_副总经理审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(BuyCredit.FUZONGJINGLISHENPI)) {
            jsonObject.put(BuyCredit.FUZONGJINGLISHENPI, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //买方_总经理审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(BuyCredit.ZONGJINGLISHENPI)) {
            jsonObject.put(BuyCredit.ZONGJINGLISHENPI, buyCredit_showxinedushenqing(cfFacilityFlow));
        }



        //卖方授信尽职调查
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(SellCredit.SHOUXINEDUSHENQING)) {
            jsonObject.put(SellCredit.SHOUXINEDUSHENQING, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //卖方_部门经理审核
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(SellCredit.BUMENJINGLISHENHE)) {
            jsonObject.put(SellCredit.BUMENJINGLISHENHE, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //卖方_合同签属
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(SellCredit.HETONGQIANSHU)) {
            jsonObject.put(SellCredit.HETONGQIANSHU, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //卖方_副总经理审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(SellCredit.FUZONGJINGLISHENPI)) {
            jsonObject.put(SellCredit.FUZONGJINGLISHENPI, buyCredit_showxinedushenqing(cfFacilityFlow));
        }
        //卖方_总经理审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(SellCredit.ZONGJINGLISHENPI)) {
            jsonObject.put(SellCredit.ZONGJINGLISHENPI, buyCredit_showxinedushenqing(cfFacilityFlow));
        }




        //回显表单
        flowParams.setViewAttribute(jsonObject);
        flowParams.setSuccess(true);
        return flowParams;
    }

    //买方授信尽职调查
    private JSONObject buyCredit_showxinedushenqing(CfFacilityFlow cfFacilityFlow) {

        //showxinedushenqing组件渲染数据
        JSONObject showxinedushenqing = new JSONObject();
        //项目名称
        showxinedushenqing.put("projectName", cfFacilityFlow.getProjectName());
        //授信申请编号
        showxinedushenqing.put("facilityCode", cfFacilityFlow.getFacilityCode());
        //项目经理ID
        showxinedushenqing.put("projectManagerId", cfFacilityFlow.getProjectManagerId());
        //项目经理名称
        showxinedushenqing.put("projectManagerName", cfFacilityFlow.getProjectManagerName());
        //授信用途
        showxinedushenqing.put("facilityPurpose", cfFacilityFlow.getFacilityPurpose());

        //卖方客户信息
        if (cfFacilityFlow.getSellEnterpriseId() != null) {
            CuEnterprise cuEnterprise = cuEnterpriseService.get(cfFacilityFlow.getSellEnterpriseId());
            showxinedushenqing.put("sellEnterpriseId", cfFacilityFlow.getSellEnterpriseId());
            showxinedushenqing.put("sellEnterpriseName", cuEnterprise.getName());
            //社会信用统一代码
            showxinedushenqing.put("sellCreditCode", cuEnterprise.getCreditCode());
            //企业联系人
            List<CuPerson> personList = cuEnterprisePersonService.findPersonList(cuEnterprise.getId(), 3);
            if (personList != null && personList.size() > 0) {
                //联系人姓名
                showxinedushenqing.put("sellCustomerName", personList.get(0).getName());
                //联系人手机号
                showxinedushenqing.put("sellMobile", personList.get(0).getMobile());
            }
        }
        //买方客户信息
        if (cfFacilityFlow.getCoreEnterpriseId() != null) {
            CuEnterprise cuEnterprise = cuEnterpriseService.get(cfFacilityFlow.getCoreEnterpriseId());
            //买方企业ID
            showxinedushenqing.put("coreEnterpriseId", cfFacilityFlow.getCoreEnterpriseId());
            //买方企业名称
            showxinedushenqing.put("coreEnterpriseName", cuEnterprise.getName());
            //社会信用统一代码
            showxinedushenqing.put("creditCode", cuEnterprise.getCreditCode());
            //企业联系人
            List<CuPerson> personList = cuEnterprisePersonService.findPersonList(cuEnterprise.getId(), 3);
            if (personList != null && personList.size() > 0) {
                //联系人姓名
                showxinedushenqing.put("customerName", personList.get(0).getName());
                //联系人手机号
                showxinedushenqing.put("mobile", personList.get(0).getMobile());
            }
        }

        //授信金额
        showxinedushenqing.put("facilityAmount", cfFacilityFlow.getFacilityAmount());
        //授信剩余额度
        showxinedushenqing.put("surplusAmount", cfFacilityFlow.getSurplusAmount());

        showxinedushenqing.put("coreEnterpriseCreditCode", cfFacilityFlow.getCoreEnterpriseCreditCode());

        //额度类型
        showxinedushenqing.put("quotaType", cfFacilityFlow.getQuotaType());
        //授信起日
        showxinedushenqing.put("guaranteeDateStart", cfFacilityFlow.getGuaranteeDateStart());
        //授信止日
        showxinedushenqing.put("guaranteeDateEnd", cfFacilityFlow.getGuaranteeDateEnd());

        return showxinedushenqing;
    }
}
