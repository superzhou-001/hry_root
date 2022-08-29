/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:29:01
 */
package hry.platform.communication.sms.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppSmsTemplate </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:29:01
 */
@Data
@ApiModel(value = "短信模版实体类")
@Table(name="app_sms_template")
public class AppSmsTemplate extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Integer id;

	/**
	* 模板名称
	*/
	@Column(name= "tempName")
    @ApiModelProperty(value = "模板名称")
	private String tempName;

	/**
	* 模板类型
	*/
	@Column(name= "tempType")
    @ApiModelProperty(value = "模板类型")
	private Integer tempType;

	/**
	* 模板内容
	*/
	@Column(name= "tempContent")
    @ApiModelProperty(value = "模板内容")
	private String tempContent;

	/**
	* 模板状态 0-关闭，1-开启
	*/
	@Column(name= "tempState")
    @ApiModelProperty(value = "模板状态 0-关闭，1-开启")
	private Integer tempState;

	/**
	* 模板语种
	*/
	@Column(name= "tempLang")
    @ApiModelProperty(value = "模板语种")
	private String tempLang;

	/**
	*
	*/
	@Column(name= "saasId")
    @ApiModelProperty(value = "")
	private String saasId;

	/**
	* 第三方模板id
	*/
	@Column(name= "thirdTemplateId")
    @ApiModelProperty(value = "第三方模板id")
	private String thirdTemplateId;

}
