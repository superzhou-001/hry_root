/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:32:45 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> FaCostNlms </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:32:45 
 */
@Data
@ApiModel(value = "费用计算公式实体类")
@Table(name="fa_cost_nlms")
public class FaCostNlms extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 公式名称
	*/
	@Column(name= "nlmsname")
    @ApiModelProperty(value = "公式名称")
	private String nlmsname;

	/**
	* 公式唯一标识
	*/
	@Column(name= "nlmsuid")
    @ApiModelProperty(value = "公式唯一标识")
	private String nlmsuid;

	/**
	* 公式
	*/
	@Column(name= "nlms")
    @ApiModelProperty(value = "公式")
	private String nlms;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

}
