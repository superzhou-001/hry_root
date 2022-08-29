/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
 */
package hry.scm.project.service;

import hry.bean.JsonResult;
import hry.core.mvc.service.BaseService;
import hry.scm.project.model.RedeemRecord;

import java.util.Map;

/**
 * <p> RedeemRecordService </p>
 *
 * @author: luyue
 * @Date: 2020-07-21 16:41:25 
 */
public interface RedeemRecordService extends BaseService<RedeemRecord, Long> {
    /**
     * 根据还款日期等计算各项费用
     * @param map
     * @return
     */
    public JsonResult calculateFee(Map<String,String> map);

    /**
     *
     * @param projectId
     * @param type  interest为计息天数，overdue 为逾期天数
     * @return
     */
    public int calculateDays(Long projectId,String type,String payBackDate);

    /**
     * 赎货申请
     * @param map
     * @return
     */
    public JsonResult apply(Map<String,String> map);

    /**
     * 赎货操作下一步
     * @param map
     * @return
     */
    public JsonResult nextStep(Map<String,String> map);

    /**
     * 赎货记录详情
     * @param map
     * @return
     */
    public JsonResult recordDetail(Map<String,String> map);




}
