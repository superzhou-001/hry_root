package utils;

import model.Column;
import model.Comments;
import plugin.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 资源工具类
 */
public class ResourceUtils {

    /**
     * 获得model是全小写
     *
     * @return
     */
    public static String getModelLC () {
        return Config.config.getProperty("HRY_MODEL").toLowerCase();
    }

    /**
     * 获得model是首字母大写
     *
     * @return
     */
    public static String getModelFUC () {
        String lowerCase = Config.config.getProperty("HRY_MODEL").toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }

    /**
     * 获得model是首字母小写
     * @return
     */
    public static String getModelFLC () {
        return Config.config.getProperty("HRY_MODEL").substring(0, 1).toLowerCase() + Config.config.getProperty("HRY_MODEL").substring(1);
    }

    /**
     * 获得requestMapping的值
     *
     * @return
     */
    public static String getRequestMapping () {
        return getRequestMappingPR() + getModelLC();
    }

    /**
     * 获得requestMapping的前缀
     *
     * @return
     */
    public static String getRequestMappingPR () {
        String pack = Config.config.getProperty("HRY_PACKAGE");
        String[] split = pack.split("\\.");
        StringBuilder rm = new StringBuilder("/");
        for (int i = 0; i < split.length; i++) {
            if (i > 1) {
                rm.append(split[i]);
                rm.append("/");
            }
        }
        return rm.toString();
    }

    /**
     * 获得js路径
     *
     * @return
     */
    public static String getPath () {
        String packagePath = Config.config.getProperty("HRY_PACKAGE");
        String pack = packagePath.substring(packagePath.indexOf(".") + 1);
        String[] split = pack.split("\\.");
        StringBuilder rm = new StringBuilder("/");
        for (int i = 0; i < split.length; i++) {
            rm.append(split[i]);
            rm.append("/");
        }
        return rm.toString();
    }

    /**
     * 获得hry.web.user中是web值
     *
     * @return
     */
    public static String getProjectName () {
        String pack = Config.config.getProperty("HRY_PACKAGE");
        String[] split = pack.split("\\.");
        return split[1];
    }

    /**
     * 获取查询条件
     *
     * @return
     */
    public static List<Comments> getQueryParams (List<Column> columns) {
        List<Comments> list = new ArrayList<Comments>();
        Comments comments = null;
        for (Column l : columns) {
            comments = l.getComments();
            if (comments != null && comments.getSearch() != null && comments.getSearch()) {
                if (comments.getName() == null || "".equals(comments.getName())) {
                    comments.setName(l.getColumn());
                }
                comments.setCondit(l.getColumn() + "_like");
                list.add(comments);
            }
        }
        return list;
    }

}
