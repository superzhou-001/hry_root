/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:29:53 
 */
package hry.business.fa.model;

import hry.business.fa.model.parent.InterestModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p> FaProduct </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:29:53 
 */
@Data
@ApiModel(value = "产品表实体类")
@Table(name="fa_product")
public class FaProduct extends InterestModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 产品名称
	*/
	@Column(name= "productname")
    @ApiModelProperty(value = "产品名称")
	private String productname;

	/**
	* 产品类型id
	*/
	@Column(name= "typeid")
    @ApiModelProperty(value = "产品类型id")
	private Long typeid;

	/**
	* 产品类型名称
	*/
	@Column(name= "typename")
    @ApiModelProperty(value = "产品类型名称")
	private String typename;

	/**
	* 工作流id
	*/
	@Column(name= "defineKey")
    @ApiModelProperty(value = "工作流key")
	private String defineKey;

	/**
	 * 融资期限（月）
	 */
	@Column(name= "financingTerm")
	@ApiModelProperty(value = "融资期限（月）")
	private Integer financingTerm;

	/**
	* 产品说明
	*/
	@Column(name= "productdesc")
    @ApiModelProperty(value = "产品说明")
	private String productdesc;

	/**
	* 合计日比例
	*/
	@Column(name= "totaldayratio")
    @ApiModelProperty(value = "合计日比例")
	private BigDecimal totaldayratio;

	/**
	* 合计月比例
	*/
	@Column(name= "totalmonthratio")
    @ApiModelProperty(value = "合计月比例")
	private BigDecimal totalmonthratio;

	/**
	* 合计年比例
	*/
	@Column(name= "totalyearratio")
    @ApiModelProperty(value = "合计年比例")
	private BigDecimal totalyearratio;

	/**
	* 合计总额
	*/
	@Column(name= "totalsum")
    @ApiModelProperty(value = "合计总额")
	private BigDecimal totalsum;

	/**
	* 罚息比例
	*/
	@Column(name= "penaltyratio")
    @ApiModelProperty(value = "罚息比例")
	private BigDecimal penaltyratio;

	/**
	* 是否开启第三方代扣 0 关闭 1 开启
	*/
	@Column(name= "openthirddebit")
    @ApiModelProperty(value = "是否开启第三方代扣 0 关闭 1 开启")
	private Integer openthirddebit;

	/**
	* 是否开启第三方支付 0 关闭 1 开启
	*/
	@Column(name= "openthirdpay")
    @ApiModelProperty(value = "是否开启第三方支付 0 关闭 1 开启")
	private Integer openthirdpay;

	/**
	* 是否开启资方 0 关闭 1 开启
	*/
	@Column(name= "openfunding")
    @ApiModelProperty(value = "是否开启资方 0 关闭 1 开启")
	private Integer openfunding;

	@Column(name= "openseal")
	@ApiModelProperty(value = "是否开启电子签章 0 关闭 1 开启")
	private Integer openseal;

	/**
	* 宽限日(天)
	*/
	@Column(name= "graceday")
    @ApiModelProperty(value = "宽限日(天)")
	private Integer graceday;

	/**
	* 是否开启节假日顺延 0 关闭 1 开启
	*/
	@Column(name= "openholidays")
    @ApiModelProperty(value = "是否开启节假日顺延 0 关闭 1 开启")
	private Integer openholidays;

	/**
	* 产品创建状态 1 第一步（产品基础信息） 2 第二步（产品计息模型配置） 3 第三步（选择附加费用）4 完成 
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "产品创建状态 1 第一步（产品基础信息） 2 第二步（产品计息模型配置） 3 第三步（选择附加费用）4 完成 ")
	private Integer status;

	/**
	* 是否启用 0 关闭 1 启用
	*/
	@Column(name= "openstart")
    @ApiModelProperty(value = "是否启用 0 关闭 1 启用")
	private Integer openstart;

	/**
	* 是否上架 0 下架 1 上架
	*/
	@Column(name= "openputaway")
    @ApiModelProperty(value = "是否上架 0 下架 1 上架")
	private Integer openputaway;

	@ApiModelProperty(value = "产品费率集合")
	@Transient
	private String productRateJson;

	@ApiModelProperty(value = "产品费用Id集合")
	@Transient
	private String productCostIds;

	@ApiModelProperty(value = "产品费用集合")
	@Transient
	private List<FaCost> faCostList;

}
