/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-04-14 14:35:04
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefine </p>
 *
 * @author: liushilei
 * @Date: 2020-04-14 14:35:04
 */
@Data
@ApiModel(value = "流程定义实体类")
@Table(name="pro_define")
public class ProDefine extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 业务表id
	 */
	@Column(name= "busTableId")
	@ApiModelProperty(value = "业务表id")
	private Long busTableId;

	/**
	* 流程图名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "流程图名称")
	private String name;

	/**
	* 流程id
	*/
	@Column(name= "defineKey")
    @ApiModelProperty(value = "流程key")
	private String defineKey;

	/**
	* 流程定义内容
	*/
	@Column(name= "defineText")
    @ApiModelProperty(value = "流程定义内容")
	private String defineText;


	/**
	 * 流程定义id(最新版本id)
	 */
	@Column(name= "pdId")
	@ApiModelProperty(value = "流程定义Id")
	private String pdId;

	/**
	 * 流程定义key
	 */
	@Column(name= "pdKey")
	@ApiModelProperty(value = "流程定义Key")
	private String pdKey;



	/**
	* 是否部署 1、是 0、否
	*/
	@Column(name= "isDeploy")
    @ApiModelProperty(value = "是否部署 1、是 0、否")
	private Integer isDeploy;

	/**
	 * 是否配置完成 1、是 0、否
	 */
	@Column(name= "isConfig")
	@ApiModelProperty(value = "是否配置完成 1、是 0、否")
	private Integer isConfig;

	/**
	* 操作人
	*/
	@Column(name= "operator")
    @ApiModelProperty(value = "操作人")
	private String operator;

	/**
	 * 版本号
	 */
	@Column(name= "version")
	@ApiModelProperty(value = "版本号")
	private Integer version;


	@Transient
	@ApiModelProperty(value = "业务表名称")
	private String busTableName;

}
