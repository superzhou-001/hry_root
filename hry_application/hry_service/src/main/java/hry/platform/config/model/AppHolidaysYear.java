/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:24:37 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppHolidaysYear </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:24:37 
 */
@Data
@ApiModel(value = "节假日年份实体类")
@Table(name="app_holidays_year")
public class AppHolidaysYear extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 年份
	*/
	@Column(name= "year")
    @ApiModelProperty(value = "年份")
	private String year;

	/**
	* 是否启用 0 启用 1 未启用
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "是否启用 0 启用 1 未启用")
	private Integer status;

}
