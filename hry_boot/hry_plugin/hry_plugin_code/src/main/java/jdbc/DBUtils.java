/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午6:59:59
 */
package jdbc;

import model.Column;
import model.Comments;
import model.Table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <p> TODO</p>
 *
 * @author: Liu Shilei
 * @Date :          2016年9月28日 下午6:59:59
 */
public class DBUtils {


    public static Table getTabel (String tableName) {
        DBHelper db = new DBHelper("show full columns from " + tableName + " ");
        Table table = new Table();
        ArrayList<Column> list = new ArrayList<Column>();
        try {
            ResultSet ret = db.pst.executeQuery();
            while (ret.next()) {
                if (!"saasid".equals(ret.getString(1)) &&
                        !"created".equals(ret.getString(1)) &&
                        !"modified".equals(ret.getString(1)) &&
                        !"createTime".equals(ret.getString(1)) &&
                        !"creator".equals(ret.getString(1)) &&
                        !"editor".equals(ret.getString(1)) &&
                        !"editTime".equals(ret.getString(1))
                ) {
                    Column column = new Column();
                    column.setColumn(ret.getString(1));
                    column.setMysqltype(ret.getString(2));
                    column.setKey(ret.getString(5));
                    if (ret.getString(9) != null && ret.getString(9) != "") {
                        Comments comments = new Comments();
                        try {
                            comments.setName(ret.getString(9));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        column.setComments(comments);
                    }
                    list.add(column);
                }
            }
            //显示数据
            //存入字段,字段排序，主键放在第一位
            table.setColumns(list);
            //关闭连接
            ret.close();
            db.close();
            return table;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
