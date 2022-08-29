/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 17:18:52 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseInvestment </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 17:18:52 
 */
@Data
@ApiModel(value = "企业对外投资信息表实体类")
@Table(name="qcc_enterprise_investment")
public class QccEnterpriseInvestment extends BaseModel {

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
	* 社会统一信用代码
	*/
	@Column(name= "CreditCode")
    @ApiModelProperty(value = "社会统一信用代码")
	private String CreditCode;

	/**
	* 企业类型
	*/
	@Column(name= "EconKind")
    @ApiModelProperty(value = "企业类型")
	private String EconKind;

	/**
	* 出资比列
	*/
	@Column(name= "FundedRatio")
    @ApiModelProperty(value = "出资比列")
	private String FundedRatio;

	/**
	* 公司Logo
	*/
	@Column(name= "ImageUrl")
    @ApiModelProperty(value = "公司Logo")
	private String ImageUrl;

	/**
	* KeyNo
	*/
	@Column(name= "KeyNo")
    @ApiModelProperty(value = "KeyNo")
	private String KeyNo;

	/**
	* 企业名称
	*/
	@Column(name= "Name")
    @ApiModelProperty(value = "企业名称")
	private String Name;

	/**
	* 注册号
	*/
	@Column(name= "No")
    @ApiModelProperty(value = "注册号")
	private String No;

	/**
	* 法人名称
	*/
	@Column(name= "OperName")
    @ApiModelProperty(value = "法人名称")
	private String OperName;

	/**
	* 注册资本
	*/
	@Column(name= "RegistCapi")
    @ApiModelProperty(value = "注册资本")
	private String RegistCapi;

	/**
	* 成立日期
	*/
	@Column(name= "StartDate")
    @ApiModelProperty(value = "成立日期")
	private String StartDate;

	/**
	* 状态
	*/
	@Column(name= "Status")
    @ApiModelProperty(value = "状态")
	private String Status;

}
