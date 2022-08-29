/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午7:15:34
 */
package plugin.code;

import jdbc.DBUtils;
import model.Table;
import org.apache.maven.plugin.logging.Log;
import plugin.Config;
import utils.DateUtils;
import utils.DocumentHandler;
import utils.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 代码生成 </p>
 *
 * @author: Liu Shilei
 * @Date: 2016年9月28日 下午7:15:34
 */
public class Engine {
    /**
     * 日志
     */
    private Log log;

    public Engine (Log log) {
        this.log = log;
    }

    private Engine () {
    }

    public void engine (String javaPath, String resourcePath) {
        // 获取模版参数
        Map<String, Object> map = setParamMap();

        // Java文件生成
        fileCreateHandler(map, javaPath, "Controller");
        fileCreateHandler(map, javaPath, "Model");
        fileCreateHandler(map, javaPath, "Service");
        fileCreateHandler(map, javaPath, "ServiceImpl");
        fileCreateHandler(map, javaPath, "Mapper");
        fileCreateHandler(map, javaPath, "Dao");

        // 静态资源文件生成-暂时不需要
        // createMenuXML(resourcePath);
        /*
        modelJs(javaPath);
        htmlList(javaPath);
        htmlSee(javaPath);
        htmlAdd(javaPath);
        htmlModify(javaPath);*/
    }

    /**
     * 参数封装
     * @return
     */
    private Map<String, Object> setParamMap () {
        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        Map<String, Object> map = new HashMap<>();
        // 代码模版参数封装
        // 公司
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        // 方法功能描述
        map.put("HRY_FUNCTION_DESC", Config.config.getProperty("HRY_FUNCTION_DESC"));
        // 表描述
        map.put("HRY_TABLE_NAME", Config.config.getProperty("HRY_TABLE_NAME"));
        // 包路径
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        // 实体名
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // 实体名转全小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 实体名首字母小写
        map.put("HRY_MODEL_SMALL", ResourceUtils.getModelFLC());
        // RequestMapping的值
        map.put("RequestMapping", ResourceUtils.getRequestMapping());
        // RequestMapping去掉model的值
        map.put("RequestMappingPR", ResourceUtils.getPath());
        // 表名
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        // 作者
        map.put("codeAuth", Config.config.getProperty("HRY_AUTH"));
        // 时间
        map.put("codeDate", DateUtils.getDate());
        // 表字段
        map.put("columns", tabel.getColumns());
        // 表字段类型
        map.put("columnsType", tabel.getColumnsType());
        return map;
    }

    // =============================================Java文件开始=======================================================================================================================================================

    /**
     * <p> 生成对应的java文件 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void fileCreateHandler (Map<String, Object> map, String javaPath, String type) {
        log.info("*****************************生成" + type + "**********************************");
        DocumentHandler documentHandler = new DocumentHandler("");
        StringBuilder ftlPath = new StringBuilder();
        StringBuilder javaName = new StringBuilder();
        javaName.append(javaPath)
                .append(File.separator)
                .append(Config.config.getProperty("HRY_JAVAPACKAGE"))
                .append(File.separator);
        switch (type) {
            case "Controller":
                ftlPath.append("/template/controller.ftl");
                // 生成文件路径
                javaName.append("controller").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append("Controller.java");
                break;
            case "Model":
                ftlPath.append("/template/model.ftl");
                javaName.append("model").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append(".java");
                break;
            case "Service":
                ftlPath.append("/template/service.ftl");
                javaName.append("service").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append("Service.java");
                break;
            case "ServiceImpl":
                ftlPath.append("/template/serviceimpl.ftl");
                javaName.append("service").append(File.separator).append("impl").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append("ServiceImpl.java");
                break;
            case "Mapper":
                ftlPath.append("/template/mapper.ftl");
                javaName.append("mapper").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append("Mapper.xml");
                break;
            case "Dao":
                ftlPath.append("/template/dao.ftl");
                javaName.append("dao").append(File.separator).append(Config.config.getProperty("HRY_MODEL")).append("Dao.java");
                break;
            default:
                break;
        }
        log.info(javaName.toString());
        documentHandler.createOrderAndDown(map, javaName.toString(), ftlPath.toString());

        log.info("*****************************************************************************");
    }

    // =============================================Java文件结束=======================================================================================================================================================

    // =============================================页面文件开始=======================================================================================================================================================

    /**
     * <p> 生成js文件 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void modelJs (String javaPath) {
        log.info("***************************生成JavaSrcipt**************************************");

        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        // 带点的包名HRY_.manage.user
        Map<String, Object> map = new HashMap<>();
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // model转小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 项目名称
        map.put("HRY_PROJECT", ResourceUtils.getProjectName());
        // model首字母大写
        map.put("HRY_MODEL_FUC", ResourceUtils.getModelFUC());
        map.put("requestMapping", ResourceUtils.getRequestMapping());
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        map.put("codeVersion", Config.config.getProperty("HRY_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        // 字段
        map.put("columns", tabel.getColumns());
        // 字段类型
        map.put("columnsType", tabel.getColumnsType());
        map.put("RequestMappingPR", ResourceUtils.getRequestMappingPR().substring(1));
        map.put("include_base", "<#include \"/base/base.ftl\">");
        map.put("getPath", ResourceUtils.getPath());

        DocumentHandler documentHandler = new DocumentHandler("");

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath += "webapp" + File.separator + "static" + File.separator + "src" + File.separator + "js" + (ResourceUtils.getPath().replace("/", File.separator));

        String htmlName = modelJsPath + Config.config.getProperty("HRY_MODEL") + ".js";
        log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "/template/modelJs.ftl");

        log.info("************************************************************************");
    }

    /**
     * <p> 生成列表页面 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void htmlList (String javaPath) {
        log.info("***************************生成htmlList**************************************");

        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        //带点的包名HRY_.manage.user
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // model转小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 项目名称
        map.put("HRY_PROJECT", ResourceUtils.getProjectName());
        // model首字母大写
        map.put("HRY_MODEL_FUC", ResourceUtils.getModelFUC());
        map.put("requestMapping", ResourceUtils.getRequestMapping());
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        map.put("codeVersion", Config.config.getProperty("HRY_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        // 字段
        map.put("columns", tabel.getColumns());
        // 字段类型
        map.put("columnsType", tabel.getColumnsType());
        map.put("RequestMappingPR", ResourceUtils.getRequestMappingPR().substring(1));
        map.put("include_base", "<#include \"/base/base.ftl\">");
        map.put("getPath", ResourceUtils.getPath());
        map.put("ctx", "${ctx}");
        //查询条件
        map.put("params", ResourceUtils.getQueryParams(tabel.getColumns()));
        DocumentHandler documentHandler = new DocumentHandler("");

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath += "webapp" + File.separator + "WEB-INF" + File.separator + "view" + (ResourceUtils.getPath().replace("/", File.separator));

        String htmlName = modelJsPath + ResourceUtils.getModelLC() + "list.ftl";
        log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "/template/htmlList.ftl");

        log.info("************************************************************************");
    }

    /**
     * <p> 生成查看页面 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void htmlSee (String javaPath) {
        log.info("***************************生成htmlSee**************************************");

        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        //带点的包名HRY_.manage.user
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // model转小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 项目名称
        map.put("HRY_PROJECT", ResourceUtils.getProjectName());
        // model首字母大写
        map.put("HRY_MODEL_FUC", ResourceUtils.getModelFUC());
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        map.put("codeVersion", Config.config.getProperty("HRY_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        // 字段
        map.put("columns", tabel.getColumns());
        // 字段类型
        map.put("columnsType", tabel.getColumnsType());
        map.put("returnURL", ResourceUtils.getProjectName() + ResourceUtils.getRequestMapping() + "/list/anon");
        map.put("RequestMappingPR", ResourceUtils.getRequestMappingPR().substring(1));
        map.put("include_base", "<#include \"/base/base.ftl\">");
        map.put("seeModel", "${model.");
        map.put("getPath", ResourceUtils.getPath());
        map.put("ctx", "${ctx}");

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("java", "");
        modelJsPath += "webapp" + File.separator + "WEB-INF" + File.separator + "view" + (ResourceUtils.getPath().replace("/", File.separator));

        String htmlName = modelJsPath + ResourceUtils.getModelLC() + "see.ftl";
        log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "/template/htmlSee.ftl");

        log.info("************************************************************************");
    }

    /**
     * <p> 生成添加页面 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void htmlAdd (String javaPath) {
        log.info("***************************生成htmlAdd**************************************");

        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        //带点的包名HRY_.manage.user
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // model转小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 项目名称
        map.put("HRY_PROJECT", ResourceUtils.getProjectName());
        // model首字母大写
        map.put("HRY_MODEL_FUC", ResourceUtils.getModelFUC());
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        map.put("codeVersion", Config.config.getProperty("HRY_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        // 字段
        map.put("columns", tabel.getColumns());
        // 字段类型
        map.put("columnsType", tabel.getColumnsType());
        map.put("returnURL", ResourceUtils.getProjectName() + ResourceUtils.getRequestMapping() + "/list/anon");
        map.put("RequestMappingPR", ResourceUtils.getRequestMappingPR().substring(1));
        map.put("include_base", "<#include \"/base/base.ftl\">");
        map.put("getPath", ResourceUtils.getPath());
        map.put("ctx", "${ctx}");

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("java", "");
        modelJsPath += "webapp" + File.separator + "WEB-INF" + File.separator + "view" + (ResourceUtils.getPath().replace("/", File.separator));

        String htmlName = modelJsPath + ResourceUtils.getModelLC() + "add.ftl";
        log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "/template/htmlAdd.ftl");

        log.info("************************************************************************");
    }

    /**
     * <p> 生成修改页面 </p>
     *
     * @author: Liu Shilei
     * @param: @param javaPath
     * @return: void
     * @Date: 2016年9月29日 下午3:30:36
     * @throws:
     */
    private void htmlModify (String javaPath) {
        log.info("***************************生成htmlModify**************************************");

        Table tabel = DBUtils.getTabel(Config.config.getProperty("HRY_TABLE"));
        //带点的包名HRY_.manage.user
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("HRY_COMPANY", Config.config.getProperty("HRY_COMPANY"));
        map.put("HRY_PACKAGE", Config.config.getProperty("HRY_PACKAGE"));
        map.put("HRY_MODEL", Config.config.getProperty("HRY_MODEL"));
        // model转小写
        map.put("HRY_MODEL_LC", ResourceUtils.getModelLC());
        // 项目名称
        map.put("HRY_PROJECT", ResourceUtils.getProjectName());
        // model首字母大写
        map.put("HRY_MODEL_FUC", ResourceUtils.getModelFUC());
        map.put("HRY_TABLE", Config.config.getProperty("HRY_TABLE"));
        map.put("codeVersion", Config.config.getProperty("HRY_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        // 字段
        map.put("columns", tabel.getColumns());
        // 字段类型
        map.put("columnsType", tabel.getColumnsType());
        map.put("returnURL", ResourceUtils.getProjectName() + ResourceUtils.getRequestMapping() + "/list/anon");
        map.put("RequestMappingPR", ResourceUtils.getRequestMappingPR().substring(1));
        map.put("include_base", "<#include \"/base/base.ftl\">");
        map.put("seeModel", "${model.");
        map.put("getPath", ResourceUtils.getPath());
        map.put("ctx", "${ctx}");

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("java", "");
        modelJsPath += "webapp" + File.separator + "WEB-INF" + File.separator + "view" + (ResourceUtils.getPath().replace("/", File.separator));

        String htmlName = modelJsPath + ResourceUtils.getModelLC() + "modify.ftl";
        log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "/template/htmlModify.ftl");

        log.info("************************************************************************");
    }

    // =============================================页面文件结束=======================================================================================================================================================

}
