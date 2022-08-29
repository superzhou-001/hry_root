/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 17:13:23 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseZhixing </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 17:13:23 
 */
@Data
@ApiModel(value = "企查查企业被执行人信息实体类")
@Table(name="qcc_enterprise_zhixing")
public class QccEnterpriseZhixing extends BaseModel {

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
	* 官网主键（内部保留字段，一般为空）
	*/
	@Column(name= "Sourceid")
    @ApiModelProperty(value = "官网主键（内部保留字段，一般为空）")
	private String Sourceid;

	/**
	* 唯一编号（内部保留字段，一般为空）
	*/
	@Column(name= "Uniqueno")
    @ApiModelProperty(value = "唯一编号（内部保留字段，一般为空）")
	private String Uniqueno;

	/**
	* 被执行人姓名/名称
	*/
	@Column(name= "Name")
    @ApiModelProperty(value = "被执行人姓名/名称")
	private String Name;

	/**
	* 立案时间
	*/
	@Column(name= "Liandate")
    @ApiModelProperty(value = "立案时间")
	private String Liandate;

	/**
	* 案号
	*/
	@Column(name= "Anno")
    @ApiModelProperty(value = "案号")
	private String Anno;

	/**
	* 身份证号码/组织机构代码
	*/
	@Column(name= "Orgno")
    @ApiModelProperty(value = "身份证号码/组织机构代码")
	private String Orgno;

	/**
	* 法定代表人或者负责人姓名
	*/
	@Column(name= "Ownername")
    @ApiModelProperty(value = "法定代表人或者负责人姓名")
	private String Ownername;

	/**
	* 执行法院
	*/
	@Column(name= "Executegov")
    @ApiModelProperty(value = "执行法院")
	private String Executegov;

	/**
	* 所在省份缩写
	*/
	@Column(name= "Province")
    @ApiModelProperty(value = "所在省份缩写")
	private String Province;

	/**
	* 做出执行依据单位
	*/
	@Column(name= "Executeunite")
    @ApiModelProperty(value = "做出执行依据单位")
	private String Executeunite;

	/**
	* 生效法律文书确定的义务
	*/
	@Column(name= "Yiwu")
    @ApiModelProperty(value = "生效法律文书确定的义务")
	private String Yiwu;

	/**
	* 被执行人的履行情况
	*/
	@Column(name= "Executestatus")
    @ApiModelProperty(value = "被执行人的履行情况")
	private String Executestatus;

	/**
	* 失信被执行人行为具体情形
	*/
	@Column(name= "Actionremark")
    @ApiModelProperty(value = "失信被执行人行为具体情形")
	private String Actionremark;

	/**
	* 发布时间
	*/
	@Column(name= "Publicdate")
    @ApiModelProperty(value = "发布时间")
	private String Publicdate;

	/**
	* 年龄
	*/
	@Column(name= "Age")
    @ApiModelProperty(value = "年龄")
	private String Age;

	/**
	* 性别
	*/
	@Column(name= "Sexy")
    @ApiModelProperty(value = "性别")
	private String Sexy;

	/**
	* 数据更新时间（内部保留字段，现已不再更新时间）
	*/
	@Column(name= "Updatedate")
    @ApiModelProperty(value = "数据更新时间（内部保留字段，现已不再更新时间）")
	private String Updatedate;

	/**
	* 执行依据文号
	*/
	@Column(name= "Executeno")
    @ApiModelProperty(value = "执行依据文号")
	private String Executeno;

	/**
	* 已履行
	*/
	@Column(name= "Performedpart")
    @ApiModelProperty(value = "已履行")
	private String Performedpart;

	/**
	* 未履行
	*/
	@Column(name= "Unperformpart")
    @ApiModelProperty(value = "未履行")
	private String Unperformpart;

	/**
	* 组织类型（1：自然人，2：企业，3：社会组织，空白：无法判定）
	*/
	@Column(name= "OrgType")
    @ApiModelProperty(value = "组织类型（1：自然人，2：企业，3：社会组织，空白：无法判定）")
	private String OrgType;

	/**
	* 组织类型名称
	*/
	@Column(name= "OrgTypeName")
    @ApiModelProperty(value = "组织类型名称")
	private String OrgTypeName;

}
