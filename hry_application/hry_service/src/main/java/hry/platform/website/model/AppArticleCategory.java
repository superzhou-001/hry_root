/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:11:54 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> AppArticleCategory </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:11:54 
 */
@Data
@ApiModel(value = "网站内容分类实体类")
@Table(name="app_article_category")
public class AppArticleCategory extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 分类名称
	*/
	@Column(name= "categoryName")
    @ApiModelProperty(value = "分类名称")
	private String categoryName;

	/**
	* 分类key
	*/
	@Column(name= "categoryKey")
    @ApiModelProperty(value = "分类key")
	private String categoryKey; // 根节点 root

	/**
	* 父分类名称
	*/
	@Column(name= "pCategoryName")
    @ApiModelProperty(value = "父分类名称")
	private String pCategoryName;

	/**
	* 父分类key
	*/
	@Column(name= "pCategoryKey")
    @ApiModelProperty(value = "父分类key")
	private String pCategoryKey;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 是否显示 1、是 0、否
	*/
	@Column(name= "isShow")
    @ApiModelProperty(value = "是否显示 1、是 0、否")
	private Integer isShow;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

	@Column(name= "isPage")
	@ApiModelProperty(value = "是否显示列表  0 文章列表 1 单页面  2菜单项")
	private Integer isPage; // 是否显示列表  0 文章列表 1 单页面  2菜单项

	@Transient
	private List<AppArticleCategory> children;


}
