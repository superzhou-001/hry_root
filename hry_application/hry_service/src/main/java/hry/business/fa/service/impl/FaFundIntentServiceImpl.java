/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-22 13:47:08
 */
package hry.business.fa.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.fa.dao.FaFundIntentDao;
import hry.business.fa.model.FaFactoringCost;
import hry.business.fa.model.FaFactoringFlow;
import hry.business.fa.model.FaFundIntent;
import hry.business.fa.service.FaFactoringCostService;
import hry.business.fa.service.FaFactoringFlowService;
import hry.business.fa.service.FaFundIntentService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.util.StringUtil;
import hry.util.date.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> FaFundIntentService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-22 13:47:08
 */
@Service("faFundIntentService")
public class FaFundIntentServiceImpl extends BaseServiceImpl<FaFundIntent, Long> implements FaFundIntentService {

	@Autowired
	private FaFactoringCostService faFactoringCostService;
	@Autowired
	private FaFactoringFlowService faFactoringFlowService;

	@Resource(name = "faFundIntentDao")
	@Override
	public void setDao (BaseDao<FaFundIntent, Long> dao) {
		super.dao = dao;
	}

	@Override
	public PageResult findPageFundIntent(QueryFilter filter) {
		// ----------------------分页查询头部外壳------------------------------
		Page<FaFundIntent> page = PageFactory.getPage(filter);
		// type 1 正常还款待确认 2 逾期还款待确认
		String type = filter.getRequest().getParameter("type");
		// 项目编号
		String projectCode = filter.getRequest().getParameter("projectCode");
		// 卖方企业名称
		String sellEnterpriseName = filter.getRequest().getParameter("sellEnterpriseName");
		// 项目经理Id
		String projectManagerId = filter.getRequest().getParameter("projectManagerId");
		// 开始时间
		String startTime = filter.getRequest().getParameter("startTime");
		// 结束时间
		String endTime = filter.getRequest().getParameter("endTime");
		// 还款状态 1 正常待还、2 已逾期、3 待核实、4 已驳回、5 已还款
		String status = filter.getRequest().getParameter("status");
		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		param.put("newDate", DateUtil.dateToString(new Date(), "yyyy-MM-dd") );
		if (StringUtil.isNull(projectCode)) {
			param.put("projectCode", projectCode+"%");
		}
		if (StringUtil.isNull(sellEnterpriseName)) {
			param.put("sellEnterpriseName", sellEnterpriseName+"%");
		}
		if (StringUtil.isNull(projectManagerId)) {
			param.put("projectManagerId", projectManagerId);
		}
		if (StringUtil.isNull(startTime)) {
			param.put("startTime", startTime);
		}
		if (StringUtil.isNull(endTime)) {
			param.put("endTime", endTime);
		}
		if (StringUtil.isNull(status)) {
			param.put("status", status);
		}
		((FaFundIntentDao)dao).findFundIntentList(param);
		return new PageResult(page, filter.getPage(), filter.getPageSize());
	}

	@Override
	public JsonResult updateStatus(Map<String, String> param) {
		String status = param.get("status");
		// 还款凭证
		String refundImage = param.get("refundImage");
		// 还款时间
		String refundDate = param.get("refundDate");
		// 还款备注
		String remark = param.get("remark");
		if (!StringUtil.isNull(status)) {
			return new JsonResult(false).setMsg("状态不能为空");
		}
		if ("3".equals(status) && !StringUtil.isNull(refundImage)) {
			return new JsonResult(false).setMsg("确认付款时，请上传付款凭证");
		}
		String factoringId = param.get("factoringId");
		String payintentPeriod = param.get("payintentPeriod");
		QueryFilter filter = new QueryFilter(FaFundIntent.class);
		filter.addFilter("factoringId=", factoringId);
		filter.addFilter("payintentPeriod=", payintentPeriod);
		List<FaFundIntent> intentList = super.find(filter);
		for (FaFundIntent intent : intentList) {
			// 3 进入待核实 4 退回 5 已还款确认
			if ("3".equals(status) && intent.getStatus() == 1) {
				intent.setStatus(3);
				intent.setRefundImage(refundImage);
				// 实际还款金额
				intent.setRealityIncomeMoney(intent.getIncomeMoney());
				// 实际还款时间
				intent.setRealityIntentDate(DateUtil.stringToDate(refundDate));
				// 实际还款时备注
				intent.setRemark(remark);
			} else if ("4".equals(status) && intent.getStatus() == 3) {
				intent.setStatus(1);
				intent.setRealityIncomeMoney(new BigDecimal("0"));
				intent.setRealityIntentDate(null);
				intent.setRemark(null);
			} else if ("5".equals(status) && intent.getStatus() == 3) {
				intent.setStatus(5);
				// 实际还款金额
				intent.setAffirmIncomeMoney(intent.getIncomeMoney());
				// 实际还款时间
				intent.setAffirmIntentDate(DateUtil.stringToDate(refundDate));
				// 实际还款时备注
				intent.setAffirmRemark(remark);
			}
			super.update(intent);
		}
		return new JsonResult(true);
	}


	@Override
	public JsonResult saveFundList(Long factoringId, String faFundListJson) {
		faFundListJson = StringEscapeUtils.unescapeHtml(faFundListJson);
		List<FaFundIntent> faFundIntentList = JSONArray.parseArray(faFundListJson, FaFundIntent.class);
		// 获取申请融资金额
		BigDecimal loanMoney = new BigDecimal("0");
		for (FaFundIntent intent : faFundIntentList) {
			if ("principalLending".equals(intent.getFundType())) {
				loanMoney = intent.getPayMoney();
			}
			intent.setFactoringId(factoringId);
			intent.setStatus(1);
			if (intent.getRepaythree() == null) {
				intent.setRepaythree(0);
			} else if (intent.getRepaythree() == 1) {
				// 是否抵扣 --- 抵扣
				intent.setRealityIncomeMoney(intent.getIncomeMoney());
				intent.setRealityIntentDate(new Date());
				intent.setAffirmIncomeMoney(intent.getIncomeMoney());
				intent.setAffirmIntentDate(new Date());
				intent.setStatus(5);
			}
			super.save(intent);
		}
		// 获取其他费用
		QueryFilter filter = new QueryFilter(FaFactoringCost.class);
		filter.addFilter("projectId=", factoringId);
		filter.addFilter("costtype=", 2);
		List<FaFactoringCost> costList = faFactoringCostService.find(filter);
		if (costList != null && costList.size() > 0) {
			for (FaFactoringCost cost : costList) {
				// 减去抵扣的实收金额
				loanMoney = loanMoney.subtract(cost.getPaidMoney());
			}
		}
		FaFactoringFlow flow = faFactoringFlowService.get(factoringId);
		flow.setLoanMoney(loanMoney);
		faFactoringFlowService.update(flow);
		return new JsonResult(true);
	}
}
