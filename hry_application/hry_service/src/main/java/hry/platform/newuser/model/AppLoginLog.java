/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-14 14:18:11 
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.zxp.esclientrhl.annotation.ESID;
import org.zxp.esclientrhl.annotation.ESMapping;
import org.zxp.esclientrhl.annotation.ESMetaData;
import org.zxp.esclientrhl.enums.DataType;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> AppLoginLog </p>
 *
 * @author: zhouming
 * @Date: 2020-08-14 14:18:11 
 */
@Data
@ApiModel(value = "用户登录日志实体类")
@Table(name="app_login_log")
@ESMetaData(indexName = "loginlog", number_of_shards = 5, number_of_replicas = 1)
public class AppLoginLog extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@ESID
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	@ESMapping(datatype = DataType.long_type)
	private Long id;

	@Column(name= "loginId")
	@ApiModelProperty(value = "登录唯一标识")
	private String loginId;
	/**
	* 用户ID
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "用户ID")
	@ESMapping(datatype = DataType.long_type)
	private Long userId;

	/**
	* 用户名
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "用户名")
	private String userName;

	/**
	* 登录时间
	*/
	@Column(name= "loginTime")
    @ApiModelProperty(value = "登录时间")
	private Date loginTime;

	/**
	* 登出时间
	*/
	@Column(name= "logoutTime")
    @ApiModelProperty(value = "登出时间")
	private Date logoutTime;

	@Column(name= "onlineTime")
	@ApiModelProperty(value = "登录时长")
	private String onlineTime;
	/**
	* ip
	*/
	@Column(name= "ip")
    @ApiModelProperty(value = "ip")
	private String ip;

	@ESMapping(datatype = DataType.long_type)
	@Transient
	private Long loginDate; // 登录时间
}
