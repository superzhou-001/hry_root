/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:17:01 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * <p> CuHouse </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:17:01 
 */
@Data
@ApiModel(value = "房产信息表实体类")
@Table(name="cu_house")
public class CuHouse extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 主体id type=1 personId，type=2 enterpriseId
	*/
	@Column(name= "subjectId")
    @ApiModelProperty(value = "主体id type=1 personId，type=2 enterpriseId")
	private Long subjectId;

	/**
	* 客户类型 1.个人 2.企业
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "客户类型 1.个人 2.企业")
	private Integer type;

	/**
	* 产权人
	*/
	@Column(name= "propertyRightName")
    @ApiModelProperty(value = "产权人")
	private String propertyRightName;

	/**
	* 产权证号
	*/
	@Column(name= "propertyRightCode")
    @ApiModelProperty(value = "产权证号")
	private String propertyRightCode;

	/**
	* 产权性质 1.国有 2.私有 3.合资 4.外资
	*/
	@Column(name= "propertyRightNature")
    @ApiModelProperty(value = "产权性质 1.国有 2.私有 3.合资 4.外资")
	private Integer propertyRightNature;

	/**
	* 房屋类型
	*/
	@Column(name= "houseType")
    @ApiModelProperty(value = "房屋类型")
	private Integer houseType;

	/**
	* 房屋大小(平米)
	*/
	@Column(name= "houseSize")
    @ApiModelProperty(value = "房屋大小(平米)")
	private String houseSize;

	/**
	* 房屋地址
	*/
	@Column(name= "houseAddress")
    @ApiModelProperty(value = "房屋地址")
	private String houseAddress;

	/**
	* 房产总值（万元）
	*/
	@Column(name= "housePrice")
    @ApiModelProperty(value = "房产总值（万元）")
	private String housePrice;

	/**
	* 贷款余额（万元）
	*/
	@Column(name= "loanBalance")
    @ApiModelProperty(value = "贷款余额（万元）")
	private String loanBalance;

	/**
	* 贷款年限
	*/
	@Column(name= "loanTerm")
    @ApiModelProperty(value = "贷款年限")
	private Integer loanTerm;

	/**
	* 剩余贷款年限
	*/
	@Column(name= "subLoanTerm")
    @ApiModelProperty(value = "剩余贷款年限")
	private Integer subLoanTerm;

	/**
	* 月供（元）
	*/
	@Column(name= "monthlySupply")
    @ApiModelProperty(value = "月供（元）")
	private String monthlySupply;

	/**
	* 建筑样式 1.砖混结构 2.砖木结构 3.框架结构 4.其他结构
	*/
	@Column(name= "architecturalStyle")
    @ApiModelProperty(value = "建筑样式 1.砖混结构 2.砖木结构 3.框架结构 4.其他结构")
	private Integer architecturalStyle;

	/**
	* 建筑结构 1.低层 2.多层 3.小高层 4.高层 5.超高层
	*/
	@Column(name= "buildingStructure")
    @ApiModelProperty(value = "建筑结构 1.低层 2.多层 3.小高层 4.高层 5.超高层")
	private Integer buildingStructure;

	/**
	* 建成年份
	*/
	@Column(name= "yearBuilt")
    @ApiModelProperty(value = "建成年份")
	private String yearBuilt;

	/**
	* 户型结构 1.一室一厅一卫 2.两室一厅一卫 3.三室两厅一卫.........
	*/
	@Column(name= "unitStructure")
    @ApiModelProperty(value = "户型结构 1.一室一厅一卫 2.两室一厅一卫 3.三室两厅一卫.........")
	private Integer unitStructure;

	/**
	* 剩余年限（年）
	*/
	@Column(name= "remainingYears")
    @ApiModelProperty(value = "剩余年限（年）")
	private String remainingYears;

	/**
	* 地段描述 1.好 2.差 3.一般 4.很好 5.其他
	*/
	@Column(name= "lotDescription")
    @ApiModelProperty(value = "地段描述 1.好 2.差 3.一般 4.很好 5.其他")
	private Integer lotDescription;

	/**
	* 登记情况 1.可以登记 2.不可登记
	*/
	@Column(name= "isRegistration")
    @ApiModelProperty(value = "登记情况 1.可以登记 2.不可登记")
	private Integer isRegistration;

	/**
	* 同等房产单位单价1
	*/
	@Column(name= "unitPriceOfTheSameProperty1")
    @ApiModelProperty(value = "同等房产单位单价1")
	private BigDecimal unitPriceOfTheSameProperty1;

	/**
	* 同等房产单位单价2
	*/
	@Column(name= "unitPriceOfTheSameProperty2")
    @ApiModelProperty(value = "同等房产单位单价2")
	private BigDecimal unitPriceOfTheSameProperty2;

	/**
	* 同等房产单位单价3
	*/
	@Column(name= "unitPriceOfTheSameProperty3")
    @ApiModelProperty(value = "同等房产单位单价3")
	private BigDecimal unitPriceOfTheSameProperty3;

	/**
	* 新房单价
	*/
	@Column(name= "newHousePrice")
    @ApiModelProperty(value = "新房单价")
	private BigDecimal newHousePrice;

	/**
	* 共有人
	*/
	@Column(name= "coOwner")
    @ApiModelProperty(value = "共有人")
	private String coOwner;

}
