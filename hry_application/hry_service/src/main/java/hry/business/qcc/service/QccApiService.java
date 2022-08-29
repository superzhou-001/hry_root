package hry.business.qcc.service;

import hry.bean.JsonResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * @Author: yaozh
 * @Description:
 * @Date:
 */
public interface QccApiService {

    /**
     * 刷新企业工商基本信息
     * @param creditCode 信用代码
     * @param enterpriseId 企业id
     * @return
     */
    JsonResult refreshBasicsByQcc(String creditCode,Long enterpriseId,Integer productClassify,String companyName);

}
