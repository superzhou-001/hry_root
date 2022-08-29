/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:57 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> SysProtocolManage </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:57 
 */
@Data
@ApiModel(value = "系统协议管理实体类")
@Table(name="sys_protocol_manage")
public class SysProtocolManage extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 协议标题
	*/
	@Column(name= "protocolTitle")
    @ApiModelProperty(value = "协议标题")
	private String protocolTitle;

	/**
	* 获取标识
	*/
	@Column(name= "protocolKey")
    @ApiModelProperty(value = "获取标识")
	private String protocolKey;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 是否启用 1、是 0、否
	*/
	@Column(name= "isDisable")
    @ApiModelProperty(value = "是否启用 1、是 0、否")
	private Integer isDisable;

	/**
	* 协议标题
	*/
	@Column(name= "protocolContent")
    @ApiModelProperty(value = "协议内容")
	private String protocolContent;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

}
