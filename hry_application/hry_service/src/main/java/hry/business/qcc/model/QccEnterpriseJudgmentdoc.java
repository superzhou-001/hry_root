/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 14:13:21 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseJudgmentdoc </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 14:13:21 
 */
@Data
@ApiModel(value = "企查查企业裁判文书信息实体类")
@Table(name="qcc_enterprise_judgmentdoc")
public class QccEnterpriseJudgmentdoc extends BaseModel {

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
	* 执行法院
	*/
	@Column(name= "Court")
    @ApiModelProperty(value = "执行法院")
	private String Court;

	/**
	* 裁判文书名字
	*/
	@Column(name= "CaseName")
    @ApiModelProperty(value = "裁判文书名字")
	private String CaseName;

	/**
	* 裁判文书编号
	*/
	@Column(name= "CaseNo")
    @ApiModelProperty(value = "裁判文书编号")
	private String CaseNo;

	/**
	* 裁判文书类型
	*/
	@Column(name= "CaseType")
    @ApiModelProperty(value = "裁判文书类型")
	private String CaseType;

	/**
	* 发布时间
	*/
	@Column(name= "SubmitDate")
    @ApiModelProperty(value = "发布时间")
	private String SubmitDate;

	/**
	* 审判时间
	*/
	@Column(name= "UpdateDate")
    @ApiModelProperty(value = "审判时间")
	private String UpdateDate;

	/**
	* 是否原告（供参考）
	*/
	@Column(name= "IsProsecutor")
    @ApiModelProperty(value = "是否原告（供参考）")
	private String IsProsecutor;

	/**
	* 是否被告（供参考）
	*/
	@Column(name= "IsDefendant")
    @ApiModelProperty(value = "是否被告（供参考）")
	private String IsDefendant;

	/**
	* 开庭时间年份
	*/
	@Column(name= "CourtYear")
    @ApiModelProperty(value = "开庭时间年份")
	private String CourtYear;

	/**
	* 涉案人员角色
	*/
	@Column(name= "CaseRole")
    @ApiModelProperty(value = "涉案人员角色")
	private String CaseRole;

	/**
	* 法院级别，最高法院 5 、高级法院 4 、中级法院 3 、基层法院 2 、其他 1
	*/
	@Column(name= "CourtLevel")
    @ApiModelProperty(value = "法院级别，最高法院 5 、高级法院 4 、中级法院 3 、基层法院 2 、其他 1")
	private String CourtLevel;

	/**
	* 案由
	*/
	@Column(name= "CaseReason")
    @ApiModelProperty(value = "案由")
	private String CaseReason;

	/**
	* 案由类型
	*/
	@Column(name= "CaseReasonType")
    @ApiModelProperty(value = "案由类型")
	private String CaseReasonType;

	/**
	* 开庭时间月份
	*/
	@Column(name= "CourtMonth")
    @ApiModelProperty(value = "开庭时间月份")
	private String CourtMonth;

	/**
	* 案件金额
	*/
	@Column(name= "Amount")
    @ApiModelProperty(value = "案件金额")
	private String Amount;

}
