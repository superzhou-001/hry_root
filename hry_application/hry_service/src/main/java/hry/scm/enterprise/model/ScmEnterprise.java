/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:35:56 
 */
package hry.scm.enterprise.model;

import hry.bean.BaseModel;
import hry.scm.file.model.ScmFile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * <p> ScmEnterprise </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:35:56 
 */
@Data
@ApiModel(value = "供应链企业信息实体类")
@Table(name="scm_enterprise")
public class ScmEnterprise extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 企业名称
	*/
	@Column(name= "enterpriseName")
    @ApiModelProperty(value = "企业名称")
	private String enterpriseName;

	/**
	* 企业简称
	*/
	@Column(name= "enterpriseShortName")
    @ApiModelProperty(value = "企业简称")
	private String enterpriseShortName;

	/**
	* 注册资本
	*/
	@Column(name= "registCapi")
    @ApiModelProperty(value = "注册资本")
	private String registCapi;

	/**
	* 社会统一信用代码
	*/
	@Column(name= "creditCode")
    @ApiModelProperty(value = "社会统一信用代码")
	private String creditCode;

	/**
	* 法人姓名
	*/
	@Column(name= "operName")
    @ApiModelProperty(value = "法人姓名")
	private String operName;

	/**
	* 法人身份证号码
	*/
	@Column(name= "operIdCardNo")
    @ApiModelProperty(value = "法人身份证号码")
	private String operIdCardNo;

	/**
	 * 状态 1.待审核 2.已通过 3.未通过 4.加入黑名单
	 */
	@Column(name= "status")
	@ApiModelProperty(value = "状态 1.待审核 2.已通过 3.未通过 4.加入黑名单")
	private Integer status;

	/**
	* 身份证正面
	*/
	@Column(name= "idCardFrontUrl")
    @ApiModelProperty(value = "身份证正面")
	private String idCardFrontUrl;

	/**
	* 营业执照
	*/
	@Column(name= "businessLicenseUrl")
    @ApiModelProperty(value = "营业执照")
	private String businessLicenseUrl;

	/**
	* 身份证反面
	*/
	@Column(name= "idCardBackUrl")
    @ApiModelProperty(value = "身份证反面")
	private String idCardBackUrl;

	/**
	* 固定电话
	*/
	@Column(name= "fixedPhone")
    @ApiModelProperty(value = "固定电话")
	private String fixedPhone;

	/**
	* 所属行业
	*/
	@Column(name= "industry")
    @ApiModelProperty(value = "所属行业")
	private String industry;

	/**
	* 企业性质
	*/
	@Column(name= "enterpriseNature")
    @ApiModelProperty(value = "企业性质")
	private String enterpriseNature;

	/**
	* 企业规模
	*/
	@Column(name= "enterpriseScale")
    @ApiModelProperty(value = "企业规模")
	private String enterpriseScale;

	/**
	* 省
	*/
	@Column(name= "province")
    @ApiModelProperty(value = "省")
	private String province;

	/**
	 * 省key值
	 */
	@Column(name= "provinceKey")
	@ApiModelProperty(value = "省key值")
	private String provinceKey;

	/**
	* 市
	*/
	@Column(name= "city")
    @ApiModelProperty(value = "市")
	private String city;

	/**
	 * 市key值
	 */
	@Column(name= "cityKey")
	@ApiModelProperty(value = "市key值")
	private String cityKey;

	/**
	* 县/区
	*/
	@Column(name= "county")
    @ApiModelProperty(value = "县/区")
	private String county;

	/**
	 * 县/区key值
	 */
	@Column(name= "countyKey")
	@ApiModelProperty(value = "县/区key值")
	private String countyKey;

	/**
	* 详细地址
	*/
	@Column(name= "address")
    @ApiModelProperty(value = "详细地址")
	private String address;

	/**
	* 年贸易量
	*/
	@Column(name= "yearTradeVolume")
    @ApiModelProperty(value = "年贸易量")
	private String yearTradeVolume;

	/**
	* 法人手机号
	*/
	@Column(name= "operMobile")
    @ApiModelProperty(value = "法人手机号")
	private String operMobile;


	/**
	 * 备注（打回原因等）
	 */
	@Column(name= "remarks")
	@ApiModelProperty(value = "备注（打回原因等）")
	private String remarks;



	/**
	 * 1:管理员（自己创建的企业），2:业务员（别人授权的企业）
	 */
	@ApiModelProperty(value = "1:管理员（自己创建的企业），2:业务员（别人授权的企业）")
	@Transient
	private Integer role;

	/**
	 * 管理员姓名
	 */
	@ApiModelProperty(value = "管理员姓名")
	@Transient
	private String userName;

	/**
	 * 管理员手机号
	 */
	@ApiModelProperty(value = "管理员手机号")
	@Transient
	private String mobile;

	/**
	 * 总额度
	 */
	@ApiModelProperty(value = "总额度")
	@Transient
	private BigDecimal sumQuotaValue;

	/**
	 * 剩余额度
	 */
	@ApiModelProperty(value = "剩余额度")
	@Transient
	private BigDecimal surplusQuotaValue;

	/**
	 * 负责人邮箱
	 */
	@ApiModelProperty(value = "负责人邮箱")
	@Transient
	private String email;



}
