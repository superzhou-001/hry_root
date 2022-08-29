/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 17:01:01
 */
package hry.business.fa.model;

import hry.business.cu.model.CuBank;
import hry.business.cu.model.CuEnterprise;
import hry.business.fa.model.parent.InterestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p> FaFactoringFlow </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 17:01:01
 */
@Data
@ApiModel(value = "保理信息实体类")
@Table(name="fa_factoring_flow")
public class FaFactoringFlow extends InterestModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	 * 授信ID
	 */
	@Column(name= "facilityFlowId")
	@ApiModelProperty(value = "授信Id")
	private Long facilityFlowId;

	/**
	 * 是否占用授信 1占用 2不占用
	 */
	@Column(name= "useFacility")
	@ApiModelProperty(value = "是否占用授信 1占用 2不占用")
	private Integer useFacility;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 项目编码
	*/
	@Column(name= "projectCode")
    @ApiModelProperty(value = "项目编码")
	private String projectCode;

	/**
	 * 项目名称
	 */
	@Column(name= "projectName")
	@ApiModelProperty(value = "项目名称")
	private String projectName;

	/**
	* 融资申请id
	*/
	@Column(name= "financingId")
    @ApiModelProperty(value = "融资申请id")
	private Long financingId;

	/**
	* 融资申请编码
	*/
	@Column(name= "financingCode")
    @ApiModelProperty(value = "融资申请编码")
	private String financingCode;

	/**
	* 融资申请时间
	*/
	@Column(name= "financingDate")
    @ApiModelProperty(value = "融资申请时间")
	private Date financingDate;

	/**
	* 产品Id
	*/
	@Column(name= "productId")
    @ApiModelProperty(value = "产品Id")
	private Long productId;

	/**
	* 项目经理id
	*/
	@Column(name= "projectManagerId")
    @ApiModelProperty(value = "项目经理id")
	private Long projectManagerId;

	/**
	* 项目经理名字
	*/
	@Column(name= "projectManagerName")
    @ApiModelProperty(value = "项目经理名字")
	private String projectManagerName;

	/**
	* 卖方企业
	*/
	@Column(name= "sellEnterpriseId")
    @ApiModelProperty(value = "卖方企业")
	private Long sellEnterpriseId;

	/**
	* 买方企业
	*/
	@Column(name= "buyEnterpriseId")
    @ApiModelProperty(value = "买方企业")
	private Long buyEnterpriseId;


	/**
	* 卖方企业名称
	*/
	@Column(name= "sellEnterpriseName")
    @ApiModelProperty(value = "卖方企业名称")
	private String sellEnterpriseName;

	/**
	* 买方企业名称
	*/
	@Column(name= "buyEnterpriseName")
    @ApiModelProperty(value = "买方企业名称")
	private String buyEnterpriseName;


	/**
	* 项目阶段 1审批流程中 2审批流程通过 3放款审批
	*/
	@Column(name= "projectStageStatus")
    @ApiModelProperty(value = "项目阶段 1审批流程中 2审批流程通过 3放款审批 ")
	private Integer projectStageStatus;

	/**
	* 项目阶段状态变更日期
	*/
	@Column(name= "projectStageStatusDate")
    @ApiModelProperty(value = "项目阶段状态变更日期")
	private Date projectStageStatusDate;

	/**
	* 放款审核状态 1无状态 2待审核 3已审核 4已驳回
	*/
	@Column(name= "loanExamineStatus")
    @ApiModelProperty(value = "放款审核状态 1无状态 2待审核 3已审核 4已驳回")
	private Integer loanExamineStatus;

	/**
	* 放款审核状态变更日期
	*/
	@Column(name= "loanExamineStatusDate")
    @ApiModelProperty(value = "放款审核状态变更日期")
	private Date loanExamineStatusDate;

	/**
	* 放款确认状态 1无状态 2待确认 3已确认
	*/
	@Column(name= "loanConfirmStatus")
    @ApiModelProperty(value = "放款确认状态 1无状态 2待确认 3已确认 ")
	private Integer loanConfirmStatus;

	/**
	* 放款日期
	*/
	@Column(name= "loanConfirmDate")
    @ApiModelProperty(value = "放款日期")
	private Date loanConfirmDate;

	/**
	* 银行Id
	*/
	@Column(name= "bankId")
    @ApiModelProperty(value = "银行Id")
	private Long bankId;

	/**
	 * 放款审核状态备注
	 */
	@Column(name= "loanExamineStatusRemark")
	@ApiModelProperty(value = "放款审核状态备注")
	private String loanExamineStatusRemark;


	/**
	 * 项目阶段备注
	 */
	@Column(name= "projectStageStatusRemark")
	@ApiModelProperty(value = "项目阶段备注")
	private String projectStageStatusRemark;


	/**
	 * 放款确认状态备注
	 */
	@Column(name= "loanConfirmStatusRemark")
	@ApiModelProperty(value = "放款确认状态备注")
	private String loanConfirmStatusRemark;

	/**
	 * 融资金额
	 */
	@Column(name= "financingMoney")
	@ApiModelProperty(value = "融资金额")
	private BigDecimal financingMoney;

	/**
	 * 放款金额金额
	 */
	@Column(name= "loanMoney")
	@ApiModelProperty(value = "放款金额金额")
	private BigDecimal loanMoney;

	/**
	 * 融资期限(月)
	 */
	@Column(name= "financingTerm")
	@ApiModelProperty(value = "融资期限(月)")
	private Integer financingTerm;

	/**
	 * 申请放款日期
	 */
	@Column(name= "applyLoanDate")
	@ApiModelProperty(value = "申请放款日期")
	private Date applyLoanDate;

	/**
	 * 到期日期
	 */
	@Column(name= "expireDate")
	@ApiModelProperty(value = "到期日期")
	private Date expireDate;

	/**
	 * 项目状态 1办理中，2办理完成  3结清  4已终止  5  已违约
	 */
	@Column(name= "projectStatus")
	@ApiModelProperty(value = "项目状态 1办理中，2办理完成  3结清  4已终止  5  已违约")
	private Integer projectStatus;

	/**
	 * 项目状态修改日期
	 */
	@Column(name= "projectStatusDate")
	@ApiModelProperty(value = "项目状态修改日期")
	private Date projectStatusDate;



	@Transient
	@ApiModelProperty(value = "银行")
	private CuBank cuBank;

	@ApiModelProperty(value = "费用明细")
	@Transient
	private List<FaFactoringCost> costList;

	@ApiModelProperty(value = "费率明细")
	@Transient
	private List<FaFactoringRate> rateList;

	@ApiModelProperty(value = "票据信息")
	@Transient
	private List<FaBill> faBillList;

	@ApiModelProperty(value = "合同签署")
	@Transient
	private List<FaFactoringContract> contractList;

	@ApiModelProperty(value = "卖方企业")
	@Transient
	private CuEnterprise sellEnterprise;

	@ApiModelProperty(value = "买方企业")
	@Transient
	private CuEnterprise buyEnterprise;


	@ApiModelProperty(value = "产品信息")
	@Transient
	private FaProduct faProduct;



}
