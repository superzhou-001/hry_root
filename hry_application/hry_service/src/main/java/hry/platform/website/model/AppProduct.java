/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-24 10:37:13
 */
package hry.platform.website.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * <p> AppProduct </p>
 *
 * @author: liushilei
 * @Date: 2020-07-24 10:37:13
 */
@Data
@ApiModel(value = "产品实体类")
@Table(name="app_product")
public class AppProduct extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 分类ID
	*/
	@Column(name= "classId")
    @ApiModelProperty(value = "分类ID")
	private Long classId;

	/**
	* 客户类型1个人2企业
	*/
	@Column(name= "customerType")
    @ApiModelProperty(value = "客户类型1个人2企业")
	private Integer customerType;

	/**
	* 产品名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "产品名称")
	private String name;

	/**
	* 贷款利率
	*/
	@Column(name= "loanRate")
    @ApiModelProperty(value = "贷款利率")
	private String loanRate;

	/**
	* 贷款期限
	*/
	@Column(name= "loanPeriod")
    @ApiModelProperty(value = "贷款期限")
	private String loanPeriod;

	/**
	* 费用说明
	*/
	@Column(name= "costMark")
    @ApiModelProperty(value = "费用说明")
	private String costMark;

	/**
	* 放款周期
	*/
	@Column(name= "outLoanPeriod")
    @ApiModelProperty(value = "放款周期")
	private String outLoanPeriod;

	/**
	* 还款说明
	*/
	@Column(name= "returnMoneyMark")
    @ApiModelProperty(value = "还款说明")
	private String returnMoneyMark;

	/**
	* 产品特点
	*/
	@Column(name= "productTrait")
    @ApiModelProperty(value = "产品特点")
	private String productTrait;

	/**
	* 顾问点评
	*/
	@Column(name= "adviserContent")
    @ApiModelProperty(value = "顾问点评")
	private String adviserContent;

	/**
	* 产品简介
	*/
	@Column(name= "productMark")
    @ApiModelProperty(value = "产品简介")
	private String productMark;

	/**
	* 排序
	*/
	@Column(name= "orderNum")
    @ApiModelProperty(value = "排序")
	private Integer orderNum;

	/**
	* 是否启用0未启用1启用
	*/
	@Column(name= "isEnable")
    @ApiModelProperty(value = "是否启用0未启用1启用")
	private Integer isEnable;

	/**
	* 图标上传
	*/
	@Column(name= "productLogo")
    @ApiModelProperty(value = "图标上传")
	private String productLogo;

	/**
	* 产品介绍
	*/
	@Column(name= "productShow")
    @ApiModelProperty(value = "产品介绍")
	private String productShow;

	/**
	 * 审批流程
	 */
	@Column(name= "productApproval")
	@ApiModelProperty(value = "审批流程")
	private String productApproval;


	/**
	 * 贷款金额
	 */
	@Column(name= "loanMoney")
	@ApiModelProperty(value = "贷款金额")
	private String loanMoney;

	@Transient
	@ApiModelProperty(value = "产品类型")
	private String className;



}
