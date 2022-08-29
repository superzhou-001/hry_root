/**
 * Copyright: 互融云
 *
 * @author: huanggh
 * @version: V1.0
 * @Date: 2020-07-08 17:43:10 
 */
package hry.scm.file.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ScmFile </p>
 *
 * @author: huanggh
 * @Date: 2020-07-08 17:43:10 
 */
@Data
@ApiModel(value = "供应链文件信息实体类")
@Table(name="scm_file")
public class ScmFile extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 文件类型key值
	*/
	@Column(name= "bsKey")
    @ApiModelProperty(value = "文件类型key值")
	private String bsKey;

	/**
	 * 业务表主键
	 */
	@Column(name= "businessId")
	@ApiModelProperty(value = "业务表主键")
	private Long businessId;

	/**
	* 0:未删除，1:已删除
	*/
	@Column(name= "isDelete")
    @ApiModelProperty(value = "0:未删除，1:已删除")
	private Integer isDelete;

	/**
	* 文件地址
	*/
	@Column(name= "fileUrl")
    @ApiModelProperty(value = "文件地址")
	private String fileUrl;

	/**
	* 文件类型
	*/
	@Column(name= "fileType")
    @ApiModelProperty(value = "文件类型")
	private String fileType;

	/**
	* 原始文件名
	*/
	@Column(name= "oldFileName")
    @ApiModelProperty(value = "原始文件名")
	private String oldFileName;

	/**
	* 新文件名
	*/
	@Column(name= "newFileName")
    @ApiModelProperty(value = "新文件名")
	private String newFileName;

	/**
	* 创建人Id
	*/
	@Column(name= "createUserId")
    @ApiModelProperty(value = "创建人Id")
	private Long createUserId;

	/**
	* 创建人姓名
	*/
	@Column(name= "createUserName")
    @ApiModelProperty(value = "创建人姓名")
	private String createUserName;

	/**
	* 修改人Id
	*/
	@Column(name= "modifiedUserId")
    @ApiModelProperty(value = "修改人Id")
	private Long modifiedUserId;

	/**
	* 修改人姓名
	*/
	@Column(name= "modifiedUserName")
    @ApiModelProperty(value = "修改人姓名")
	private String modifiedUserName;

}
