/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:59:19 
 */
package hry.platform.communication.email.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> MailTemplate </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:59:19 
 */
@Data
@ApiModel(value = "邮件模版实体类")
@Table(name="mail_template")
public class MailTemplate extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 模板名称
	*/
	@Column(name= "tempName")
    @ApiModelProperty(value = "模板名称")
	private String tempName;

	/**
	* 模板key
	*/
	@Column(name= "tempKey")
    @ApiModelProperty(value = "模板key")
	private String tempKey;

	/**
	* 模板内容
	*/
	@Column(name= "tempContent")
    @ApiModelProperty(value = "模板内容")
	private String tempContent;

	/**
	* 模板备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "模板备注")
	private String remark;

	/**
	* 启用状态   1开启  0 关闭
	*/
	@Column(name= "tempStatus")
    @ApiModelProperty(value = "启用状态   1开启  0 关闭")
	private Integer tempStatus;

	/**
	* 邮箱账号id
	*/
	@Column(name= "mailConfigId")
    @ApiModelProperty(value = "邮箱账号id")
	private Long mailConfigId;

	/**
	* 语言   0 中文  1英文
	*/
	@Column(name= "language")
    @ApiModelProperty(value = "语言   0 中文  1英文")
	private Integer language;

	/**
	* 
	*/
	@Column(name= "saasId")
    @ApiModelProperty(value = "")
	private String saasId;

	/**
	* 数据字典中的语言
	*/
	@Column(name= "languageDic")
    @ApiModelProperty(value = "数据字典中的语言")
	private String languageDic;

}
