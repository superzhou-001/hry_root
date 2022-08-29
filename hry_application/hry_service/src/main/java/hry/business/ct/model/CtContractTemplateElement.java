/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-06-09 11:30:50 
 */
package hry.business.ct.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> CtContractTemplateElement </p>
 *
 * @author: yaoz
 * @Date: 2020-06-09 11:30:50 
 */
@Data
@ApiModel(value = "合同模板要素实体类")
@Table(name="ct_contract_template_element")
public class CtContractTemplateElement extends BaseModel {

	/**
	* 
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "")
	private Long id;

	/**
	* 模板id
	*/
	@Column(name= "contractTemplateId")
    @ApiModelProperty(value = "模板id")
	private Long contractTemplateId;

	/**
	* 要素id
	*/
	@Column(name= "contractElementId")
    @ApiModelProperty(value = "要素id")
	private Long contractElementId;

	/**
	* 描述
	*/
	@Column(name= "remark")
    @ApiModelProperty(value = "描述")
	private String remark;

	/**
	* 宽
	*/
	@Column(name= "width")
    @ApiModelProperty(value = "宽")
	private String width;

	/**
	* 高
	*/
	@Column(name= "height")
    @ApiModelProperty(value = "高")
	private String height;

	/**
	* 文字大小
	*/
	@Column(name= "textSize")
    @ApiModelProperty(value = "文字大小")
	private Integer textSize;

	/**
	* 页码
	*/
	@Column(name= "pageNum")
    @ApiModelProperty(value = "页码")
	private Integer pageNum;

	/**
	* 页码
	*/
	@Column(name= "x")
    @ApiModelProperty(value = "x轴")
	private String x;

	/**
	* 页码
	*/
	@Column(name= "y")
    @ApiModelProperty(value = "y轴")
	private String y;

	/**
	 * 模板文件地址
	 */
	@Transient
	@ApiModelProperty(value = "模板文件地址")
	private String fileUrl;



	/**
	 * 要素key
	 */
	@Transient
	@ApiModelProperty(value = "要素key")
	private String elementKey;

	/**
	 * 要素值
	 */
	@Transient
	@ApiModelProperty(value = "要素值")
	private String elementValue;

	/**
	 * 要素名称
	 */
	@Transient
	@ApiModelProperty(value = "要素名称")
	private String elementName;

	/**
	 * 要素格式
	 */
	@Transient
	@ApiModelProperty(value = "要素格式")
	private String elementFormat;

	/**
	 * 取值表名
	 */
	@Transient
	@ApiModelProperty(value = "取值表名")
	private String tableName;

	/**
	 * 取值字段
	 */
	@Transient
	@ApiModelProperty(value = "取值字段")
	private String tableField;

	/**
	 * 取值条件
	 */
	@Transient
	@ApiModelProperty(value = "取值条件")
	private String valueConditions;

	/**
	 * 描述
	 */
	@Transient
	@ApiModelProperty(value = "描述")
	private String cceRemark;

}
