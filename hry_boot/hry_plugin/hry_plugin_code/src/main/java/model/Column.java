/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午6:54:37
 */
package model;


/**
 * <p>列对象</p>
 * @author: Liu Shilei
 * @Date :          2016年9月28日 下午6:54:37
 */
public class Column {

    private final static String BIGINT = "bigint";
    private final static String DATETIME = "datetime";
    private final static String VARCHAR = "varchar";
    private final static String INT = "int";
    private final static String DECIMAL = "decimal";

    /**
     * 字段名称
     */
    private String column = "";
    /**
     * 第一个字母大写
     */
    private String getcolumn = "";
    /**
     * mysql 类型
     */
    private String mysqltype = "";
    /**
     * JAVA 类型
     */
    private String javatype = "";
    /**
     * 主外建
     */
    private String key = "";
    /**
     * 注释
     */
    private Comments comments = null;

    /**
     * <p> TODO</p>
     * @return: String
     */
    public String getColumn () {
        return column;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public void setColumn (String column) {
        this.column = column;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public String getMysqltype () {
        return mysqltype;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public void setMysqltype (String mysqltype) {
        this.mysqltype = mysqltype;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public String getJavatype () {
        return replaceType(mysqltype);
    }


    /**
     * <p> TODO</p>
     * @return: String
     */
    public String getKey () {
        return key;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public void setKey (String key) {
        this.key = key;
    }


    public String replaceType (String type) {
        if (type.contains(BIGINT)) {
            return "Long";
        }
        if (type.contains(DATETIME)) {
            return "Date";
        }
        if (type.contains(VARCHAR)) {
            return "String";
        }
        if (type.contains(INT)) {
            return "Integer";
        }
        if (type.contains(DECIMAL)) {
            return "BigDecimal";
        }

        return "String";
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public String getGetcolumn () {
        getcolumn = column.substring(0, 1).toUpperCase() + column.substring(1);
        return getcolumn;
    }

    /**
     * <p>如果注释为空都返回字段名称</p>
     * @return: String
     */
    public Comments getComments () {
        if (comments != null) {
            return comments;
        }
        return null;
    }

    /**
     * <p> TODO</p>
     * @return: String
     */
    public void setComments (Comments comments) {
        this.comments = comments;
    }


}
