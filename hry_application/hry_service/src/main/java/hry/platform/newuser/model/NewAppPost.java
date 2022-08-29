/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:11:00 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> NewAppPost </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:11:00 
 */
@Data
@ApiModel(value = "岗位配置实体类")
@Table(name="new_app_post")
public class NewAppPost extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 岗位名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "岗位名称")
	private String name;

	/**
	* 岗位别名
	*/
	@Column(name= "anothername")
    @ApiModelProperty(value = "岗位别名")
	private String anothername;

	/**
	* 岗位说明
	*/
	@Column(name= "details")
    @ApiModelProperty(value = "岗位说明")
	private String details;

	/**
	* 父级岗位ID
	*/
	@Column(name= "pid")
    @ApiModelProperty(value = "父级岗位ID")
	private Long pid;

	/**
	* 岗位节点类型 root 根节点 post 子节点
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "岗位节点类型 root 根节点 post 子节点")
	private String type;

	/**
	* 排序号
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序号")
	private Integer sort;

	@Transient
	private List<NewAppPost> children;


}
