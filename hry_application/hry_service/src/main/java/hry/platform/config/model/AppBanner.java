/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-05-09 14:12:32 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppBanner </p>
 *
 * @author: zhouming
 * @Date: 2020-05-09 14:12:32 
 */
@Data
@ApiModel(value = "banner配置实体类")
@Table(name="app_banner")
public class AppBanner extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* banner名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "banner名称")
	private String name;

	/**
	* 类型key
	*/
	@Column(name= "bannerKey")
    @ApiModelProperty(value = "类型key")
	private String bannerKey;

	/**
	* banner路径
	*/
	@Column(name= "filePath")
    @ApiModelProperty(value = "banner路径")
	private String filePath;

	/**
	* banner状态 是否删除 0否 1是
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "banner状态 是否删除 0否 1是")
	private Integer status;

	/**
	* 是否显示 0不显示  1显示
	*/
	@Column(name= "isShow")
    @ApiModelProperty(value = "是否显示 0不显示  1显示")
	private Integer isShow;

	/**
	* 排序的序号
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序的序号")
	private Integer sort;

	/**
	* 0 pc端 1 app端
	*/
	@Column(name= "bannerType")
    @ApiModelProperty(value = "0 pc端 1 app端")
	private Integer bannerType;

}
