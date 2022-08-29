/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-23 18:54:27
 */
package hry.platform.communication.email.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> Mail </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-23 18:54:27
 */
@Data
@ApiModel(value = "邮件发送记录实体类")
@Table(name="mail")
public class Mail extends BaseModel {

	/**
	*
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	*
	*/
	@Column(name= "saasId")
    @ApiModelProperty(value = "")
	private String saasId;

	/**
	*
	*/
	@Column(name= "title")
    @ApiModelProperty(value = "")
	private String title;

	/**
	*
	*/
	@Column(name= "content")
    @ApiModelProperty(value = "")
	private String content;

	/**
	*
	*/
	@Column(name= "address")
    @ApiModelProperty(value = "")
	private String address;

	/**
	*
	*/
	@Column(name= "errorCode")
    @ApiModelProperty(value = "")
	private String errorCode;

	/**
	*
	*/
	@Column(name= "errorContent")
    @ApiModelProperty(value = "")
	private String errorContent;

}
