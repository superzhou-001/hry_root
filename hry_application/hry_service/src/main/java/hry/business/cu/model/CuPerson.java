/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:29:14 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> CuPerson </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:29:14 
 */
@Data
@ApiModel(value = "用户信息实体类")
@Table(name="cu_person")
public class CuPerson extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	*/
	@Column(name= "customerId")
    @ApiModelProperty(value = "")
	private Long customerId;

	/**
	* 身份证正面照片
	*/
	@Column(name= "idCardFrontUrl")
    @ApiModelProperty(value = "身份证正面照片")
	private String idCardFrontUrl;

	/**
	* 身份证反面照片
	*/
	@Column(name= "idCardBackUrl")
    @ApiModelProperty(value = "身份证反面照片")
	private String idCardBackUrl;

	/**
	* 授权书
	*/
	@Column(name= "authorizationUrl")
    @ApiModelProperty(value = "授权书")
	private String authorizationUrl;

	/**
	* 工作地址
	*/
	@Column(name= "workAddress")
    @ApiModelProperty(value = "工作地址")
	private String workAddress;

	/**
	* 通讯地址
	*/
	@Column(name= "homeAddress")
    @ApiModelProperty(value = "通讯地址")
	private String homeAddress;

	/**
	* 生日
	*/
	@Column(name= "birthday")
    @ApiModelProperty(value = "生日")
	private Date birthday;

	/**
	* 性别 1男 2女
	*/
	@Column(name= "sex")
    @ApiModelProperty(value = "性别 1男 2女")
	private Integer sex;

	/**
	* 
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "")
	private String name;

	/**
	* 手机
	*/
	@Column(name= "mobile")
    @ApiModelProperty(value = "手机")
	private String mobile;


	/**
	* 电话
	*/
	@Column(name= "phone")
    @ApiModelProperty(value = "电话")
	private String phone;

	/**
	* 证件类型 1.身份证 
	*/
	@Column(name= "cardType")
    @ApiModelProperty(value = "证件类型 1.身份证 ")
	private Integer cardType;

	/**
	* 邮箱
	*/
	@Column(name= "email")
    @ApiModelProperty(value = "邮箱")
	private String email;

	/**
	* 微信
	*/
	@Column(name= "weixin")
    @ApiModelProperty(value = "微信")
	private String weixin;

	/**
	* qq
	*/
	@Column(name= "qq")
    @ApiModelProperty(value = "qq")
	private String qq;

	/**
	* 身份证号
	*/
	@Column(name= "cardId")
    @ApiModelProperty(value = "身份证号")
	private String cardId;

	/**
	 * 身份证到期类型：1.有时限 2.无限期
	 */
	@Column(name= "cardTimeStatus")
	@ApiModelProperty(value = "身份证到期类型：1.有时限 2.无限期")
	private Integer cardTimeStatus;

	/**
	 * 身份证到期时间	
	 */
	@Column(name= "cardEndTime")
	@ApiModelProperty(value = "身份证到期时间")
	private String cardEndTime;

	/**
	 * 中间表id
	 */
	@Transient
	@ApiModelProperty(value = "中间表id")
	private Long cuEnterprisePersonId;


	/**
	 * 企业id
	 */
	@Transient
	@ApiModelProperty(value = "企业id")
	private Long cuEnterpriseId;

	/**
	 * 人员类型:1.法人 2.控制人 3.联系人
	 */
	@Transient
	@ApiModelProperty(value = "人员类型:1.法人 2.控制人 3.联系人")
	private Integer personType;

	/**
	 * 是否是主要联系人 1是 2否
	 */
	@Transient
	@ApiModelProperty(value = "是否是主要联系人 1是 2否")
	private Integer isMainPerson;

	/**
	 * 意向客户联系人关联表ID
	 */
	@Transient
	@ApiModelProperty(value = "意向客户联系人关联表ID")
	private Long cipId;


}
