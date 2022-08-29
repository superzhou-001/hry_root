/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午7:06:19
 */
package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>表对象</p>
 * @author: Liu Shilei
 * @Date :          2016年9月28日 下午7:06:19
 */
public class Table {

    private List<Column> columns;

    /**
     * <p> TODO</p>
     * @return: List<Column>
     */
    public List<Column> getColumns () {
        return columns;
    }

    /**
     * <p> TODO</p>
     * @return: List<Column>
     */
    public void setColumns (List<Column> columns) {
        this.columns = columns;
    }

    public Set<String> getColumnsType () {
        Set<String> set = new HashSet<String>();
        for (Column c : columns) {
            set.add(c.getJavatype());
        }
        return set;
    }

}
