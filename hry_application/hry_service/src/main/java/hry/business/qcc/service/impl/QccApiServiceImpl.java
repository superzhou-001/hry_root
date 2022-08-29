package hry.business.qcc.service.impl;

import com.alibaba.fastjson.JSON;
import hry.bean.JsonResult;
import hry.business.qcc.api.EnterpriseSearchApi;
import hry.business.qcc.service.*;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.platform.config.model.YzyConfig;
import hry.platform.config.service.AppConfigService;
import hry.platform.config.service.YzyConfigService;
import hry.util.SpringUtil;
import hry.util.StringUtil;
import hry.util.UUIDUtil;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
@Service("qccApiServiceImpl")
public class QccApiServiceImpl implements QccApiService {

    @Autowired
    private AppConfigService appConfigService;
    @Autowired
    private YzyConfigService yzyConfigService;




    @Override
    public JsonResult refreshBasicsByQcc(String creditCode, Long enterpriseId,Integer productClassify,String companyName) {

        String qccIpAdrees = appConfigService.getValueByKey("yzyRequestUrl");
        String uri = null;
        //公钥
        String qccPublicKeyUrl = appConfigService.getValueByKey("publicKey");
        //公钥
        String qccPrivateKeyUrl = appConfigService.getValueByKey("secretKey");
        //商户号
        String companyCode = appConfigService.getValueByKey("companyCode");
        //所属平台
        String belonged = appConfigService.getValueByKey("belonged");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("qccIpAdrees",qccIpAdrees);
        paramMap.put("qccPublicKeyUrl",qccPublicKeyUrl);
        paramMap.put("qccPrivateKeyUrl",qccPrivateKeyUrl);
        paramMap.put("companyCode",companyCode);
        paramMap.put("enterpriseId",enterpriseId);
        paramMap.put("creditCode",creditCode);

        Map<String, Object> qccMap = new HashMap<>();

        qccMap.put("belonged",belonged);
        qccMap.put("pageSize", "20");
        qccMap.put("pageIndex", "1");

        qccMap.put("searchKey", creditCode);
        qccMap.put("keyNo", creditCode);
        qccMap.put("keyword", creditCode);
        qccMap.put("keyWord", creditCode);
        qccMap.put("searchType", "1");

        qccMap.put("isExactlySame", true);//是否要与关键字完全一样,默认为False
        qccMap.put("companyName", companyName);//公司名称


        //查询工商基本信息接口列表
        List<YzyConfig> list = yzyConfigService.findYzyConfigByProductClassify(productClassify);
        StringBuffer sbResult = new StringBuffer();
        for (YzyConfig yzyConfig : list) {
            uri = yzyConfig.getProductUrl();
            paramMap.put("uri",uri);
            qccMap.put("ordernumber",UUIDUtil.getUUID());
            JsonResult jsr = this.getQccInfoByURl(yzyConfig,paramMap,qccMap);
            sbResult.append(jsr.getMsg());
            sbResult.append("</br>");
        }
        return new JsonResult().setSuccess(true).setMsg(sbResult.toString());
    }

    private JsonResult getQccInfoByURl(YzyConfig yzyConfig, Map<String, Object> paramMap, Map<String, Object> qccMap){
        QccFactoryService qccFactoryService = null;
        switch (yzyConfig.getProductCode()){
            case "getBasicDetailsByName"://工商基础信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseBasicDetailsService");
                break;
            case "getOpException"://营异常信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseExceptionService");
                break;
            case "getECIPartnerList"://企业股东信息
                qccFactoryService = SpringUtil.getBean("qccEnterprisePartnerService");
                break;
            case "getECIEmployeeList"://企业主要人员信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseEmployeeService");
                break;
            case "getInvestmentList"://企业对外投资信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseInvestmentService");
                break;
            case "getECIBranchList"://企业分支机构信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseBranchService");
                break;
            case "getECIChangeList"://企业变更内容信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseChangeService");
                break;
            case "searchCourtNotice"://开庭公告信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseCourtnoticeService");
                break;
            case "searchJudgmentDoc"://裁判文书信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseJudgmentdocService");
                break;
            case "searchCourtAnnouncement"://法院公告信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseCourtannouncementService");
                break;
            case "searchZhiXing"://被执行人信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseZhixingService");
                break;
            case "getJudicialAssistance"://股权冻结信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseJudicialAssistanceService");
                break;
            case "getLegalProcCountInfo"://法律诉讼维度条目信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseLegalProcCountService");
                break;
            case "getCaseFilingList"://立案信息
                qccFactoryService = SpringUtil.getBean("qccEnterpriseCaseFilingService");
                break;
            case "getECIInfoOverviewInfo"://工商风险扫描
                qccFactoryService = SpringUtil.getBean("qccEnterpriseOverviewInfoService");
                break;
        }
        return qccFactoryService.getQccInfo(paramMap,qccMap);
    }

}
