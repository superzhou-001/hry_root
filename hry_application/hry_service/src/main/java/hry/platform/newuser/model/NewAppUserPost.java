/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:13:47 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


/**
 * <p> NewAppUserPost </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:13:47 
 */
@Data
@ApiModel(value = "用户岗位关系配置实体类")
@Table(name="new_app_user_post")
public class NewAppUserPost extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 岗位id
	*/
	@Column(name= "postid")
    @ApiModelProperty(value = "岗位id")
	private Long postid;

	/**
	* 用户id
	*/
	@Column(name= "userid")
    @ApiModelProperty(value = "用户id")
	private Long userid;

	@Transient
	private String postName; // 岗位名称

	@Transient
	private String userName;  //用户名--账户

	@Transient
	private String name; // 姓名

	@Transient
	private String organizationid; // 组织id

	@Transient
	private String organizationName; // 组织名称

	@Transient
	private Integer islock;  //是否锁定，0否1是

}
