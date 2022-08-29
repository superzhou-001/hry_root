/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 16:17:44
 */
package hry.business.cf.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> CfFacilityFlow </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 16:17:44
 */
@Data
@ApiModel(value = "授信信息实体类")
@Table(name="cf_facility_flow")
public class CfFacilityFlow extends BaseModel {




	/**
	*
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 项目名称
	*/
	@Column(name= "projectName")
    @ApiModelProperty(value = "项目名称")
	private String projectName;

	/**
	* 授信编码
	*/
	@Column(name= "facilityCode")
    @ApiModelProperty(value = "授信编码")
	private String facilityCode;

	/**
	* 卖方企业
	*/
	@Column(name= "sellEnterpriseId")
    @ApiModelProperty(value = "卖方企业")
	private Long sellEnterpriseId;

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
	* 授信用途
	*/
	@Column(name= "facilityPurpose")
    @ApiModelProperty(value = "授信用途")
	private String facilityPurpose;

	/**
	* 业务方向 1正向保理 2反向保理
	*/
	@Column(name= "businessDirection")
    @ApiModelProperty(value = "业务方向 1正向保理 2反向保理")
	private Integer businessDirection;

	/**
	 * 核心企业
	 * 永远是买方企业
	 *
	 */
	@Column(name= "coreEnterpriseId")
    @ApiModelProperty(value = "核心企业")
	private Long coreEnterpriseId;

	/**
	* 核心企业信用代码
	*/
	@Column(name= "coreEnterpriseCreditCode")
    @ApiModelProperty(value = "核心企业信用代码")
	private String coreEnterpriseCreditCode;

	/**
	* 授信金额
	*/
	@Column(name= "facilityAmount")
    @ApiModelProperty(value = "授信金额")
	private BigDecimal facilityAmount;


	@Column(name= "surplusAmount")
	@ApiModelProperty(value = "授信剩余金额")
	private BigDecimal surplusAmount;

	/**
	* 周期类型 1.年 2.季 3.月
	*/
	@Column(name= "cycleType")
    @ApiModelProperty(value = "周期类型 1.年 2.季 3.月")
	private Integer cycleType;

	/**
	* 担保日期开始
	*/
	@Column(name= "guaranteeDateStart")
    @ApiModelProperty(value = "担保日期开始")
	private Date guaranteeDateStart;

	/**
	* 担保日期结束
	*/
	@Column(name= "guaranteeDateEnd")
    @ApiModelProperty(value = "担保日期结束")
	private Date guaranteeDateEnd;

	/**
	* 期限
	*/
	@Column(name= "term")
    @ApiModelProperty(value = "期限")
	private Integer term;

	/**
	* 额度类型 1一次性 2循环
	*/
	@Column(name= "quotaType")
    @ApiModelProperty(value = "额度类型 1一次性 2循环")
	private Integer quotaType;

	/**
	* 审批进度 1进行中 2已完成
	*/
	@Column(name= "approvalProgress")
    @ApiModelProperty(value = "审批进度 1进行中 2已完成")
	private Integer approvalProgress;

	/**
	* 是否有效 1是 2否
	*/
	@Column(name= "isEffective")
    @ApiModelProperty(value = "是否有效 1是 2否")
	private Integer isEffective;

	/**
	* 授信类型 1.卖方 2.买方
	*/
	@Column(name= "facilityType")
    @ApiModelProperty(value = "授信类型 1.卖方 2.买方")
	private Integer facilityType;


	/**
	 * 卖方企业
	 */
	@Transient
	@ApiModelProperty(value = "卖方企业名称")
	private String sellEnterpriseName;

	/**
	 * 核心企业
	 */
	@Transient
	@ApiModelProperty(value = "核心企业名称")
	private String coreEnterpriseName;

	/**
	 * 总额度
	 */
	@Transient
	@ApiModelProperty(value = "总额度")
	private String totalAmount;


}
