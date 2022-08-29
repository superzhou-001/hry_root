/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:45:12 
 */
package hry.scm.quota.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> EnterpriseQuota </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:45:12 
 */
@Data
@ApiModel(value = "企业额度实体类")
@Table(name="scm_enterprise_quota")
public class EnterpriseQuota extends BaseModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "Id")
	private Long id;

	/**
	 * 企业Id
	 */
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业Id")
	private Long enterpriseId;

	/**
	* 总额度
	*/
	@Column(name= "sumQuotaValue")
    @ApiModelProperty(value = "总额度")
	private BigDecimal sumQuotaValue;

	/**
	* 资金方Id
	*/
	@Column(name= "fundSupportId")
    @ApiModelProperty(value = "资金方Id")
	private Long fundSupportId;

	/**
	 * 资金方名称
	 */
	@Column(name= "fundSupportName")
	@ApiModelProperty(value = "资金方名称")
	private String fundSupportName;

	/**
	* 0待审，1申请中，2已审
	*/
	@Column(name= "aduitStatus")
    @ApiModelProperty(value = "0待审，1申请中，2已审，3已拒绝")
	private Integer aduitStatus;

	/**
	* 已用额度
	*/
	@Column(name= "usedQuotaValue")
    @ApiModelProperty(value = "已用额度")
	private BigDecimal usedQuotaValue;

	/**
	* 剩余额度
	*/
	@Column(name= "surplusQuotaValue")
    @ApiModelProperty(value = "剩余额度")
	private BigDecimal surplusQuotaValue;

	/**
	* 冻结额度
	*/
	@Column(name= "freezeQuotaValue")
    @ApiModelProperty(value = "冻结额度-没用到")
	private BigDecimal freezeQuotaValue;

	/**
	 * 	1 无效 2有效
	 */
	@Column(name= "isValid")
	@ApiModelProperty(value = "1 无效，2有效")
	private Integer isValid;

	/**
	 * 	拒绝原因
	 */
	@Column(name= "remark")
	@ApiModelProperty(value = "拒绝原因")
	private String remark;

	@Transient
	@ApiModelProperty(value = "企业名称")
	private String enterpriseName;

	@Transient
	@ApiModelProperty(value = "负责人姓名")
    private String operName;

	@Transient
	@ApiModelProperty(value = "负责人电话")
	private String operMobile;

	@Transient
	@ApiModelProperty(value = "注册资本")
	private String registCapi;

	@Transient
	@ApiModelProperty(value = "年贸易量")
	private String yearTradeVolume;

	@Transient
	@ApiModelProperty(value = "地区")
	private String address;


}
