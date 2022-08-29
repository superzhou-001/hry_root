/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:40:57
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefineBtn </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:40:57
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_define_btn")
public class ProDefineBtn extends BaseModel {

	//1通过2拒绝3打回4打回至自定义

	public final static  Integer  BUTYPE_1  = 1;
	public final static  Integer  BUTYPE_2  = 2;
	public final static  Integer  BUTYPE_3  = 3;
	public final static  Integer  BUTYPE_4  = 4;

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;


	/**
	 * 控制器名称
	 */
	@Column(name= "controlName")
	@ApiModelProperty(value = "控制器名称")
	private String controlName;


	/**
	* 流程节点id
	*/
	@Column(name= "nodeId")
    @ApiModelProperty(value = "流程节点id")
	private Long nodeId;

	/**
	* 按钮名称
	*/
	@Column(name= "btnName")
    @ApiModelProperty(value = "按钮名称")
	private String btnName;

	/**
	 * 按钮类型
	 */
	@Column(name= "btnType")
	@ApiModelProperty(value = "按钮类型")
	private Integer btnType;

	/**
	* 业务主表
	*/
	@Column(name= "buTable")
    @ApiModelProperty(value = "业务主表")
	private String buTable;

	/**
	* 业务类型 1通过2拒绝3打回4打回至自定义
	*/
	@Column(name= "buType")
    @ApiModelProperty(value = "业务类型")
	private String buType;

	/**
	* 业务状态
	*/
	@Column(name= "buState")
    @ApiModelProperty(value = "业务状态")
	private String buState;

}
