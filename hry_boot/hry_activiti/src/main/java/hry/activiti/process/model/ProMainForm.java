/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 18:33:52
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProMainForm </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 18:33:52
 */
@Data
@ApiModel(value = "材料分类实体类")
@Table(name="pro_main_form")
public class ProMainForm extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 类型1后台，2前台
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "类型1后台，2前台")
	private Integer type;

	/**
	* 表单名称
	*/
	@Column(name= "formKey")
    @ApiModelProperty(value = "表单key")
	private String formKey;

	/**
	* 显示名称
	*/
	@Column(name= "viewName")
    @ApiModelProperty(value = "显示名称")
	private String viewName;

	/**
	* 控制器
	*/
	@Column(name= "controllerName")
    @ApiModelProperty(value = "控制器")
	private String controllerName;

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
	* 排序
	*/
	@Column(name= "orderNum")
    @ApiModelProperty(value = "排序")
	private Integer orderNum;

	/**
	* 查url
	*/
	@Column(name= "seeUrl")
    @ApiModelProperty(value = "查url")
	private String seeUrl;

	/**
	* 编辑url
	*/
	@Column(name= "editUrl")
    @ApiModelProperty(value = "编辑url")
	private String editUrl;


	/**
	 * 是否包含节点表单  1包含0不包含
	 */
	@Transient
	private Integer hasNode = 0;
}
