/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-28 12:05:59 
 */
package hry.platform.flow.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppFlow </p>
 *
 * @author: liushilei
 * @Date: 2020-05-28 12:05:59 
 */
@Data
@ApiModel(value = "通用流程表实体类")
@Table(name="app_flow")
public class AppFlow extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 流程key
	*/
	@Column(name= "defineKey")
    @ApiModelProperty(value = "流程key")
	private String defineKey;

	/**
	* 流程名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "流程名称")
	private String name;

	/**
	* 流程状态
	*/
	@Column(name= "state")
    @ApiModelProperty(value = "流程状态")
	private Integer state;

}
