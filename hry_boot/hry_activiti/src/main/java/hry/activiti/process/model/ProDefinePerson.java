/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:42:17
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefinePerson </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:42:17
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_define_person")
public class ProDefinePerson extends BaseModel {

	/**
	 * 发起人
	 */
	public final static Integer PERSONTYPE_1 = 1;
	/**
	 * 指定人
	 */
	public final static Integer PERSONTYPE_2 = 2;
	/**
	 * 人员组
	 */
	public final static Integer PERSONTYPE_3 = 3;
	/**
	 * 上一步执行人
	 */
	public final static Integer PERSONTYPE_4 = 4;

	/**
	 * 会签人员
	 */
	public final static Integer PERSONTYPE_5 = 5;

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
	* 人员类型1发起人2指定人3人员组
	*/
	@Column(name= "personType")
    @ApiModelProperty(value = "人员类型1发起人2指定人3人员组4上一步执行人")
	private Integer personType;

	/**
	* 人员内容
	*/
	@Column(name= "personValue")
    @ApiModelProperty(value = "人员内容")
	private String personValue;

	@Transient
	@ApiModelProperty(value = "人员/人员组名称")
	private String personName;

}
