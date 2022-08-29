/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:49:25 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtContractElement </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:49:25 
 */
@Data
@ApiModel(value = "合同要素实体类")
@Table(name="ct_contract_element")
public class CtContractElement extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 要素key
	*/
	@Column(name= "elementKey")
    @ApiModelProperty(value = "要素key")
	private String elementKey;

	/**
	* 要素值
	*/
	@Column(name= "elementValue")
    @ApiModelProperty(value = "要素值")
	private String elementValue;

	/**
	* 要素名称
	*/
	@Column(name= "elementName")
    @ApiModelProperty(value = "要素名称")
	private String elementName;

	/**
	* 要素格式
	*/
	@Column(name= "elementFormat")
    @ApiModelProperty(value = "要素格式")
	private String elementFormat;

	/**
	* 取值表名
	*/
	@Column(name= "tableName")
    @ApiModelProperty(value = "取值表名")
	private String tableName;

	/**
	* 取值字段
	*/
	@Column(name= "tableField")
    @ApiModelProperty(value = "取值字段")
	private String tableField;

	/**
	* 取值条件
	*/
	@Column(name= "valueConditions")
    @ApiModelProperty(value = "取值条件")
	private String valueConditions;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

}
