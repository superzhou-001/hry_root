/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-12 17:38:48 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtContractTemplateSeal </p>
 *
 * @author: yaoz
 * @Date: 2020-06-12 17:38:48 
 */
@Data
@ApiModel(value = "合同模板印章实体类")
@Table(name="ct_contract_template_seal")
public class CtContractTemplateSeal extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 模板id
	*/
	@Column(name= "contractTemplateId")
    @ApiModelProperty(value = "模板id")
	private Long contractTemplateId;

	/**
	* 印章id
	*/
	@Column(name= "contractSealId")
    @ApiModelProperty(value = "印章id")
	private Long contractSealId;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 宽
	*/
	@Column(name= "width")
    @ApiModelProperty(value = "宽")
	private String width;

	/**
	* 高
	*/
	@Column(name= "height")
    @ApiModelProperty(value = "高")
	private String height;

	/**
	* 页码
	*/
	@Column(name= "pageNum")
    @ApiModelProperty(value = "页码")
	private Integer pageNum;

	/**
	* 
	*/
	@Column(name= "x")
    @ApiModelProperty(value = "")
	private String x;

	/**
	* 
	*/
	@Column(name= "y")
    @ApiModelProperty(value = "")
	private String y;

	/**
	* 印章地址
	*/
	@Transient
    @ApiModelProperty(value = "印章地址")
	private String sealUrl;
	/**
	* 印章名称
	*/
	@Transient
    @ApiModelProperty(value = "印章名称")
	private String sealName;

	/**
	* 印章备注
	*/
	@Transient
    @ApiModelProperty(value = "印章备注")
	private String cceRemark;

}
