/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 11:00:17
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProBusState </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 11:00:17
 */
@Data
@ApiModel(value = "业务状态管理实体类")
@Table(name="pro_bus_state")
public class ProBusState extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 业务表id
	*/
	@Column(name= "tableId")
    @ApiModelProperty(value = "业务表id")
	private Long tableId;

	/**
	* 字段名称
	*/
	@Column(name= "sqlName")
    @ApiModelProperty(value = "字段名称")
	private String sqlName;

	/**
	* 状态名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "状态名称")
	private String name;

	/**
	* 状态值
	*/
	@Column(name= "value")
    @ApiModelProperty(value = "状态值")
	private String value;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

	@Transient
	@ApiModelProperty(value = "业务表名称")
	private String tableName;
}
