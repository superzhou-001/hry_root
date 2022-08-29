/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:33:28 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuBank </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:33:28 
 */
@Data
@ApiModel(value = "银行开户信息实体类")
@Table(name="cu_bank")
public class CuBank extends BaseModel {

	/**
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@ApiModelProperty(value = "")
	private Long id;

	/**
	* 主体id type=1 personId，type=2 enterpriseId
	*/
	@Column(name= "subjectId")
    @ApiModelProperty(value = "主体id type=1 personId，type=2 enterpriseId")
	private Long subjectId;

	/**
	* 客户类型 1.个人 2.企业
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "客户类型 1.个人 2.企业")
	private Integer type;


	/**
	* 账户类型 1.基本户 2.一般户
	*/
	@Column(name= "accountType")
    @ApiModelProperty(value = "账户类型 1.基本户 2.一般户")
	private Integer accountType;

	/**
	* 银行卡号
	*/
	@Column(name= "cardNumber")
    @ApiModelProperty(value = "银行卡号")
	private String cardNumber;

	/**
	* 开户银行
	*/
	@Column(name= "cardBank")
    @ApiModelProperty(value = "开户银行")
	private String cardBank;

	/**
	* 开户行所在地
	*/
	@Column(name= "bankAddress")
    @ApiModelProperty(value = "开户行所在地(开户支行)")
	private String bankAddress;

	/**
	* 0未删除1已删除
	*/
	@Column(name= "isDelete")
    @ApiModelProperty(value = "0未删除1已删除")
	private Integer isDelete;

	/**
	* 
	*/
	@Column(name= "telPhone")
    @ApiModelProperty(value = "")
	private String telPhone;

	/**
	*名称
	*/
	@Column(name= "bankName")
    @ApiModelProperty(value = "名称")
	private String bankName;

}
