/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午6:33:16
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;

import javax.persistence.*;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午6:33:16
 */
@Table(name = "new_app_role_menu")
public class NewAppRoleMenu extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "roleid", unique = false, nullable = false)
	private Long roleid;

	@Column(name = "menukey", unique = false, nullable = false)
	private String menukey;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRoleid() {
		return roleid;
	}
	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}
	public String getMenukey() {
		return menukey;
	}
	public void setMenukey(String menukey) {
		this.menukey = menukey;
	}




}
