/**
 * Copyright: ${HRY_COMPANY}
 *
 * @author: ${codeAuth!}
 * @version: V1.0
 * @Date: ${codeDate!}
 */
package ${HRY_PACKAGE!}.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

<#if columnsType ?? >
<#list columnsType as ct >
<#if ct=="BigDecimal" >
import java.math.BigDecimal;
<#elseif  ct=="Date" >
import java.util.Date;
<#else>
</#if>
</#list>
</#if>

/**
 * <p> ${HRY_MODEL!} </p>
 *
 * @author: ${codeAuth!}
 * @Date: ${codeDate!}
 */
@Data
@ApiModel(value = "${HRY_TABLE_NAME!}实体类")
@Table(name="${HRY_TABLE!}")
public class ${HRY_MODEL!} extends BaseModel {

<#if columns ?? >
<#list columns as c >
	/**
	* ${(c.comments.name)!}
	*/
	<#if c.key=="PRI">
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "${(c.column)!}", unique = true, nullable = false)
	<#else>
	@Column(name= "${(c.column)!}")
	</#if>
    @ApiModelProperty(value = "${(c.comments.name)!}")
	private ${(c.javatype)!} ${(c.column)!};

</#list>
</#if>
}
