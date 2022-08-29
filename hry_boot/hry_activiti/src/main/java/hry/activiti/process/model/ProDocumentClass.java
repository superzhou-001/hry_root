/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:54:44 
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProDocumentClass </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:54:44 
 */
@Data
@ApiModel(value = "材料分类实体类")
@Table(name="pro_document_class")
public class ProDocumentClass extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 分类名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "分类名称")
	private String name;

	/**
	* 分类图标
	*/
	@Column(name= "icon")
    @ApiModelProperty(value = "分类图标")
	private String icon;

	/**
	* 1启用0未启用
	*/
	@Column(name= "isEnable")
    @ApiModelProperty(value = "1启用0未启用")
	private Integer isEnable;

}
