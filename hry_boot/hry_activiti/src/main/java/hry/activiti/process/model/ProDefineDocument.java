/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:53:14
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefineDocument </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:53:14
 */
@Data
@ApiModel(value = "流程材料实体类")
@Table(name="pro_define_document")
public class ProDefineDocument extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 节点id
	*/
	@Column(name= "nodeId")
    @ApiModelProperty(value = "节点id")
	private Long nodeId;

	/**
	* 材料名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "材料名称")
	private String name;

	/**
	* 材料key
	*/
	@Column(name= "fileKey")
    @ApiModelProperty(value = "材料key")
	private String fileKey;


	@Column(name= "isRequire")
	@ApiModelProperty(value = "是否必传")
	private Integer isRequire; //0否1是


	/**
	 * 	1、编辑
	 * 	2、审核
	 * 	3、预览
	 */
	@Column(name= "viewModel")
	@ApiModelProperty(value = "显示状态")
	private Integer viewModel;



}
