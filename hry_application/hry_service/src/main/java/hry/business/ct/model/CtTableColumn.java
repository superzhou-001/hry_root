/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-16 11:23:51 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtTableColumn </p>
 *
 * @author: yaoz
 * @Date: 2020-06-16 11:23:51 
 */
@Data
@ApiModel(value = "合同要素配置实体类")
@Table(name="ct_table_column")
public class CtTableColumn extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 表id
	*/
	@Column(name= "tableId")
    @ApiModelProperty(value = "表id")
	private Long tableId;

	/**
	* 列名称
	*/
	@Column(name= "tableColumnName")
    @ApiModelProperty(value = "列名称")
	private String tableColumnName;

	/**
	* 列
	*/
	@Column(name= "tableColumn")
    @ApiModelProperty(value = "列")
	private String tableColumn;

	/**
	* 是否开启 1是 2.否
	*/
	@Column(name= "isOpen")
    @ApiModelProperty(value = "是否开启 1是 2.否")
	private Integer isOpen;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

}
