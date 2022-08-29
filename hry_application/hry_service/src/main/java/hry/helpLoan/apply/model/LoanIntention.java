/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-22 13:50:08 
 */
package hry.helpLoan.apply.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> LoanIntention </p>
 *
 * @author: huanggh
 * @Date: 2020-07-22 13:50:08 
 */
@Data
@ApiModel(value = "助贷意向信息实体类")
@Table(name="hl_loan_intention")
public class LoanIntention extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 用户姓名
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "用户姓名")
	private String userName;

	/**
	* 手机号
	*/
	@Column(name= "mobile")
    @ApiModelProperty(value = "手机号")
	private String mobile;

	/**
	* 省
	*/
	@Column(name= "province")
    @ApiModelProperty(value = "省")
	private String province;

	/**
	* 市
	*/
	@Column(name= "city")
    @ApiModelProperty(value = "市")
	private String city;

	/**
	* 状态：0：待回复，1：已回复
	*/
	@Column(name= "states")
    @ApiModelProperty(value = "状态：0：待回复，1：已回复")
	private Integer states;

	/**
	* 备注
	*/
	@Column(name= "remarks")
    @ApiModelProperty(value = "备注")
	private String remarks;

	/**
	 * 身份
	 */
	@Column(name= "identityType")
	@ApiModelProperty(value = "身份")
	private String identityType;

	/**
	 * 有无押品 0：无 1：有
	 */
	@Column(name= "isPledge")
	@ApiModelProperty(value = "有无押品")
	private String isPledge;

	/**
	 * 押品类型
	 */
	@Column(name= "pledgeType")
	@ApiModelProperty(value = "押品类型")
	private String pledgeType;

}
