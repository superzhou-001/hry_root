/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 11:21:29 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterprisePartner </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 11:21:29 
 */
@Data
@ApiModel(value = "企业股东信息表实体类")
@Table(name="qcc_enterprise_partner")
public class QccEnterprisePartner extends BaseModel {

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

	/**
	* 股东KeyNo
	*/
	@Column(name= "KeyNo")
    @ApiModelProperty(value = "股东KeyNo")
	private String KeyNo;

	/**
	* 投资人姓名
	*/
	@Column(name= "StockName")
    @ApiModelProperty(value = "投资人姓名")
	private String StockName;

	/**
	* 投资人类型
	*/
	@Column(name= "StockType")
    @ApiModelProperty(value = "投资人类型")
	private String StockType;

	/**
	* 出资比例
	*/
	@Column(name= "StockPercent")
    @ApiModelProperty(value = "出资比例")
	private String StockPercent;

	/**
	* 认缴出资时间
	*/
	@Column(name= "ShoudDate")
    @ApiModelProperty(value = "认缴出资时间")
	private String ShoudDate;

	/**
	* 认缴出资额
	*/
	@Column(name= "ShouldCapi")
    @ApiModelProperty(value = "认缴出资额")
	private String ShouldCapi;

}
