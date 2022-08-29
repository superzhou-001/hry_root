/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CfProcessContract </p>
 *
 * @author: yaoz
 * @Date: 2020-07-24 17:45:18 
 */
@Data
@ApiModel(value = "授信合同关联表(基础配置)实体类")
@Table(name="cf_process_contract")
public class CfProcessContract extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 授信流程id
	*/
	@Column(name= "processId")
    @ApiModelProperty(value = "授信流程id")
	private Long processId;

	/**
	* 合同id
	*/
	@Column(name= "contractId")
    @ApiModelProperty(value = "合同id")
	private Long contractId;

}
