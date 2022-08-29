/**
 * Copyright:   互融云
 *
 * @author: Liu Shilei
 * @version: V1.0
 * @Date: 2016年9月28日 下午1:48:32
 */
package plugin.code;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import plugin.Config;

import java.io.File;
import java.util.List;


/**
 * <p> 入口，代码生成前参数的设置 </p>
 *
 * @author: Liu Shilei
 * @Date : 20180611 19:33
 */
@Mojo(name = "code2020")
public class Code2020 extends AbstractMojo {

    /**
     * Maven Project.
     * 使用插件的maven项目
     */
    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    @Override
    public void execute () throws MojoExecutionException, MojoFailureException {

        getLog().info("互融云神奇代码生成器启动中........code2020");
        getLog().info("互融云神奇代码生成器启动中........code2020");
        getLog().info("互融云神奇代码生成器启动中........code2020");

        getLog().info("********************************读取资源*************************************");
        // 读取根目录下的资源 如：hry_admin/src/main下的资源
        List<Resource> resources = project.getResources();

        // 根路径下的资源路径
        String resourcePath = "";

        // 根路径下的java路径
        String javaPath = "";

        // 遍历获得资源路径
        for (Resource resource : resources) {
            if (resource.getDirectory().contains("resources")) {
                resourcePath = resource.getDirectory();
            }
            if (resource.getDirectory().contains("java")) {
                javaPath = resource.getDirectory();
            }
        }

        // 读取生成参数配置信息
        new Config(resourcePath);
        getLog().info("********************************读取完毕*************************************");

        getLog().info("********************************配置参数*************************************");
        paramConfig();
        getLog().info("********************************配置完毕*************************************");

        getLog().info("********************************生成代码*************************************");
        // 生成具体代码
        Engine engine = new Engine(getLog());
        engine.engine(javaPath, resourcePath);
        getLog().info("********************************生成完毕*************************************");

        getLog().info("-----------------------------------------------------------------------------");
        getLog().info("auth = LIUSL");
        getLog().info("version = code2020");
        getLog().info("@ALL，使用中遇到BUG联系刘诗垒！");
        getLog().info("@ALL，使用中遇到BUG联系刘诗垒！");
        getLog().info("@ALL，使用中遇到BUG联系刘诗垒！");
    }

    /**
     * 参数处理
     */
    private void paramConfig() {
        getLog().info("HRY_COMPANY=" + Config.config.getProperty("HRY_COMPANY"));
        getLog().info("HRY_FUNCTION_DESC=" + Config.config.getProperty("HRY_FUNCTION_DESC"));
        getLog().info("HRY_TABLE_NAME=" + Config.config.getProperty("HRY_TABLE_NAME"));
        getLog().info("HRY_PACKAGE=" + Config.config.getProperty("HRY_PACKAGE"));
        getLog().info("HRY_TABLE=" + Config.config.getProperty("HRY_TABLE"));
        getLog().info("HRY_MODEL=" + Config.config.getProperty("HRY_MODEL"));
        getLog().info("HRY_AUTH=" + Config.config.getProperty("HRY_AUTH"));

        // 获取包路径并设置
        Config.config.put("HRY_JAVAPACKAGE", Config.config.getProperty("HRY_PACKAGE").replace(".", File.separator));
        getLog().info("HRY_JAVAPACKAGE=" + Config.config.getProperty("HRY_JAVAPACKAGE"));
    }
}
