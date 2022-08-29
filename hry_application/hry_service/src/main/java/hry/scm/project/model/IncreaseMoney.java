/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-28 16:37:12 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> IncreaseMoney </p>
 *
 * @author: luyue
 * @Date: 2020-07-28 16:37:12 
 */
@Data
@ApiModel(value = "补款记录信息实体类")
@Table(name="scm_increase_money")
public class IncreaseMoney extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 补款编号
	*/
	@Column(name= "number")
    @ApiModelProperty(value = "补款编号")
	private String number;

	/**
	* 订单id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "订单id")
	private Long projectId;

	/**
	* 订单编号
	*/
	@Column(name= "projectNumber")
    @ApiModelProperty(value = "订单编号")
	private String projectNumber;

	/**
	* 补款金额
	*/
	@Column(name= "money")
    @ApiModelProperty(value = "补款金额")
	private BigDecimal money;

	/**
	* 补款凭证照片路径
	*/
	@Column(name= "imageUrl")
    @ApiModelProperty(value = "补款凭证照片路径")
	private String imageUrl;

	/**
	* 申请账户id
	*/
	@Column(name= "applyId")
    @ApiModelProperty(value = "申请账户id")
	private Long applyId;

}
