/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-30 18:49:11
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> AppConfig </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-30 18:49:11
 */
@Data
@ApiModel(value = "网站参数配置实体类")
@Table(name="app_config")
public class AppConfig extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "configid", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long configid;

	/**
	* 配置key
	*/
	@Column(name= "configkey")
    @ApiModelProperty(value = "配置key")
	private String configkey;

	/**
	* 配置名
	*/
	@Column(name= "configname")
    @ApiModelProperty(value = "配置名")
	private String configname;

	/**
	* 配置描述
	*/
	@Column(name= "configdescription")
    @ApiModelProperty(value = "配置描述")
	private String configdescription;

	/**
	* 数据类型  1、文本框 2、下拉选 3、上传组件 4、富文本编辑器 5、时间控件 6、多文本编辑
	*/
	@Column(name= "datatype")
    @ApiModelProperty(value = "数据类型  1、文本框 2、下拉选 3、上传组件 4、富文本编辑器 5、时间控件 6、多文本编辑")
	private Integer datatype;

	/**
	* 配置值
	*/
	@Column(name= "value")
    @ApiModelProperty(value = "配置值")
	private String value;

	/**
	* 分类key
	*/
	@Column(name= "typekey")
    @ApiModelProperty(value = "分类key")
	private String typekey;

	/**
	* 分类名
	*/
	@Column(name= "typename")
    @ApiModelProperty(value = "分类名")
	private String typename;

	/**
	* 排序
	*/
	@Column(name= "sort")
    @ApiModelProperty(value = "排序")
	private String sort;

	/**
	* 0开启 1关闭
	*/
	@Column(name= "ishidden")
    @ApiModelProperty(value = "1开启 0关闭")
	private Integer ishidden;

}
