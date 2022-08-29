/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:38:17 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppProductClass </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:38:17 
 */
@Data
@ApiModel(value = "产品分类实体类")
@Table(name="app_product_class")
public class AppProductClass extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 流程名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "流程名称")
	private String name;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

}
