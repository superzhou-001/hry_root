/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-06-30 11:37:30 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> NewAppOrganizationCharge </p>
 *
 * @author: zhouming
 * @Date: 2020-06-30 11:37:30 
 */
@Data
@ApiModel(value = "组织负责人关系表实体类")
@Table(name="new_app_organization_charge")
public class NewAppOrganizationCharge extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 组织id
	*/
	@Column(name= "organizationid")
    @ApiModelProperty(value = "组织id")
	private Long organizationid;

	@Transient
	private String organizationName; // 组织名称
	/**
	* 用户id
	*/
	@Column(name= "userid")
    @ApiModelProperty(value = "用户id")
	private Long userid;

	@Transient
	private String userName;  //用户名--账户

	@Transient
	private String name; // 姓名

	@Transient
	private Integer islock;  //是否锁定，0否1是
}
