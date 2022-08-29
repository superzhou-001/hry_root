/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 18:55:10 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> QccEnterpriseException </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 18:55:10 
 */
@Data
@ApiModel(value = "企业异常信息表实体类")
@Table(name="qcc_enterprise_exception")
public class QccEnterpriseException extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业id")
	private String enterpriseId;

	/**
	* 列入经营异常名录原因
	*/
	@Column(name= "AddReason")
    @ApiModelProperty(value = "列入经营异常名录原因")
	private String AddReason;

	/**
	* 列入日期
	*/
	@Column(name= "AddDate")
    @ApiModelProperty(value = "列入日期")
	private Date AddDate;

	/**
	* 移出经营异常名录原因
	*/
	@Column(name= "RomoveReason")
    @ApiModelProperty(value = "移出经营异常名录原因")
	private String RomoveReason;

	/**
	* 移出日期
	*/
	@Column(name= "RemoveDate")
    @ApiModelProperty(value = "移出日期")
	private Date RemoveDate;

	/**
	* 作出决定机关
	*/
	@Column(name= "DecisionOffice")
    @ApiModelProperty(value = "作出决定机关")
	private String DecisionOffice;

	/**
	* 移除决定机关
	*/
	@Column(name= "RemoveDecisionOffice")
    @ApiModelProperty(value = "移除决定机关")
	private String RemoveDecisionOffice;

	/**
	* 
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "")
	private String remark;

	/**
	* 
	*/
	@Column(name= "uuid")
    @ApiModelProperty(value = "")
	private String uuid;

}
