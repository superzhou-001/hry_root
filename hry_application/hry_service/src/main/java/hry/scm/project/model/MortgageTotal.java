/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:01:27 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> MortgageTotal </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:01:27 
 */
@Data
@ApiModel(value = "抵质押物汇总信息实体类")
@Table(name="scm_mortgage_total")
public class MortgageTotal extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 融资申请主键
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "融资申请主键")
	private Long projectId;

	/**
	* 订单编号
	*/
	@Column(name= "projectNumber")
    @ApiModelProperty(value = "订单编号")
	private String projectNumber;

	/**
	 * 订单类型
	 */
	@Column(name= "projectType")
	@ApiModelProperty(value = "订单类型，1现货质押，2在途质押")
	private String projectType;

	/**
	* 货物名称
	*/
	@Column(name= "goodsName")
    @ApiModelProperty(value = "货物名称")
	private String goodsName;

	/**
	* 库存重量
	*/
	@Column(name= "totalWeight")
    @ApiModelProperty(value = "库存重量")
	private BigDecimal totalWeight;

	/**
	* 抵押重量
	*/
	@Column(name= "mortWeight")
    @ApiModelProperty(value = "抵押重量")
	private BigDecimal mortWeight;

	/**
	 * 抵押重量
	 */
	@Column(name= "actMortWeight")
	@ApiModelProperty(value = "实际抵押重量")
	private BigDecimal actMortWeight;

	/**
	* 已赎回重量
	*/
	@Column(name= "backWeight")
    @ApiModelProperty(value = "已赎回重量")
	private BigDecimal backWeight;

	/**
	* 剩余重量
	*/
	@Column(name= "surplusWeight")
    @ApiModelProperty(value = "剩余重量")
	private BigDecimal surplusWeight;

	/**
	* 抵押中的每件的平均重量
	*/
	@Column(name= "aveWeight")
    @ApiModelProperty(value = "抵押中的每件的平均重量")
	private BigDecimal aveWeight;

	/**
	* 抵押件数
	*/
	@Column(name= "mortCount")
    @ApiModelProperty(value = "抵押件数")
	private Integer mortCount;

	/**
	* 已赎回件数
	*/
	@Column(name= "backCount")
    @ApiModelProperty(value = "已赎回件数")
	private Integer backCount;

	/**
	* 剩余件数
	*/
	@Column(name= "surplusCount")
    @ApiModelProperty(value = "剩余件数")
	private Integer surplusCount;

	/**
	* 抵押单价
	*/
	@Column(name= "mortPrice")
    @ApiModelProperty(value = "抵押单价")
	private BigDecimal mortPrice;

	/**
	* 修改单价
	*/
	@Column(name= "modifyPrice")
    @ApiModelProperty(value = "修改单价")
	private BigDecimal modifyPrice;

	/**
	* 抵押总价
	*/
	@Column(name= "mortTotalPrice")
    @ApiModelProperty(value = "抵押总价")
	private BigDecimal mortTotalPrice;

	/**
	 * 剩余价值
	 */
	@Column(name= "surTotalPrice")
	@ApiModelProperty(value = "剩余价值")
	private BigDecimal surTotalPrice;



	/**
	* 融资申请企业主键
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "融资申请企业主键")
	private Long enterpriseId;

	/**
	* 企业统一社会信用代码
	*/
	@Column(name= "creditCode")
    @ApiModelProperty(value = "企业统一社会信用代码")
	private String creditCode;

}
