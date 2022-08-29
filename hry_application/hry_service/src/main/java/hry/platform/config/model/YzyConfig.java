/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-05-28 16:12:18 
 */
package hry.platform.config.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> YzyConfig </p>
 *
 * @author: yaoz
 * @Date: 2020-05-28 16:12:18 
 */
@Data
@ApiModel(value = "易租云产品实体类")
@Table(name="yzy_config")
public class YzyConfig extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

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
	* 剩余次数
	*/
	@Column(name= "remainingnumber")
    @ApiModelProperty(value = "剩余次数")
	private Integer remainingnumber;

	/**
	* 总次数
	*/
	@Column(name= "totalnumber")
    @ApiModelProperty(value = "总次数")
	private Integer totalnumber;

	/**
	* 产品名称
	*/
	@Column(name= "productname")
    @ApiModelProperty(value = "产品名称")
	private String productname;

	/**
	* 产品编码
	*/
	@Column(name= "productCode")
    @ApiModelProperty(value = "产品编码")
	private String productCode;

	/**
	* 请求地址
	*/
	@Column(name= "productUrl")
    @ApiModelProperty(value = "请求地址")
	private String productUrl;

	/**
	* 产品分类 1.工商信息 2.法律诉讼 3.风险识别
	*/
	@Column(name= "productClassify")
    @ApiModelProperty(value = "产品分类 1.工商信息 2.法律诉讼 3.风险识别")
	private Integer productClassify;

	/**
	* 是否开启 1是 2否
	*/
	@Column(name= "isOpen")
    @ApiModelProperty(value = "是否开启 1是 2否")
	private Integer isOpen;

}
