/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:27:30 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> FaProductType </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:27:30 
 */
@Data
@ApiModel(value = "产品类型表实体类")
@Table(name="fa_product_type")
public class FaProductType extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 类型名称
	*/
	@Column(name= "typename")
    @ApiModelProperty(value = "类型名称")
	private String typename;

	/**
	* 类型描述
	*/
	@Column(name= "typedesc")
    @ApiModelProperty(value = "类型描述")
	private String typedesc;

}
