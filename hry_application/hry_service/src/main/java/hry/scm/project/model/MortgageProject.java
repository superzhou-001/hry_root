/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:03:52 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> MortgageProject </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:03:52 
 */
@Data
@ApiModel(value = "现货质押项目信息实体类")
@Table(name="scm_mortgage_project")
public class MortgageProject extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 订单编号
	*/
	@Column(name= "number")
    @ApiModelProperty(value = "订单编号")
	private String number;

	/**
	* 订单类型，1现货质押，2在途质押
	*/
	@Column(name= "projectType")
    @ApiModelProperty(value = "订单类型，1现货质押，2在途质押")
	private String projectType;

	/**
	* 采购企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "采购企业id")
	private Long enterpriseId;

	/**
	* 采购企业名称
	*/
	@Column(name= "enterpriseName")
    @ApiModelProperty(value = "采购企业名称")
	private String enterpriseName;

	/**
	 * 企业统一社会信用代码
	 */
	@Column(name= "creditCode")
	@ApiModelProperty(value = "企业统一社会信用代码")
	private String creditCode;

	/**
	* 资金方id
	*/
	@Column(name= "fundSupportId")
    @ApiModelProperty(value = "资金方id")
	private Long fundSupportId;

	/**
	* 资金方名称
	*/
	@Column(name= "fundSupportName")
    @ApiModelProperty(value = "资金方名称")
	private String fundSupportName;

	/**
	* 资金方案id
	*/
	@Column(name= "fundPlanId")
    @ApiModelProperty(value = "资金方案id")
	private Long fundPlanId;

	/**
	* 资金方案名称
	*/
	@Column(name= "fundPlanName")
    @ApiModelProperty(value = "资金方案名称")
	private String fundPlanName;

	/**
     * 借款金额
     */
    @Column(name= "loanMoney")
    @ApiModelProperty(value = "借款金额")
    private BigDecimal loanMoney;

    /**
     * 已还金额
     */
    @Column(name= "backMoney")
    @ApiModelProperty(value = "已还金额")
    private BigDecimal backMoney;

    /**
     * 未还金额
     */
    @Column(name= "surplusMoney")
    @ApiModelProperty(value = "未还金额")
    private BigDecimal surplusMoney;

	/**
	* 融资备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "融资备注")
	private String remark;

	/**
	* 保证金/放款比例
	*/
	@Column(name= "proportion")
    @ApiModelProperty(value = "保证金/放款比例")
	private BigDecimal proportion;

	/**
	 * 实际质押率
	 */
	@Column(name= "mortRate")
	@ApiModelProperty(value = "实际质押率")
	private BigDecimal mortRate;//放款金额/质押物总价值

	/**
	* 还款期限
	*/
	@Column(name= "period")
    @ApiModelProperty(value = "还款期限")
	private Integer period;

	/**
	* 服务费率
	*/
	@Column(name= "serviceRate")
    @ApiModelProperty(value = "服务费率")
	private BigDecimal serviceRate;

	/**
	* 逾期费率
	*/
	@Column(name= "overRate")
    @ApiModelProperty(value = "逾期费率")
	private BigDecimal overRate;

	/**
	 * 本金模型：等比例释放-sameRateRelease,最后一笔释放-lastRelease
	 */
	@Column(name= "principalModel")
	@ApiModelProperty(value = "本金模型")
	private String principalModel;

	/**
	 * 利息模型:全额利息-fullInterest,本金利息-principalInterest
	 */
	@Column(name= "interestModel")
	@ApiModelProperty(value = "利息模型")
	private String interestModel;

	/**
	* 开始日期
	*/
	@Column(name= "startDate")
    @ApiModelProperty(value = "开始日期")
	private Date startDate;

	/**
	* 到期日期
	*/
	@Column(name= "endDate")
    @ApiModelProperty(value = "到期日期")
	private Date endDate;

	/**
	* 借款金额
	*/
	@Column(name= "mortgageMoney")
    @ApiModelProperty(value = "借款金额")
	private BigDecimal mortgageMoney;

	/**
	* 最大借款金额
	*/
	@Column(name= "maxLoanMoney")
    @ApiModelProperty(value = "最大借款金额")
	private BigDecimal maxLoanMoney;

	/**
	* 申请账户id
	*/
	@Column(name= "applyId")
    @ApiModelProperty(value = "申请账户id")
	private Long applyId;

	/**
	* 业务审批操作人id
	*/
	@Column(name= "businessOpeId")
    @ApiModelProperty(value = "业务审批操作人id")
	private Long businessOpeId;

	/**
	* 业务审批操作人姓名
	*/
	@Column(name= "businessOpeName")
    @ApiModelProperty(value = "业务审批操作人姓名")
	private String businessOpeName;

	/**
	* 质押审批操作人id
	*/
	@Column(name= "mortgageOpeId")
    @ApiModelProperty(value = "质押审批操作人id")
	private Long mortgageOpeId;

	/**
	* 质押审批操作人姓名
	*/
	@Column(name= "mortgageOpeName")
    @ApiModelProperty(value = "质押审批操作人姓名")
	private String mortgageOpeName;

	/**
	* 项目状态，0新申请，10业务审核，20确认质押，30质押审批，50放款审批
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "项目状态，0新申请，10业务审核，20确认质押，30质押审批，50放款审批 ，40客户取消申请,60已结清，70已逾期")
	private Integer status;

	/**
	 * 放款凭证照片路径
	 */
	@Column(name= "loanImageUrl")
	@ApiModelProperty(value = "放款凭证照片路径")
	private String loanImageUrl;

	/**
	 * 实际完结日期
	 */
	@Column(name= "actualEndDate")
	@ApiModelProperty(value = "实际完结日期")
	private Date actualEndDate;

}
