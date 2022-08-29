/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:40 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtContractSeal </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:40 
 */
@Data
@ApiModel(value = "印章配置实体类")
@Table(name="ct_contract_seal")
public class CtContractSeal extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 父id
	*/
	@Column(name= "parentId")
    @ApiModelProperty(value = "父id")
	private Long parentId;

	/**
	* 印章名称
	*/
	@Column(name= "sealName")
    @ApiModelProperty(value = "印章名称")
	private String sealName;

	/**
	* 印章地址
	*/
	@Column(name= "sealUrl")
    @ApiModelProperty(value = "印章地址")
	private String sealUrl;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

}
