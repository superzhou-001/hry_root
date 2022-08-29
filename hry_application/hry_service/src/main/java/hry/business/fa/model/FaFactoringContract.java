/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 14:11:12 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> FaFactoringContract </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 14:11:12 
 */
@Data
@ApiModel(value = "保理合同签署实体类")
@Table(name="fa_factoring_contract")
public class FaFactoringContract extends BaseModel {

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
	* 项目id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "项目id")
	private Long projectId;

	/**
	* 合同名称
	*/
	@Column(name= "contractName")
    @ApiModelProperty(value = "合同名称")
	private String contractName;

	/**
	* 合同对象
	*/
	@Column(name= "contractObject")
    @ApiModelProperty(value = "合同对象")
	private String contractObject;

	/**
	* 合同编号
	*/
	@Column(name= "contractCode")
    @ApiModelProperty(value = "合同编号")
	private String contractCode;

	/**
	* 合同地址
	*/
	@Column(name= "contractUrl")
    @ApiModelProperty(value = "合同地址")
	private String contractUrl;

	/**
	* 签署状态 1待签署 2已签署
	*/
	@Column(name= "signStatus")
    @ApiModelProperty(value = "签署状态 1待签署 2已签署")
	private Integer signStatus;

	/**
	* 签署时间
	*/
	@Column(name= "signDate")
    @ApiModelProperty(value = "签署时间")
	private Date signDate;

}
