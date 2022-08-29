/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午6:46:27
 */
package jdbc;

import plugin.Config;

import java.sql.*;


/**
 * <p> TODO</p>
 * @author: Liu Shilei
 * @Date :          2016年9月28日 下午6:46:27
 */
public class DBHelper {
    public static final String NAME = "com.mysql.jdbc.Driver";

    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBHelper (String sql) {
        try {
            //指定连接类型
            Class.forName(NAME);
            //获取连接
            conn = DriverManager.getConnection(Config.config.getProperty("mysql_url"), Config.config.getProperty("mysql_user"), Config.config.getProperty("mysql_password"));
            //准备执行语句
            pst = conn.prepareStatement(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close () {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main (String[] args) {
        DBHelper db = new DBHelper("show columns from app_user");
        try {
            ResultSet ret = db.pst.executeQuery();
            while (ret.next()) {
                String column = ret.getString(1);
                String type = ret.getString(2);
                System.out.println(column + "=========" + type);
            }//显示数据
            ret.close();
            db.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
