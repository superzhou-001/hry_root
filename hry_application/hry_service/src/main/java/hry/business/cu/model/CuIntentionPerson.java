/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 16:09:20 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuIntentionPerson </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 16:09:20 
 */
@Data
@ApiModel(value = "意向客户-关联人信息中间表实体类")
@Table(name="cu_intention_person")
public class CuIntentionPerson extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 个人信息表id
	*/
	@Column(name= "personId")
    @ApiModelProperty(value = "个人信息表id")
	private Long personId;

	/**
	* 意向客户id
	*/
	@Column(name= "intentionId")
    @ApiModelProperty(value = "意向客户id")
	private Long intentionId;

	/**
	* 人员类型:1.法人 2.控制人 3.联系人 
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "人员类型:1.法人 2.控制人 3.联系人 ")
	private Integer type;

	/**
	* 是否是主要联系人 1是 2否
	*/
	@Column(name= "isMainPerson")
    @ApiModelProperty(value = "是否是主要联系人 1是 2否")
	private Integer isMainPerson;

	@Transient
	@ApiModelProperty(value = "人")
	private Object cuPerson;

}
