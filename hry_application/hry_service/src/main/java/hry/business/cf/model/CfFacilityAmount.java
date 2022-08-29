/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-19 09:55:05
 */
package hry.business.cf.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * <p> CfFacilityAmount </p>
 *
 * @author: yaoz
 * @Date: 2020-06-19 09:55:05
 */
@Data
@ApiModel(value = "授信额度信息实体类")
@Table(name="cf_facility_amount")
public class CfFacilityAmount extends BaseModel {

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
	* 总额度
	*/
	@Column(name= "totalAmount")
    @ApiModelProperty(value = "总额度")
	private BigDecimal totalAmount;

	/**
	* 剩余额度
	*/
	@Column(name= "surplusAmount")
    @ApiModelProperty(value = "剩余额度")
	private BigDecimal surplusAmount;

	/**
	* 企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业id")
	private Long enterpriseId;
	/**
	* 额度类型 1.卖方 2.买方
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "额度类型 1.卖方 2.买方")
	private Integer type;

}
