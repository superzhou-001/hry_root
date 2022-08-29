/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 15:27:23
 */
package hry.platform.communication.sms.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppSmsSend </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 15:27:23
 */
@Data
@ApiModel(value = "短信发送记录实体类")
@Table(name="app_sms_send")
public class AppSmsSend extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Integer id;

	/**
	* 短信类型
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "短信类型")
	private String type;

	/**
	*
	*/
	@Column(name= "serverUrl")
    @ApiModelProperty(value = "")
	private String serverUrl;

	/**
	* 请求参数
	*/
	@Column(name= "postParam")
    @ApiModelProperty(value = "请求参数")
	private String postParam;

	/**
	* 接受状态 1-接收成功 0-接收失败
	*/
	@Column(name= "receiveStatus")
    @ApiModelProperty(value = "接受状态 1-接收成功 0-接收失败")
	private String receiveStatus;

	/**
	* 发送状态 1-发送成功 0-发送失败
	*/
	@Column(name= "sendStatus")
    @ApiModelProperty(value = "发送状态 1-发送成功 0-发送失败")
	private String sendStatus;

	/**
	* 第三方返回结果
	*/
	@Column(name= "thirdPartyResult")
    @ApiModelProperty(value = "第三方返回结果")
	private String thirdPartyResult;

	/**
	* 短信内容
	*/
	@Column(name= "sendContent")
    @ApiModelProperty(value = "短信内容")
	private String sendContent;

	/**
	*
	*/
	@Column(name= "saasId")
    @ApiModelProperty(value = "")
	private String saasId;

	/**
	* 手机号
	*/
	@Column(name= "mobileNum")
    @ApiModelProperty(value = "手机号")
	private String mobileNum;

	/**
	* 第三方模板id
	*/
	@Column(name= "thirdTemplateId")
    @ApiModelProperty(value = "第三方模板id")
	private String thirdTemplateId;

}
