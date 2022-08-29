/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 11:31:20 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> NewAppDic </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 11:31:20 
 */
@Data
@ApiModel(value = "数据字典实体类")
@Table(name="new_app_dic")
public class NewAppDic extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 字典项key
	*/
	@Column(name= "mkey")
    @ApiModelProperty(value = "字典项key")
	private String mkey;

	/**
	* 字典分类key
	*/
	@Column(name= "pkey")
    @ApiModelProperty(value = "字典分类key")
	private String pkey;

	/**
	* 字典项名称
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "字典项名称")
	private String name;

	/**
	* 字典项值
	*/
	@Column(name= "value")
    @ApiModelProperty(value = "字典项值")
	private String value;

	/**
	* 分类层级 1-1级 2-2级 3-3级 。。。
	*/
	@Column(name= "type")
    @ApiModelProperty(value = "分类层级 1-1级 2-2级 3-3级 。。。")
	private String type;

	/**
	* 扩展字段1
	*/
	@Column(name= "remark1")
    @ApiModelProperty(value = "扩展字段1")
	private String remark1;

	/**
	* 扩展字段2
	*/
	@Column(name= "remark2")
    @ApiModelProperty(value = "扩展字段2")
	private String remark2;

	/**
	* 扩展字段3
	*/
	@Column(name= "remark3")
    @ApiModelProperty(value = "扩展字段3")
	private String remark3;

	@Transient
	private List<NewAppDic> children;

}
