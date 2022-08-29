/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 17:01:01
 */
package hry.business.fa.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.business.cu.model.CuBank;
import hry.business.cu.model.CuEnterprise;
import hry.business.cu.model.CuPerson;
import hry.business.cu.service.CuBankService;
import hry.business.cu.service.CuEnterprisePersonService;
import hry.business.cu.service.CuEnterpriseService;
import hry.business.fa.model.*;
import hry.business.fa.service.*;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.thread.ThreadPoolUtils;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p> FaFactoringFlowService </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 17:01:01
 */
@Slf4j
@Service("faFactoringFlowService")
public class FaFactoringFlowServiceImpl extends BaseServiceImpl<FaFactoringFlow, Long> implements FaFactoringFlowService, CommonFlowService {


    static class FactoringFlow_Key {
        private final static String JINZHIDIAOCHA = "factoringFlow_jinzhidiaocha";
        private final static String FENGXIANSHENHE = "factoringFlow_fengxianshenhe";
        private final static String BUMENJINGLISHENHE = "factoringFlow_bumenjinglishenhe";
        private final static String QIANDINGHETONG = "factoringFlow_qiandinghetong";
        private final static String ZONGJINGLISHENPI = "factoringFlow_zongjinglishenpi";
        private final static String TIJIAOFANGKUANSHENPI = "factoringFlow_tijiaofangkuanshenpi";
    }


    @Resource(name = "faFactoringFlowDao")
    @Override
    public void setDao(BaseDao<FaFactoringFlow, Long> dao) {
        super.dao = dao;
    }

    @Autowired
    private CuEnterpriseService cuEnterpriseService;
    @Autowired
    private FaFactoringCostService faFactoringCostService;
    @Autowired
    private FaFactoringRateService faFactoringRateService;
    @Autowired
    private FaBillService faBillService;
    @Autowired
    private CuBankService cuBankService;
    @Autowired
    private FaFactoringContractService faFactoringContractService;
    @Autowired
    private CuEnterprisePersonService cuEnterprisePersonService;
    @Autowired
    private FaProductService faProductService;
    @Autowired
    private NewAppUserService newAppUserService;
    @Autowired
    private FaProductCostService faProductCostService;
    @Autowired
    private FaFundIntentService faFundIntentService;


    @Override
    public JsonResult getFaFactoringFlowInfo(Long id) {
        JsonResult jsonResult = new JsonResult();
        FaFactoringFlow faFactoringFlow = get(id);
        if (faFactoringFlow == null) return jsonResult.setMsg("为查询s到信息！");
        CountDownLatch latch = new CountDownLatch(8);

        //1查询卖方企业
        ThreadPoolUtils.execute(() -> {
            if (!StringUtils.isEmpty(faFactoringFlow.getSellEnterpriseId())) {
                CuEnterprise cuEnterprise = cuEnterpriseService.get(faFactoringFlow.getSellEnterpriseId());
                //联系人
                CuPerson lianxiren = cuEnterprisePersonService.getCuEnterprisePersonByEnterpriseIdAndType(faFactoringFlow.getSellEnterpriseId(), 3);
                cuEnterprise.setContacts(lianxiren);
                faFactoringFlow.setSellEnterprise(cuEnterprise);
            }
            latch.countDown();
        });

        //2查询买方企业
        ThreadPoolUtils.execute(() -> {
            if (!StringUtils.isEmpty(faFactoringFlow.getBuyEnterpriseId())) {
                faFactoringFlow.setBuyEnterprise(cuEnterpriseService.get(faFactoringFlow.getBuyEnterpriseId()));
            }
            latch.countDown();
        });

        //3查询费用明细
        ThreadPoolUtils.execute(() -> {
            QueryFilter queryFilter = new QueryFilter(FaFactoringCost.class);
            queryFilter.addFilter("projectId=", faFactoringFlow.getId());
            faFactoringFlow.setCostList(faFactoringCostService.find(queryFilter));
            latch.countDown();
        });

        //4查询费率明细
        ThreadPoolUtils.execute(() -> {
            QueryFilter queryFilter = new QueryFilter(FaFactoringRate.class);
            queryFilter.addFilter("projectId=", faFactoringFlow.getId());
            faFactoringFlow.setRateList(faFactoringRateService.find(queryFilter));
            latch.countDown();
        });

        //5查询票据信息
        ThreadPoolUtils.execute(() -> {
            faFactoringFlow.setFaBillList(faBillService.findBillByProjectId(faFactoringFlow.getId()));
            latch.countDown();
        });

        //6查询银行信息
        ThreadPoolUtils.execute(() -> {
            if (!StringUtils.isEmpty(faFactoringFlow.getBankId())) {
                faFactoringFlow.setCuBank(cuBankService.get(faFactoringFlow.getBankId()));
            }
            latch.countDown();
        });

        //7查询合同签署信息
        ThreadPoolUtils.execute(() -> {
            QueryFilter filter = new QueryFilter(FaFactoringContract.class);
            filter.addFilter("projectId=", faFactoringFlow.getId());
            faFactoringFlow.setContractList(faFactoringContractService.find(filter));
            latch.countDown();
        });

        //8查询产品信息
        ThreadPoolUtils.execute(() -> {
            faFactoringFlow.setFaProduct(faProductService.get(faFactoringFlow.getProductId()));
            latch.countDown();
        });

        try {
            latch.await();
            return jsonResult.setSuccess(true).setObj(faFactoringFlow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    @Override
    public FlowParams jzdc(FlowParams flowParams) {
        try {
            //获取表单
            JSONObject paramsList = (JSONObject) flowParams.getAttribute("factoringFlow_jinzhidiaocha");
            //企业id
            Long enterpriseId = paramsList.getLong("enterpriseId");
            //项目经理id
            Long projectManagerId = paramsList.getLong("projectManagerId");
            //买方企业
            Long buyEnterpriseId = paramsList.getLong("buyEnterpriseId");
            //是否占用授信
            Integer useFacility = paramsList.getInteger("useFacility");
            //授信Id
            Long facilityFlowId = paramsList.getLong("facilityFlowId");

            //查询流程
            FaFactoringFlow project = get(flowParams.getProjectId());

            //保存费用信息
            String costList = paramsList.getString("costList");
            if (!StringUtils.isEmpty(costList)) {
                JSONArray jsonArray = JSONObject.parseArray(costList);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject costObject = jsonArray.getJSONObject(i);
                    FaFactoringCost faFactoringCost = new FaFactoringCost();
                    faFactoringCost.setCostname(costObject.getString("costname"));
                    faFactoringCost.setProjectId(flowParams.getProjectId());
                    faFactoringCost.setCosttype(costObject.getInteger("costtype"));
                    faFactoringCost.setType(costObject.getInteger("type"));
                    faFactoringCost.setPaidMoney(costObject.getBigDecimal("paidMoney"));
                    faFactoringCost.setReceivableDate(costObject.getDate("receivableDate"));
                    faFactoringCost.setRemark(costObject.getString("remark"));
                    faFactoringCostService.save(faFactoringCost);
                }
            }

            //保存款项信息
            JSONObject interestModel = paramsList.getJSONObject("interestModel");
            if (interestModel != null) {
                //融资金额
                project.setFinancingMoney(interestModel.getBigDecimal("financingMoney"));
                //周期
                project.setFinancingTerm(interestModel.getInteger("financingTerm"));
                //放款日期
                project.setApplyLoanDate(interestModel.getDate("applyLoanDate"));
                //到期日期
                project.setExpireDate(interestModel.getDate("expireDate"));

                project.setRepaytype(interestModel.getInteger("repaytype"));
                project.setRepayperiod(interestModel.getInteger("repayperiod"));
                project.setPeriodday(interestModel.getInteger("periodday"));
                project.setModeltype(interestModel.getInteger("modeltype"));
                project.setPeriodtype(interestModel.getInteger("periodtype"));
                project.setYearmodeltype(interestModel.getInteger("yearmodeltype"));
                project.setRepaydaytype(interestModel.getInteger("repaydaytype"));
                project.setRepayday(interestModel.getInteger("repayday"));
                project.setPenaltyratio(interestModel.getBigDecimal("penaltyratio"));


                //保存款项信息
                JSONArray tableData = interestModel.getJSONArray("tableData");
                if (tableData != null && tableData.size() > 0) {
                    //设置流程id
                    for (int i = 0; i < tableData.size(); i++) {
                        JSONObject jsonObject = tableData.getJSONObject(i);
                        jsonObject.put("factoringId", project.getId());
                    }
                    faFundIntentService.saveFundList(project.getId(), tableData.toJSONString());
                }
                //费率信息
                JSONArray productRateJson = interestModel.getJSONArray("productRateJson");
                //保存费率信息
                faFactoringRateService.saveJson(project.getId(),productRateJson.toJSONString());

            }

            //买方客户ID
            project.setBuyEnterpriseId(buyEnterpriseId);
            //是否占用授信
            project.setUseFacility(useFacility);
            //授信流程id
            if(facilityFlowId!=null) {
                project.setFacilityFlowId(facilityFlowId);
            }
            update(project);
        } catch (Exception e) {
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
    public FlowParams qdht(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams zjlsp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    @Override
    public FlowParams tjfksp(FlowParams flowParams) {
        flowParams.setSuccess(true);
        return flowParams;
    }

    //---------------------------------------------保理业务启动方法------------------------------------------------------
    @Override
    public FlowParams start(FlowParams flowParams) {
        //卖方企业id
        Long enterpriseId = flowParams.getAttributeLong("sellCustomerId");
        //项目经理id
        Long projectManagerId = flowParams.getAttributeLong("projectManagerId");
        //产品ID
        Long productId = flowParams.getAttributeLong("productId");

        //企业对象
        CuEnterprise cuEnterprise = cuEnterpriseService.get(Long.valueOf(enterpriseId));
        if (cuEnterprise == null) {
            flowParams.setSuccess(false).setMsg("sellCustomerId错误");
            return flowParams;
        }

        //项目经理
        NewAppUser projectManager = newAppUserService.get(projectManagerId);

        //产品信息
        FaProduct product = faProductService.get(productId);

        //创建流程项目
        FaFactoringFlow project = new FaFactoringFlow();
        //项目名称
        String projectName = cuEnterprise.getName() + DateUtil.dateToString(new Date(), "yyyyMMdd");
        //授信编号
        String projectCode = DateUtil.dateToString(new Date(), "yyyyMMdd");
        project.setProjectName(projectName);
        project.setProjectCode(projectCode);
        project.setProjectManagerId(projectManagerId);
        project.setProjectManagerName(projectManager.getUserName());
        //卖方企业ID
        project.setSellEnterpriseId(enterpriseId);
        project.setSellEnterpriseName(cuEnterprise.getName());
        project.setFinancingDate(new Date());
        //产品id
        project.setProductId(productId);

        //产品参数复制到流程中
        project.setRepaytype(product.getRepaytype());
        project.setRepayperiod(product.getRepayperiod());
        project.setPeriodday(product.getPeriodday());
        project.setRepaydaytype(product.getRepaydaytype());
        project.setPenaltyratio(product.getPenaltyratio());
        project.setModeltype(product.getModeltype());
        project.setPeriodtype(product.getPeriodtype());
        project.setYearmodeltype(product.getYearmodeltype());
        project.setRepayday(product.getRepayday());

        //保存费用
        List<FaCost> costList = faProductCostService.findByProductId(product.getId());
        if (costList != null && costList.size() > 0) {
            for (FaCost cost : costList) {
                FaFactoringCost faFactoringCost = new FaFactoringCost();
                faFactoringCost.setCostname(cost.getCostname());
                faFactoringCost.setProjectId(flowParams.getProjectId());
                faFactoringCost.setCosttype(cost.getCosttype());
                faFactoringCost.setType(cost.getType());
                faFactoringCost.setCostmoney(cost.getCostmoney());
                faFactoringCost.setRemark(cost.getRemark());
                faFactoringCost.setNlms(cost.getNlmsname());
                faFactoringCostService.save(faFactoringCost);
            }
        }


        //保存流程
        save(project);

        //设置流水号--更新流程
        String number = String.format("%4d", project.getId()).replace(" ", "0");
        //流程名称
        project.setProjectName(project.getProjectName() + number);
        project.setProjectCode(project.getProjectCode() + number);
        //融资编号
        project.setFinancingCode(project.getProjectCode());
        update(project);

        //流程参数
        flowParams.setProjectId(project.getId());
        flowParams.setProjectName(project.getProjectName());
        flowParams.setSuccess(true);

        return flowParams;
    }

    @Override
    public FlowParams view(FlowParams flowParams) {

        Long projectId = flowParams.getProjectId();
        FaFactoringFlow project = get(projectId);

        //回显组件
        JSONObject jsonObject = new JSONObject();
        //尽职调查
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(FactoringFlow_Key.JINZHIDIAOCHA)) {
            jsonObject.put(FactoringFlow_Key.JINZHIDIAOCHA, viewCommon(flowParams.getSonForm(), project));
        }
        //部门经理审核
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(FactoringFlow_Key.BUMENJINGLISHENHE)) {
            jsonObject.put(FactoringFlow_Key.BUMENJINGLISHENHE, viewCommon(flowParams.getSonForm(), project));
        }
        //签订合同
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(FactoringFlow_Key.QIANDINGHETONG)) {
            jsonObject.put(FactoringFlow_Key.QIANDINGHETONG, viewCommon(flowParams.getSonForm(), project));
        }
        //总经理审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(FactoringFlow_Key.ZONGJINGLISHENPI)) {
            jsonObject.put(FactoringFlow_Key.ZONGJINGLISHENPI, viewCommon(flowParams.getSonForm(), project));
        }
        //提交放款审批
        if (!StringUtils.isEmpty(flowParams.getSonForm()) && flowParams.getSonForm().contains(FactoringFlow_Key.TIJIAOFANGKUANSHENPI)) {
            jsonObject.put(FactoringFlow_Key.TIJIAOFANGKUANSHENPI, viewCommon(flowParams.getSonForm(), project));
        }

        //回显表单
        flowParams.setViewAttribute(jsonObject);
        flowParams.setSuccess(true);
        return flowParams;
    }


    //回显核心方法
    private JSONObject viewCommon(String formKey, FaFactoringFlow project) {

        //showxinedushenqing组件渲染数据
        JSONObject data = new JSONObject();
        //项目名称
        data.put("projectName", project.getProjectName());
        //授信申请编号
        data.put("projectCode", project.getProjectCode());
        //项目经理ID
        data.put("projectManagerId", project.getProjectManagerId());
        //项目经理名称
        data.put("projectManagerName", project.getProjectManagerName());

        FaProduct product = faProductService.get(project.getProductId());
        if (product != null) {
            data.put("productName", product.getProductname());
        }

        //尽职调查回显产品
        if (FactoringFlow_Key.JINZHIDIAOCHA.equals(formKey)) {
            //获取产品,回显计算模型
            if (product != null) {
                JSONObject interestModel = new JSONObject();
                interestModel.put("repaytype", product.getRepaytype());
                interestModel.put("repayperiod", product.getRepayperiod());
                interestModel.put("periodday", product.getPeriodday());
                interestModel.put("repaydaytype", product.getRepaytype());
                interestModel.put("penaltyratio", product.getPenaltyratio());
                interestModel.put("modeltype", product.getModeltype());
                interestModel.put("periodtype", product.getPeriodtype());
                interestModel.put("yearmodeltype", product.getYearmodeltype());
                interestModel.put("repayday", product.getRepayday());
                data.put("interestModel", interestModel);
            }
        }else{
            JSONObject interestModel = new JSONObject();
            interestModel.put("repaytype", project.getRepaytype());
            interestModel.put("repayperiod", project.getRepayperiod());
            interestModel.put("periodday", project.getPeriodday());
            interestModel.put("modeltype", project.getModeltype());
            interestModel.put("periodtype", project.getPeriodtype());
            interestModel.put("yearmodeltype", project.getYearmodeltype());
            interestModel.put("repaydaytype", project.getRepaytype());
            interestModel.put("repayday", project.getRepayday());
            interestModel.put("penaltyratio", project.getPenaltyratio());
            interestModel.put("financingMoney", project.getFinancingMoney());
            interestModel.put("financingTerm", project.getFinancingTerm());
            interestModel.put("applyLoanDate", project.getApplyLoanDate());
            interestModel.put("expireDate", project.getExpireDate());

            //放款信息
            List<FaFundIntent> faFundIntents = faFundIntentService.find(new QueryFilter(FaFundIntent.class).addFilter("factoringId=", project.getId()));
            if(faFundIntents!=null&&faFundIntents.size()>0) {
                interestModel.put("tableData", faFundIntents);
            }
            //查询费率信息
            List<FaFactoringRate> rateList = faFactoringRateService.find(new QueryFilter(FaFactoringRate.class).addFilter("projectId=", project.getId()));
            if(rateList!=null&&rateList.size()>0){
                interestModel.put("productRateJson",rateList);
            }
            data.put("interestModel", interestModel);
        }

        //查询费用信息
        List<FaFactoringCost> costList = faFactoringCostService.findAll();
        if (costList != null && costList.size() > 0) {
            data.put("costList", costList);
        }


        //卖方客户信息
        if (project.getSellEnterpriseId() != null) {
            CuEnterprise cuEnterprise = cuEnterpriseService.get(project.getSellEnterpriseId());
            data.put("sellEnterpriseId", project.getSellEnterpriseId());
            data.put("sellEnterpriseName", cuEnterprise.getName());
            //社会信用统一代码
            data.put("sellCreditCode", cuEnterprise.getCreditCode());
            //企业联系人
            List<CuPerson> personList = cuEnterprisePersonService.findPersonList(cuEnterprise.getId(), 3);
            if (personList != null && personList.size() > 0) {
                //联系人姓名
                data.put("sellCustomerName", personList.get(0).getName());
                //联系人手机号
                data.put("sellMobile", personList.get(0).getMobile());
            }

            //卖方银行账户
            QueryFilter filter = new QueryFilter(CuBank.class);
            filter.addFilter("subjectId=", project.getSellEnterpriseId());
            filter.addFilter("type=", 2);
            CuBank cuBank = cuBankService.get(filter);
            if (cuBank != null) {
                //开户名称
                data.put("bankName", cuBank.getBankName());
                //银行名称
                data.put("cardBank", cuBank.getBankName());
                //收款账户类型 账户类型 1.基本户 2.一般户
                data.put("accountType", cuBank.getAccountType());
                //开启支行
                data.put("bankAddress", cuBank.getBankAddress());
                //银行卡号
                data.put("cardNumber", cuBank.getCardNumber());
            }

        }
        //买方客户信息
        if (project.getBuyEnterpriseId() != null) {
            CuEnterprise cuEnterprise = cuEnterpriseService.get(project.getBuyEnterpriseId());
            //买方企业ID
            data.put("buyEnterpriseId", project.getBuyEnterpriseId());
            //买方企业名称
            data.put("buyEnterpriseName", cuEnterprise.getName());
            //社会信用统一代码
            data.put("buyCreditCode", cuEnterprise.getCreditCode());
            //企业联系人
            List<CuPerson> personList = cuEnterprisePersonService.findPersonList(cuEnterprise.getId(), 3);
            if (personList != null && personList.size() > 0) {
                //联系人姓名
                data.put("buyCustomerName", personList.get(0).getName());
                //联系人手机号
                data.put("buyMobile", personList.get(0).getMobile());
            }
        }

        return data;
    }
}
