/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-22 10:17:37 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> QccEnterpriseChange </p>
 *
 * @author: yaoz
 * @Date: 2020-05-22 10:17:37 
 */
@Data
@ApiModel(value = "企业变更信息表实体类")
@Table(name="qcc_enterprise_change")
public class QccEnterpriseChange extends BaseModel {

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
	* 变更事项
	*/
	@Column(name= "ProjectName")
    @ApiModelProperty(value = "变更事项")
	private String ProjectName;

	/**
	* 变更前列表
	*/
	@Column(name= "BeforeList")
    @ApiModelProperty(value = "变更前列表")
	private String BeforeList;

	/**
	* 变更后列表
	*/
	@Column(name= "AfterList")
    @ApiModelProperty(value = "变更后列表")
	private String AfterList;

	/**
	* 变更前内容列表
	*/
	//@Column(name= "BeforeInfoList")
    @ApiModelProperty(value = "变更前内容列表")
	private List<String> BeforeInfoList;

	/**
	* 变更后内容列表
	*/
	//@Column(name= "AfterInfoList")
    @ApiModelProperty(value = "变更后内容列表")
	private List<String>  AfterInfoList;

	/**
	* 变更日期
	*/
	@Column(name= "ChangeDate")
    @ApiModelProperty(value = "变更日期")
	private String ChangeDate;

}
