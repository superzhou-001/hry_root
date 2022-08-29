/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 15:55:35
 */
package hry.business.cf.model;

import hry.bean.BaseModel;
import hry.business.cu.model.CuCar;
import hry.business.cu.model.CuHouse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> CfFacilityMortgage </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 15:55:35
 */
@Data
@ApiModel(value = "抵质押物表实体类")
@Table(name="cf_facility_mortgage")
public class CfFacilityMortgage extends BaseModel {

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
	* 抵质押物主体ID
	*/
	@Column(name= "mortgageSubjectId")
    @ApiModelProperty(value = "抵质押物主体ID")
	private Long mortgageSubjectId;

	/**
	* 抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款
	*/
	@Column(name= "mortgageType")
    @ApiModelProperty(value = "抵质押物类型 1.车辆 2.住宅 3. 股权 4.基金 5.公寓 6.应收账款")
	private Integer mortgageType;

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
	* 所有权主体ID
	*/
	@Column(name= "ownershipSubjectId")
    @ApiModelProperty(value = "所有权主体ID")
	private Long ownershipSubjectId;

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

	@Transient
	@ApiModelProperty(value = "车辆信息")
	private CuCar cuCar;

	@Transient
	@ApiModelProperty(value = "房产信息")
	private CuHouse cuHouse;

	@Transient
	@ApiModelProperty(value = "所有权人")
	private String ownershipName;

}
