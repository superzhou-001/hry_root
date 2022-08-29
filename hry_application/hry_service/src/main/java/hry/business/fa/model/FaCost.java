/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:23 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> FaCost </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:23 
 */
@Data
@ApiModel(value = "产品费用表实体类")
@Table(name="fa_cost")
public class FaCost extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 费用名称
	*/
	@Column(name= "costname")
    @ApiModelProperty(value = "费用名称")
	private String costname;

	/**
	* 类型 1 一次性 2 计算模型
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "类型 1 一次性 2 计算模型")
	private Integer type;

	/**
	* 计算公式id
	*/
	@Column(name= "nlmsid")
    @ApiModelProperty(value = "计算公式id")
	private Long nlmsid;

	/**
	 * 公式
	 * */
	@Transient
	private String nlmsname; // 公式名称

	/**
	* 费用金额
	*/
	@Column(name= "costmoney")
    @ApiModelProperty(value = "费用金额")
	private BigDecimal costmoney;

	/**
	* 收费类型 1 收取 2 付款抵扣
	*/
	@Column(name= "costtype")
    @ApiModelProperty(value = "收费类型 1 收取 2 付款抵扣")
	private Integer costtype;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

}
