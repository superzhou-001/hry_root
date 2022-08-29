/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Liu Shilei
 * @version:      V1.0
 * @Date:        2015年12月9日 下午6:35:18
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;
import lombok.Data;

import javax.persistence.*;

/**
 * <p> TODO</p>
 * @author:         Liu Shilei
 * @Date :          2015年12月9日 下午6:35:18
 */
@Data
@Entity
@Table(name = "new_app_user_organization")
public class NewAppUserOrganization extends BaseModel{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "userid", unique = false, nullable = false)
	private Long userid;

	@Column(name = "organizationid", unique = false, nullable = false)
	private Long organizationid;

	//company     所属公司
	//subcompany  所属分公司
	//shop        所属门店
	//department  所属部门
	@Column(name = "type", unique = false, nullable = false)
	private String type;

	@Transient
	private String organizationName; // 组织名称

	@Transient
	private String userName;  //用户名--账户

	@Transient
	private String name; // 姓名

	@Transient
	private Integer islock;  //是否锁定，0否1是

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIslock() {
		return islock;
	}

	public void setIslock(Integer islock) {
		this.islock = islock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Long getOrganizationid() {
		return organizationid;
	}

	public void setOrganizationid(Long organizationid) {
		this.organizationid = organizationid;
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


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
}
