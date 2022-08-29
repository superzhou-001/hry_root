/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:33:52 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> FaProductCost </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:33:52 
 */
@Data
@ApiModel(value = "产品费用表实体类")
@Table(name="fa_product_cost")
public class FaProductCost extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 产品id
	*/
	@Column(name= "productid")
    @ApiModelProperty(value = "产品id")
	private Long productid;

	/**
	* 费用id
	*/
	@Column(name= "costid")
    @ApiModelProperty(value = "费用id")
	private Long costid;

}
