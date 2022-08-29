/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 09:52:22 
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
 * <p> FaFactoringCost </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 09:52:22 
 */
@Data
@ApiModel(value = "保理流程费用表实体类")
@Table(name="fa_factoring_cost")
public class FaFactoringCost extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 项目id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "项目id")
	private Long projectId;

	/**
	* 费用名称
	*/
	@Column(name= "costname")
    @ApiModelProperty(value = "费用名称")
	private String costname;

	/**
	* 类型 1 一次性 2 计算模型
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "类型 1 一次性 2 计算模型")
	private Integer type;

	/**
	* 计算公式id
	*/
	@Column(name= "nlmsid")
    @ApiModelProperty(value = "计算公式id")
	private Long nlmsid;

	/**
	* 计算公式
	*/
	@Column(name= "nlms")
    @ApiModelProperty(value = "计算公式")
	private String nlms;

	/**
	* 费用金额
	*/
	@Column(name= "costmoney")
    @ApiModelProperty(value = "费用金额")
	private BigDecimal costmoney;

	/**
	* 收费类型 1 收取 2 付款抵扣
	*/
	@Column(name= "costtype")
    @ApiModelProperty(value = "收费类型 1 收取 2 付款抵扣")
	private Integer costtype;

	/**
	* 费用状态 1流程中 2待核实 3已核实 4已否决
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "费用状态 1流程中 2待核实 3已核实 4已否决")
	private Integer status;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

	@Column(name= "receivableDate")
	@ApiModelProperty(value = "应收日期")
	private Date receivableDate;

	@Column(name= "paidDate")
	@ApiModelProperty(value = "实收日期")
	private Date paidDate;

	@Column(name= "verifyDate")
	@ApiModelProperty(value = "verifyDate")
	private Date verifyDate;

	/**
	 * 实收金额
	 */
	@Column(name= "paidMoney")
	@ApiModelProperty(value = "实收金额")
	private BigDecimal paidMoney;

	@Transient
	@ApiModelProperty(value = "项目编码")
	private String projectCode;

	@Transient
	@ApiModelProperty(value = "融资金额")
	private String financingMoney;

	@Transient
	@ApiModelProperty(value = "产品类型")
	private String typeName;

	@Transient
	@ApiModelProperty(value = "买方")
	private String sellName;

	@Transient
	@ApiModelProperty(value = "买方")
	private String buyName;

}
