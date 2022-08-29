/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-28 15:16:11 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> NewAppUserUpordown </p>
 *
 * @author: zhouming
 * @Date: 2020-06-28 15:16:11 
 */
@Data
@ApiModel(value = "用户上下级配置实体类")
@Table(name="new_app_user_upordown")
public class NewAppUserUpordown extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 用户id
	*/
	@Column(name= "userid")
    @ApiModelProperty(value = "用户id")
	private Long userid;

	/**
	* 关联用户id
	*/
	@Column(name= "otheruserid")
    @ApiModelProperty(value = "关联用户id")
	private Long otheruserid;

	/**
	* 0 上级 1 下级
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "0 上级 1 下级")
	private Integer type;

	@Transient
	private String name; // 姓名

	@Transient
	private String islock; // 是否禁用 0 否 1 是

	@Transient
	private Long organizationid; // 组织id

	@Transient
	private String organizationName; // 组织名称

}
