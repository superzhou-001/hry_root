/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午6:54:37
 */
package model;


/**
 * <p> TODO</p>
 * @author: Shangxl
 * @Date :          2017年6月1日 下午6:17:08
 */
public class Comments {
    /**
     * 列名中文名
     */
    private String name;
    /**
     * 是否查询字段（true or false）
     */
    private Boolean search;
    /**
     * 查询条件
     */
    private String condit;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Boolean getSearch () {
        return search;
    }

    public void setSearch (Boolean search) {
        this.search = search;
    }

    public String getCondit () {
        return condit;
    }

    public void setCondit (String condit) {
        this.condit = condit;
    }

}
