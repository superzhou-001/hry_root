/**
 * Copyright: 互融云
 *
 * @author: liushilei
 * @version: V1.0
 * @Date: 2020-05-12 17:42:39 
 */
package hry.activiti.process.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> ProPersonGroup </p>
 *
 * @author: liushilei
 * @Date: 2020-05-12 17:42:39 
 */
@Data
@ApiModel(value = "人员组实体类")
@Table(name="pro_person_group")
public class ProPersonGroup extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 人员组名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "人员组名称")
	private String name;

	/**
	* 人员id
	*/
	@Column(name= "personValue")
    @ApiModelProperty(value = "人员id")
	private String personValue;

}
