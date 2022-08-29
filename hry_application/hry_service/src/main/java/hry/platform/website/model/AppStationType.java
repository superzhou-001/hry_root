/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:13:14 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppStationType </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:13:14 
 */
@Data
@ApiModel(value = "站内信类型实体类")
@Table(name="app_station_type")
public class AppStationType extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 类型名称
	*/
	@Column(name= "typeName")
    @ApiModelProperty(value = "类型名称")
	private String typeName;

	/**
	* 获取标识
	*/
	@Column(name= "typeKey")
    @ApiModelProperty(value = "获取标识")
	private String typeKey;

	/**
	* 说明
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "说明")
	private String remark;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 状态 1、启用 0、禁用
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "状态 1、启用 0、禁用")
	private Integer status;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

}
