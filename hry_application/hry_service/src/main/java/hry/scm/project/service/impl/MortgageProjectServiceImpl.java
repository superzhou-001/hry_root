/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.scm.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import hry.bean.JsonResult;
import hry.bean.PageResult;
import hry.business.cu.model.CuCustomer;
import hry.business.cu.service.CuCustomerService;
import hry.core.mvc.dao.BaseDao;
import hry.core.mvc.service.impl.BaseServiceImpl;
import hry.core.util.PageFactory;
import hry.core.util.QueryFilter;
import hry.platform.newuser.model.NewAppUser;
import hry.scm.enterprise.model.ScmEnterprise;
import hry.scm.enterprise.service.ScmEnterpriseService;
import hry.scm.enterprise.service.UserRelationService;
import hry.scm.fundsupport.model.FundSupport;
import hry.scm.fundsupport.model.FundSupportPlan;
import hry.scm.fundsupport.service.FundSupportPlanService;
import hry.scm.fundsupport.service.FundSupportService;
import hry.scm.project.model.*;
import hry.scm.project.service.*;
import hry.scm.quota.dao.EnterpriseQuotaDao;
import hry.scm.quota.model.EnterpriseQuota;
import hry.scm.quota.service.EnterpriseQuotaService;
import hry.security.jwt.JWTContext;
import hry.util.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p> MortgageProjectService </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:03:52 
 */
@Service("mortgageProjectService")
public class MortgageProjectServiceImpl extends BaseServiceImpl<MortgageProject, Long> implements MortgageProjectService {

	@Resource(name = "mortgageProjectDao")
	@Override
	public void setDao (BaseDao<MortgageProject, Long> dao) {
		super.dao = dao;
	}

	@Autowired
	public FundSupportService fundSupportService;
	@Autowired
	public FundSupportPlanService fundSupportPlanService;
	@Autowired
	public ScmEnterpriseService  scmEnterpriseService;
	@Autowired
	public MortgageTotalService mortgageTotalService;
	@Autowired
	public ProcessRecordService processRecordService;
	@Autowired
	public StorageService storageService;
	@Autowired
	public MortgageDetailService mortgageDetailService;
	@Autowired
    public PriceService priceService;
	@Autowired
	public EnterpriseQuotaService enterpriseQuotaService;
	@Autowired
	public UserRelationService userRelationService;
	@Autowired
	public RedeemRecordService redeemRecordService;
	@Autowired
	public IncreaseMoneyService increaseMoneyService;



    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


	@Override
	public Map<String, Object> getProjectProcessInfo(Long projectId,String type) {
		Map<String,Object> map = new HashMap<>(4);
		MortgageProject mortgageProject = this.get(projectId);
		map.put("mortgageProject",mortgageProject);
		QueryFilter mfilter = new QueryFilter(MortgageTotal.class);
		mfilter.addFilter("projectId=",projectId);
		List<MortgageTotal> mortgageTotalList = mortgageTotalService.find(mfilter);
		//抵质押物汇总信息
		map.put("mortgageTotalList",mortgageTotalList);
	//	if(type.equals("aduit")){   前台资金方也查看额度等数据，所以放开
			ScmEnterprise enterprise = scmEnterpriseService.get(mortgageProject.getEnterpriseId());
			//额度信息
			QueryFilter efilter = new QueryFilter(EnterpriseQuota.class);
			efilter.addFilter("enterpriseId=",enterprise.getId());
			efilter.addFilter("fundSupportId=",mortgageProject.getFundSupportId());
			EnterpriseQuota enterpriseQuota = enterpriseQuotaService.get(efilter);
			if(null!=enterpriseQuota){
				enterprise.setSumQuotaValue(enterpriseQuota.getSumQuotaValue());
				enterprise.setSurplusQuotaValue(enterpriseQuota.getSurplusQuotaValue());
			}
			//用户信息
			CuCustomer cuCustomer = userRelationService.getCustomer(mortgageProject.getEnterpriseId(),1);
			enterprise.setEmail(cuCustomer.getEmail());
			map.put("enterprise",enterprise);
			FundSupportPlan fundSupportPlan = fundSupportPlanService.get(mortgageProject.getFundSupportId());
			//资金方案
			map.put("fundSupportPlan",fundSupportPlan);
		//}
		//已放款的订单，查询下赎货记录
		if(mortgageProject.getStatus()>=50){
			QueryFilter qf=new QueryFilter(RedeemRecord.class);
			qf.addFilter("projectId=",mortgageProject.getId());
			qf.setOrderby("id desc");
			List<RedeemRecord> redeemRecordList=redeemRecordService.find(qf);
			map.put("redeemRecordList",redeemRecordList);
			//补款记录
			QueryFilter qf1=new QueryFilter(IncreaseMoney.class);
			qf1.addFilter("projectId=",mortgageProject.getId());
			qf1.setOrderby("id desc");
			List<IncreaseMoney> increaseMoneyList=increaseMoneyService.find(qf1);
			map.put("increaseMoneyList",increaseMoneyList);
		}

		return map;
	}

	@Override
	public PageResult listByStatusAndRole(QueryFilter filter,String role, Integer projectStatus) {
        //项目状态，0新申请，10业务审核，20确认质押，30质押审批，50放款审批 ，40客户取消申请
		switch(role){
			case "admin":
				switch(projectStatus){
					case 1 :
						//待处理
						filter.addFilter("status_in","0,20");
						break;
					case 2 :
						//处理中
						filter.addFilter("status_in","10,30");
						break;
					case 3 :
						//已放款
						filter.addFilter("status=","50");
						break;

					case 4 :
						//待还款-4
						filter.addFilter("status_in","50,70");
						break;
					case 5 :
						//已逾期
						filter.addFilter("status=","70");
						break;
					case 6 :
						//已结清
						filter.addFilter("status=","60");
						break;
                    case 7 :
                        //已关闭
                        filter.addFilter("status=","40");
                        break;
					default:
						throw new IllegalStateException("Unexpected value: " + projectStatus);
				}
				break;
			case "enterprise":
				switch(projectStatus){
					case 1 :
						//待处理
						filter.addFilter("status=","10");
						break;
					case 2 :
						//处理中
						filter.addFilter("status_in","0,20,30");
						break;
					case 3 :
						//已放款
						filter.addFilter("status_in","50");
						break;
					case 4 :
						//已关闭
						filter.addFilter("status=","40");
						break;
					case 5 :
						//已逾期
						filter.addFilter("status=","70");
						break;
					case 6 :
						//已结清
						filter.addFilter("status=","60");
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + projectStatus);
				}
				break;
			case "fundSupport":
				switch(projectStatus){
					case 1 :
						//待处理
						filter.addFilter("status=","30");
						break;
					case 2 :
						//处理中
						filter.addFilter("status_in","0,10,20");
						break;
					case 3 :
						//已放款
						filter.addFilter("status=","50");
						break;
					default:
						throw new IllegalStateException("Unexpected value: " + projectStatus);
				}
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + role);
		}
		filter.setOrderby("id desc");
		return this.findPageResult(filter);
	}

	/**
	 * 生成质押订单的订单编号
	 * @return
	 */
	@Override
	public String createNumber() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String newDate="ZY"+sdf.format(new Date());
		QueryFilter filter=new QueryFilter(MortgageProject.class);
		filter.addFilter("number_LIKE", "%"+newDate+"%");
		filter.setOrderby("id desc");
		List<MortgageProject> list=this.find(filter);
		MortgageProject project=null;
		if(null!=list && list.size()>0){
			project=list.get(0);
		}
		String pnumber = new String(""); //订单编号
		if(null!=project){
			StringBuffer number = new StringBuffer("");
			String proNum = project.getNumber();
			String ss = proNum.substring(proNum.length()-4);
			int num = new Integer(ss);
			num+=1;
			if(num<10){
				number = number.append("000").append(num);
			}else if(num>=10&&num<100){
				number = number.append("00").append(num);
			}else if(num>=100&&num<1000){
				number = number.append("0").append(num);
			}else {
				number = number.append(num);
			}
			pnumber=newDate+number.toString();

		}else{
			pnumber=newDate+"0001";
		}
		return pnumber;
	}

	/**
	 * 保存融资申请信息以及质押统计数据
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult apply(Map<String, String> map) {
		JsonResult result=new JsonResult();
		String jsonStr=map.get("jsonStr");
		//1、解析json数据
		//将参数转为json类型
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		//2、保存一些项目基本信息
		String sproject = jsonObject.getString("project");
		if(StringUtils.isEmpty(sproject)){
			result.setSuccess(false).setMsg("申请信息不能为空!");
			return result;
		}
		MortgageProject project=JSONObject.parseObject(sproject,MortgageProject.class);
		if(null==project.getFundSupportId() || "".equals(project.getFundSupportId() )){
			result.setSuccess(false).setMsg("请选择资金方!");
			return result;
		}
		//查询资金方
		FundSupport support=fundSupportService.get(project.getFundSupportId());
		project.setFundSupportName(support.getFundSupportName());
		//3、查询资金方案，保存服务费率等信息
		if(null==project.getFundPlanId()|| "".equals(project.getFundPlanId() )){
			result.setSuccess(false).setMsg("请选择资金方案!");
			return result;
		}
		FundSupportPlan plan=fundSupportPlanService.get(project.getFundPlanId());
		if(null==plan || "".equals(plan)){
			result.setSuccess(false).setMsg("该方案存在，请重新选择!");
			return result;
		}
		project.setFundPlanName(plan.getFundPlanName());//资金方案名称
		project.setProportion(plan.getProportion());//放款比例
		project.setPeriod(plan.getPeriod());//还款期限
		project.setServiceRate(plan.getServiceRate());//服务费率
		project.setOverRate(plan.getOverRate());//逾期费率
        project.setPrincipalModel(plan.getPrincipalModel());//本金模型
        project.setInterestModel(plan.getInterestModel());//利息模型
		project.setApplyId(Long.valueOf(map.get("userId")));
		project.setNumber(this.createNumber());//订单编号
		project.setCreditCode(map.get("creditCode"));//企业社会信用代码
        Long enterpriseId=Long.valueOf(map.get("enterpriseId"));
		ScmEnterprise enterprise=scmEnterpriseService.get(enterpriseId);
		project.setEnterpriseId(enterpriseId);//企业id
        project.setBackMoney(new BigDecimal("0"));//已还金额
        project.setSurplusMoney(project.getLoanMoney());//未还金额
		project.setEnterpriseName(enterprise.getEnterpriseName());//企业名称
		//4、保存申请项目信息
		this.save(project);
		//5、解析抵制押物信息
		JSONArray tarray = jsonObject.getJSONArray("storage");
		BigDecimal money=new BigDecimal("0");
		BigDecimal maxMoney=new BigDecimal("0");
		if(!tarray.isEmpty() && tarray.size()>0){
			for(int j=0;j<tarray.size();j++){
				//6、保存抵质押物信息
				JSONObject tobject = (JSONObject) tarray.get(j);
				MortgageTotal total= JSON.parseObject(tobject.toString(),MortgageTotal.class);
				total.setProjectId(project.getId());//申请id
                total.setProjectType(project.getProjectType());//订单类型
				total.setProjectNumber(project.getNumber());//订单编号
				total.setEnterpriseId(project.getEnterpriseId());//企业id
				total.setCreditCode(project.getCreditCode());//企业社会信用统一代码
				total.setSurTotalPrice(total.getMortTotalPrice());//剩余总价值
				money=money.add(total.getMortTotalPrice());
				mortgageTotalService.save(total);
			}
			//计算最大借款金额 =质押物总价值*比例
			maxMoney=money.multiply(project.getProportion()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
			project.setMortgageMoney(money);
			project.setMaxLoanMoney(maxMoney);
			this.update(project);
			//6、保存处理业务记录数据
			ProcessRecord record=new ProcessRecord();
			record.setProjectId(project.getId());//订单id
			record.setType("1");//流程类别1现货质押，2代采，3取件
			record.setName("融资申请");//节点名称
			record.setStep(1);//第几步
			record.setHandelId(project.getApplyId());//申请账号id
			record.setHandelName(map.get("username"));//申请账号username
			record.setOpinion("0");//处理意见 0新申请 1同意2拒绝',
			processRecordService.save(record);
		}else{
			result.setSuccess(false).setMsg("请选择质押物!");
			return result;
		}
		result.setSuccess(true).setMsg("申请成功!");
		return result;
	}

	/**
	 * 现货质押处理下一步
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult nextStep(Map<String, String> map) {
		JsonResult result=new JsonResult();
		try {


		//1、获得申请项目信息
		String projectId=map.get("projectId");
		if(null==projectId || "".equals(projectId)){
			result.setSuccess(false).setMsg("请先选择订单!");
			return result;
		}
		MortgageProject project=this.get(Long.valueOf(projectId));
		if(null==project || "".equals(project)){
			result.setSuccess(false).setMsg("该订单不存在，请重新选择确认!");
			return result;
		}
		if(50==project.getStatus()){
			result.setSuccess(false).setMsg("该订单已放款，请不要重复操作!");
			return result;
		}
		int step=0;
		String stepname="";
		if(0==project.getStatus()){
             step=2;
             stepname="业务审核";
		}else if(10==project.getStatus()){
			step=3;
			stepname="确认质押";
		}else if(20==project.getStatus()){
			step=4;
			stepname="质押审批";
		}else if(30==project.getStatus()){
			step=5;
			stepname="确认放款";
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
			/*handelId=1L;
			handelName="小可爱";*/

		}
		//4、如果处理意见是同意再业务逻辑，不然只保存处理意见即可
		if("1".equals(opinion)){
			if(0==project.getStatus()){//业务审批
				project.setStatus(10);
				project.setBusinessOpeId(handelId);
				project.setBusinessOpeName(handelName);
                this.update(project);
			}else if(10==project.getStatus()){//确认质押
			    BigDecimal loanMoney=new BigDecimal(map.get("loanMoney"));//借款金额
				if(loanMoney.compareTo(project.getMaxLoanMoney())>0){
					result.setSuccess(false).setMsg("借款金额大于最大借款金额，请重新输入!");
					return result;
				}
				project.setLoanMoney(loanMoney);
				project.setBackMoney(new BigDecimal("0"));
				project.setSurplusMoney(loanMoney);
				project.setStatus(20);
				this.update(project);
			}else if(20==project.getStatus()){//质押审批通过
				//判断是否选择了库存进行了质押
				QueryFilter qf=new QueryFilter(MortgageDetail.class);
				qf.addFilter("projectId=",project.getId());
				List<MortgageDetail> dlist=mortgageDetailService.find(qf);
				if(null==dlist || dlist.size()<=0){
					result.setSuccess(false).setMsg("请在质押物的库存清单中选择要质押的物品，然后再提交!");
					return result;
				}
				project.setStatus(30);
				project.setMortgageOpeId(handelId);
				project.setMortgageOpeName(handelName);
				this.update(project);

			}else if(30==project.getStatus()){//确认放款
				project.setStatus(50);
				project.setLoanImageUrl(map.get("loanImageUrl"));
				//5、确定开始、到期时间
				String sdate=sdf.format(new Date());
				Date now=sdf.parse(sdate) ;
				project.setStartDate(now);
				//计算到期日
				Date endDate= DateUtil.addDaysToDate(now,project.getPeriod()-1);
				project.setEndDate(endDate);
				//计算实际质押率=借款金额/质押物总金额
				BigDecimal rate=project.getLoanMoney().divide(project.getMortgageMoney(),8,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
				project.setMortRate(rate);
				//6、扣减额度
				QueryFilter filter=new QueryFilter(EnterpriseQuota.class);
				filter.addFilter("enterpriseId=",project.getEnterpriseId());
				filter.addFilter("fundSupportId=",project.getFundSupportId());
				List<EnterpriseQuota> list1=enterpriseQuotaService.find(filter);
				if(null!=list1 && list1.size()>0){
					EnterpriseQuota quota=list1.get(0);
					quota.setUsedQuotaValue(quota.getUsedQuotaValue().add(project.getLoanMoney()));//已用额度增加
					quota.setSurplusQuotaValue(quota.getSurplusQuotaValue().subtract(project.getLoanMoney()));//可用额度减少
					enterpriseQuotaService.update(quota);
				}else{
					result.setSuccess(false).setMsg("借款企业在我处无授信额度，请企业先去申请额度");
				}
				this.update(project);

			}
		}else{//拒绝的都进去关闭状态
			project.setStatus(40);//取消申请
			this.update(project);
		}

		//6、保存处理业务记录数据
		ProcessRecord record=new ProcessRecord();
		record.setProjectId(project.getId());//订单id
		record.setType("1");//流程类别1现货质押，2代采，3取件
		record.setName(stepname);//节点名称
		record.setStep(step);//第几步
		record.setHandelId(handelId);//处理人id
		record.setHandelName(handelName);//处理人姓名
		record.setOpinion(opinion);//处理意见 0新申请 1同意2拒绝',
		processRecordService.save(record);
		result.setSuccess(true).setMsg("处理成功!");
		}catch(Exception e){
			e.printStackTrace();
			result.setSuccess(false).setMsg("后台报错，请联系管理员!");

		}
		return result;
	}
	/**
	 * 确认质押审批-保存质押物明细
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult confirmMortgage(Map<String, String> map) {
		JsonResult result=new JsonResult();
		String totalId=map.get("totalId");
		String jsonStr=map.get("jsonStr");
		//1、查询质押物汇总信息
		MortgageTotal total=mortgageTotalService.get(Long.valueOf(totalId));
		if(null==total || "".equals(total)){
			result.setSuccess(false).setMsg("质押物信息不存在，请确认");
			return result;
		}
       //2、解析json
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		JSONArray tarray = jsonObject.getJSONArray("storage");
		BigDecimal mortWeight=new BigDecimal("0");//质押总重量
		List<MortgageDetail> list=new ArrayList<MortgageDetail>();
		if(!tarray.isEmpty() && tarray.size()>0){
			for(int i=0;i<tarray.size();i++){
				JSONObject tobject = (JSONObject) tarray.get(i);
				MortgageDetail detail= JSON.parseObject(tobject.toString(),MortgageDetail.class);
				mortWeight=mortWeight.add(detail.getMortWeight());
				list.add(detail);
			}
			//3、选择的质押物是否大于质押总量
			if(mortWeight.compareTo(total.getMortWeight())<0){
				result.setSuccess(false).setMsg("选择的质押物重量之和小于质押总重量，请重新选择");
				return result;
			}

			//4、查询已经保存的明细，用户可能在质押物详情页确认质押后再重新操作，所以要先删除上次保留的明细以及释放具体仓库信息
			QueryFilter qf=new QueryFilter(MortgageDetail.class);
			qf.addFilter("totalId=",totalId);
			List<MortgageDetail> dlist=mortgageDetailService.find(qf);
		    for(MortgageDetail detail:dlist){
		    	Storage st=storageService.get(detail.getStorageId());
		    	st.setIsMortgage("0");
				storageService.update(st);
				mortgageDetailService.delete(detail.getId());
			}

			//5、根据选择的清单保存数据、冻结库存
			int mortCount=0;//质押总件数
			for(MortgageDetail detail:list){
				//6、查询库存信息  --暂时没有考虑再提交质押时，已经被另一个申请质押的问题，这项目可能性应该不大
				Storage storage=storageService.get(detail.getStorageId());
				//7、计算质押件数=质押重量*库存件数/库存重量
				int count=detail.getMortWeight().multiply(new BigDecimal(storage.getGoodsCount())).divide(storage.getWeight(),0,BigDecimal.ROUND_UP).intValue();
				mortCount=mortCount+count;
				//8、保存质押明细
				detail.setNumber(storage.getNumber());
				detail.setProjectId(total.getProjectId());
				detail.setTotalId(total.getId());
				detail.setProjectNumber(total.getProjectNumber());
				detail.setGoodsName(storage.getGoodsName());
				detail.setWeight(storage.getWeight());
				detail.setGoodsCount(storage.getGoodsCount());
				detail.setInspectionNo(storage.getInspectionNo());
				detail.setCaseNo(storage.getCaseNo());
				detail.setLocation(storage.getLocation());
				detail.setArea(storage.getArea());
				detail.setLine(storage.getLine());
				detail.setQueue(storage.getQueue());
				detail.setLayer(storage.getLayer());
				detail.setMortCount(count);
				detail.setBackCount(0);
				detail.setSurplusCount(count);
				detail.setEnterpriseId(total.getEnterpriseId());
				detail.setCreditCode(total.getCreditCode());
				mortgageDetailService.save(detail);
				//9、库存改为冻结状态
				storage.setIsMortgage("1");
				storageService.update(storage);
			}
			//10、更改质押物汇总信息
			total.setActMortWeight(mortWeight);//实际抵押重量
			total.setBackWeight(new BigDecimal("0"));//已赎回重量
			total.setSurplusWeight(total.getMortWeight());//剩余重量
			total.setMortCount(mortCount);
			total.setBackCount(0);
			total.setSurplusCount(mortCount);//剩余件数
			//11、计算抵押中的每件的平均重量----用质押重量/质押件数
			BigDecimal avg=total.getMortWeight().divide(new BigDecimal(mortCount),6,BigDecimal.ROUND_HALF_UP);
			total.setAveWeight(avg);
			mortgageTotalService.update(total);
			result.setSuccess(true).setMsg("处理成功");

		}else{
			result.setSuccess(false).setMsg("请选择相应的抵押物明细！");
			return result;
		}
		return result;
	}

	/**
	 * 修改质押物价格
	 * @param map
	 * @return
	 */
	@Override
	public JsonResult changePrice(Map<String, String> map) {
		JsonResult result=new JsonResult();
		try{
		String totalId=map.get("totalId");
		String sprice=map.get("price");
		//1、查找该质押物记录
		MortgageTotal total=mortgageTotalService.get(Long.valueOf(totalId));
		if(null==total || "".equals(total)){
			result.setSuccess(false).setMsg("该质押物不存在，请确认！");
			return result;
		}
		BigDecimal aprice=new BigDecimal(sprice);
		MortgageProject project=this.get(total.getProjectId());
		if(0==project.getStatus()){//业务审批之前修改价格，要更改订单的质押物价格、最大借款金额
			//2、查询除该质押物外其他质押物的总价值
			Map<String,String> map1=new HashMap<String,String>();
			map1.put("projectId",project.getId().toString());
			map1.put("totalId",totalId);
			BigDecimal otherPrice=mortgageTotalService.findMortWeight(map1);
           //3、总质押价值=修改价格*质押重量+
			BigDecimal	money=total.getMortWeight().multiply(aprice).add(otherPrice);
			//计算最大借款金额 =质押物总价值*比例+其他抵质物总价值
			BigDecimal	maxMoney=money.multiply(project.getProportion()).divide(new BigDecimal("100"),2,BigDecimal.ROUND_HALF_UP);
			project.setMaxLoanMoney(maxMoney);
			project.setMortgageMoney(money);
			this.update(project);
			total.setMortTotalPrice(total.getMortWeight().multiply(aprice));//质押物总价值
			total.setSurTotalPrice(total.getMortWeight().multiply(aprice));//质押物剩余价值
		}
		total.setModifyPrice(total.getMortPrice());
		total.setMortPrice(aprice);
		mortgageTotalService.update(total);

		//添加修改记录
        //4、查询当天是否有修改记录，有则修改，无则新增
            Price price=null;
            String sdate=sdf.format(new Date());
            Date now=sdf.parse(sdate) ;
            QueryFilter qf=new QueryFilter(Price.class);
            qf.addFilter("goodsName=",total.getGoodsName());
            qf.addFilter("recordDate=",now);
            List<Price> list=priceService.find(qf);
            NewAppUser user = (NewAppUser) JWTContext.getUser(NewAppUser.class);
            if(null!=list && list.size()>0){
                price=list.get(0);
                price.setBeforePrice(price.getPrice());
                price.setPrice(aprice);
                price.setOperaId(user.getId());
                price.setOperaName(user.getName());
                priceService.update(price);
            }else{
                price=new Price();
                price.setPrice(aprice);
                price.setRecordDate(now);
                price.setOperaId(user.getId());
                price.setOperaName(user.getName());
                price.setGoodsName(total.getGoodsName());
                priceService.save(price);
            }
			result.setSuccess(true).setMsg("修改成功！");
        }catch (Exception e){
		    e.printStackTrace();
            result.setSuccess(false).setMsg("后台报错，请联系管理员！");

        }


		return result;
	}

	@Override
	public void updateMortgageStatus() {
		QueryFilter filter = new QueryFilter(MortgageProject.class);
		filter.addFilter("status=",50);
		filter.addFilter("endDate<",new Date());
		List<MortgageProject> list = this.find(filter);
		for(MortgageProject m : list){
			m.setStatus(70);
			this.update(m);
		}
	}

	@Override
	public String createIncreaseNumber(Long projectId) {
		MortgageProject project=this.get(projectId);
		String projectNumber=project.getNumber()+"_bk";
		QueryFilter filter=new QueryFilter(IncreaseMoney.class);
		filter.addFilter("number_LIKE", "%"+projectNumber+"%");
		filter.setOrderby("id desc");
		List<IncreaseMoney> list=increaseMoneyService.find(filter);
		IncreaseMoney increaseMoney=null;
		if(null!=list && list.size()>0){
			increaseMoney=list.get(0);
		}
		String pnumber = new String(""); //订赎货编号
		if(null!=increaseMoney){
			StringBuffer number = new StringBuffer("");
			String proNum = increaseMoney.getNumber();
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
			pnumber=projectNumber+number.toString();

		}else{
			pnumber=projectNumber+"001";
		}
		return pnumber;
	}


}
