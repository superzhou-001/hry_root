/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年9月21日 上午9:39:13
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;

import javax.persistence.*;

/**
 * <p> TODO</p>     用户角色表
 * @author:         Liu Shilei
 * @Date :          2015年9月21日 上午9:39:13
 */
@Table(name = "new_app_role")
public class NewAppRole extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	//角色名称
	@Column(name = "name")
	private String name;

	//角色描述
	@Column(name = "remark")
	private String remark;

	//角色类型  默认正常类型normal
	//      分公司管理者角色  subcompany
	@Column(name = "type")
	private String type ="normal";


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * <p> TODO</p>
	 * @return:     String
	 */
	public String getName() {
		return name;
	}

	/**
	 * <p> TODO</p>
	 * @return: String
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * <p> TODO</p>
	 * @return:     String
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * <p> TODO</p>
	 * @return: String
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}




	/**
	 * <p> TODO</p>
	 * @return:     String
	 */
	public String getType() {
		return type;
	}

	/**
	 * <p> TODO</p>
	 * @return: String
	 */
	public void setType(String type) {
		this.type = type;
	}









}
