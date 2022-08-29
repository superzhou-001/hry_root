/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-04-01 17:10:31 
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> AdContentManage </p>
 *
 * @author: liuchenghui
 * @Date: 2020-04-01 17:10:31 
 */
@Data
@ApiModel(value = "广告内容管理实体类")
@Table(name="ad_content_manage")
public class AdContentManage extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 广告位置id
	*/
	@Column(name= "adpId")
    @ApiModelProperty(value = "广告位置id")
	private Long adpId;

	@Transient
	@ApiModelProperty(value = "广告位置名称")
	private String adPositionName;
	/**
	* 广告标题
	*/
	@Column(name= "adContentTitle")
    @ApiModelProperty(value = "广告标题")
	private String adContentTitle;

	/**
	* 是否允许外链 1、是 0、否
	*/
	@Column(name= "isAllowExtLink")
    @ApiModelProperty(value = "是否允许外链 1、是 0、否")
	private Integer isAllowExtLink;

	/**
	* 跳转链接
	*/
	@Column(name= "adJumpLink")
    @ApiModelProperty(value = "跳转链接")
	private String adJumpLink;

	/**
	* 图片路径
	*/
	@Column(name= "adBanner")
    @ApiModelProperty(value = "图片路径")
	private String adBanner;

	/**
	* 开始时间
	*/
	@Column(name= "startTime")
    @ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	* 结束时间
	*/
	@Column(name= "endTime")
    @ApiModelProperty(value = "结束时间")
	private Date endTime;

	/**
	* 内容一
	*/
	@Column(name= "contentOne")
    @ApiModelProperty(value = "内容一")
	private String contentOne;

	/**
	* 内容二
	*/
	@Column(name= "contentTwo")
    @ApiModelProperty(value = "内容二")
	private String contentTwo;

	/**
	* 内容三
	*/
	@Column(name= "contentThree")
    @ApiModelProperty(value = "内容三")
	private String contentThree;

	/**
	* 内容四
	*/
	@Column(name= "contentFour")
    @ApiModelProperty(value = "内容四")
	private String contentFour;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

	/**
	 * 获取标识
	 */
	@Transient
	@ApiModelProperty(value = "获取标识")
	private String adPositionKey;

}
