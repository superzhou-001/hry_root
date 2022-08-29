/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:48:43 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtContractType </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:48:43 
 */
@Data
@ApiModel(value = "合同分类实体类")
@Table(name="ct_contract_type")
public class CtContractType extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 分类名称
	*/
	@Column(name= "typeName")
    @ApiModelProperty(value = "分类名称")
	private String typeName;

	/**
	* 用途分类
	*/
	@Column(name= "purposeType")
    @ApiModelProperty(value = "用途分类")
	private Integer purposeType;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

}
