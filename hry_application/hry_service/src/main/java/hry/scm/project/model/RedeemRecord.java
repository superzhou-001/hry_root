/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-21 16:41:25 
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
 * <p> RedeemRecord </p>
 *
 * @author: luyue
 * @Date: 2020-07-21 16:41:25 
 */
@Data
@ApiModel(value = "价格记录信息实体类")
@Table(name="scm_redeem_record")
public class RedeemRecord extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 赎货编号
	*/
	@Column(name= "number")
    @ApiModelProperty(value = "赎货编号")
	private String number;

	/**
	* 订单id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "订单id")
	private Long projectId;

	/**
	* 订单编号
	*/
	@Column(name= "projectNumber")
    @ApiModelProperty(value = "订单编号")
	private String projectNumber;

	/**
	* 资金方id
	*/
	@Column(name= "fundSupportId")
    @ApiModelProperty(value = "资金方id")
	private Long fundSupportId;

	/**
	* 采购企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "采购企业id")
	private Long enterpriseId;

	/**
	* 采购企业名称
	*/
	@Column(name= "enterpriseName")
    @ApiModelProperty(value = "采购企业名称")
	private String enterpriseName;

	/**
	* 申请账户id
	*/
	@Column(name= "applyId")
    @ApiModelProperty(value = "申请账户id")
	private Long applyId;

	/**
	* 企业统一社会信用代码
	*/
	@Column(name= "creditCode")
    @ApiModelProperty(value = "企业统一社会信用代码")
	private String creditCode;

	/**
	* 赎货价值
	*/
	@Column(name= "goodsWorth")
    @ApiModelProperty(value = "赎货价值")
	private BigDecimal goodsWorth;

	/**
	 * 本金金额
	 */
	@Column(name= "principalMoney")
	@ApiModelProperty(value = "本金金额")
	private BigDecimal principalMoney;

	/**
	* 服务费
	*/
	@Column(name= "serviceMoney")
    @ApiModelProperty(value = "服务费")
	private BigDecimal serviceMoney;

	/**
	* 逾期天数
	*/
	@Column(name= "overdueDays")
    @ApiModelProperty(value = "逾期天数")
	private Integer overdueDays;

	/**
	 * 借款天数
	 */
	@Column(name= "loanDays")
	@ApiModelProperty(value = "借款天数")
	private Integer loanDays;

	/**
	* 逾期费
	*/
	@Column(name= "overdueMoney")
    @ApiModelProperty(value = "逾期费")
	private BigDecimal overdueMoney;

	/**
	* 应还总金额
	*/
	@Column(name= "sumMoney")
    @ApiModelProperty(value = "应还总金额")
	private BigDecimal sumMoney;

	/**
	* 付款凭证照片路径
	*/
	@Column(name= "payImageUrl")
    @ApiModelProperty(value = "付款凭证照片路径")
	private String payImageUrl;

	/**
	* 剩余金额
	*/
	@Column(name= "surplusMoney")
    @ApiModelProperty(value = "剩余金额")
	private BigDecimal surplusMoney;

	/**
	* 剩余质押金额
	*/
	@Column(name= "surplusMortMoney")
    @ApiModelProperty(value = "剩余质押金额")
	private BigDecimal surplusMortMoney;

	/**
	* 还款日期
	*/
	@Column(name= "paybackDate")
    @ApiModelProperty(value = "还款日期")
	private Date paybackDate;

	/**
	* 项目状态，0新申请，10资金方确认收款，20解除质押
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "项目状态，0新申请，10资金方确认收款，20解除质押")
	private Integer status;

	/**
	 * 解除质押日期
	 */
	@Column(name= "redeemDate")
	@ApiModelProperty(value = "解除质押日期")
	private Date redeemDate;


}
