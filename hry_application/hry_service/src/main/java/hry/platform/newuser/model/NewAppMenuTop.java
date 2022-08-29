/**
 * Copyright: 互融云
 *
 * @author: liuchenghui
 * @version: V1.0
 * @Date: 2020-03-31 16:04:36
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> NewAppMenuTop </p>
 *
 * @author: liuchenghui
 * @Date: 2020-03-31 16:04:36
 */
@Data
@ApiModel(value = "顶部菜单配置实体类")
@Table(name="new_app_menu_top")
public class NewAppMenuTop extends BaseModel {

	/**
	* 主键
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键")
	private Long id;

	/**
	* 菜单名
	*/
	@Column(name= "name")
    @ApiModelProperty(value = "菜单名")
	private String name;

	/**
	* 父级key
	*/
	@Column(name= "pkey")
    @ApiModelProperty(value = "父级key")
	private String pkey;

	/**
	* 菜单key
	*/
	@Column(name= "mkey")
    @ApiModelProperty(value = "菜单key")
	private String mkey;

	/**
	* 图标
	*/
	@Column(name= "icon")
    @ApiModelProperty(value = "图标")
	private String icon;

	/**
	* 排序字段
	*/
	@Column(name= "orderno")
    @ApiModelProperty(value = "排序字段")
	private Integer orderno;

	@ApiModelProperty(value = "顶部菜单子集数据")
	@Transient
	private List<NewAppMenuTop> children;

}
