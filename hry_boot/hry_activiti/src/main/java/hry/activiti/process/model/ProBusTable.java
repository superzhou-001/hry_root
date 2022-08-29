/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-18 10:59:51
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProBusTable </p>
 *
 * @author: liushilei
 * @Date: 2020-05-18 10:59:51
 */
@Data
@ApiModel(value = "业务表管理实体类")
@Table(name="pro_bus_table")
public class ProBusTable extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 表名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "表名称")
	private String name;

	/**
	* javaName     对应model名称
	*/
	@Column(name= "javaName")
    @ApiModelProperty(value = "javaName")
	private String javaName;

	/**
	* sqlName
	*/
	@Column(name= "sqlName")
    @ApiModelProperty(value = "sqlName")
	private String sqlName;

}
