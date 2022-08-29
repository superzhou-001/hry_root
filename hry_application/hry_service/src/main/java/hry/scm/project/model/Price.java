/**
 * Copyright: 互融云
 *
 * @author: luyue
 * @version: V1.0
 * @Date: 2020-07-17 15:08:55 
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
 * <p> Price </p>
 *
 * @author: luyue
 * @Date: 2020-07-17 15:08:55 
 */
@Data
@ApiModel(value = "价格记录信息实体类")
@Table(name="scm_price")
public class Price extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 货物名称
	*/
	@Column(name= "goodsName")
    @ApiModelProperty(value = "货物名称")
	private String goodsName;

	/**
	* 价格
	*/
	@Column(name= "price")
    @ApiModelProperty(value = "价格")
	private BigDecimal price;

	/**
	* 修改之前价格
	*/
	@Column(name= "beforePrice")
    @ApiModelProperty(value = "修改之前价格")
	private BigDecimal beforePrice;

	/**
	* 操作人id
	*/
	@Column(name= "operaId")
    @ApiModelProperty(value = "操作人id")
	private Long operaId;

	/**
	* 姓名
	*/
	@Column(name= "operaName")
    @ApiModelProperty(value = "姓名")
	private String operaName;

	/**
	* 记录日期
	*/
	@Column(name= "recordDate")
    @ApiModelProperty(value = "记录日期")
	private Date recordDate;

}
