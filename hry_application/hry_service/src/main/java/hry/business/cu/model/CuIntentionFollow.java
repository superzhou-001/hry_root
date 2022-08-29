/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-19 15:21:33 
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
 * <p> CuIntentionFollow </p>
 *
 * @author: yaoz
 * @Date: 2020-05-19 15:21:33 
 */
@Data
@ApiModel(value = "意向跟进信息实体类")
@Table(name="cu_intention_follow")
public class CuIntentionFollow extends BaseModel {

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
	* 跟进人id
	*/
	@Column(name= "followUserId")
    @ApiModelProperty(value = "跟进人id")
	private Long followUserId;

	/**
	* 跟进人
	*/
	@Column(name= "followUserName")
    @ApiModelProperty(value = "跟进人")
	private String followUserName;

	/**
	* 跟进时间
	*/
	@Column(name= "followDate")
    @ApiModelProperty(value = "跟进时间")
	private Date followDate;

	/**
	* 跟进方式
	*/
	@Column(name= "followType")
    @ApiModelProperty(value = "跟进方式")
	private Integer followType;

	/**
	* 跟进结果
	*/
	@Column(name= "followResult")
    @ApiModelProperty(value = "跟进结果")
	private Integer followResult;

	/**
	* 跟进标题
	*/
	@Column(name= "followTitle")
    @ApiModelProperty(value = "跟进标题")
	private String followTitle;


	/**
	 * 跟进目的
	 */
	@Column(name= "followoBjective")
	@ApiModelProperty(value = "跟进目的")
	private Integer followoBjective;


	/**
	* 跟进内容
	*/
	@Column(name= "followContent")
    @ApiModelProperty(value = "跟进内容")
	private String followContent;

	/**
	* 计划下次跟进时间
	*/
	@Column(name= "planFollowDate")
    @ApiModelProperty(value = "计划下次跟进时间")
	private Date planFollowDate;

	/**
	* 计划下次跟进方式
	*/
	@Column(name= "planFollowType")
    @ApiModelProperty(value = "计划下次跟进方式")
	private Integer planFollowType;

	/**
	* 计划下次跟进内容
	*/
	@Column(name= "planFollowContent")
    @ApiModelProperty(value = "计划下次跟进内容")
	private String planFollowContent;

	/**
	* 跟进目的
	*/
	@Column(name= "planfollowoBjective")
    @ApiModelProperty(value = "跟进目的")
	private Integer planfollowoBjective;

	/**
	* 计划跟进人id
	*/
	@Column(name= "planFollowUserId")
    @ApiModelProperty(value = "计划跟进人id")
	private Long planFollowUserId;

	/**
	* 计划跟进人
	*/
	@Column(name= "planFollowUserName")
    @ApiModelProperty(value = "计划跟进人")
	private String planFollowUserName;

	/**
	 * 跟进状态 1.待跟进 2.已完成
	 */
	@Column(name= "planFollowStatus")
	@ApiModelProperty(value = "跟进状态 1.待跟进 2.已完成")
	private Integer planFollowStatus;

	/**
	 * 是否紧急 1是 2否
	 */
	@Column(name= "isUrgent")
	@ApiModelProperty(value = "是否紧急 1是 2否")
	private Integer isUrgent;

	/**
	 * 是否短信 1是 2否
	 */
	@Column(name= "isMessage")
	@ApiModelProperty(value = "是否短信 1是 2否")
	private Integer isMessage;

	/**
	 * 评论信息
	 */
	@Transient
	@ApiModelProperty(value = "评论信息")
	private List<CuIntentionFollowComment> list;

}
