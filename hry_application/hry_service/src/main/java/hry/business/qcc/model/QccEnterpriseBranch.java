/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 09:59:10 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseBranch </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 09:59:10 
 */
@Data
@ApiModel(value = "企业分支机构信息表实体类")
@Table(name="qcc_enterprise_branch")
public class QccEnterpriseBranch extends BaseModel {

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
	* 企业KeyNo
	*/
	@Column(name= "KeyNo")
    @ApiModelProperty(value = "企业KeyNo")
	private String KeyNo;

	/**
	* 企业名称
	*/
	@Column(name= "Name")
    @ApiModelProperty(value = "企业名称")
	private String Name;

	/**
	* 企业状态
	*/
	@Column(name= "Status")
    @ApiModelProperty(value = "企业状态")
	private String Status;

	/**
	* 法人信息
	*/
	@Column(name= "OperInfo")
    @ApiModelProperty(value = "法人信息")
	private String OperInfo;

}
