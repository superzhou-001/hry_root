/**
 * Copyright: 互融云
 *
 * @author: yaoz
 * @version: V1.0
 * @Date: 2020-08-11 14:14:15 
 */
package hry.business.fa.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;


/**
 * <p> FaFactoringBill </p>
 *
 * @author: yaoz
 * @Date: 2020-08-11 14:14:15 
 */
@Data
@ApiModel(value = "保理业务关联票据实体类")
@Table(name="fa_factoring_bill")
public class FaFactoringBill extends BaseModel {

	/**
	* 主键id
	*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
    @ApiModelProperty(value = "主键id")
	private Long id;

	/**
	* 项目id
	*/
	@Column(name= "projectId")
    @ApiModelProperty(value = "项目id")
	private Long projectId;

	/**
	* 票据Id
	*/
	@Column(name= "billId")
    @ApiModelProperty(value = "票据Id")
	private Long billId;

}
