/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-22 17:18:30 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> RedeemTotal </p>
 *
 * @author: luyue
 * @Date: 2020-07-22 17:18:30 
 */
@Data
@ApiModel(value = "赎货解除质押物汇总信息实体类")
@Table(name="scm_redeem_total")
public class RedeemTotal extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 赎货申请主键
	*/
	@Column(name= "redeemId")
    @ApiModelProperty(value = "赎货申请主键")
	private Long redeemId;

	/**
	* 质押物汇总id
	*/
	@Column(name= "totalId")
    @ApiModelProperty(value = "质押物汇总id")
	private Long totalId;

	/**
	* 赎货件数
	*/
	@Column(name= "backCount")
    @ApiModelProperty(value = "赎货件数")
	private Integer backCount;

	/**
	* 赎货重量
	*/
	@Column(name= "backWeight")
    @ApiModelProperty(value = "赎货重量")
	private BigDecimal backWeight;

	/**
	* 赎货价值
	*/
	@Column(name= "backWorth")
    @ApiModelProperty(value = "赎货价值")
	private BigDecimal backWorth;

}
