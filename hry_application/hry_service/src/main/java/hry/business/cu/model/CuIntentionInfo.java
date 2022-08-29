/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:37:17 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> CuIntentionInfo </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:37:17 
 */
@Data
@ApiModel(value = "意向信息子表信息实体类")
@Table(name="cu_intention_info")
public class CuIntentionInfo extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 意向信息主表id
	*/
	@Column(name= "intentionId")
    @ApiModelProperty(value = "意向信息主表id")
	private Long intentionId;

	/**
	* 融资金额
	*/
	@Column(name= "financingAmount")
    @ApiModelProperty(value = "融资金额")
	private String financingAmount;

	/**
	* 融资期限/月
	*/
	@Column(name= "financingTerm")
    @ApiModelProperty(value = "融资期限/月")
	private Integer financingTerm;

	/**
	* 用款时间
	*/
	@Column(name= "paymentTime")
    @ApiModelProperty(value = "用款时间")
	private Date paymentTime;

	/**
	* 意向来源
	*/
	@Column(name= "sourceIntention")
    @ApiModelProperty(value = "意向来源")
	private Integer sourceIntention;

	/**
	* 意向成功率
	*/
	@Column(name= "intentionSuccessRate")
    @ApiModelProperty(value = "意向成功率")
	private String intentionSuccessRate;

	@Column(name = "userId")
	@ApiModelProperty(value = "")
	private Long userId;

	/**
	 * 创建人
	 */
	@Column(name= "userName")
	@ApiModelProperty(value = "创建人")
	private String userName;

	/**
	 * 意向状态 1.跟进 2.排除 3.转已签
	 */
	@Column(name= "status")
	@ApiModelProperty(value = "意向状态 1.跟进 2.排除 3.转已签")
	private Integer status;


	/**
	 * 状态变更说明
	 */
	@Column(name= "statusRemark")
	@ApiModelProperty(value = "状态变更说明")
	private String statusRemark;

}
