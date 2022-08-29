/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月2日 上午11:14:48
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 *
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月2日 上午11:14:48
 */
@Data
@Table(name = "new_app_organization")
public class NewAppOrganization extends BaseModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	/**
	 *   root  集团   --根节点
	 *   agent 代理商
	 *   company 公司
	 *   shop  门店
	 *   department 部门
	 *
	 */
	@Column(name="type")
	@ApiModelProperty(value = "组织类型 company 公司  agent代理商  shop 门店 department部门")
	private String type;

	/**
	 * @Description: 父级组织机构
	 */
	@Column(name="pid")
	@ApiModelProperty(value = "组织父id")
	private Long pid;

	//排序号
	@Column(name="orderno")
	@ApiModelProperty(value = "排序号")
	private Integer orderno ;

	//集团名称/分公司名称/部门名称
	@Column(name="name")
	@ApiModelProperty(value = "组织名称")
	private String name ;

	@Column(name= "anothername")
	@ApiModelProperty(value = "组织别名")
	private String anothername;

	//备注
	@Column(name="remark")
	@ApiModelProperty(value = "备注")
	private String remark;

	@Transient
	private List<NewAppOrganization> children;

	/**
	 * @Description:  部门树结构使用字段
	 */
	@Transient
	private String pkey;
}
