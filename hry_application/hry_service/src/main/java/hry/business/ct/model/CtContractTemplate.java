/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:50:03 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> CtContractTemplate </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:50:03 
 */
@Data
@ApiModel(value = "合同模板实体类")
@Table(name="ct_contract_template")
public class CtContractTemplate extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 合同分类Id
	*/
	@Column(name= "contractTypeId")
    @ApiModelProperty(value = "合同分类Id")
	private Long contractTypeId;

	/**
	* 模板名称
	*/
	@Column(name= "templateName")
    @ApiModelProperty(value = "模板名称")
	private String templateName;

	/**
	* 编码格式
	*/
	@Column(name= "codeFormat")
    @ApiModelProperty(value = "编码格式")
	private String codeFormat;

	/**
	* 编码前缀
	*/
	@Column(name= "codePrefix")
    @ApiModelProperty(value = "编码前缀")
	private String codePrefix;

	/**
	* 文件地址
	*/
	@Column(name= "fileUrl")
    @ApiModelProperty(value = "文件地址")
	private String fileUrl;

	/**
	* 文件名称
	*/
	@Column(name= "fileName")
    @ApiModelProperty(value = "文件名称")
	private String fileName;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 合同类型
	*/
	@Transient
    @ApiModelProperty(value = "合同类型")
	private String typeName;

	/**
	 * 用途分类
	 */
	@Transient
	@ApiModelProperty(value = "用途分类")
	private Integer purposeType;

	/**
	 * 模板元素列表
	 */
	@Transient
	@ApiModelProperty(value = "模板元素列表")
	private List<CtContractTemplateElement> list;

	/**
	 * 模板元素列表
	 */
	@Transient
	@ApiModelProperty(value = "模板元素列表")
	private Object objectList;

	/**
	 * 模板印章列表
	 */
	@Transient
	@ApiModelProperty(value = "模板印章列表")
	private Object objectSealList;

	/**
	 * 模板印章列表
	 */
	@Transient
	@ApiModelProperty(value = "模板印章列表")
	private List<CtContractTemplateSeal> sealList;

	/**
	 * 更改内容类型 1.模板元素 2.模板印章
	 */
	@Transient
	@ApiModelProperty(value = "更改内容类型 1.模板元素 2.模板印章")
	private Integer updateType;


}
