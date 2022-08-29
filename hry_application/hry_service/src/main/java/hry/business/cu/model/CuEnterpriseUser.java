/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 17:39:48 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuEnterpriseUser </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 17:39:48 
 */
@Data
@ApiModel(value = "正式客户分配中间表实体类")
@Table(name="cu_enterprise_user")
public class CuEnterpriseUser extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 后台管理用户id
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "后台管理用户id")
	private Long userId;

	/**
	* 企业客户id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业客户id")
	private Long enterpriseId;

	/**
	* 分配人id
	*/
	@Column(name= "distributionUserId")
    @ApiModelProperty(value = "分配人id")
	private Long distributionUserId;

}
