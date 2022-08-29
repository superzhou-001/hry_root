/**
 * Copyright:   北京互融时代软件有限公司
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2015年12月9日 下午6:17:53
 */
package hry.platform.newuser.model;

import hry.bean.BaseModel;

import javax.persistence.*;

/**
 * <p> TODO</p>
 * @author: Liu Shilei
 * @Date :          2015年12月9日 下午6:17:53
 */
@Entity
@Table(name = "new_app_user_role")
public class NewAppUserRole extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "userid", unique = false, nullable = false)
    private Long userid;
    @Column(name = "roleid", unique = false, nullable = false)
    private Long roleid;

    @Transient
    private String roleName; // 角色名称

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }


}
