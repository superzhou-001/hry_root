/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:18:03 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> NewAppGroup </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:18:03 
 */
@Data
@ApiModel(value = "用户组配置实体类")
@Table(name="new_app_group")
public class NewAppGroup extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 组名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "组名称")
	private String name;

	/**
	* 组别名
	*/
	@Column(name= "anothername")
    @ApiModelProperty(value = "组别名")
	private String anothername;

	/**
	* 组说明
	*/
	@Column(name= "details")
    @ApiModelProperty(value = "组说明")
	private String details;

	/**
	* 父级组ID
	*/
	@Column(name= "pid")
    @ApiModelProperty(value = "父级组ID")
	private Long pid;

	/**
	* 组节点类型 root 根节点   	group 子节点
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "组节点类型 root 根节点   	group 子节点")
	private String type;

	/**
	* 排序号
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序号")
	private Integer sort;

	@Transient
	private List<NewAppUserGroup> userGroupList;

	@Transient
	@ApiModelProperty(value = "组下成员Id, 用英文逗号连接")
	private String userIds;

}
