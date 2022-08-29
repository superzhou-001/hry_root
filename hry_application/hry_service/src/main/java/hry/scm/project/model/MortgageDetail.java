/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-13 19:02:46 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> MortgageDetail </p>
 *
 * @author: luyue
 * @Date: 2020-07-13 19:02:46 
 */
@Data
@ApiModel(value = "抵质押物详细信息实体类")
@Table(name="scm_mortgage_detail")
public class MortgageDetail extends BaseModel {

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
	 * 质押物汇总id
	 */
	@Column(name= "totalId")
	@ApiModelProperty(value = "质押物汇总id")
	private Long totalId;

	/**
	 * 库存id
	 */
	@Column(name= "storageId")
	@ApiModelProperty(value = "库存id")
	private Long storageId;

	/**
	 * 区列行组成唯一标识
	 */
	@Column(name= "number")
	@ApiModelProperty(value = "区列行组成唯一标识")
	private String number;

	/**
	* 订单编号
	*/
	@Column(name= "projectNumber")
    @ApiModelProperty(value = "订单编号")
	private String projectNumber;

	/**
	* 货物名称
	*/
	@Column(name= "goodsName")
    @ApiModelProperty(value = "货物名称")
	private String goodsName;

	/**
	* 库存重量
	*/
	@Column(name= "weight")
    @ApiModelProperty(value = "库存重量")
	private BigDecimal weight;

	/**
	 * 库存件数
	 */
	@Column(name= "goodsCount")
	@ApiModelProperty(value = "库存件数")
	private int goodsCount;


	/**
	* 报检号
	*/
	@Column(name= "inspectionNo")
    @ApiModelProperty(value = "报检号")
	private String inspectionNo;

	/**
	* 箱号
	*/
	@Column(name= "caseNo")
    @ApiModelProperty(value = "箱号")
	private String caseNo;

	/**
	* 库位
	*/
	@Column(name= "location")
    @ApiModelProperty(value = "库位")
	private String location;

	/**
	* 区
	*/
	@Column(name= "area")
    @ApiModelProperty(value = "区")
	private String area;

	/**
	* 行
	*/
	@Column(name= "line")
    @ApiModelProperty(value = "行")
	private String line;

	/**
	* 列
	*/
	@Column(name= "queue")
    @ApiModelProperty(value = "列")
	private String queue;

	/**
	* 层
	*/
	@Column(name= "layer")
    @ApiModelProperty(value = "层")
	private String layer;

	/**
	* 抵押重量
	*/
	@Column(name= "mortWeight")
    @ApiModelProperty(value = "抵押重量")
	private BigDecimal mortWeight;

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
