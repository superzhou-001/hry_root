/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:18:39 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> CuEnterprise </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:18:39 
 */
@Data
@ApiModel(value = "企业信息实体类")
@Table(name="cu_enterprise")
public class CuEnterprise extends BaseModel {


	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@ApiModelProperty(value = "")
	private Long id;

	/**
	 * 用户ID
	 */
	@Column(name= "customerId")
	@ApiModelProperty(value = "用户ID")
	private Long customerId;

	/**
	 * 状态 1.待审核 2.已通过 3.未通过
	 */
	@Column(name= "status")
	@ApiModelProperty(value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单")
	private Integer status;
	/**
	 * 经营状态
	 */
	@Column(name= "managementStatus")
	@ApiModelProperty(value = "经营状态 1.开业 2. 3.")
	private Integer managementStatus;

	/**
	 * 来源：1.前端注册  2.后台录入  3.意向转入
	 */
	@Column(name= "source")
	@ApiModelProperty(value = "来源：1.前端注册  2.后台录入  3.意向转入")
	private Integer source;

	/**
	 * 企业类型 1.卖方 2.买方
	 */
	@Column(name= "enterpriseType")
	@ApiModelProperty(value = "企业类型 1.卖方 2.买方")
	private Integer enterpriseType;

	/**
	 * 职工人数
	 */
	@Column(name= "workersNumber")
	@ApiModelProperty(value = "职工人数")
	private Integer workersNumber;

	/**
	 * 驳回原因
	 */
	@Column(name= "rejectRemark")
	@ApiModelProperty(value = "驳回原因")
	private String rejectRemark;

	/**
	 * 客户类型转换备注
	 */
	@Column(name= "typeRemark")
	@ApiModelProperty(value = "客户类型转换备注")
	private String typeRemark;

	/**
	 * 内部KeyNo
	 */
	@Column(name= "keyNo")
	@ApiModelProperty(value = "内部KeyNo")
	private String keyNo;

	/**
	 * 公司名称
	 */
	@Column(name= "name")
	@ApiModelProperty(value = "公司名称")
	private String name;

	/**
	 * 注册号
	 */
	@Column(name= "no")
	@ApiModelProperty(value = "注册号")
	private String no;

	/**
	 * 登记机关
	 */
	@Column(name= "belongOrg")
	@ApiModelProperty(value = "登记机关")
	private String belongOrg;

	/**
	 * 法人名
	 */
	@Column(name= "operName")
	@ApiModelProperty(value = "法人名")
	private String operName;

	/**
	 * 成立日期
	 */
	@Column(name= "startDate")
	@ApiModelProperty(value = "成立日期")
	private Date startDate;

	/**
	 * 吊销日期
	 */
	@Column(name= "endDate")
	@ApiModelProperty(value = "吊销日期")
	private Date endDate;

	/**
	 * 省份
	 */
	@Column(name= "province")
	@ApiModelProperty(value = "省份")
	private String province;

	/**
	 * 更新日期
	 */
	@Column(name= "updatedDate")
	@ApiModelProperty(value = "更新日期")
	private Date updatedDate;

	/**
	 * 社会统一信用代码
	 */
	@Column(name= "creditCode")
	@ApiModelProperty(value = "社会统一信用代码")
	private String creditCode;

	/**
	 * 注册资本
	 */
	@Column(name= "registCapi")
	@ApiModelProperty(value = "注册资本")
	private String registCapi;

	/**
	 * 企业类型
	 */
	@Column(name= "econKind")
	@ApiModelProperty(value = "企业类型")
	private String econKind;

	/**
	 * 地址
	 */
	@Column(name= "address")
	@ApiModelProperty(value = "地址")
	private String address;

	/**
	 * 经营范围
	 */
	@Column(name= "scope")
	@ApiModelProperty(value = "经营范围")
	private String scope;

	/**
	 * 营业开始日期
	 */
	@Column(name= "termStart")
	@ApiModelProperty(value = "营业开始日期")
	private Date termStart;

	/**
	 * 营业结束日期
	 */
	@Column(name= "teamEnd")
	@ApiModelProperty(value = "营业结束日期")
	private Date teamEnd;

	/**
	 * 发照日期
	 */
	@Column(name= "checkDate")
	@ApiModelProperty(value = "发照日期")
	private Date checkDate;

	/**
	 * 组织机构代码
	 */
	@Column(name= "orgNo")
	@ApiModelProperty(value = "组织机构代码")
	private String orgNo;

	/**
	 * 是否IPO上市(0为未上市，1为上市)
	 */
	@Column(name= "isOnStock")
	@ApiModelProperty(value = "是否IPO上市(0为未上市，1为上市)")
	private Integer isOnStock;

	/**
	 * 上市公司代码
	 */
	@Column(name= "stockNumber")
	@ApiModelProperty(value = "上市公司代码")
	private String stockNumber;

	/**
	 * 上市类型
	 */
	@Column(name= "stockType")
	@ApiModelProperty(value = "上市类型")
	private String stockType;

	/**
	 * 企业类型，0-公司，1-社会组织 ，3-香港公司，4政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他
	 */
	@Column(name= "entType")
	@ApiModelProperty(value = "企业类型，0-公司，1-社会组织 ，3-香港公司，4政府机构，5-台湾公司，6-基金会，7-医院，8-海外公司，9-律师事务所，10-学校 ，-1-其他")
	private Integer entType;

	/**
	 * 实缴资本
	 */
	@Column(name= "recCap")
	@ApiModelProperty(value = "实缴资本")
	private String recCap;

	/**
	 * 曾用名
	 */
	@Column(name= "originalName")
	@ApiModelProperty(value = "曾用名")
	private String originalName;

	/**
	 *
	 */
	@Column(name= "remark")
	@ApiModelProperty(value = "")
	private String remark;

	/**
	 * 创建人id
	 */
	@Column(name= "userId")
	@ApiModelProperty(value = "创建人id")
	private Long userId;


	/**
	 * 创建人
	 */
	@Transient
	@ApiModelProperty(value = "创建人")
	private String userName;


	/**
	 * 用户名称
	 */
	@Transient
	@ApiModelProperty(value = "用户名称")
	private String customerName;

	/**
	 * 注册手机号
	 */
	@Transient
	@ApiModelProperty(value = "注册手机号")
	private String customerMobile;

	/**
	 * 联系人信息
	 */
	@Transient
	@ApiModelProperty(value = "联系人信息")
	private Object contacts;
	/**
	 * 法人信息
	 */
	@Transient
	@ApiModelProperty(value = "法人信息")
	private Object legalPerson;
	/**
	 * 银行信息
	 */
	@Transient
	@ApiModelProperty(value = "银行信息")
	private Object bank;

}
