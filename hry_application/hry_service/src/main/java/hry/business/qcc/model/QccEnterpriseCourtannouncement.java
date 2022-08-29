/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-01 15:04:09 
 */
package hry.business.qcc.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> QccEnterpriseCourtannouncement </p>
 *
 * @author: yaoz
 * @Date: 2020-06-01 15:04:09 
 */
@Data
@ApiModel(value = "企查查企业法院公告信息实体类")
@Table(name="qcc_enterprise_courtannouncement")
public class QccEnterpriseCourtannouncement extends BaseModel {

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
	* 提交时间
	*/
	@Column(name= "UploadDate")
    @ApiModelProperty(value = "提交时间")
	private String UploadDate;

	/**
	* 执行法院
	*/
	@Column(name= "Court")
    @ApiModelProperty(value = "执行法院")
	private String Court;

	/**
	* 内容
	*/
	@Column(name= "Content")
    @ApiModelProperty(value = "内容")
	private String Content;

	/**
	* 种类
	*/
	@Column(name= "Category")
    @ApiModelProperty(value = "种类")
	private String Category;

	/**
	* 公布日期
	*/
	@Column(name= "PublishedDate")
    @ApiModelProperty(value = "公布日期")
	private String PublishedDate;

	/**
	* 公布、页
	*/
	@Column(name= "PublishedPage")
    @ApiModelProperty(value = "公布、页")
	private String PublishedPage;

	/**
	* 公司名、当事人
	*/
	@Column(name= "Party")
    @ApiModelProperty(value = "公司名、当事人")
	private String Party;

	/**
	* 上诉人信息
	*/
	@Column(name= "ProsecutorList")
    @ApiModelProperty(value = "上诉人信息")
	private String ProsecutorList;

	/**
	* 被上诉人信息
	*/
	@Column(name= "DefendantList")
    @ApiModelProperty(value = "被上诉人信息")
	private String DefendantList;

}
