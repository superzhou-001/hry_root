/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:09:41 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AdPositionManage </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:09:41 
 */
@Data
@ApiModel(value = "广告位置管理实体类")
@Table(name="ad_position_manage")
public class AdPositionManage extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 广告位置名称
	*/
	@Column(name= "adPositionName")
    @ApiModelProperty(value = "广告位置名称")
	private String adPositionName;

	/**
	* 获取标识
	*/
	@Column(name= "adPositionKey")
    @ApiModelProperty(value = "获取标识")
	private String adPositionKey;

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
	* 说明
	*/
	@Column(name= "adPositionDesc")
    @ApiModelProperty(value = "说明")
	private String adPositionDesc;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

}
