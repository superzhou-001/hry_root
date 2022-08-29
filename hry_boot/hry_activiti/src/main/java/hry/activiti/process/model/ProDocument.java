/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:29
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDocument </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:29
 */
@Data
@ApiModel(value = "材料子项实体类")
@Table(name="pro_document")
public class ProDocument extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 分类id
	*/
	@Column(name= "classId")
    @ApiModelProperty(value = "分类id")
	private Long classId;

	/**
	* 分类名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "分类名称")
	private String name;

	/**
	* 分类图标
	*/
	@Column(name= "icon")
    @ApiModelProperty(value = "分类图标")
	private String icon;

	/**
	* 1启用0未启用
	*/
	@Column(name= "isEnable")
    @ApiModelProperty(value = "1启用0未启用")
	private Integer isEnable;

	/**
	* 样例
	*/
	@Column(name= "demoFile")
    @ApiModelProperty(value = "样例")
	private String demoFile;

	/**
	* 说明
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "说明")
	private String remark;

	/**
	 * 材料key
	 */
	@Column(name= "fileKey")
	@ApiModelProperty(value = "材料key")
	private String fileKey;


	@Transient
	@ApiModelProperty(value = "类型名称")
	private String className;

	@Transient
	@ApiModelProperty(value = "是否必传")
	private Integer isRequire; //0否1是


	/**
	 * 	1、编辑
	 * 	2、审核
	 * 	3、预览
	 */
	@Transient
	@ApiModelProperty(value = "显示状态")
	private Integer viewModel;





}
