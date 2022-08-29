/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 13:41:21
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDefineNode </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 13:41:21
 */
@Data
@ApiModel(value = "流程实例实体类")
@Table(name="pro_define_node")
public class ProDefineNode extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 节点名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "节点名称")
	private String name;

	/**
	* 流程key
	*/
	@Column(name= "defineKey")
    @ApiModelProperty(value = "流程key")
	private String defineKey;

	/**
	* 节点key
	*/
	@Column(name= "nodeKey")
    @ApiModelProperty(value = "节点key")
	private String nodeKey;

	/**
	* 版本号
	*/
	@Column(name= "version")
    @ApiModelProperty(value = "版本号")
	private Integer version;


	/**
	 * 节点类型
	 * 1后台
	 * 2前台
	 */
	@Column(name= "nodeType")
	@ApiModelProperty(value = "节点类型")
	private Integer nodeType;

	/**
	 * 时间类型
	 * 1自然日
	 * 2工作日
	 */
	@Column(name= "timeType")
	@ApiModelProperty(value = "时间类型")
	private Integer timeType;


	/**
	 * 时间天数
	 */
	@Column(name= "timeDay")
	@ApiModelProperty(value = "时间天数")
	private Integer timeDay;

	/**
	 * 是否会签节点
	 */
	@Column(name= "hasMulti")
	@ApiModelProperty(value = "是否会签节点")
	private Integer hasMulti; //0否  1是s


	/**
	 * 会签决策类型
	 */
	@Column(name= "decideType")
	@ApiModelProperty(value = "决策类型")
	private Integer decideType; //1投票 2打分


	/**
	 * 会签决策值 票数 或分数
	 */
	@Column(name= "decideValue")
	@ApiModelProperty(value = "决策值")
	private Integer decideValue; //



}
