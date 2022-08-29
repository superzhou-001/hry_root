/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-22 13:47:08 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> FaFundIntent </p>
 *
 * @author: zhouming
 * @Date: 2020-07-22 13:47:08 
 */
@Data
@ApiModel(value = "还款计划分录实体类")
@Table(name="fa_fund_intent")
public class FaFundIntent extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 保理id
	*/
	@Column(name= "factoringId")
    @ApiModelProperty(value = "保理id")
	private Long factoringId;

	/**
	* 资金类型： 本金放贷 principalLending 本金偿还 principalRepayment 利息偿还 loanInterest 费用偿还 loanCost
	*/
	@Column(name= "fundType")
    @ApiModelProperty(value = "资金类型： 本金放贷 principalLending 本金偿还 principalRepayment 利息偿还 loanInterest 费用偿还 loanCost")
	private String fundType;

	/**
	* 资金名称
	*/
	@Column(name= "fundTypeName")
    @ApiModelProperty(value = "资金名称")
	private String fundTypeName;

	/**
	* 当前期数
	*/
	@Column(name= "payintentPeriod")
    @ApiModelProperty(value = "当前期数")
	private Integer payintentPeriod;

	/**
	* 计息开始时间
	*/
	@Column(name= "interestStarTime")
    @ApiModelProperty(value = "计息开始时间")
	private Date interestStarTime;

	/**
	* 计息结束时间
	*/
	@Column(name= "interestEndTime")
    @ApiModelProperty(value = "计息结束时间")
	private Date interestEndTime;

	/**
	* 计息天数
	*/
	@Column(name= "interestDays")
    @ApiModelProperty(value = "计息天数")
	private Integer interestDays;

	/**
	* 计划到账日
	*/
	@Column(name= "intentDate")
    @ApiModelProperty(value = "计划到账日")
	private Date intentDate;

	/**
	* 计划收入金额
	*/
	@Column(name= "incomeMoney")
    @ApiModelProperty(value = "计划收入金额")
	private BigDecimal incomeMoney;

	/**
	* 计划支出金额
	*/
	@Column(name= "payMoney")
    @ApiModelProperty(value = "计划支出金额")
	private BigDecimal payMoney;

	/**
	* 融资金额--本金
	*/
	@Column(name= "principalRepayment")
    @ApiModelProperty(value = "融资金额--本金")
	private BigDecimal principalRepayment;

	/**
	* 是否抵扣 0 收取 1 抵扣
	*/
	@Column(name= "repaythree")
    @ApiModelProperty(value = "是否抵扣 0 收取 1 抵扣")
	private Integer repaythree;

	/**
	* 实际收款金额
	*/
	@Column(name= "realityIncomeMoney")
    @ApiModelProperty(value = "实际收款金额")
	private BigDecimal realityIncomeMoney;

	/**
	* 实际到账日
	*/
	@Column(name= "realityIntentDate")
    @ApiModelProperty(value = "实际到账日")
	private Date realityIntentDate;

	/**
	 * 确认收款金额
	 */
	@Column(name= "affirmIncomeMoney")
	@ApiModelProperty(value = "确认收款金额")
	private BigDecimal affirmIncomeMoney;

	/**
	 * 确认收款时间
	 */
	@Column(name= "affirmIntentDate")
	@ApiModelProperty(value = "确认收款时间")
	private Date affirmIntentDate;

	/**
	* 还款凭证
	*/
	@Column(name= "refundImage")
    @ApiModelProperty(value = "还款凭证")
	private String refundImage;

	/**
	* 还款备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "还款备注")
	private String remark;

	/**
	* 还款备注
	*/
	@Column(name= "affirmRemark")
    @ApiModelProperty(value = "确认还款时备注")
	private String affirmRemark;

	/**
	* 还款状态 1 正常待还、 2 已逾期、3 待核实、4 已驳回、5 已还款
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "还款状态 1 正常待还、 2 已逾期、3 待核实、4 已驳回、5 已还款")
	private Integer status;



	@Transient
	private String projectCode; // 项目编号

	@Transient
	private String sellEnterpriseName; // 买方企业名称

	@Transient
	private BigDecimal financingMoney; // 融资金额

	@Transient
	private Date loanConfirmDate; // 融资放款时间

	@Transient
	private Integer financingTerm; // 总期数

	@Transient
	private BigDecimal allPayment; // 应还本金

	@Transient
	private BigDecimal allInterest; // 应还合计利息

	@Transient
	private BigDecimal allCost; // 应还合计费用

}
