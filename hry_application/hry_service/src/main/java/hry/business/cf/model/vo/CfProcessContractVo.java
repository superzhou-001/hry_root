/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-24 17:45:18 
 */
package hry.business.cf.model.vo;

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
public class CfProcessContractVo extends BaseModel {

	private Long id;

	@ApiModelProperty(value = "流程名称")
	private String name;

	@ApiModelProperty(value = "授信流程名称")
	private String className;

	@ApiModelProperty(value = "关联合同数")
	private String contractNum;

}
