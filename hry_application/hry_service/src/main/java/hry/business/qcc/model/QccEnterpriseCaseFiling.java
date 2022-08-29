/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:34:54 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseCaseFiling </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:34:54 
 */
@Data
@ApiModel(value = "企查查企业立案信息实体类")
@Table(name="qcc_enterprise_casefiling")
public class QccEnterpriseCaseFiling extends BaseModel {

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
	* 案号
	*/
	@Column(name= "CaseNo")
    @ApiModelProperty(value = "案号")
	private String CaseNo;

	/**
	* 立案日期
	*/
	@Column(name= "PublishDate")
    @ApiModelProperty(value = "立案日期")
	private String PublishDate;

	/**
	* 公诉人/原告/上诉人/申请人
	*/
	@Column(name= "ProsecutorList")
    @ApiModelProperty(value = "公诉人/原告/上诉人/申请人")
	private String ProsecutorList;

	/**
	* 被告人/被告/被上诉人/被申请人
	*/
	@Column(name= "DefendantList")
    @ApiModelProperty(value = "被告人/被告/被上诉人/被申请人")
	private String DefendantList;

	/**
	* 案件年份
	*/
	@Column(name= "CourtYear")
    @ApiModelProperty(value = "案件年份")
	private String CourtYear;

}
