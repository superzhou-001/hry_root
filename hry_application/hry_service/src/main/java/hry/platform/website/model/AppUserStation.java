/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-13 14:57:20 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> AppUserStation </p>
 *
 * @author: zhouming
 * @Date: 2020-08-13 14:57:20 
 */
@Data
@ApiModel(value = "用户信件表实体类")
@Table(name="app_user_station")
public class AppUserStation extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 用户ID
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	* 信件ID
	*/
	@Column(name= "stationId")
    @ApiModelProperty(value = "信件ID")
	private Long stationId;

	/**
	* 阅读时间
	*/
	@Column(name= "readTime")
    @ApiModelProperty(value = "阅读时间")
	private Date readTime;

	/**
	* 状态 0 未阅读 1 已阅读 2 已删除
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "状态 0 未阅读 1 已阅读 2 已删除")
	private Integer status;

	/**
	 * 用户类型 0 后台用户 1 前台用户
	 */
	@Column(name= "userType")
	@ApiModelProperty(value = "用户类型 0 后台用户 1 前台用户")
	private Integer userType;

	@Transient
	private String contentTitle; // 信件标题

	@Transient
	private String content; // 信件内容


}
