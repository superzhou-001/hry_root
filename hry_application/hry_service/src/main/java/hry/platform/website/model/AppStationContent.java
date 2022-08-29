/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:14:23 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppStationContent </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:14:23 
 */
@Data
@ApiModel(value = "站内信内容实体类")
@Table(name="app_station_content")
public class AppStationContent extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 标题
	*/
	@Column(name= "contentTitle")
    @ApiModelProperty(value = "标题")
	private String contentTitle;

	/**
	* 发送类型id
	*/
	@Column(name= "typeId")
    @ApiModelProperty(value = "发送类型id")
	private Long typeId;

	/**
	* 收件人id，多个用逗号隔开
	*/
	@Column(name= "receivers")
    @ApiModelProperty(value = "收件人id，多个用逗号隔开")
	private String receivers;

	/**
	* 文章内容
	*/
	@Column(name= "content")
    @ApiModelProperty(value = "文章内容")
	private String content;

	/**
	* 状态 1、已发送 0、未发送 2、已删除
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "状态 1、已发送 0、未发送 2、已删除")
	private Integer status;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

	/**
	 * 用户类型 0 后台用户 1 前台用户
	 */
	@Column(name= "userType")
	@ApiModelProperty(value = "用户类型 0 后台用户 1 前台用户")
	private Integer userType;

}
