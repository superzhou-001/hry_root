/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-13 16:04:23 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuIntentionUser </p>
 *
 * @author: yaoz
 * @Date: 2020-05-13 16:04:23 
 */
@Data
@ApiModel(value = "意向客户分配中间表")
@Table(name="cu_intention_user")
public class CuIntentionUser extends BaseModel {

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
	* 意向客户id
	*/
	@Column(name= "intentionId")
    @ApiModelProperty(value = "意向客户id")
	private Long intentionId;

	/**
	* 分配人id
	*/
	@Column(name= "distributionUserId")
    @ApiModelProperty(value = "分配人id")
	private Long distributionUserId;

}
