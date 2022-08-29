/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-02 14:52:53 
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
 * <p> CfFacilityGuarantee </p>
 *
 * @author: yaoz
 * @Date: 2020-07-02 14:52:53 
 */
@Data
@ApiModel(value = "抵质押物担保人表实体类")
@Table(name="cf_facility_guarantee")
public class CfFacilityGuarantee extends BaseModel {

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
	* 项目id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "项目id")
	private Long projectId;

	/**
	* 客户类型 1.个人 2.企业
	*/
	@Column(name= "customerType")
    @ApiModelProperty(value = "客户类型 1.个人 2.企业")
	private Integer customerType;

	/**
	* 主体ID
	*/
	@Column(name= "customerSubjectId")
    @ApiModelProperty(value = "主体ID")
	private Long customerSubjectId;

	/**
	* 担保类型 ：1.抵押 2.质押
	*/
	@Column(name= "guaranteeType")
    @ApiModelProperty(value = "担保类型 ：1.抵押 2.质押")
	private Integer guaranteeType;

	/**
	* 与债务人关系
	*/
	@Column(name= "relationshipWithDebtor")
    @ApiModelProperty(value = "与债务人关系")
	private String relationshipWithDebtor;

	/**
	* 评估价值
	*/
	@Column(name= "evaluationValue")
    @ApiModelProperty(value = "评估价值")
	private BigDecimal evaluationValue;

	/**
	* 公允价值
	*/
	@Column(name= "fairValue")
    @ApiModelProperty(value = "公允价值")
	private BigDecimal fairValue;

	/**
	* 抵质押率
	*/
	@Column(name= "mortgageRate")
    @ApiModelProperty(value = "抵质押率")
	private BigDecimal mortgageRate;

	/**
	* 抵押价值
	*/
	@Column(name= "mortgageValue")
    @ApiModelProperty(value = "抵押价值")
	private BigDecimal mortgageValue;

	/**
	* 获取时间
	*/
	@Column(name= "acquisitionTime")
    @ApiModelProperty(value = "获取时间")
	private Date acquisitionTime;

	/**
	* 公允价值获取方式
	*/
	@Column(name= "fairAcquisitionMethod")
    @ApiModelProperty(value = "公允价值获取方式")
	private String fairAcquisitionMethod;

	/**
	* 对外负债
	*/
	@Column(name= "externalLiabilities")
    @ApiModelProperty(value = "对外负债")
	private BigDecimal externalLiabilities;

	/**
	* 反担保物
	*/
	@Column(name= "counterCollateral")
    @ApiModelProperty(value = "反担保物")
	private String counterCollateral;

	/**
	* 担保方式 ：1.连带责任 2.一般责任
	*/
	@Column(name= "guaranteeMode")
    @ApiModelProperty(value = "担保方式 ：1.连带责任 2.一般责任")
	private Integer guaranteeMode;


	@Transient
	@ApiModelProperty(value = "保证人")
	private String guarantorName;

}
