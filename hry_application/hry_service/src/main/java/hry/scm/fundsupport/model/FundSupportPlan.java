/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:37:09 
 */
package hry.scm.fundsupport.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> FundSupportPlan </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:37:09 
 */
@Data
@ApiModel(value = "资金方案实体类")
@Table(name="scm_fundsupport_plan")
public class FundSupportPlan extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	 * 方案名称
	 */
	@Column(name= "fundPlanName")
	@ApiModelProperty(value = "方案名称")
	private String fundPlanName;

	/**
	* 资金方Id
	*/
	@Column(name= "fundSupportId")
    @ApiModelProperty(value = "资金方Id")
	private Long fundSupportId;


	/**
	* pledge 质押   dece 代采
	*/
	@Column(name= "planType")
    @ApiModelProperty(value = "pledge 质押   dece 代采   数据字典对应key-planType")
	private String planType;

	/**
	* 保证金/放款比例
	*/
	@Column(name= "proportion")
    @ApiModelProperty(value = "保证金/放款比例")
	private BigDecimal proportion;

	/**
	* 还款期限
	*/
	@Column(name= "period")
    @ApiModelProperty(value = "还款期限")
	private Integer period;

	/**
	* 服务费率
	*/
	@Column(name= "serviceRate")
    @ApiModelProperty(value = "服务费率")
	private BigDecimal serviceRate;

	/**
	* 逾期费率
	*/
	@Column(name= "overRate")
    @ApiModelProperty(value = "逾期费率")
	private BigDecimal overRate;

	/**
	* 1禁用，2可用
	*/
	@Column(name= "isEnable")
    @ApiModelProperty(value = "1禁用，2可用 默认值为2")
	private Integer isEnable;

	@Column(name= "principalModel")
	@ApiModelProperty(value = "本金模型：等比例释放-sameRateRelease,最后一笔释放-lastRelease")
	private String principalModel;

	@Column(name= "interestModel")
	@ApiModelProperty(value = "利息模型:全额利息-fullInterest,本金利息-principalInterest")
	private String interestModel;


}
