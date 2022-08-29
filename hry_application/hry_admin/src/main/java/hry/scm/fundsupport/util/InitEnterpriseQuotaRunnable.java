package hry.scm.fundsupport.util;

import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.util.SpringUtil;

public class InitEnterpriseQuotaRunnable implements Runnable{

    private FundSupport fundSupport;

    public InitEnterpriseQuotaRunnable(FundSupport fundSupport){
        this.fundSupport = fundSupport;
    }

    @Override
    public void run() {
        FundSupportService fundSupportService = SpringUtil.getBean("fundSupportService");
        fundSupportService.initEnterpriseQuota(fundSupport);
    }
}
