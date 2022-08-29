/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-15 11:58:19 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProcessRecord </p>
 *
 * @author: luyue
 * @Date: 2020-07-15 11:58:19 
 */
@Data
@ApiModel(value = "订单处理记录信息实体类")
@Table(name="scm_process_record")
public class ProcessRecord extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 流程类别1现货质押，2代采，3取件
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "流程类别1现货质押，2代采，3取件")
	private String type;

	/**
	* 融资申请主键
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "融资申请主键")
	private Long projectId;

	/**
	* 节点名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "节点名称")
	private String name;

	/**
	* 处理人id
	*/
	@Column(name= "handelId")
    @ApiModelProperty(value = "处理人id")
	private Long handelId;

	/**
	* 处理人姓名
	*/
	@Column(name= "handelName")
    @ApiModelProperty(value = "处理人姓名")
	private String handelName;

	/**
	* 处理意见1同意2拒绝
	*/
	@Column(name= "opinion")
    @ApiModelProperty(value = "处理意见1同意2拒绝")
	private String opinion;

	/**
	* 当前处于第几步
	*/
	@Column(name= "step")
    @ApiModelProperty(value = "当前处于第几步")
	private Integer step;

}
