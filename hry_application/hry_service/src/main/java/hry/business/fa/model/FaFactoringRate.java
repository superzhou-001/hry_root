/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-20 10:36:12 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> FaFactoringRate </p>
 *
 * @author: yaoz
 * @Date: 2020-07-20 10:36:12 
 */
@Data
@ApiModel(value = "保理流程费率表实体类")
@Table(name="fa_factoring_rate")
public class FaFactoringRate extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 项目id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "项目id")
	private Long projectId;

	/**
	* 费率名称
	*/
	@Column(name= "ratename")
    @ApiModelProperty(value = "费率名称")
	private String ratename;

	/**
	* 日比例
	*/
	@Column(name= "dayratio")
    @ApiModelProperty(value = "日比例")
	private BigDecimal dayratio;

	/**
	* 月比例
	*/
	@Column(name= "monthratio")
    @ApiModelProperty(value = "月比例")
	private BigDecimal monthratio;

	/**
	* 年比例
	*/
	@Column(name= "yearratio")
    @ApiModelProperty(value = "年比例")
	private BigDecimal yearratio;

	/**
	* 固定金额
	*/
	@Column(name= "summoney")
    @ApiModelProperty(value = "固定金额")
	private BigDecimal summoney;

	/**
	* 还款方式 前置 0 关闭 1 启动
	*/
	@Column(name= "repayone")
    @ApiModelProperty(value = "还款方式 前置 0 关闭 1 启动")
	private Integer repayone;

	/**
	* 还款方式 一次性 0 关闭 1 启动
	*/
	@Column(name= "repaytwo")
    @ApiModelProperty(value = "还款方式 一次性 0 关闭 1 启动")
	private Integer repaytwo;

	/**
	* 还款方式 是否放开抵扣 0 关闭 1 启动
	*/
	@Column(name= "repaythree")
    @ApiModelProperty(value = "还款方式 是否放开抵扣 0 关闭 1 启动")
	private Integer repaythree;

}
