/**
 * Copyright:   互融云
 * @author:      liushilei
 * @version:     V1.0
 * @Date:        2017-06-01 19:44:41
 */
package hry.platform.newuser.model;


import hry.bean.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * <p> AppMenu </p>
 * @author:         liushilei
 * @Date :          2017-06-01 19:44:41
 */
@ApiModel(value = "菜单权限类")
@Table(name="new_app_menu")
@Data
public class NewAppMenu extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;  //id

    @Column(name= "name")
    @ApiModelProperty(value = "名称")
    private String name;  //name

    @Column(name= "shirourl")
    @ApiModelProperty(value = "权限名称")
    private String shirourl;  //shiroUrl

    @Column(name= "url")
    @ApiModelProperty(value = "url")
    private String url;  //url

    @Column(name= "orderno")
    @ApiModelProperty(value = "排序字段")
    private Integer orderno;  //orderNo

    @Column(name= "appname")
    private String appname;  //appName

    @Column(name= "mkey")
    private String mkey;  //mkey

    @Column(name= "pkey")
    @ApiModelProperty(value = "父级key")
    private String pkey;  //pkey

    @Column(name= "type")
    @ApiModelProperty(value = "菜单类型,menu菜单button按钮")
    private String type;  //type

    @Column(name= "icon")
    @ApiModelProperty(value = "图标")
    private String icon;  //type

    @Transient
    private List<NewAppMenu> children;


}
