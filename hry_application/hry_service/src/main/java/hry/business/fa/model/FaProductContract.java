/**
 * Copyright: 互融云
 *
 * @author: zhouming
 * @version: V1.0
 * @Date: 2020-07-17 15:34:53 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import hry.business.ct.model.CtContractTemplate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


/**
 * <p> FaProductContract </p>
 *
 * @author: zhouming
 * @Date: 2020-07-17 15:34:53 
 */
@Data
@ApiModel(value = "产品合同表实体类")
@Table(name="fa_product_contract")
public class FaProductContract extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 产品id
	*/
	@Column(name= "productid")
    @ApiModelProperty(value = "产品id")
	private Long productid;

	/**
	* 合同id
	*/
	@Column(name= "contractid")
    @ApiModelProperty(value = "合同id")
	private Long contractid;

	@Transient
	private String productname; // 产品名称

	@Transient
	private Long productTypeId; // 产品分类Id

	@Transient
	private String productTypeName; // 产品分类名称

	@Transient
	private Integer number; // 产品关联合同数

	@Transient
	private List<CtContractTemplate> contractTemplates; // 关联的合同

}
