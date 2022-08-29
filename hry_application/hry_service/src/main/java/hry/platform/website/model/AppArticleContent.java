/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:12:35 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppArticleContent </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:12:35 
 */
@Data
@ApiModel(value = "网站内容发布实体类")
@Table(name="app_article_content")
public class AppArticleContent extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 内容分类id
	*/
	@Column(name= "categoryId")
    @ApiModelProperty(value = "内容分类id")
	private Long categoryId;

	@Transient
	@ApiModelProperty(value = "内容分类名称")
	private String categoryName;
	/**
	* 文章标题
	*/
	@Column(name= "title")
    @ApiModelProperty(value = "文章标题")
	private String title;

	/**
	* 是否显示 1、是 0、否
	*/
	@Column(name= "isShow")
    @ApiModelProperty(value = "是否显示 1、是 0、否")
	private Integer isShow;

	/**
	* seo标题
	*/
	@Column(name= "seoTitle")
    @ApiModelProperty(value = "seo标题")
	private String seoTitle;

	/**
	* seo关键字
	*/
	@Column(name= "seoKeyword")
    @ApiModelProperty(value = "seo关键字")
	private String seoKeyword;

	/**
	* seo描述
	*/
	@Column(name= "seoDescribe")
    @ApiModelProperty(value = "seo描述")
	private String seoDescribe;

	/**
	* 是否置顶 1、是 0、否
	*/
	@Column(name= "isStick")
    @ApiModelProperty(value = "是否置顶 1、是 0、否")
	private Integer isStick;

	/**
	* 文章排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "文章排序")
	private Integer sort;

	/**
	* 作者
	*/
	@Column(name= "writer")
    @ApiModelProperty(value = "作者")
	private String writer;

	/**
	* 来源
	*/
	@Column(name= "source")
    @ApiModelProperty(value = "来源")
	private String source;

	/**
	* 是否允许外链 1、是 0、否
	*/
	@Column(name= "isOutLink")
    @ApiModelProperty(value = "是否允许外链 1、是 0、否")
	private Integer isOutLink;

	@Column(name= "outLinkUrl")
	@ApiModelProperty(value = "外链地址")
	private String outLinkUrl;
	/**
	* 权限控制
	*/
	@Column(name= "authorityControl")
    @ApiModelProperty(value = "权限控制 : 0 游客 1会员")
	private Integer authorityControl;

	/**
	* 文章logo图片地址
	*/
	@Column(name= "logoImage")
    @ApiModelProperty(value = "文章logo图片地址")
	private String logoImage;

	/**
	* 文章摘要
	*/
	@Column(name= "shortContent")
    @ApiModelProperty(value = "文章摘要")
	private String shortContent;

	/**
	* 文章正文
	*/
	@Column(name= "content")
    @ApiModelProperty(value = "文章正文")
	private String content;

	@Column(name= "accessoryName")
	@ApiModelProperty(value = "附件名称")
	private String accessoryName;

	@Column(name= "accessoryUrl")
	@ApiModelProperty(value = "附件地址")
	private String accessoryUrl;

	@Column(name= "labelName")
	@ApiModelProperty(value = "标签")
	private String labelName;



}
