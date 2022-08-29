/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:36:21 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuIntention </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:36:21 
 */
@Data
@ApiModel(value = "意向客户信息实体类")
@Table(name="cu_intention")
public class CuIntention extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 创建人id
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "创建人id")
	private Long userId;

	/**
	* 意向来源
	*/
	@Column(name= "source")
    @ApiModelProperty(value = "意向来源")
	private Integer source;

	/**
	* 客户类型 1.个人 2.企业
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "客户类型 1.个人 2.企业")
	private Integer type;

	/**
	* 客户状态 1.未跟进 2.跟进中 3.排除 4.拉黑 5.转正式
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "客户状态 1.未跟进 2.跟进中 3.排除 4.拉黑 5.转正式")
	private Integer status;

	/**
	* 统一社会信用代码
	*/
	@Column(name= "creditCode")
    @ApiModelProperty(value = "统一社会信用代码")
	private String creditCode;

	/**
	* 主营业务
	*/
	@Column(name= "mainBusiness")
    @ApiModelProperty(value = "主营业务")
	private String mainBusiness;

	/**
	* 行业类别
	*/
	@Column(name= "industryCategory")
    @ApiModelProperty(value = "行业类别")
	private Integer industryCategory;

	/**
	* 企业联系人
	*/
	@Column(name= "enterpriseContacts")
    @ApiModelProperty(value = "企业联系人")
	private String enterpriseContacts;

	/**
	* 移动电话
	*/
	@Column(name= "movePhone")
    @ApiModelProperty(value = "移动电话")
	private String movePhone;

	/**
	* 固定电话
	*/
	@Column(name= "fixedTelephone")
    @ApiModelProperty(value = "固定电话")
	private String fixedTelephone;

	/**
	* 邮箱
	*/
	@Column(name= "email")
    @ApiModelProperty(value = "邮箱")
	private String email;

	/**
	* 地址
	*/
	@Column(name= "address")
    @ApiModelProperty(value = "地址")
	private String address;

	/**
	* 名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "名称")
	private String name;

	/**
	* 状态变更说明
	*/
	@Column(name= "statusRemark")
    @ApiModelProperty(value = "状态变更说明")
	private String statusRemark;

	/**
	* 状态变更说明
	*/
	@Column(name= "intentionCode")
    @ApiModelProperty(value = "客户编码")
	private String intentionCode;

	/**
	* 微信
	*/
	@Column(name= "weixin")
    @ApiModelProperty(value = "微信")
	private String weixin;

	/**
	* 跟进次数
	*/
	@Column(name= "followCount")
    @ApiModelProperty(value = "跟进次数")
	private Integer followCount;

	/**
	* 排除原因
	*/
	@Column(name= "excludeType")
    @ApiModelProperty(value = "排除原因")
	private Integer excludeType;



	/**
	 * 创建人
	 */
	@Transient
	@ApiModelProperty(value = "创建人")
	private String userName;

	/**
	 * 意向信息实体
	 */
	@Transient
	@ApiModelProperty(value = "意向信息实体")
	private Object cuIntentionInfo;
	/**
	 * 意向信息联系人实体
	 */
	@Transient
	@ApiModelProperty(value = "意向信息联系人实体")
	private Object cuIntentionPerson;

	/**
	 * 省key值
	 */
	@Column(name= "province")
	@ApiModelProperty(value = "省key值")
	private String province;

	/**
	 * 市key值
	 */
	@Column(name= "city")
	@ApiModelProperty(value = "市key值")
	private String city;

	/**
	 * 县/区key值
	 */
	@Column(name= "county")
	@ApiModelProperty(value = "县/区key值")
	private String county;

	/**
	 * 意向备注
	 */
	@Column(name= "remarks")
	@ApiModelProperty(value = "意向备注")
	private String remarks;

}
