/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:56:45 
 */
package hry.platform.communication.email.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> MailConfig </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:56:45 
 */
@Data
@ApiModel(value = "邮件配置实体类")
@Table(name="mail_config")
public class MailConfig extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 端口
	*/
	@Column(name= "port")
    @ApiModelProperty(value = "端口")
	private String port;

	/**
	* 服务器
	*/
	@Column(name= "host")
    @ApiModelProperty(value = "服务器")
	private String host;

	/**
	* 协议
	*/
	@Column(name= "protocol")
    @ApiModelProperty(value = "协议")
	private String protocol;

	/**
	* 开启认证   1开启  0未开启 
	*/
	@Column(name= "auth")
    @ApiModelProperty(value = "开启认证   1开启  0未开启 ")
	private Integer auth;

	/**
	* 发送用户
	*/
	@Column(name= "emailUser")
    @ApiModelProperty(value = "发送用户")
	private String emailUser;

	/**
	* 认证用户
	*/
	@Column(name= "agreedUser")
    @ApiModelProperty(value = "认证用户")
	private String agreedUser;

	/**
	* 认证密码
	*/
	@Column(name= "agreedPwd")
    @ApiModelProperty(value = "认证密码")
	private String agreedPwd;

	/**
	* 自定义发件名称
	*/
	@Column(name= "customName")
    @ApiModelProperty(value = "自定义发件名称")
	private String customName;

	/**
	* 是否开启了ssl验证  1开启 0未开启
	*/
	@Column(name= "sslflag")
    @ApiModelProperty(value = "是否开启了ssl验证  1开启 0未开启")
	private Integer sslflag;

	/**
	* 是否开启tls   1开启  0未开启
	*/
	@Column(name= "starttls")
    @ApiModelProperty(value = "是否开启tls   1开启  0未开启")
	private Integer starttls;

	/**
	* 邮箱标题前缀
	*/
	@Column(name= "prefix")
    @ApiModelProperty(value = "邮箱标题前缀")
	private String prefix;

	/**
	* 启用状态     1开启   0关闭
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "启用状态     1开启   0关闭")
	private Integer status;

	/**
	* sassId
	*/
	@Column(name= "saasId")
    @ApiModelProperty(value = "sassId")
	private String saasId;

}
