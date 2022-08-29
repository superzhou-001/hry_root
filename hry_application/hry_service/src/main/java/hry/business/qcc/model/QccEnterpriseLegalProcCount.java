/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:26:57 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseLegalProcCount </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:26:57 
 */
@Data
@ApiModel(value = "企查查企业法律诉讼维度条目信息实体类")
@Table(name="qcc_enterprise_legalproccount")
public class QccEnterpriseLegalProcCount extends BaseModel {

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
	* 开庭公告数量
	*/
	@Column(name= "CourtAnnoCount")
    @ApiModelProperty(value = "开庭公告数量")
	private String CourtAnnoCount;

	/**
	* 法院公告数量
	*/
	@Column(name= "CourtNotiCount")
    @ApiModelProperty(value = "法院公告数量")
	private String CourtNotiCount;

	/**
	* 裁判文书数量
	*/
	@Column(name= "JudgeCount")
    @ApiModelProperty(value = "裁判文书数量")
	private String JudgeCount;

	/**
	* 股权冻结数量
	*/
	@Column(name= "JudicialAssCount")
    @ApiModelProperty(value = "股权冻结数量")
	private String JudicialAssCount;

	/**
	* 失信被执行人数量
	*/
	@Column(name= "SXCount")
    @ApiModelProperty(value = "失信被执行人数量")
	private String SXCount;

	/**
	* 被执行人数量
	*/
	@Column(name= "ZXCount")
    @ApiModelProperty(value = "被执行人数量")
	private String ZXCount;

	/**
	* 立案信息数量
	*/
	@Column(name= "CaseFilingCount")
    @ApiModelProperty(value = "立案信息数量")
	private String CaseFilingCount;

}
