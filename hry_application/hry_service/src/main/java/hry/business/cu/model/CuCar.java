/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-07-01 16:45:28 
 */
package hry.business.cu.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p> CuCar </p>
 *
 * @author: yaoz
 * @Date: 2020-07-01 16:45:28 
 */
@Data
@ApiModel(value = "车辆信息表实体类")
@Table(name="cu_car")
public class CuCar extends BaseModel {

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
	* 所属权人
	*/
	@Column(name= "ownerName")
    @ApiModelProperty(value = "所属权人")
	private String ownerName;

	/**
	* 车辆品牌
	*/
	@Column(name= "carBrand")
    @ApiModelProperty(value = "车辆品牌")
	private String carBrand;

	/**
	* 车型
	*/
	@Column(name= "carType")
    @ApiModelProperty(value = "车型")
	private String carType;

	/**
	* 车牌号
	*/
	@Column(name= "carNumber")
    @ApiModelProperty(value = "车牌号")
	private String carNumber;

	/**
	* 使用年限（年）
	*/
	@Column(name= "serviceLife")
    @ApiModelProperty(value = "使用年限（年）")
	private String serviceLife;

	/**
	* 市场价值（万元）
	*/
	@Column(name= "marketPrice")
    @ApiModelProperty(value = "市场价值（万元）")
	private String marketPrice;

	/**
	* 贷款余额（万元）
	*/
	@Column(name= "loanBalance")
    @ApiModelProperty(value = "贷款余额（万元）")
	private String loanBalance;

	/**
	* 月供（元）
	*/
	@Column(name= "monthlySupply")
    @ApiModelProperty(value = "月供（元）")
	private String monthlySupply;

	/**
	* 制造商
	*/
	@Column(name= "manufacturer")
    @ApiModelProperty(value = "制造商")
	private String manufacturer;

	/**
	* 车型编号
	*/
	@Column(name= "modelNumber")
    @ApiModelProperty(value = "车型编号")
	private String modelNumber;

	/**
	* 排量 1. L<=1.1 2. 1.6 3. 2.0 .............
	*/
	@Column(name= "displacement")
    @ApiModelProperty(value = "排量 1. L<=1.1 2. 1.6 3. 2.0 .............")
	private Integer displacement;

	/**
	* 车系
	*/
	@Column(name= "vehicleSystem")
    @ApiModelProperty(value = "车系")
	private String vehicleSystem;

	/**
	* 配置
	*/
	@Column(name= "toConfigure")
    @ApiModelProperty(value = "配置")
	private String toConfigure;

	/**
	* 车辆产地
	*/
	@Column(name= "vehicleOrigin")
    @ApiModelProperty(value = "车辆产地")
	private String vehicleOrigin;

	/**
	* 发动机号
	*/
	@Column(name= "engineNumber")
    @ApiModelProperty(value = "发动机号")
	private String engineNumber;

	/**
	* 座位数 1.小于5座 2.5座 3.7座 4.大于7座
	*/
	@Column(name= "seatingCapacity")
    @ApiModelProperty(value = "座位数 1.小于5座 2.5座 3.7座 4.大于7座")
	private Integer seatingCapacity;

	/**
	* 车架号
	*/
	@Column(name= "frameNumber")
    @ApiModelProperty(value = "车架号")
	private String frameNumber;

	/**
	* 车辆颜色 1.黑色 2.白色 3.................
	*/
	@Column(name= "vehicleColour")
    @ApiModelProperty(value = "车辆颜色 1.黑色 2.白色 3.................")
	private Integer vehicleColour;

	/**
	* 新车价格（万）
	*/
	@Column(name= "newCarPrice")
    @ApiModelProperty(value = "新车价格（万）")
	private BigDecimal newCarPrice;

	/**
	* 登记情况 1.可以登记 2.不可登记
	*/
	@Column(name= "isRegistration")
    @ApiModelProperty(value = "登记情况 1.可以登记 2.不可登记")
	private Integer isRegistration;

	/**
	* 里程数（公里）
	*/
	@Column(name= "mileage")
    @ApiModelProperty(value = "里程数（公里）")
	private String mileage;

	/**
	* 出厂日期
	*/
	@Column(name= "productionTime")
    @ApiModelProperty(value = "出厂日期")
	private Date productionTime;

	/**
	* 事故次数
	*/
	@Column(name= "accidentsNumber")
    @ApiModelProperty(value = "事故次数")
	private Integer accidentsNumber;

	/**
	* 使用时间 （年）
	*/
	@Column(name= "usageTime")
    @ApiModelProperty(value = "使用时间 （年）")
	private String usageTime;

	/**
	* 车况 1.好 2.差 3.一般 4.很好 5.其他
	*/
	@Column(name= "carCondition")
    @ApiModelProperty(value = "车况 1.好 2.差 3.一般 4.很好 5.其他")
	private Integer carCondition;

	/**
	* 模型骨架(万)
	*/
	@Column(name= "modelSkeleton")
    @ApiModelProperty(value = "模型骨架(万)")
	private BigDecimal modelSkeleton;

	/**
	* 市场交易价1
	*/
	@Column(name= "marketTradingPrice1")
    @ApiModelProperty(value = "市场交易价1")
	private BigDecimal marketTradingPrice1;

	/**
	* 市场交易价2
	*/
	@Column(name= "marketTradingPrice2")
    @ApiModelProperty(value = "市场交易价2")
	private BigDecimal marketTradingPrice2;

}
