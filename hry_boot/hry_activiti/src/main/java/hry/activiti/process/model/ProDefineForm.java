/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:07
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> ProDefineForm </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:07
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_define_form")
public class ProDefineForm extends BaseModel {

	public final static Integer TYPE_1 = 1;
	public final static Integer TYPE_2 = 2;

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
	* 表单ID
	*/
	@Column(name= "formId")
    @ApiModelProperty(value = "表单ID")
	private Long formId;

	/**
	* 1主表单2子表单
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "1主表单2子表单")
	private Integer type;

	/**
	 * 是否可编辑
	 */
	@Column(name= "isEdit")
	@ApiModelProperty(value = "是否可编辑")
	private Integer isEdit;

	@Transient
	@ApiModelProperty(value = "表单名称")
	private String formName;

	@Transient
	@ApiModelProperty(value = "表单key")
	private String formKey;

	@Transient
	private List<ProDefineForm> sonFormList; //子表单列表


}
