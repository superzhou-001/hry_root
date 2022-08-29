/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 10:19:39 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CuUpdateLog </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 10:19:39 
 */
@Data
@ApiModel(value = "信息修改记录实体类")
@Table(name="cu_update_log")
public class CuUpdateLog extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 创建人id
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "创建人id")
	private Long userId;

	/**
	* 创建人id
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "创建人id")
	private String userName;

	/**
	* 表
	*/
	@Column(name= "tableTabel")
    @ApiModelProperty(value = "表")
	private String tableTabel;

	/**
	* 表主键id
	*/
	@Column(name= "tableId")
    @ApiModelProperty(value = "表主键id")
	private Long tableId;

	/**
	* 字段
	*/
	@Column(name= "tableField")
    @ApiModelProperty(value = "字段")
	private String tableField;

	/**
	* 字段说明
	*/
	@Column(name= "fieldReamrk")
    @ApiModelProperty(value = "字段说明")
	private String fieldReamrk;

	/**
	* 原值
	*/
	@Column(name= "oldValue")
    @ApiModelProperty(value = "原值")
	private String oldValue;

	/**
	* 新值
	*/
	@Column(name= "newValue")
    @ApiModelProperty(value = "新值")
	private String newValue;

	/**
	* 备注
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "备注")
	private String remark;

}
