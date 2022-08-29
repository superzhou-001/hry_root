/**
 * Copyright: 互融云
 *
 * @author: zengzhiyan
 * @version: V1.0
 * @Date: 2020-07-08 14:25:30 
 */
package hry.scm.fundsupport.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> FundSupport </p>
 *
 * @author: zengzhiyan
 * @Date: 2020-07-08 14:25:30 
 */
@Data
@ApiModel(value = "资金方实体类")
@Table(name="scm_fund_support")
public class FundSupport extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 资金方名称
	*/
	@Column(name= "fundSupportName")
    @ApiModelProperty(value = "资金方名称")
	private String fundSupportName;

	/**
	* 资方类型key
	*/
	@Column(name= "fundSupportType")
    @ApiModelProperty(value = "资方类型key 父类数据字典Key-fundSupportType,保存值为NewAppDic对象的value")
	private String fundSupportType;

	/**
	* 资方类型名称
	*/
	@Column(name= "typeName")
    @ApiModelProperty(value = "资方类型名称,,保存值为NewAppDic对象的name")
	private String typeName;

	/**
	* 资方简称
	*/
	@Column(name= "shortName")
    @ApiModelProperty(value = "资方简称")
	private String shortName;

	/**
	* 负责人名称
	*/
	@Column(name= "principalName")
    @ApiModelProperty(value = "负责人名称")
	private String principalName;

	/**
	* 负责人电话
	*/
	@Column(name= "principalMobile")
    @ApiModelProperty(value = "负责人电话")
	private String principalMobile;

	/**
	* 用户关联Id
	*/
	@Column(name= "userRelationId")
    @ApiModelProperty(value = "用户关联Id")
	private Long userRelationId;

	/**
	* 省Id
	*/
	@Column(name= "provinceKey")
    @ApiModelProperty(value = "省Key")
	private String provinceKey;

	/**
	* 市Id
	*/
	@Column(name= "cityKey")
    @ApiModelProperty(value = "市Key")
	private String cityKey;

	/**
	* 区Id
	*/
	@Column(name= "areaKey")
    @ApiModelProperty(value = "区Key")
	private String areaKey;

	/**
	* 省
	*/
	@Column(name= "provinceName")
    @ApiModelProperty(value = "省")
	private String provinceName;

	/**
	* 
	*/
	@Column(name= "cityName")
    @ApiModelProperty(value = "")
	private String cityName;

	/**
	* 区
	*/
	@Column(name= "areaName")
    @ApiModelProperty(value = "区")
	private String areaName;

	/**
	 * 1 禁用 2启用
	 */
	@Column(name= "isEnable")
	@ApiModelProperty(value = "1 禁用 2启用")
	private Integer isEnable;

}
