/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-20 15:54:35 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseBasicDetails </p>
 *
 * @author: yaoz
 * @Date: 2020-05-20 15:54:35 
 */
@Data
@ApiModel(value = "企业基本信息实体类")
@Table(name="qcc_enterprise_basic_details")
public class QccEnterpriseBasicDetails extends BaseModel {

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
	* 内部KeyNo
	*/
	@Column(name= "KeyNo")
    @ApiModelProperty(value = "内部KeyNo")
	private String KeyNo;

	/**
	* 公司名称
	*/
	@Column(name= "Name")
    @ApiModelProperty(value = "公司名称")
	private String Name;

	/**
	* 注册号
	*/
	@Column(name= "No")
    @ApiModelProperty(value = "注册号")
	private String No;

	/**
	* 登记机关
	*/
	@Column(name= "BelongOrg")
    @ApiModelProperty(value = "登记机关")
	private String BelongOrg;

	/**
	* 法人名
	*/
	@Column(name= "OperName")
    @ApiModelProperty(value = "法人名")
	private String OperName;

	/**
	* 成立日期
	*/
	@Column(name= "StartDate")
    @ApiModelProperty(value = "成立日期")
	private String StartDate;

	/**
	* 吊销日期
	*/
	@Column(name= "EndDate")
    @ApiModelProperty(value = "吊销日期")
	private String EndDate;

	/**
	* 企业状态
	*/
	@Column(name= "Status")
    @ApiModelProperty(value = "企业状态")
	private String Status;

	/**
	* 省份
	*/
	@Column(name= "Province")
    @ApiModelProperty(value = "省份")
	private String Province;

	/**
	* 更新日期
	*/
	@Column(name= "UpdatedDate")
    @ApiModelProperty(value = "更新日期")
	private String UpdatedDate;

	/**
	* 社会统一信用代码
	*/
	@Column(name= "CreditCode")
    @ApiModelProperty(value = "社会统一信用代码")
	private String CreditCode;

	/**
	* 注册资本
	*/
	@Column(name= "RegistCapi")
    @ApiModelProperty(value = "注册资本")
	private String RegistCapi;

	/**
	* 企业类型
	*/
	@Column(name= "EconKind")
    @ApiModelProperty(value = "企业类型")
	private String EconKind;

	/**
	* 地址
	*/
	@Column(name= "Address")
    @ApiModelProperty(value = "地址")
	private String Address;

	/**
	* 经营范围
	*/
	@Column(name= "Scope")
    @ApiModelProperty(value = "经营范围")
	private String Scope;

	/**
	* 营业开始日期
	*/
	@Column(name= "TermStart")
    @ApiModelProperty(value = "营业开始日期")
	private String TermStart;

	/**
	* 营业结束日期
	*/
	@Column(name= "TeamEnd")
    @ApiModelProperty(value = "营业结束日期")
	private String TeamEnd;

	/**
	* 发照日期
	*/
	@Column(name= "CheckDate")
    @ApiModelProperty(value = "发照日期")
	private String CheckDate;

	/**
	* 组织机构代码
	*/
	@Column(name= "OrgNo")
    @ApiModelProperty(value = "组织机构代码")
	private String OrgNo;

	/**
	* 是否IPO上市(0为未上市，1为上市)
	*/
	@Column(name= "IsOnStock")
    @ApiModelProperty(value = "是否IPO上市(0为未上市，1为上市)")
	private String IsOnStock;

	/**
	* 上市公司代码
	*/
	@Column(name= "StockNumber")
    @ApiModelProperty(value = "上市公司代码")
	private String StockNumber;

	/**
	* 上市类型
	*/
	@Column(name= "StockType")
    @ApiModelProperty(value = "上市类型")
	private String StockType;

	/**
	* 企业类型，0-公司，1-社会组织 ，3-香港公司，4政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他
	*/
	@Column(name= "EntType")
    @ApiModelProperty(value = "企业类型，0-公司，1-社会组织 ，3-香港公司，4政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他")
	private String EntType;

	/**
	* 实缴资本
	*/
	@Column(name= "RecCap")
    @ApiModelProperty(value = "实缴资本")
	private String RecCap;

	/**
	* 曾用名
	*/
	@Column(name= "OriginalName")
    @ApiModelProperty(value = "曾用名")
	private String OriginalName;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

	/**
	* uuid
	*/
	@Column(name= "uuid")
    @ApiModelProperty(value = "uuid")
	private String uuid;

}
