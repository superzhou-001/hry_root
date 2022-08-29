/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-21 13:57:47 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseEmployee </p>
 *
 * @author: yaoz
 * @Date: 2020-05-21 13:57:47 
 */
@Data
@ApiModel(value = "企业主要人员信息表实体类")
@Table(name="qcc_enterprise_employee")
public class QccEnterpriseEmployee extends BaseModel {

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
	* KeyNo
	*/
	@Column(name= "KeyNo")
    @ApiModelProperty(value = "KeyNo")
	private String KeyNo;

	/**
	* 职务
	*/
	@Column(name= "Job")
    @ApiModelProperty(value = "职务")
	private String Job;

	/**
	* 姓名
	*/
	@Column(name= "Name")
    @ApiModelProperty(value = "姓名")
	private String Name;

}
