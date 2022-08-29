/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-08-19 11:26:51 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

import java.util.Date;

/**
 * <p> AppHolidaysInfo </p>
 *
 * @author: zhouming
 * @Date: 2020-08-19 11:26:51 
 */
@Data
@ApiModel(value = "节假日明细实体类")
@Table(name="app_holidays_info")
public class AppHolidaysInfo extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 年份Id
	*/
	@Column(name= "yearId")
    @ApiModelProperty(value = "年份Id")
	private Long yearId;

	/**
	* 年份
	*/
	@Column(name= "year")
    @ApiModelProperty(value = "年份")
	private String year;

	/**
	* 日期名称
	*/
	@Column(name= "dateName")
    @ApiModelProperty(value = "日期名称")
	private String dateName;

	/**
	* 日期类型 0 节假日 1 工作日
	*/
	@Column(name= "dateType")
    @ApiModelProperty(value = "日期类型 1 节假日 2 工作日")
	private Integer dateType;

	/**
	* 节假日日期
	*/
	@Column(name= "holidayDate")
    @ApiModelProperty(value = "节假日日期")
	private Date holidayDate;

	/**
	* 日期开始时间
	*/
	@Column(name= "dateStartTime")
	@Transient
	private String dateStartTime;

	/**
	 * 日期结束时间
	 */
	@Column(name= "dateEndTime")
	@Transient
	private String dateEndTime;

}
