package hry.scm.fundsupport.util;

import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.service.FundSupportService;
import hry.util.SpringUtil;

public class UpdateEnterpriseQuotaRunnable implements Runnable{

    private FundSupport fundSupport;

    public UpdateEnterpriseQuotaRunnable(FundSupport fundSupport){
        this.fundSupport = fundSupport;
    }

    @Override
    public void run() {
        FundSupportService fundSupportService = SpringUtil.getBean("fundSupportService");
        fundSupportService.updateEnterpriseQuota(fundSupport);
    }
}
