/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-17 13:41:22 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> FaBill </p>
 *
 * @author: yaoz
 * @Date: 2020-07-17 13:41:22 
 */
@Data
@ApiModel(value = "票据信息实体类")
@Table(name="fa_bill")
public class FaBill extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 买方名称
	*/
	@Column(name= "buyName")
    @ApiModelProperty(value = "买方名称")
	private String buyName;

	/**
	* 卖方名称
	*/
	@Column(name= "sellName")
    @ApiModelProperty(value = "卖方名称")
	private String sellName;

	/**
	* 发票号
	*/
	@Column(name= "billNo")
    @ApiModelProperty(value = "发票号")
	private String billNo;

	/**
	* 发票金额
	*/
	@Column(name= "billMoney")
    @ApiModelProperty(value = "发票金额")
	private BigDecimal billMoney;

	/**
	* 应收金额
	*/
	@Column(name= "receivableMoney")
    @ApiModelProperty(value = "应收金额")
	private BigDecimal receivableMoney;

	/**
	* 发票开票日期
	*/
	@Column(name= "billStartDate")
    @ApiModelProperty(value = "发票开票日期")
	private Date billStartDate;

	/**
	* 发票到期日期
	*/
	@Column(name= "billEndDate")
    @ApiModelProperty(value = "发票到期日期")
	private Date billEndDate;

	/**
	* 项目合同编号
	*/
	@Column(name= "projectContractCode")
    @ApiModelProperty(value = "项目合同编号")
	private String projectContractCode;

	/**
	* 项目公司名称
	*/
	@Column(name= "projectCompanyName")
    @ApiModelProperty(value = "项目公司名称")
	private String projectCompanyName;

	/**
	* 应收账款到期日
	*/
	@Column(name= "receivableAccountsEndDate")
    @ApiModelProperty(value = "应收账款到期日")
	private Date receivableAccountsEndDate;

	/**
	* 确认函编号
	*/
	@Column(name= "confirmationNo")
    @ApiModelProperty(value = "确认函编号")
	private String confirmationNo;

	/**
	* 发票原件地址
	*/
	@Column(name= "billImgUrl")
    @ApiModelProperty(value = "发票原件地址")
	private String billImgUrl;

	/**
	* 凭证名称
	*/
	@Column(name= "certificateName")
    @ApiModelProperty(value = "凭证名称")
	private String certificateName;

	/**
	* 数据来源 1前台录入 2后台录入 
	*/
	@Column(name= "source")
    @ApiModelProperty(value = "数据来源 1前台录入 2后台录入 ")
	private Integer source;

	/**
	* 录入人Id
	*/
	@Column(name= "userId")
    @ApiModelProperty(value = "录入人Id")
	private Long userId;

	/**
	* 录入人名称
	*/
	@Column(name= "userName")
    @ApiModelProperty(value = "录入人名称")
	private String userName;

	/**
	* 审核状态 1待审核 2已通过 3已否决
	*/
	@Column(name= "status")
    @ApiModelProperty(value = "审核状态 0录入未审核 1待审核 2已通过 3已否决")
	private Integer status;

	/**
	* 状态说明
	*/
	@Column(name= "statusRemark")
    @ApiModelProperty(value = "状态说明")
	private String statusRemark;

}
