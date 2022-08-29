/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 14:31:34 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuEnterprisePerson </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 14:31:34 
 */
@Data
@ApiModel(value = "联系人信息表")
@Table(name="cu_enterprise_person")
public class CuEnterprisePerson extends BaseModel {

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
	* 企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业id")
	private Long enterpriseId;

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



}
