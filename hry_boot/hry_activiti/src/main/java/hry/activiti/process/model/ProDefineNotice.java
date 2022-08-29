/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:56
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefineNotice </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:56
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_define_notice")
public class ProDefineNotice extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 流程节点id
	*/
	@Column(name= "nodeId")
    @ApiModelProperty(value = "流程节点id")
	private Long nodeId;

	/**
	* 通知类型1站内信2短信3邮件
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "通知类型1站内信2短信3邮件")
	private Integer type;

	/**
	* 模板ID
	*/
	@Column(name= "templateId")
    @ApiModelProperty(value = "模板ID")
	private Long templateId;


	/**
	 * 通知人
	 */
	@Column(name= "personValue")
	@ApiModelProperty(value = "通知人")
	private String personValue;



}
