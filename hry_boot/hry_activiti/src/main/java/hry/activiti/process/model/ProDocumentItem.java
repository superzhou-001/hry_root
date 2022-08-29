/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-07-21 11:19:36
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDocumentItem </p>
 *
 * @author: liushilei
 * @Date: 2020-07-21 11:19:36
 */
@Data
@ApiModel(value = "流程材料文件实体类")
@Table(name="pro_document_item")
public class ProDocumentItem extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 流程id
	*/
	@Column(name= "processId")
    @ApiModelProperty(value = "流程id")
	private Long processId;

	/**
	* 业务流程id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "业务流程id")
	private Long projectId;

	/**
	* 分类id
	*/
	@Column(name= "classId")
    @ApiModelProperty(value = "分类id")
	private Long classId;

	/**
	* 材料id
	*/
	@Column(name= "documentId")
    @ApiModelProperty(value = "材料id")
	private Long documentId;

	/**
	* 文件名称
	*/
	@Column(name= "fileName")
    @ApiModelProperty(value = "文件名称")
	private String fileName;

	/**
	* 文件路径
	*/
	@Column(name= "filePath")
    @ApiModelProperty(value = "文件路径")
	private String filePath;

	/**
	* 文件大小
	*/
	@Column(name= "fileSize")
    @ApiModelProperty(value = "文件大小")
	private String fileSize;

	/**
	* 来源1后台2前台
	*/
	@Column(name= "source")
    @ApiModelProperty(value = "来源1后台2前台")
	private Integer source;

	/**
	* 是否审核0未审核1通过2未通过
	*/
	@Column(name= "isCheck")
    @ApiModelProperty(value = "是否审核0未审核1通过2未通过")
	private Integer isCheck;

	/**
	* 上传人
	*/
	@Column(name= "creater")
    @ApiModelProperty(value = "上传人")
	private String creater;


	@Transient
	@ApiModelProperty(value = "文件显示路径")
	private String fileViewPath;

}
