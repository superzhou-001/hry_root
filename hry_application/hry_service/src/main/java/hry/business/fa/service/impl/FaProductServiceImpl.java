/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:29:53
 */
package hry.business.fa.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.loanTrial.FundIntentUtil;
import com.loanTrial.model.BpFundIntentInitParameter;
import com.loanTrial.model.SlFundIntent;
import hry.bean.JsonResult;
import hry.business.fa.model.*;
import hry.business.fa.model.em.*;
import hry.business.fa.service.FaFundIntentService;
import hry.business.fa.service.FaProductCostService;
import hry.business.fa.service.FaProductRateService;
import hry.business.fa.service.FaProductService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.util.StringUtil;
import hry.util.date.DateUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p> FaProductService </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:29:53
 */
@Service("faProductService")
public class FaProductServiceImpl extends BaseServiceImpl<FaProduct, Long> implements FaProductService {

	@Autowired
	private FaProductRateService faProductRateService;
	@Autowired
	private FaProductCostService faProductCostService;
	@Autowired
	private FaFundIntentService faFundIntentService;

	@Resource(name = "faProductDao")
	@Override
	public void setDao (BaseDao<FaProduct, Long> dao) {
		super.dao = dao;
	}

	@Override
	public JsonResult createProduct(FaProduct product) {
		// 产品创建步骤 1: 产品基础信息 2: 配置计息模型 3: 选择附加费用 4: 创建完成
		int status = product.getStatus();
		if (product.getId() == null && status == 1) {
			product.setStatus(2);
			super.save(product);
		} else {
			switch (status) {
				case 1 :
					product.setStatus(2);
					break;
				case 2 :
					product.setStatus(3);
					// 创建计息模型
					this.saveProductRate(product.getProductRateJson());
					// 添加费用
					this.saveProductCost(product.getId(), product.getProductCostIds());
					break;
				case 3 :
					product.setStatus(4);
					break;
			}
			super.update(product);
		}
		// 查询产品
		FaProduct newFaProduct = super.get(product.getId());
		return new JsonResult(true).setObj(newFaProduct);
	}

	/**
	 * 保存产品关联的费率
	 * */
	public JsonResult saveProductRate(String productRateJson) {
		if (StringUtil.isNull(productRateJson)) {
			productRateJson = StringEscapeUtils.unescapeHtml(productRateJson);
			List<FaProductRate> rateList = JSONArray.parseArray(productRateJson, FaProductRate.class);
			QueryFilter filter = new QueryFilter(FaProductRate.class);
			filter.addFilter("productid=", rateList.get(0).getProductid());
			List<FaProductRate> oldList = faProductRateService.find(filter);

			// 删除
			List<FaProductRate> deleteOldList = oldList.stream()
					.filter(item -> !rateList.stream()
							.map(e -> e.getId())
							.collect(Collectors.toList())
							.contains(item.getId()))
					.collect(Collectors.toList());
			deleteOldList.stream().forEach(rate ->faProductRateService.delete(rate.getId()));

			// 修改
			List<FaProductRate> updateOldList = rateList.stream()
					.filter(item -> oldList.stream()
							.map(e -> e.getId())
							.collect(Collectors.toList())
							.contains(item.getId()))
					.collect(Collectors.toList());
			updateOldList.stream().forEach(rate ->faProductRateService.update(rate));

			// 添加
			List<FaProductRate> newList = rateList.stream()
					.filter(item -> !oldList.stream()
							.map(e -> e.getId())
							.collect(Collectors.toList())
							.contains(item.getId()))
					.collect(Collectors.toList());
			newList.stream().forEach(rate ->faProductRateService.save(rate));
		}
		return new JsonResult(true);
	}
	/*
	* 保存产品相关费用
	* */
	public JsonResult saveProductCost(Long productId, String productCostIds) {
		if (StringUtil.isNull(productCostIds)) {
			String[] costIds = productCostIds.split(",");
			// 删除所有产品费用
			QueryFilter filter = new QueryFilter(FaProductCost.class);
			filter.addFilter("productid=", productId);
			faProductCostService.delete(filter);
			Arrays.stream(costIds).forEach(costId ->{
				FaProductCost cost = new FaProductCost();
				cost.setProductid(productId);
				cost.setCostid(Long.parseLong(costId));
				faProductCostService.save(cost);
			});
		}
		return new JsonResult(true);
	}




	@Override
	public JsonResult createFundList(FaFundInitParam param) {
		BpFundIntentInitParameter bpFund = new BpFundIntentInitParameter();
		// 项目名称
		bpFund.setBusinessType("SmallLoan");
		// 融资金额
		if (param.getFinancingMoney() == null)
			return new JsonResult(false).setMsg("请输入融资金额");
		bpFund.setProjectMoney(param.getFinancingMoney());

		// 融资期数
		if (param.getFinancingTerm() == null)
			return new JsonResult(false).setMsg("请输入融资期数");
		bpFund.setPayintentPeriod(param.getFinancingTerm());

		// 放款日期
		if (param.getApplyLoanDate() == null)
			return new JsonResult(false).setMsg("请输入放款日期");
		bpFund.setStartDate(param.getApplyLoanDate());

		// 到期日期
		if (param.getExpireDate() == null)
			return new JsonResult(false).setMsg("请输入到期日期");
		bpFund.setIntentDate(param.getExpireDate());

		/**-------------------------------------------------------------**/
		// 还款方式 repaytype: 1 等额本金 2 等额本息 3 等本等息 4 按期收息, 到本还期 5 其他方式
		bpFund.setAccrualtype(AccrualType.getValue(param.getRepaytype()));
		// 还款周期 repayperiod: 1 日 2 月 3 季 4 半年 5年 6 自定义
		bpFund.setPayaccrualType(PayAccrualType.getValue(param.getRepayperiod()));
		// 自定义周期---每期天数： periodday
		if (param.getRepayperiod() == 6) {
			if (param.getPeriodday() == null) {
				return new JsonResult(false).setMsg("自定义还款请填写周期天数");
			}
			bpFund.setDayOfEveryPeriod(param.getPeriodday());
		}
		// 模型设定-还款模型 modeltype: 1 算头不算尾 2 算头又算尾
		bpFund.setHeadTailModel(HeadTailModel.getValue(param.getModeltype()));
		// 月模型 periodtype: 1 按月化利率直接计算 2 按日化利率乘实际天数计算 3 按日化利率乘固定天数30天计算
		bpFund.setMonthModel(MonthModel.getValue(param.getPeriodtype()));
		// 年模型 yearmodeltype: 1 360天 2 365天
		bpFund.setYearModel(YearModel.getValue(param.getYearmodeltype()));
		// 还款日类型 repaydaytype: 1 按还款日对日还款 2 按固定还款
		bpFund.setIsStartDatePay(StartDatePay.getValue(param.getRepaydaytype()));
		// 注： 还款周期 是日 or 自定义, 则还款日类型模默认是对日还款
		if (param.getRepayperiod() == 1 || param.getRepayperiod() == 5) {
			bpFund.setIsStartDatePay(StartDatePay.getValue(1));
		}
		// 还款类型--- 按固定还款日： repayday
		if (param.getRepaydaytype() == 2) {
			if (param.getRepayday() == null) {
				return new JsonResult(false).setMsg("固定还款请输入固定还款日");
			}
			bpFund.setPayintentPerioDate(param.getRepayday());
		}
		/**-------------------------------------------------------------**/
		// 获取费率集合
		String productRateJson = StringEscapeUtils.unescapeHtml(param.getProductRateJson());
		List<FaProductRate> rateList = JSONArray.parseArray(productRateJson, FaProductRate.class);
		List<FaFundIntent> fundList = this.findFundIntentList(bpFund, rateList);
		return new JsonResult(true).setObj(fundList);
	}

	/**
	 * 生成分录台账列表
	 * */
	private List<FaFundIntent> findFundIntentList (BpFundIntentInitParameter bpFund, List<FaProductRate> rateList) {
		List<FaFundIntent> fundList = new ArrayList<>();
		// flag 本金偿还 是否已经加入返回集合
		boolean flag = true;
		for (FaProductRate rate : rateList) {
			// Repayone 是否前置付息 0 否 1 是
			// Repaytwo 是否一次性付息 0 否 1 是
			// Repaythree 是否抵扣 0 否 1 是
			// 年比例
			if (rate.getYearratio().compareTo(BigDecimal.ZERO) != 0) {
				bpFund.setYearAccrualRate(rate.getYearratio());
				bpFund.setFixedAccrualRate(new BigDecimal(0));
			}
			// 固定金额
			if (rate.getSummoney().compareTo(BigDecimal.ZERO) != 0) {
				bpFund.setFixedAccrualRate(rate.getSummoney());
				bpFund.setYearAccrualRate(new BigDecimal("0"));
			}
			// 是否前置付息
			bpFund.setIsPreposePayAccrual(rate.getRepayone());
			// 是否一次性付息
			bpFund.setIsInterestByOneTime(rate.getRepaytwo());
			List<SlFundIntent> list = FundIntentUtil.createFundList(bpFund);
			/**
			 * fundType:
			 * 	principalLending 本金放贷
			 * 	principalRepayment 本金偿还
			 * 	loanInterest 利息收取
			 * 	loanCost 贷款费用
			 * */
      		// fundList 以fundType分组 Map<String,List<Apple>>
			Map<String, List<SlFundIntent>> groupBy = list.stream().collect(
					Collectors.groupingBy(SlFundIntent::getFundType));
			// 本金放贷
			List<SlFundIntent> principalLendingList = null;
			// 本金偿还
			List<SlFundIntent> principalRepaymentList = null;
			List<FaFundIntent> newFundList = new ArrayList<>();
			if (flag) {
				flag = false;
				principalLendingList = groupBy.get("principalLending");
				principalRepaymentList = groupBy.get("principalRepayment");
				this.copyProperties(principalLendingList, newFundList);
				newFundList.stream().forEach(fund -> fund.setFundTypeName("本金放贷"));
				fundList.addAll(newFundList);
				this.copyProperties(principalRepaymentList, newFundList);
				newFundList.stream().forEach(fund -> fund.setFundTypeName("本金偿还"));
				fundList.addAll(newFundList);
			}
			// 利息收取
			List<SlFundIntent> loanInterestList = groupBy.get("loanInterest");
			if (loanInterestList != null) {
				this.copyProperties(loanInterestList, newFundList);
				for (int i = 0; i < newFundList.size(); i++) {
					FaFundIntent fa = newFundList.get(i);
					if (i != 0) {
						// 设置类型--贷款费用
						fa.setFundType("loanCost");
					}
					// 设置费率名称
					fa.setFundTypeName(rate.getRatename());
					/*
					 * 抵扣规则
					 * 	1、非一次性扣款，则抵扣第一期金额，放贷金额扣除，该记录设置抵扣
					 *  2、一次性扣款，则直接抵扣，放贷金额扣除，该记录设置抵扣
					 * */
					// 不管利息记录是多少条 取第一条 抵扣
					if (rate.getRepaythree() == 1 && i == 0) {
						fa.setRepaythree(rate.getRepaythree());
						fundList.stream().forEach(fund -> {
							if ("principalLending".equals(fund.getFundType())) {
								BigDecimal money = fund.getPayMoney().subtract(fa.getIncomeMoney());
								fund.setPayMoney(money);
							}
						});
					}
				}
				fundList.addAll(newFundList);
			}
		}
		// 期数排序
		fundList = fundList.stream().sorted((f1,f2)->f1.getPayintentPeriod()-f2.getPayintentPeriod()).collect(Collectors.toList());
		return fundList;
	}

	/**
	 * 相同对象属性拷贝
	 * */
	private void copyProperties(List<SlFundIntent> slFund, List<FaFundIntent> faFund) {
		faFund.clear();
		slFund.stream().forEach(sl -> {
			FaFundIntent fa = new FaFundIntent();
			BeanUtils.copyProperties(sl, fa);
			faFund.add(fa);
		});
	}

	@Override
	public JsonResult createFundListSynthesize(FaFundInitParam param) {
		JsonResult result = this.createFundList(param);
		if (!result.getSuccess()) {
			return result;
		}
		List<FaFundIntent> faFundIntentList = (List<FaFundIntent>) result.getObj();
		Map<Integer, List<FaFundIntent>> fundIntentMap = faFundIntentList.stream().collect(Collectors.groupingBy(FaFundIntent::getPayintentPeriod));
		List<FaFundIntentSynthesize> fundSynthesize = new ArrayList<>();
		fundIntentMap.forEach((key, value) -> {
			FaFundIntentSynthesize synthesize = new FaFundIntentSynthesize();
			synthesize.setPayintentPeriod(key);
			synthesize.setFundIntentList(value);
			for (FaFundIntent intent : value) {
				// 本金放贷合计为负数
				if ("principalLending".equals(intent.getFundType())) {
					intent.setIncomeMoney(intent.getPayMoney().negate());
				}
				synthesize.setInterestStarTime(intent.getInterestStarTime());
				synthesize.setInterestEndTime(intent.getInterestEndTime());
				synthesize.setInterestDays(intent.getInterestDays());
				synthesize.setIntentDate(intent.getIntentDate());
			}
			fundSynthesize.add(synthesize);
		});
		return new JsonResult(true).setObj(fundSynthesize);
	}




	@Override
	public JsonResult getIntentDate(Map<String, String> map) {
		String financingTerm = map.get("financingTerm");
		String repayperiod = map.get("repayperiod");
		String periodday = map.get("periodday");
		// 放款时间
		String applyLoanDate = map.get("applyLoanDate");
		Date applyDate = DateUtil.stringToDate(applyLoanDate, "yyyy-MM-dd");
		// 到期时间
		Date intentDate = null;
		// 天数
		int day = 0;
		// 月数
		int month = 0;
		// 期数
		int periods = 1;
		// 1 天 6 自定义
		if ("1".equals(repayperiod) || "6".equals(repayperiod)) {
			if ("6".equals(repayperiod) && StringUtil.isNull(periodday)) {
				periods = Integer.parseInt(periodday);
			}
			day = Integer.parseInt(financingTerm) * periods;
		} else {
			// 2 月 3 季 4 半年 5年
			if ("2".equals(repayperiod)) {
				month = 1;
			} else if ("3".equals(repayperiod)) {
				month = 3;
			} else if ("4".equals(repayperiod)) {
				month = 6;
			} else if ("5".equals(repayperiod)) {
				month = 12;
			}
		}
		if (day != 0) {
			intentDate = DateUtil.addDaysToDate(applyDate, day);
		}
		if (month != 0) {
			intentDate = DateUtil.addMonthsToDate(applyDate, month*Integer.parseInt(financingTerm));
		}
		// 合计天数
		int allDay = DateUtil.getDaysBetweenDate(applyDate, intentDate);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("allDay", allDay);
		resultMap.put("intentDate", DateUtil.dateToString(intentDate, "yyyy-MM-dd"));
 		return new JsonResult(true).setObj(resultMap);
	}
}
