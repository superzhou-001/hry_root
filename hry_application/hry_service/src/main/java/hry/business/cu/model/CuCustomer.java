/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-04-26 15:12:26 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import hry.platform.config.model.NewAppDic;
import hry.scm.enterprise.model.UserRelation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> CuCustomer </p>
 *
 * @author: yaoz
 * @Date: 2020-04-26 15:12:26 
 */
@Data
@ApiModel(value = "前端用户信息实体类")
@Table(name="cu_customer")
public class CuCustomer extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 用户名
	*/
	@Column(name= "username")
    @ApiModelProperty(value = "用户名")
	private String username;

	/**
	* 密码
	*/
	@Column(name= "password")
    @ApiModelProperty(value = "密码")
	private String password;

	/**
	* 邮箱
	*/
	@Column(name= "email")
    @ApiModelProperty(value = "邮箱")
	private String email;

	/**
	* 手机号码
	*/
	@Column(name= "mobile")
    @ApiModelProperty(value = "手机号码")
	private String mobile;

	/**
	* 帐号启用状态:0禁用；1启用
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "帐号启用状态:0禁用；1启用")
	private Integer status;


	/**
	* 用户类型:0企业；1个人
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "用户类型:0企业；1个人")
	private Integer type;

	/**
	* 详细地址
	*/
	@Column(name= "address")
    @ApiModelProperty(value = "详细地址")
	private String address;

	/**
	* 
	*/
	@Column(name= "salt")
    @ApiModelProperty(value = "")
	private String salt;

	/**
	* 身份证正面照片
	*/
	@Column(name= "idCardFrontUrl")
    @ApiModelProperty(value = "身份证正面照片")
	private String idCardFrontUrl;

	/**
	* 身份证反面照片
	*/
	@Column(name= "idCardBackUrl")
    @ApiModelProperty(value = "身份证反面照片")
	private String idCardBackUrl;

	/**
	* 授权书
	*/
	@Column(name= "authorizationUrl")
    @ApiModelProperty(value = "授权书")
	private String authorizationUrl;

	/**
	* 标识
	*/
	@Column(name= "identification")
    @ApiModelProperty(value = "标识")
	private String identification;

	/**
	* 用户昵称
	*/
	@Column(name= "nickname")
    @ApiModelProperty(value = "用户昵称")
	private String nickname;

	/**
	 * 用户性别:0男；1女
	 */
	@Column(name= "gender")
	@ApiModelProperty(value = "用户性别:0男；1女")
	private Integer gender;

	/**
	 * 省
	 */
	@Column(name= "province")
	@ApiModelProperty(value = "省")
	private String province;

	/**
	 * 市
	 */
	@Column(name= "city")
	@ApiModelProperty(value = "市")
	private String city;

	/**
	 * 县/区
	 */
	@Column(name= "county")
	@ApiModelProperty(value = "县/区")
	private String county;

	/**
	 * 头像
	 */
	@Column(name= "headPortraitUrl")
	@ApiModelProperty(value = "头像")
	private String headPortraitUrl;


	/**
	 * 1:采购方，2:资金方,3:供应商
	 */
	@ApiModelProperty(value = "1:采购方，2:资金方,3:供应商")
	@Transient
	private Integer userType;

	/**
	 * 企业/资金方/供应商Id
	 */
	@ApiModelProperty(value = "企业/资金方/供应商Id")
	@Transient
	private Long InfoId;

	/**
	 * 企业社会统一信用代码
	 */
	@ApiModelProperty(value = "企业社会统一信用代码")
	@Transient
	private String creditCode;

	/**
	 * 1:管理员（自己创建的企业），2:业务员（别人授权的企业）
	 */
	@ApiModelProperty(value = "1:管理员（自己创建的企业），2:业务员（别人授权的企业）")
	@Transient
	private Integer role;

}
