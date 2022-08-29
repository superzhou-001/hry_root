/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-02 11:13:41 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseJudicialAssistance </p>
 *
 * @author: yaoz
 * @Date: 2020-06-02 11:13:41 
 */
@Data
@ApiModel(value = "企查查企业股权冻结信息实体类")
@Table(name="qcc_enterprise_judicialassistance")
public class QccEnterpriseJudicialAssistance extends BaseModel {

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
	* 被执行人
	*/
	@Column(name= "ExecutedBy")
    @ApiModelProperty(value = "被执行人")
	private String ExecutedBy;

	/**
	* 股权数额
	*/
	@Column(name= "EquityAmount")
    @ApiModelProperty(value = "股权数额")
	private String EquityAmount;

	/**
	* 执行法院
	*/
	@Column(name= "EnforcementCourt")
    @ApiModelProperty(value = "执行法院")
	private String EnforcementCourt;

	/**
	* 执行通知书文号
	*/
	@Column(name= "ExecutionNoticeNum")
    @ApiModelProperty(value = "执行通知书文号")
	private String ExecutionNoticeNum;

	/**
	* 类型 | 状态
	*/
	@Column(name= "Status")
    @ApiModelProperty(value = "类型 | 状态")
	private String Status;

	/**
	* 股权冻结情况
	*/
	@Column(name= "EquityFreezeDetail")
    @ApiModelProperty(value = "股权冻结情况")
	private String EquityFreezeDetail;

	/**
	* 解除冻结详情
	*/
	@Column(name= "EquityUnFreezeDetail")
    @ApiModelProperty(value = "解除冻结详情")
	private String EquityUnFreezeDetail;

	/**
	* 股东变更信息
	*/
	@Column(name= "JudicialPartnersChangeDetail")
    @ApiModelProperty(value = "股东变更信息")
	private String JudicialPartnersChangeDetail;

}
