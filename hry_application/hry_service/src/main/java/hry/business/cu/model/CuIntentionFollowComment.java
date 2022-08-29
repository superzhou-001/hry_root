/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 10:04:39 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * <p> CuIntentionFollowComment </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 10:04:39 
 */
@Data
@ApiModel(value = "意向跟进信息评论表实体类")
@Table(name="cu_intention_follow_comment")
public class CuIntentionFollowComment extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 意向客户主体id
	*/
	@Column(name= "intentionId")
    @ApiModelProperty(value = "意向客户主体id")
	private Long intentionId;

	/**
	* 意向客户子表id
	*/
	@Column(name= "intentionInfoId")
    @ApiModelProperty(value = "意向客户子表id")
	private Long intentionInfoId;

	/**
	* 意向客户跟进信息id
	*/
	@Column(name= "intentionfollowId")
    @ApiModelProperty(value = "意向客户跟进信息id")
	private Long intentionfollowId;

	/**
	* 父节点id
	*/
	@Column(name= "parentId")
    @ApiModelProperty(value = "父节点id")
	private Long parentId;

	/**
	* 评论内容
	*/
	@Column(name= "content")
    @ApiModelProperty(value = "评论内容")
	private String content;

	/**
	* 评论人id
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "评论人id")
	private Long userId;

	/**
	* 评论人
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "评论人")
	private String userName;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 评论信息
	 */
	@Transient
	@ApiModelProperty(value = "评论信息")
	private List<CuIntentionFollowComment> list;


	/**
	 * 父评论人
	 */
	@Transient
	@ApiModelProperty(value = "父评论人")
	private String parentUserName;

	/**
	 * 父评论
	 */
	@Transient
	@ApiModelProperty(value = "父评论")
	private String parentContent;
	/**
	 * 父评论时间
	 */
	@Transient
	@ApiModelProperty(value = "父评论时间")
	private Date parentCreated;

}
