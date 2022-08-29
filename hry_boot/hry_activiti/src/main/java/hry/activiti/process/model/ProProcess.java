/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:55:47
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProProcess </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:55:47
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_process")
public class ProProcess extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 流程定义id
	*/
	@Column(name= "defineId")
    @ApiModelProperty(value = "流程定义id")
	private Long defineId;

	/**
	 * 流程部署key
	 */
	@Column(name= "pdKey")
	@ApiModelProperty(value = "流程部署key")
	private String pdKey;

	/**
	 * 流程版本
	 */
	@Column(name= "version")
	@ApiModelProperty(value = "流程版本")
	private Integer version;

	/**
	* 流程名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "流程名称")
	private String name;

	/**
	* 流程状态1运行中，2结束
	*/
	@Column(name= "state")
    @ApiModelProperty(value = "流程状态1运行中，2结束")
	private Integer state;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;


	/**
	 * 流程实例id
	 */
	@Column(name= "piId")
	@ApiModelProperty(value = "流程实例id")
	private String piId;

}
