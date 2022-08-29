/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-26 14:20:31 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseCourtnotice </p>
 *
 * @author: yaoz
 * @Date: 2020-05-26 14:20:31 
 */
@Data
@ApiModel(value = "企查查开庭公告信息表实体类")
@Table(name="qcc_enterprise_courtnotice")
public class QccEnterpriseCourtnotice extends BaseModel {

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
	* 被告/被上诉人
	*/
	@Column(name= "Defendantlist")
    @ApiModelProperty(value = "被告/被上诉人")
	private String Defendantlist;

	/**
	* 法院
	*/
	@Column(name= "Executegov")
    @ApiModelProperty(value = "法院")
	private String Executegov;

	/**
	* 原告/上诉人
	*/
	@Column(name= "Prosecutorlist")
    @ApiModelProperty(value = "原告/上诉人")
	private String Prosecutorlist;

	/**
	* 开庭日期
	*/
	@Column(name= "LianDate")
    @ApiModelProperty(value = "开庭日期")
	private String LianDate;

	/**
	* 案由
	*/
	@Column(name= "CaseReason")
    @ApiModelProperty(value = "案由")
	private String CaseReason;

	/**
	* 案号
	*/
	@Column(name= "CaseNo")
    @ApiModelProperty(value = "案号")
	private String CaseNo;

}
