/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:41:09 
 */
package hry.scm.enterprise.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> UserRelation </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:41:09 
 */
@Data
@ApiModel(value = "供应链用户关系信息实体类")
@Table(name="scm_user_relation")
public class UserRelation extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 注册用户Id
	*/
	@Column(name= "customerId")
    @ApiModelProperty(value = "注册用户Id")
	private Long customerId;

	/**
	* 详细主体id
	*/
	@Column(name= "infoId")
    @ApiModelProperty(value = "详细主体id")
	private Long infoId;

	/**
	* 1:采购方，2:资金方,3:供应商
	*/
	@Column(name= "userType")
    @ApiModelProperty(value = "1:采购方，2:资金方,3:供应商")
	private Integer userType;

	/**
	 * 1:管理员（自己创建的企业），2:业务员（别人授权的企业）
	 */
	@Column(name= "role")
	@ApiModelProperty(value = "1:管理员（自己创建的企业），2:业务员（别人授权的企业）")
	private Integer role;


	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	@Transient
	private String username;

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	@Transient
	private String mobile;

	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	@Transient
	private String email;

}
