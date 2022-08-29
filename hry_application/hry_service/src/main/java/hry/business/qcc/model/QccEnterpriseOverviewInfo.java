/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 14:19:13 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseOverviewInfo </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 14:19:13 
 */
@Data
@ApiModel(value = "企查查企业工商风险扫描实体类")
@Table(name="qcc_enterprise_overviewinfo")
public class QccEnterpriseOverviewInfo extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 企业id
	*/
	@Column(name= "enterpriseId")
    @ApiModelProperty(value = "企业id")
	private String enterpriseId;

	/**
	* 
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "")
	private String remark;

	/**
	* 
	*/
	@Column(name= "uuid")
    @ApiModelProperty(value = "")
	private String uuid;

	/**
	* 行政许可【信用中国】
	*/
	@Column(name= "PermissionInfo")
    @ApiModelProperty(value = "行政许可【信用中国】")
	private String PermissionInfo;

	/**
	* 行政处罚
	*/
	@Column(name= "Penalty")
    @ApiModelProperty(value = "行政处罚")
	private String Penalty;

	/**
	* 经营异常
	*/
	@Column(name= "Exceptions")
    @ApiModelProperty(value = "经营异常")
	private String Exceptions;

	/**
	* 失信
	*/
	@Column(name= "ShiXinItems")
    @ApiModelProperty(value = "失信")
	private String ShiXinItems;

	/**
	* 执行
	*/
	@Column(name= "ZhiXingItems")
    @ApiModelProperty(value = "执行")
	private String ZhiXingItems;

	/**
	* 动产抵押
	*/
	@Column(name= "MPledge")
    @ApiModelProperty(value = "动产抵押")
	private String MPledge;

}
