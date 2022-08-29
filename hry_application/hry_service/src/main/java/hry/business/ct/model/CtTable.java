/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:22:58 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtTable </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:22:58 
 */
@Data
@ApiModel(value = "合同要素配置表实体类")
@Table(name="ct_table")
public class CtTable extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 表名
	*/
	@Column(name= "tableName")
    @ApiModelProperty(value = "表名")
	private String tableName;

	/**
	* 表
	*/
	@Column(name= "tableTable")
    @ApiModelProperty(value = "表")
	private String tableTable;

	/**
	* 是否开启 1是 2.否
	*/
	@Column(name= "isOpen")
    @ApiModelProperty(value = "是否开启 1是 2.否")
	private Integer isOpen;


	/**
	* 类别(取值数据字典)
	*/
	@Column(name= "tableType")
    @ApiModelProperty(value = "类别(取值数据字典)")
	private Integer tableType;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

}
