/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-10 11:34:53 
 */
package hry.scm.project.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> Storage </p>
 *
 * @author: luyue
 * @Date: 2020-07-10 11:34:53 
 */
@Data
@ApiModel(value = "仓库存储信息实体类")
@Table(name="scm_storage")
public class Storage extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 区列行组成唯一标识
	*/
	@Column(name= "number")
    @ApiModelProperty(value = "区列行组成唯一标识")
	private String number;

	/**
	* 货物名称
	*/
	@Column(name= "goodsName")
    @ApiModelProperty(value = "货物名称")
	private String goodsName;

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
	* 库存件数
	*/
	@Column(name= "goodsCount")
    @ApiModelProperty(value = "库存件数")
	private Integer goodsCount;

	/**
	* 库存重量
	*/
	@Column(name= "weight")
    @ApiModelProperty(value = "库存重量")
	private BigDecimal weight;

	/**
	 * 库存重量
	 */
	@Column(name= "volume")
	@ApiModelProperty(value = "库存体积")
	private BigDecimal volume;

	/**
	* 单价
	*/
	@Column(name= "price")
    @ApiModelProperty(value = "单价")
	private BigDecimal price;

	/**
	* 总价
	*/
	@Column(name= "totalPrice")
    @ApiModelProperty(value = "总价")
	private BigDecimal totalPrice;

	/**
	* 入库日期
	*/
	@Column(name= "putInDate")
    @ApiModelProperty(value = "入库日期")
	private Date putInDate;

	/**
	* 委托单号
	*/
	@Column(name= "entrustNo")
    @ApiModelProperty(value = "委托单号")
	private String entrustNo;

	/**
	* 合格日期
	*/
	@Column(name= "qualifiedDate")
    @ApiModelProperty(value = "合格日期")
	private Date qualifiedDate;

	/**
	* 货代
	*/
	@Column(name= "goodsNo")
    @ApiModelProperty(value = "货代")
	private String goodsNo;

	/**
	* 货主
	*/
	@Column(name= "goodsOwner")
    @ApiModelProperty(value = "货主")
	private String goodsOwner;

	/**
	* 企业统一社会信用代码
	*/
	@Column(name= "creditCode")
    @ApiModelProperty(value = "企业统一社会信用代码")
	private String creditCode;

	/**
	* 规格包装
	*/
	@Column(name= "specs")
    @ApiModelProperty(value = "规格包装")
	private String specs;

	/**
	* 理货人员
	*/
	@Column(name= "tallyName")
    @ApiModelProperty(value = "理货人员")
	private String tallyName;

	/**
	* 盘存状态
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "盘存状态")
	private String status;

	/**
	* 取样件数
	*/
	@Column(name= "sampleCount")
    @ApiModelProperty(value = "取样件数")
	private Integer sampleCount;

	/**
	* 取样日期
	*/
	@Column(name= "sampleDate")
    @ApiModelProperty(value = "取样日期")
	private Date sampleDate;

	/**
	* 是否分配出库托盘
	*/
	@Column(name= "isDistribute")
    @ApiModelProperty(value = "是否分配出库托盘")
	private String isDistribute;

	/**
	* 分配出库托盘人员
	*/
	@Column(name= "distributeName")
    @ApiModelProperty(value = "分配出库托盘人员")
	private String distributeName;

	/**
	* 分配出库托盘时间
	*/
	@Column(name= "distributeDate")
    @ApiModelProperty(value = "分配出库托盘时间")
	private Date distributeDate;

	/**
	* 破损件数
	*/
	@Column(name= "breakCount")
    @ApiModelProperty(value = "破损件数")
	private Integer breakCount;

	/**
	* 破损状态
	*/
	@Column(name= "breakStatus")
    @ApiModelProperty(value = "破损状态")
	private String breakStatus;

	/**
	* 是否返样
	*/
	@Column(name= "isReturn")
    @ApiModelProperty(value = "是否返样")
	private String isReturn;

	/**
	* 返样日期
	*/
	@Column(name= "returnDate")
    @ApiModelProperty(value = "返样日期")
	private Date returnDate;

	/**
	* 破损件数
	*/
	@Column(name= "returnCount")
    @ApiModelProperty(value = "破损件数")
	private Integer returnCount;

	/**
	* 是否被抵押，1是0否
	*/
	@Column(name= "isMortgage")
    @ApiModelProperty(value = "是否被抵押，1是0否")
	private String isMortgage;

	@Transient
	private BigDecimal  mortWeight;//质押重量

	@Transient
	private  int mortCount;//质押件数

}
