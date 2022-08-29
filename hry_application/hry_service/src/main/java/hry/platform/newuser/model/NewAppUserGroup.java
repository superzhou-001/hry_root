/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:19:39 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> NewAppUserGroup </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:19:39 
 */
@Data
@ApiModel(value = "用户组关系配置实体类")
@Table(name="new_app_user_group")
public class NewAppUserGroup extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 组id
	*/
	@Column(name= "groupid")
    @ApiModelProperty(value = "组id")
	private Long groupid;

	@Transient
	private String groupName; // 组名称

	/**
	* 用户id
	*/
	@Column(name= "userid")
    @ApiModelProperty(value = "用户id")
	private Long userid;

	/*
	* 用户部门Id
	* */
	@Transient
	private Long organizationId;

	@Transient
	private String name; // 用户名称

	@Transient
	private String islock; // 是否禁用 0 否 1 是

}
