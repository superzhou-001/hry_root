/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:08:12
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> WebsiteNavigationManage </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:08:12
 */
@Data
@ApiModel(value = "网站导航管理实体类")
@Table(name="website_navigation_manage")
public class WebsiteNavigationManage extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 导航key
	*/
	@Column(name= "nkey")
    @ApiModelProperty(value = "导航key")
	private String nkey;

	/**
	* 标题
	*/
	@Column(name= "siteTitle")
    @ApiModelProperty(value = "标题")
	private String siteTitle;

	@Column(name= "title")
	@ApiModelProperty(value = "路由title")
	private String title;
	/**
	* 链接地址
	*/
	@Column(name= "siteUrl")
    @ApiModelProperty(value = "链接地址")
	private String siteUrl;

	/**
	* 关键字
	*/
	@Column(name= "keywords")
    @ApiModelProperty(value = "关键字")
	private String keywords;

	/**
	* 描述
	*/
	@Column(name= "description")
    @ApiModelProperty(value = "描述")
	private String description;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 上级导航key，根默认为root
	*/
	@Column(name= "pkey")
    @ApiModelProperty(value = "上级导航key，根默认为root")
	private String pkey;

	/**
	* 是否新窗口打开 1、是 0、否
	*/
	@Column(name= "isBlank")
    @ApiModelProperty(value = "是否新窗口打开 1、是 0、否")
	private Integer isBlank;

	/**
	* 是否hot 1、是 0、否
	*/
	@Column(name= "isHot")
    @ApiModelProperty(value = "是否hot 1、是 0、否")
	private Integer isHot;

	/**
	* 是否允许外链 1、是 0、否
	*/
	@Column(name= "isOutLink")
    @ApiModelProperty(value = "是否允许外链 1、是 0、否")
	private Integer isOutLink;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;
	@Transient
	private List<WebsiteNavigationManage> children;
}
