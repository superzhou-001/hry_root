/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
 */
package hry.scm.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import hry.bean.JsonResult;
import hry.business.cu.model.CuCustomer;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.fundsupport.model.FundSupportPlan;
import hry.scm.fundsupport.service.FundSupportPlanService;
import hry.scm.project.dao.RedeemRecordDao;
import hry.scm.project.dao.RedeemTotalDao;
import hry.scm.project.model.*;
import hry.scm.project.model.vo.RedeemTotalVo;
import hry.scm.project.service.*;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.JWTContext;
import hry.util.date.DateUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p> RedeemRecordService </p>
 *
 * @author: luyue
 * @Date: 2020-07-21 16:41:25 
 */
@Service("redeemRecordService")
public class RedeemRecordServiceImpl extends BaseServiceImpl<RedeemRecord, Long> implements RedeemRecordService {

	@Resource(name = "redeemRecordDao")
	@Override
	public void setDao (BaseDao<RedeemRecord, Long> dao) {
		super.dao = dao;
	}
	@Autowired
	public MortgageProjectService mortgageProjectService;
	@Autowired
	public MortgageTotalService  mortgageTotalService;
	@Autowired
	public RedeemTotalService redeemTotalService;
	@Autowired
	public ProcessRecordService processRecordService;
	@Autowired
	public EnterpriseQuotaService enterpriseQuotaService;
	@Autowired
	public RedeemDetailService redeemDetailService;
	@Autowired
	public MortgageDetailService mortgageDetailService;
	@Autowired
	public StorageService storageService;
	@Autowired
	public ScmEnterpriseService scmEnterpriseService;
	@Autowired
	public UserRelationService userRelationService;

	@Autowired
	public FundSupportPlanService fundSupportPlanService;
	/**
	 * 根据还款日期等计算各项费用
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult calculateFee(Map<String, String> map) {
		JsonResult result=new JsonResult();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
		BigDecimal ling=new BigDecimal("0");
		BigDecimal  bai=new BigDecimal("100");
		String projectId=map.get("projectId");//订单id
		String payDate=map.get("payDate");//还款日期
		String goodsWorth=map.get("goodsWorth");//赎货总价值
		BigDecimal goodsMoney=new BigDecimal(goodsWorth);
		//1、获得订单信息
		MortgageProject project=mortgageProjectService.get(Long.valueOf(projectId));
		if(null==project || "".equals(project)){
			result.setSuccess(false).setMsg("订单不存在，请重新选择");
		}
		BigDecimal principalMoney=ling;
		//2、计算本次还款本金
			if("lastRelease".equals(project.getPrincipalModel())){//最后一次释放
				//3、本次赎回货物价值和剩余未还金额对比，如果本次赎回货物价值>剩余未还金额,则本金=剩余未还金额，如果<=，则本金=本次赎回货物价值
				if(goodsMoney.compareTo(project.getSurplusMoney())>0){
					principalMoney=project.getSurplusMoney();
				}else{
					principalMoney=goodsMoney;
				}
			}else if("sameRateRelease".equals(project.getPrincipalModel())){
                //4、等比例本金模型，本金=赎回货物价值*实际质押率
				BigDecimal money=goodsMoney.multiply(project.getMortRate()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
				//5、计算金额>剩余本金，则取剩余本金，否则取计算金额
				if(money.compareTo(project.getSurplusMoney())>0){
					principalMoney=project.getSurplusMoney();
				}else{
					principalMoney=money;
				}
			}
			BigDecimal interest=ling;
			int days=0;
			//借款时间（本金利息=还款时间-放款日）（全额利息=还款时间-上次还款日）
			if("principalInterest".equals(project.getInterestModel())){//本金利息
             //6、计算本次赎货日期和开始日期的时间差
				 days= DateUtil.getDaysBetweenDate(sdf.format(project.getStartDate()),payDate)+1;
				//利息=本次还款本金*实际借款天数
				interest=principalMoney.multiply(project.getServiceRate()).multiply(new BigDecimal(days)).divide(bai,2,BigDecimal.ROUND_HALF_UP);

			}else if("fullInterest".equals(project.getInterestModel())){//全额本息
                days=this.calculateDays(project.getId(),"interest",payDate);//计息时间
			//利息=当前剩余未还本金*实际借款天数
			interest=project.getSurplusMoney().multiply(project.getServiceRate()).multiply(new BigDecimal(days)).divide(bai,2,BigDecimal.ROUND_HALF_UP);
			}
			//7、计算逾期数据
			BigDecimal overdueMoney=ling;
			int overdays=0;
			//判断是否逾期
			int d=DateUtil.getDaysBetweenDate(sdf.format(project.getEndDate()),payDate);
			if(d>0){//赎货日期>到期日，则逾期
				overdays=this.calculateDays(project.getId(),"overdue",payDate);
				overdueMoney=project.getSurplusMoney().multiply(project.getOverRate()).multiply(new BigDecimal(overdays)).divide(bai,2,BigDecimal.ROUND_HALF_UP);
			}
			//8、本次还款总金额
			BigDecimal sumMoney=principalMoney.add(interest).add(overdueMoney);
			//9、借款时间 =还款时间-开始时间
			//int loandays= DateUtil.getDaysBetweenDate(sdf.format(project.getStartDate()),payDate)+1;
		    Map<String,Object> map1=new HashMap<>();
		    map1.put("goodsWorth",goodsWorth);//地址押物价值
			map1.put("principalMoney",principalMoney);//本金
			map1.put("interest",interest);//服务费
			map1.put("overdueMoney",overdueMoney);//逾期费用
			map1.put("overdays",overdays);//逾期天数
			map1.put("loandays",days);//借款天数
			map1.put("sumMoney",sumMoney);//本次还款总金额
			result.setSuccess(true).setObj(map1).setMsg("查询成功");
		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false).setMsg("系统出错，请联系客服");
		}
		return result;
	}
	/**
	 *
	 * @param projectId
	 * @param type  interest为计息天数，overdue 为逾期天数
	 * @return
	 */
	@Override
	public int calculateDays(Long projectId, String type,String payBackDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int days=0;
		MortgageProject project=mortgageProjectService.get(projectId);
		//1、查询是否有还款记录
		QueryFilter filter=new QueryFilter(RedeemRecord.class);
		filter.addFilter("projectId=",projectId);
		filter.addFilter("status>=","10");//资方确认收款
		filter.setOrderby("paybackDate desc");//按还款日期倒序排
		List<RedeemRecord> list=this.find(filter);
		//2、如果有还款记录
		if(null!=list && list.size()>0){
			RedeemRecord record=list.get(0);
			Date startDate=DateUtil.addDaysToDate(record.getPaybackDate(), 1);//上次赎货日+1
			if("interest".equals(type)){//计算利息时间
              //利息计算则以上次还款时间的第二天作为起始时间计算
				days=DateUtil.getDaysBetweenDate(sdf.format(startDate),payBackDate)+1;
			}else if("overdue".equals(type)){
             //如果是逾期，比较最近的赎货日期和合同到期日，哪个大按照哪个作为开始日期计算逾期天数
				int d=DateUtil.getDaysBetweenDate(project.getEndDate(),startDate);
				//赎货日期小于到期日，则按照合同到期日计算
				if(d<0){
					days=DateUtil.getDaysBetweenDate(sdf.format(project.getEndDate()),payBackDate);
				}else{//上次赎货日+1，作为开始时间
					days=DateUtil.getDaysBetweenDate(sdf.format(startDate),payBackDate);
				}
			}
		}else{
			//没有还款记录
			if("interest".equals(type)){//计算利息时间
			    //已订单开始时间为起始时间，计算借款天数
				days=DateUtil.getDaysBetweenDate(sdf.format(project.getStartDate()),payBackDate)+1;
			}else if("overdue".equals(type)){
				//从来没有还过钱，则到期日作为起始时间计算
				days=DateUtil.getDaysBetweenDate(sdf.format(project.getEndDate()),payBackDate);
			}
		}
		return days;
	}

	@Override
	public JsonResult apply(Map<String, String> map) {

		JsonResult result=new JsonResult();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			BigDecimal ling=new BigDecimal("0");
			BigDecimal  bai=new BigDecimal("100");
			String projectId=map.get("projectId");//订单id
			String payDate=map.get("payDate");//还款日期
			String  jsonStr=map.get("jsonStr");//质押物json
			String payImageUrl=map.get("payImageUrl");//还款凭证路径
			//1、获得订单信息
			MortgageProject project=mortgageProjectService.get(Long.valueOf(projectId));
			if(null==project || "".equals(project)){
				result.setSuccess(false).setMsg("订单不存在，请重新选择");
			}
			//2、解析抵质押物json
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			JSONArray tarray = jsonObject.getJSONArray("storage");
			List<RedeemTotal> list=new ArrayList<RedeemTotal>();
			BigDecimal goodsWorth=ling;//质押物总价值
			if(!tarray.isEmpty() && tarray.size()>0){
				for(int i=0;i<tarray.size();i++){
					JSONObject tobject = (JSONObject) tarray.get(i);
					RedeemTotal rt= JSON.parseObject(tobject.toString(),RedeemTotal.class);
					//3、查询对应库存汇总信息
					MortgageTotal total=mortgageTotalService.get(rt.getTotalId());
					//4、计算赎货重量=件数*平均重量
					BigDecimal backWeight=total.getAveWeight().multiply(new BigDecimal(rt.getBackCount()));
					//5、计算赎回货物价值=赎回重量*单价
					BigDecimal backWorth=backWeight.multiply(total.getMortPrice());
					rt.setBackWeight(backWeight);
					rt.setBackWorth(backWorth);
					goodsWorth=goodsWorth.add(backWorth);
					list.add(rt);
				}
			}
			//6、调用接口计算款项等信息
			map.put("goodsWorth",goodsWorth.toString());
			result=this.calculateFee(map);
			if(!result.getSuccess()){
				return result;
			}else{
				//款项数据
                 Map<String,Object> mapf=(HashMap<String,Object>)(result.getObj());
                 BigDecimal principalMoney=(BigDecimal) mapf.get("principalMoney");//本金
				 BigDecimal interest=(BigDecimal) mapf.get("interest");//利息
				 BigDecimal overdueMoney=(BigDecimal) mapf.get("overdueMoney");//逾期飞鹰
				 BigDecimal sumMoney=(BigDecimal) mapf.get("sumMoney");//本次还款总金额
				 int overdays=(int) mapf.get("overdays");//逾期天数
				 int  loandays=(int)mapf.get("loandays");//借款天数
				 RedeemRecord record=new RedeemRecord();
				 record.setLoanDays(loandays);
				 record.setNumber(this.createNumber(project.getNumber()));
				 record.setProjectId(project.getId());
				 record.setProjectNumber(project.getNumber());
				 record.setFundSupportId(project.getFundSupportId());
				 record.setEnterpriseId(project.getEnterpriseId());
				 record.setEnterpriseName(project.getEnterpriseName());
				 record.setApplyId(Long.valueOf(map.get("userId")));
				 record.setCreditCode(project.getCreditCode());
				 record.setGoodsWorth(goodsWorth);
				 record.setPrincipalMoney(principalMoney);
				 record.setServiceMoney(interest);
				 record.setOverdueDays(overdays);
				 record.setOverdueMoney(overdueMoney);
				 record.setSumMoney(sumMoney);
				 record.setPayImageUrl(payImageUrl);
				 record.setPaybackDate(sdf.parse(payDate));
				 record.setSurplusMoney(project.getSurplusMoney().subtract(principalMoney));
				 record.setSurplusMortMoney(project.getMortgageMoney().subtract(goodsWorth));
				 this.save(record);
				 for(RedeemTotal rt:list){
					 rt.setRedeemId(record.getId());
					 redeemTotalService.save(rt);
				 }
				//7、保存处理业务记录数据
				ProcessRecord processRecord=new ProcessRecord();
				processRecord.setProjectId(record.getId());//订单id
				processRecord.setType("3");//流程类别1现货质押，2代采，3取件
				processRecord.setName("申请赎货");//节点名称
				processRecord.setStep(1);//第几步
				processRecord.setHandelId(Long.valueOf(map.get("userId")));//处理人id
				processRecord.setHandelName(map.get("username"));//处理人姓名
				processRecord.setOpinion("1");//处理意见 0新申请 1同意2拒绝',
				processRecordService.save(processRecord);
				result.setSuccess(true).setMsg("申请成功");
			}



		}catch (Exception e){
			e.printStackTrace();
			result.setSuccess(false).setMsg("系统出错，请联系客服");
		}
		return result;
	}
	/**
	 * 赎货操作下一步
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult nextStep(Map<String, String> map) {
		JsonResult result=new JsonResult();
		String redeemId=map.get("redeemId");
		//1、查询赎货申请
		RedeemRecord record=this.get(Long.valueOf(redeemId));
		if(null==record || "".equals(record)){
			result.setSuccess(false).setMsg("该赎货申请不存在，请重新选择");
			return result;
		}
		int step=0;
		String stepname="";
		if(0==record.getStatus()){
			step=2;
			stepname="确认收款";
		}else if(10==record.getStatus()){
			step=3;
			stepname="解除质押";
		}
		//2、根据处理意见保存信息
		String opinion=map.get("opinion");
		if(null==opinion || "".equals(opinion)){
			result.setSuccess(false).setMsg("请选择处理意见!");
			return result;
		}
		//3、获得当前处理新信息
		Long handelId=0L;
		String handelName="";
		if("2".equals(map.get("reType"))){//前台请求
			handelId=Long.valueOf(map.get("userId"));
			handelName=map.get("username");

		}else{//后台请求
			NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
			handelId=user.getId();
			handelName=user.getName();
	/*		handelId=1L;
			handelName="小可爱";*/
		}
		//4、根据处理意见处理业务
		if("1".equals(opinion)){
            if(0==record.getStatus()){//确认收款
            	record.setStatus(10);
            	//5、更改融资申请的金额数据
                MortgageProject project=mortgageProjectService.get(record.getProjectId());
				project.setBackMoney(project.getBackMoney().add(record.getPrincipalMoney()));//已还金额+本次本金
				project.setSurplusMoney(project.getSurplusMoney().subtract(record.getPrincipalMoney()));//剩余未还金额-本次本金
				mortgageProjectService.update(project);
				//6、额度增加
				QueryFilter filter=new QueryFilter(EnterpriseQuota.class);
				filter.addFilter("enterpriseId=",project.getEnterpriseId());
				filter.addFilter("fundSupportId=",project.getFundSupportId());
				List<EnterpriseQuota> list1=enterpriseQuotaService.find(filter);
				if(null!=list1 && list1.size()>0){
					EnterpriseQuota quota=list1.get(0);
					quota.setUsedQuotaValue(quota.getUsedQuotaValue().subtract(record.getPrincipalMoney()));//已用额度减少
					quota.setSurplusQuotaValue(quota.getSurplusQuotaValue().add(record.getPrincipalMoney()));//可用额度zengjia
					enterpriseQuotaService.update(quota);
				}
				//7、修改质押物汇总的信息，剩余件数，剩余质押物金额等
				//查询本次赎货汇总记录
				QueryFilter qf=new QueryFilter(RedeemTotal.class);
				qf.addFilter("redeemId=",record.getId());
				List<RedeemTotal> totalList=redeemTotalService.find(qf);
				for(RedeemTotal rt:totalList){
					//8、查询对应的质押物汇总记录
					MortgageTotal total=mortgageTotalService.get(rt.getTotalId());
					total.setSurplusWeight(total.getSurplusWeight().subtract(rt.getBackWeight()));//剩余重量=剩余总重量-本次赎货重量
					total.setSurTotalPrice(total.getSurTotalPrice().subtract(rt.getBackWorth()));//剩余价值=剩余总价值-本次赎货价值
					total.setSurplusCount(total.getSurplusCount()-rt.getBackCount());//剩余件数=剩余总件数-本次赎货件数
					total.setBackCount(total.getBackCount()+rt.getBackCount());//赎货件数=赎货总件数+本次赎货件数
					total.setBackWeight(total.getBackWeight().add(rt.getBackWeight()));//剩余重量=赎货总重量+本次赎货重量
					mortgageTotalService.update(total);
				}
            	this.update(record);
			}else if(10==record.getStatus()){//解除质押
            	//9、确认解除质押，要更改质押物详细信息数据，并判断是否释放相应库存
				//查询该次赎货所有解除质押明细
				QueryFilter qf=new QueryFilter(RedeemDetail.class);
				qf.addFilter("redeemId=",record.getId());
				List<RedeemDetail> rlist=redeemDetailService.find(qf);
				for(RedeemDetail rd:rlist){
					MortgageDetail md=mortgageDetailService.get(rd.getDetailId());
					md.setBackCount(md.getBackCount()+rd.getBackCount());//赎回件数+本次赎回件数
					md.setSurplusCount(md.getSurplusCount()-rd.getBackCount());//剩余件数-本次赎回件数
					mortgageDetailService.update(md);
					if(md.getSurplusCount()<=0){
						//10、质押中剩余件数<=0,则将该库存货架释放
						QueryFilter qf1=new QueryFilter(Storage.class);
						qf1.addFilter("number=",md.getNumber());
						List<Storage> slist=storageService.find(qf1);
						if(null!=slist && slist.size()>0){
							Storage storage=slist.get(0);
							storage.setIsMortgage("0");
							storage.setGoodsCount(storage.getGoodsCount()-md.getMortCount());//件数=库存件数-本次质押的件数
							storageService.update(storage);
						}
					}
				}
				record.setRedeemDate(new Date());
				record.setStatus(20);
				this.update(record);
				//11、判断该订单是否已全部还完
				MortgageProject project=mortgageProjectService.get(record.getProjectId());
				if(project.getSurplusMoney().compareTo(new BigDecimal(0))==0) {
					project.setStatus(60);//已完结
					project.setActualEndDate(new Date());
					mortgageProjectService.update(project);
					//12、把所有的抵质押物过一遍，质押物的价值本来大于借款金额，可能已还清，质押物还未解除完
					QueryFilter mqf = new QueryFilter(MortgageTotal.class);//剩余件数不为0 的清0
					mqf.addFilter("projectId=", project.getId());
					mqf.addFilter("surplusCount>", 0);
					List<MortgageTotal> mortgageTotalList = mortgageTotalService.find(mqf);
					BigDecimal ling = new BigDecimal("0");
					for (MortgageTotal total : mortgageTotalList) {
						total.setSurplusWeight(ling);//剩余重量=0
						total.setSurTotalPrice(ling);//剩余价值=0
						total.setSurplusCount(0);//剩余件数=0
						total.setBackCount(total.getMortCount());//赎货件数=质押件数
						total.setBackWeight(total.getMortWeight());//剩余重量=质押重量
						mortgageTotalService.update(total);
					}
					//13、释放质押明细
					QueryFilter dqf = new QueryFilter(MortgageDetail.class);
					dqf.addFilter("projectId=", project.getId());
					dqf.addFilter("surplusCount>", 0);
					List<MortgageDetail> mortgageDetailList = mortgageDetailService.find(dqf);
					for (MortgageDetail detail : mortgageDetailList) {
						detail.setSurplusCount(0);
						detail.setBackCount(detail.getMortCount());
						mortgageDetailService.update(detail);
						//14、释放货架
						QueryFilter qf1 = new QueryFilter(Storage.class);
						qf1.addFilter("number=", detail.getNumber());
						List<Storage> slist = storageService.find(qf1);
						if (null != slist && slist.size() > 0) {
							Storage storage = slist.get(0);
							storage.setIsMortgage("0");
							storage.setGoodsCount(storage.getGoodsCount()-detail.getMortCount());//件数=库存件数-本次质押的件数
							storage.setWeight(storage.getWeight().subtract(detail.getMortWeight()));//重量=库存重量-本次质押重量
							storageService.update(storage);
						}
					}
				}
			}
		}
		//11、保存处理业务记录数据
		ProcessRecord processRecord=new ProcessRecord();
		processRecord.setProjectId(record.getId());//赎货申请id
		processRecord.setType("3");//流程类别1现货质押，2代采，3取件
		processRecord.setName(stepname);//节点名称
		processRecord.setStep(step);//第几步
		processRecord.setHandelId(handelId);//处理人id
		processRecord.setHandelName(handelName);//处理人姓名
		processRecord.setOpinion(opinion);//处理意见 0新申请 1同意2拒绝',
		processRecordService.save(processRecord);
		result.setSuccess(true).setMsg("处理成功!");
		return result;
	}
	/**
	 * 赎货记录详情
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult recordDetail(Map<String, String> map) {
		Map<String,Object> fmap = new HashMap<>();
		JsonResult result=new JsonResult();
		String redeemId=map.get("redeemId");
		//1、查询赎货申请
		RedeemRecord record=this.get(Long.valueOf(redeemId));
		if(null==record || "".equals(record)){
			result.setSuccess(false).setMsg("该赎货申请不存在，请重新选择");
			return result;
		}
		fmap.put("redeemRecord",record);//本次赎货信息
		//2、查询原申请融资订单
		MortgageProject project=mortgageProjectService.get(record.getProjectId());
		fmap.put("mortgageProject",project);//原融资申请信息
		ScmEnterprise enterprise = scmEnterpriseService.get(record.getEnterpriseId());
		//3、额度信息
		QueryFilter efilter = new QueryFilter(EnterpriseQuota.class);
		efilter.addFilter("enterpriseId=",record.getEnterpriseId());
		efilter.addFilter("fundSupportId=",record.getFundSupportId());
		EnterpriseQuota enterpriseQuota = enterpriseQuotaService.get(efilter);
		if(null!=enterpriseQuota){
			enterprise.setSumQuotaValue(enterpriseQuota.getSumQuotaValue());
			enterprise.setSurplusQuotaValue(enterpriseQuota.getSurplusQuotaValue());
		}
		//4、用户信息
		CuCustomer cuCustomer = userRelationService.getCustomer(record.getEnterpriseId(),1);
		enterprise.setEmail(cuCustomer.getEmail());
		fmap.put("enterprise",enterprise);
		FundSupportPlan fundSupportPlan = fundSupportPlanService.get(project.getFundPlanId());
		//5、资金方案
		fmap.put("fundSupportPlan",fundSupportPlan);
		//6、赎货货单
		List<RedeemTotalVo> list=redeemTotalService.findRedeemTotalBySql(map);
		fmap.put("redeemTotalList",list);
		//7、赎货记录
		if("2".equals(map.get("reType"))){//前台请求，即资方
			QueryFilter qf=new QueryFilter(RedeemRecord.class);
			qf.addFilter("projectId=",redeemId);
			qf.addFilter("id!=",redeemId);
			List<RedeemRecord> rlist=this.find(qf);
			fmap.put("redeemRecordList",rlist);//赎货记录
		}
		result.setSuccess(true).setObj(fmap);

		return result;
	}

	public String createNumber(String projectNumber) {
		QueryFilter filter=new QueryFilter(RedeemRecord.class);
		filter.addFilter("number_LIKE", "%"+projectNumber+"%");
		filter.setOrderby("id desc");
		List<RedeemRecord> list=this.find(filter);
		RedeemRecord record=null;
		if(null!=list && list.size()>0){
			record=list.get(0);
		}
		String pnumber = new String(""); //订赎货编号
		if(null!=record){
			StringBuffer number = new StringBuffer("");
			String proNum = record.getNumber();
			String ss = proNum.substring(proNum.length()-3);
			int num = new Integer(ss);
			num+=1;
			if(num<10){
				number = number.append("00").append(num);
			}else if(num>=10&&num<100){
				number = number.append("0").append(num);
			}else {
				number = number.append(num);
			}
			pnumber=projectNumber+"_"+number.toString();

		}else{
			pnumber=projectNumber+"_001";
		}
		return pnumber;
	}


}
