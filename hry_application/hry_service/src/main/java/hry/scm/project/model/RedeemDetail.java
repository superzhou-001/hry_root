/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-24 11:17:34 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> RedeemDetail </p>
 *
 * @author: luyue
 * @Date: 2020-07-24 11:17:34 
 */
@Data
@ApiModel(value = "赎货解除质押物详细信息实体类")
@Table(name="scm_redeem_detail")
public class RedeemDetail extends BaseModel {

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
	* 解除质押汇总表id
	*/
	@Column(name= "redeemTotalId")
    @ApiModelProperty(value = "解除质押汇总表id")
	private Long redeemTotalId;

	/**
	* 质押物详细id
	*/
	@Column(name= "detailId")
    @ApiModelProperty(value = "质押物详细id")
	private Long detailId;

	/**
	 * 区列行组成唯一标识
	 */
	@Column(name= "number")
	@ApiModelProperty(value = "区列行组成唯一标识")
	private String number;

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

}
