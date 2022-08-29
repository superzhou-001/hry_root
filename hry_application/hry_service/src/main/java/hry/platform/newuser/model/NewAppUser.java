/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-05-27 16:05:55
 */
package hry.platform.newuser.model;


import hry.bean.BaseModel;
import hry.security.jwt.JWTUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * <p> AppUser </p>
 * @author:         liushilei
 * @Date :          2017-05-27 16:05:55
 */
@Table(name="new_app_user")
@Data
public class NewAppUser extends BaseModel implements JWTUser {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@ApiModelProperty(value = "用户ID")
	private Long id;  //id

	@Column(name= "userName")
	@ApiModelProperty(value = "用户名")
	private String userName;  //用户名

	@Column(name= "passWord")
	@ApiModelProperty(value = "密码")
	private String passWord;  //密码

	@Column(name= "salt")
	@ApiModelProperty(value = "盐值")
	private String salt;  //盐值

	@Column(name= "isdelete")
	@ApiModelProperty(value = "是否删除，0否1是")
	private Integer isdelete;  //是否删除，0否1是

	@Column(name= "islock")
	@ApiModelProperty(value = "是否锁定，0否1是")
	private Integer islock;  //是否锁定，0否1是

	@Column(name= "created")
	@ApiModelProperty(value = "创建时间")
	private Date created;  //created

	@Column(name= "modified")
	@ApiModelProperty(value = "修改时间")
	private Date modified;  //modified

	@Column(name= "name")
	@ApiModelProperty(value = "姓名")
	private String name;  //姓名

	@Column(name= "email")
	@ApiModelProperty(value = "邮箱")
	private String email;  //邮箱

	@Column(name= "mobile")
	@ApiModelProperty(value = "手机号")
	private String mobile;  //手机号

	@Column(name= "weChat")
	@ApiModelProperty(value = "微信号")
	private String weChat;  //微信号

	@Column(name= "address")
	@ApiModelProperty(value = "地址")
	private String address;  //地址

	@Column(name= "ukeyState")
	@ApiModelProperty(value = "UKey状态 0关闭 1开启")
	private Integer ukeyState; // UKey状态 0 关闭 1 开启

	@Column(name= "ukeyNo")
	@ApiModelProperty(value = "Ukey编号")
	private String ukeyNo; // Ukey编号

	@Column(name= "googleState")
	@ApiModelProperty(value = "谷歌状态 0关闭 1开启")
	private Integer googleState; // 谷歌状态 0 关闭 1 开启

	@Column(name= "googleKey")
	@ApiModelProperty(value = "谷歌key")
	private String googleKey; // 谷歌key

	@Column(name= "headImage")
	@ApiModelProperty(value = "用户头像")
	private String headImage;

	@Column(name= "weChatAuthLogin")
	@ApiModelProperty(value = "是否授权微信登录 0 未授权 1 已授权")
	private Integer weChatAuthLogin;

	@Transient
	private String roleName;//角色名称
	@Transient
	private Long roleId; // 角色id
	@Transient
	private String departmentName;//部门名称

	@Transient
	@ApiModelProperty(value = "部门id")
	private Long departmentNameId;//部门id

	@Transient
	@ApiModelProperty(value = "岗位集合")
	private String postIds;//岗位Id集合

	@Transient
	@ApiModelProperty(value = "角色集合")
	private String roleIds;//岗位Id集合

	@Transient
	@ApiModelProperty(value = "用户组集合")
	private String groupIds;//用户组集合

	@Transient
	@ApiModelProperty(value = "用户登录时间")
	private Date loginTime;

	@Transient
	@ApiModelProperty(value = "是否是超级管理员 0 否 1 是")
	private Integer superAdmin;


	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String getUserName() {
		return userName;
	}
}
